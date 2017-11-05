package com.esm.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.esm.dao.IncomeDao;
import com.esm.dao.UserDao;
import com.esm.model.Income;
import com.esm.model.User;
import com.esm.service.IncomeService;
import com.esm.util.Constants;
import com.esm.util.ListView;
import com.esm.util.NumberUtils;
import com.esm.util.PageInfo;
import com.esm.util.StringUtil;

@Service
public class IncomeServiceImpl implements IncomeService{
	
	@Resource
	private IncomeDao incomeDao;
	
	@Resource
	private UserDao userDao;

	@Override
	public JSONObject doCal(String mediaName, Double commissionAmount, String seqExcel, String status) throws Exception {
		JSONObject json = new JSONObject();
		if(StringUtil.isEmpty(mediaName)) {
			throw new Exception("第"+seqExcel+"行的【媒体名称】为空！");
		}
		if(StringUtil.isEmpty(commissionAmount)) {
			throw new Exception("第"+seqExcel+"行的【佣金金额】为空！");
		}
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("accountEq", mediaName);
		List<User> userList = userDao.selectByMap(condition);
		User user = null;
		if(StringUtil.isEmpty(userList)) {
			throw new Exception("根据媒体名称未找到账号为【"+mediaName+"】的用户！");
		}else if(userList.size() > 1) {
			throw new Exception("根据媒体名称找到多个账号为【"+mediaName+"】的用户！");
		}else {
			user = userList.get(0);
		}
		
		Integer type = null;
		if(Constants.INCOME_STATUS_PAID.equals(status)) {//已付款
			type = 1;//用户当前的预期收入
		}else if(Constants.INCOME_STATUS_SETTLEMENT.equals(status)) {//已结算
			type = 2;//用户当前的可结算收入
		}else if(Constants.INCOME_STATUS_CANCEL.equals(status)){
			return json; //失效的不用处理
		}else {
			throw new Exception("订单类型不能识别！");
		}
		doAllocation(user, commissionAmount, type);
		return json;
	}
	
	/**
	 * 佣金递归分配.
	 * @param user
	 * @param commissionAmount
	 * @param type
	 * @throws Exception
	 */
	public void doAllocation(User user,Double commissionAmount,Integer type) throws Exception {
		
		//找用户对应的收入数据.
		Map<String,Object> condition = new HashMap<String,Object>();
		Income income = null;
		condition.clear();
		condition.put("type", type);
		condition.put("userId", user.getId());
		List<Income> incomeList = incomeDao.selectByMap(condition);
		if(StringUtil.isEmpty(incomeList)) {
			income = new Income();
			income.setType(type);
			income.setAmount(0d);
			income.setUserId(user.getId());
			incomeDao.insert(income);
		}else if(incomeList.size() > 1) {
			throw new Exception("用户数据异常，找到用户【"+user.getName()+"】多个相同的收入数据！");
		}else {
			income = incomeList.get(0);
		}
		
		//找上级代理,算分给当前用户的收入.
		Long parentId = user.getParentId();
		Double commissionRate = user.getCommissionRate();//佣金比率
		if(commissionRate == null) {
			throw new Exception("账号【"+user.getAccount()+"】当前未设置佣金比率！");
		}
		if(parentId != null) {
			Double commissionRateToParent = user.getCommissionRateToParent();
			if(commissionRateToParent == null) {
				throw new Exception("账号【"+user.getAccount()+"】当前选择了上级代理，但是未设置给上级代理的分成比率！");
			}
			commissionRate = NumberUtils.subtract(commissionRate, commissionRateToParent);
			
			//计算上级
			User parent = userDao.selectByPrimaryKey(parentId);
			if(1 == type) {//如果是当前用户的预期收入
				type = 3;//那么就变成下级用户提供的预期收入
			}else if(2 == type) {//如果是当前用户的可结算收入
				type =4;//那么就变成下级用户的可结算收入
			}
			Double newAmount = NumberUtils.multiply(commissionAmount, NumberUtils.divide(commissionRateToParent, 100d));
			doAllocation(parent, newAmount, type);
		}
		
		//计算分给该用户的钱
		Double newAmount = NumberUtils.multiply(commissionAmount, NumberUtils.divide(commissionRate,100d));
		income.setAmount(NumberUtils.add(NumberUtils.nullTo0(income.getAmount()), newAmount));
		incomeDao.updateByPrimaryKey(income);
	}
	

	@Override
	public ListView<HashMap<String,Object>> queryByGroup(Map<String, Object> condition, PageInfo pageInfo) {
		// TODO Auto-generated method stub
		ListView<HashMap<String,Object>> listView = new ListView<HashMap<String,Object>>();
		List<HashMap<String, Object>> incomeList = incomeDao.sumByGroup(condition, pageInfo);
		String amountList = null;
		String[] valueArr = null;//0是type 1是amount
		String[] typeAmountArr = null;//每个值都是type_amount
		String type = null;
		String amount = null;
		String key = null;
		for(Map<String,Object> map : incomeList) {
			amountList = StringUtil.objectToString(map.get("amountList"));
			if(StringUtil.isEmpty(amountList)) {
				continue;
			}
			amountList = amountList.replaceAll("\"", "");
			valueArr = amountList.split(",");
			Double settleIncomeTotal = 0d;//可结算收入汇总（用户可结算收入+下级代理分成的可结算收入）
			for(String value:valueArr) {
				if(!value.contains("_")) {
					continue;
				}
				typeAmountArr = value.split("_");
				type = typeAmountArr[0];
				amount = typeAmountArr[1];
				if("1".equals(type)) {//用户当前预期收入
					key = "expectIncome";
				}else if("2".equals(type)) {//用户当前可结算收入
					key = "settleIncome";
					if(!StringUtil.isEmpty(amount)) {
						settleIncomeTotal = NumberUtils.add(settleIncomeTotal, Double.parseDouble(amount));
					}
				}else if("3".equals(type)) {//下级代理提供的当前预期收入
					key = "expectIncomeProvide";
				}else if("4".equals(type)) {//下级代理提供的可结算收入
					key = "settleIncomeProvide";
					if(!StringUtil.isEmpty(amount)) {
						settleIncomeTotal = NumberUtils.add(settleIncomeTotal, Double.parseDouble(amount));
					}
				}
				if(!StringUtil.isEmpty(amount)) {
					amount = new BigDecimal(amount).setScale(2, BigDecimal.ROUND_FLOOR).toString();
				}
				map.put(key, amount);
			}
			map.put("settleIncomeTotal", new BigDecimal(settleIncomeTotal).setScale(2, BigDecimal.ROUND_FLOOR).toString());
		}
		listView.setRows(incomeList);
		listView.setTotalRows(incomeDao.countByGroup(condition));
		return listView;
	}
	
	@Override
	public void doDeal(Long userId, Double amount) throws Exception{
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("userId", userId);
		condition.put("typeListIn2And4", "true");
		List<Income> list = incomeDao.selectByMap(condition);
		if(StringUtil.isEmpty(list)) {
			throw new Exception("数据异常，未从获取到用户【"+userId+"】的收入数据！");
		}else if(list.size() > 2) {
			throw new Exception("数据异常，获取到用户【"+userId+"】的收入数据大于两条！");
		}
		Double rest = amount;
		for(Income income : list) {
			rest = NumberUtils.subtract(income.getAmount(), rest);
			if(rest >= 0) {
				income.setAmount(rest);
				incomeDao.updateByPrimaryKey(income);
				continue;
			}else {
				income.setAmount(0d);
				incomeDao.updateByPrimaryKey(income);
			}
		}
		if(rest < 0) {
			throw new Exception("用户可结算金额不足已扣除提现金额【"+amount+"】");
		}
	}
}
