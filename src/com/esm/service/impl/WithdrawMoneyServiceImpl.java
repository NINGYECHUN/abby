package com.esm.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.esm.dao.WithdrawMoneyDao;
import com.esm.model.WithdrawMoney;
import com.esm.service.WithdrawMoneyService;
import com.esm.util.ListView;
import com.esm.util.PageInfo;
import com.esm.util.TransforUtil;

@Service
public class WithdrawMoneyServiceImpl implements WithdrawMoneyService{
	
	@Resource
	private WithdrawMoneyDao withdrawMoneyDao;

	@Override
	public JSONObject add(WithdrawMoney withdrawMoney) throws Exception {
		JSONObject rs = new JSONObject();
		withdrawMoney.setStatus(1);
		withdrawMoney.setWithdrawDate(new Date());
		withdrawMoneyDao.insert(withdrawMoney);
		return rs;
	}

	@Override
	public ListView queryByMap(Map<String, Object> condition, PageInfo pageInfo) {
		// TODO Auto-generated method stub
		ListView listView = new ListView();
		List<WithdrawMoney> list = withdrawMoneyDao.queryByMap(condition, pageInfo);
		for(WithdrawMoney bean: list) {
			bean.setStatusName(TransforUtil.transWithdrawMoneyStatus(bean.getStatus()));
		}
		listView.setRows(list);
		listView.setTotalRows(withdrawMoneyDao.countByMap(condition));
		return listView;
	}
	
	@Override
	public JSONObject doDealBatch(String judgeType, WithdrawMoney withdrawMoney,String ids) throws Exception {
		
		String[] idArr = ids.split(",");
		for(String id : idArr) {
			withdrawMoney.setId(Long.parseLong(id));
			doDeal(judgeType, withdrawMoney);
		}
		return null;
	}

	@Override
	public JSONObject doDeal(String judgeType, WithdrawMoney withdrawMoney) throws Exception {
		
		Integer status = null;
		if("1".equals(judgeType)) {//通过
			status = 2;
		}else if("0".equals(judgeType)) {//驳回
			status = 3;
		}else {
			throw new Exception("结算异常，系统不能判断是通过还是驳回！");
		}
		WithdrawMoney entity = withdrawMoneyDao.selectByPrimaryKey(withdrawMoney.getId());
		if(!Integer.valueOf("1").equals(entity.getStatus())) {//结算中
			throw new Exception("该提现申请已处理，不能再次处理，请先刷新下页面！");
		}
		entity.setDealDate(new Date());
		entity.setDealBy(withdrawMoney.getDealBy());
		entity.setRmkAdmin(withdrawMoney.getRmkAdmin());
		entity.setStatus(status);
		withdrawMoneyDao.updateByPrimaryKey(entity);
		return null;
	}
	
}
