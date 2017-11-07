package com.esm.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.esm.dao.OrderDao;
import com.esm.model.Order;
import com.esm.model.User;
import com.esm.service.IncomeService;
import com.esm.service.OrderService;
import com.esm.util.Constants;
import com.esm.util.ListView;
import com.esm.util.NumberUtils;
import com.esm.util.PageInfo;
import com.esm.util.PoiUtil;
import com.esm.util.StringUtil;
import com.esm.util.TransforUtil;
import com.sun.corba.se.impl.orbutil.closure.Constant;

@Service
public class OrderServiceImpl implements OrderService{
	
	@Resource
	private OrderDao orderDao;
	
	@Resource
	private IncomeService incomeService;

	@Override
	public JSONObject doImport(MultipartFile file, HttpServletRequest request,HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		String[] headers = {"createDate","B","goodsName","D","E","F","qty","price","status","J","incomeRate",
				"dividedRate","payment","effectEstimate","setAmount","incomeEstimate","Q","commissionRate",
				"commissionAmount","T","subsidyAmount","V","W","X","orderNo","Z","mediaId","mediaName","adsenseId",
				"adsenseName"};
		User user = (User) request.getSession().getAttribute("user");
		List<Map<String,Object>> list = PoiUtil.readExcel(file, headers, 2, 1);
		Order order = null;
		String orderNo = null;
		List<Order> orderList = null;
		Double toCalAmount = null;//将计算的金额
		Map<String,Object> condition = new HashMap<String,Object>();
		for(Map<String,Object> map : list) {
			order = new Order();
			orderNo = StringUtil.objectToString(map.get("orderNo")); 
			toCalAmount = NumberUtils.objectToDouble(map.get("commissionAmount"));
			
			if(orderNo != null) {
				orderNo = orderNo.replaceAll("\"", "").replaceAll("\n", "").trim();
			}
			condition.clear();
			condition.put("orderNoEq", orderNo);
			orderList = orderDao.selectByMap(condition);
			if(orderList.size() >= 2) {
				throw new Exception("数据异常，根据订单号【"+orderNo+"】找到2条以上数据！");
			}else if(orderList.size() == 1) {
				//判断当前订单状态是否与之前的相同
				String status = StringUtil.objectToString(map.get("status"));
				Order oldOrder = orderList.get(0);
				String oldStatus = oldOrder.getStatus();
				if(status.equals(oldStatus)) {
					continue;
				}else {
					//状态不一样，删除之前的订单
					orderDao.deleteByPrimaryKey(oldOrder.getId());
					
					if(Constants.INCOME_STATUS_SETTLEMENT.equals(oldStatus) 
							&& Constants.INCOME_STATUS_CANCEL.equals(status)) {//原订单是已结算，新订单是取消,那么佣金金额变成负数
						toCalAmount = -oldOrder.getCommissionAmount();
					}
				}
			}
			
			TransforUtil.transFromMapToBean(TransforUtil.transMapToRMap(map), order);
			order.setOrderNo(orderNo);
			order.setImportDate(new Date());
			order.setImportBy(user.getId());
			orderDao.insert(order);
			
			if(Constants.INCOME_STATUS_PAID.equals(order.getStatus())) {
				toCalAmount = order.getEffectEstimate();
			}
			
			incomeService.doCal(order.getMediaName(), toCalAmount, StringUtil.objectToString(map.get("SEQ_EXCEL")), order.getStatus());
		}
		return json;
	}
	

	@Override
	public ListView queryByMap(Map<String, Object> condition, PageInfo pageInfo) {
		// TODO Auto-generated method stub
		ListView listView = new ListView();
		List<Order> list = orderDao.queryByMap(condition, pageInfo);
		Double commissionRateUser = null;
		for(Order order: list) {
			commissionRateUser = order.getCommissionRateUser();
			if(order.getCommissionRateToParent() != null) {
				commissionRateUser = NumberUtils.subtract(commissionRateUser, order.getCommissionRateToParent());
			}
			if(commissionRateUser != null) {
				commissionRateUser = NumberUtils.divide(commissionRateUser,100d);
				order.setEffectEstimateUser(NumberUtils.keep2Decimal(NumberUtils.multiply(order.getEffectEstimate(), commissionRateUser)));
				order.setCommissionAmountUser(NumberUtils.keep2Decimal(NumberUtils.multiply(order.getCommissionAmount(), commissionRateUser)));
			}
		}
		listView.setRows(list);
		listView.setTotalRows(orderDao.countByMap(condition));
		return listView;
	}
	
}
