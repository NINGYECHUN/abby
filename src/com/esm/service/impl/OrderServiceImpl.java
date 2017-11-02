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
import com.esm.service.OrderService;
import com.esm.util.ListView;
import com.esm.util.PageInfo;
import com.esm.util.PoiUtil;
import com.esm.util.StringUtil;
import com.esm.util.TransforUtil;

@Service
public class OrderServiceImpl implements OrderService{
	
	@Resource
	private OrderDao orderDao;

	@Override
	public JSONObject doImport(MultipartFile file, HttpServletRequest request,HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		String[] headers = {"createDate","B","goodsName","D","E","F","qty","price","status","J","incomeRate",
				"dividedRate","payment","effectEstimate","setAmount","incomeEstimate","Q","commissionRate",
				"S","T","subsidyAmount","V","W","X","orderNo","Z","mediaId","mediaName","adsenseId",
				"adsenseName"};
		User user = (User) request.getSession().getAttribute("user");
		List<Map<String,Object>> list = PoiUtil.readExcel(file, headers, 2, 1);
		Order order = null;
		String orderNo = null;
		Integer count = null;
		Map<String,Object> condition = new HashMap<String,Object>();
		for(Map<String,Object> map : list) {
			order = new Order();
			orderNo = StringUtil.objectToString(map.get("orderNo")); 
			condition.clear();
			condition.put("orderNoEq", orderNo);
			count = orderDao.countByMap(condition);
			if(count > 0) {
				continue;
			}
			TransforUtil.transFromMapToBean(TransforUtil.transMapToRMap(map), order);
			order.setImportDate(new Date());
			order.setImportBy(user.getId());
			orderDao.insert(order);
		}
		return json;
	}
	

	@Override
	public ListView queryByMap(Map<String, Object> condition, PageInfo pageInfo) {
		// TODO Auto-generated method stub
		ListView listView = new ListView();
		listView.setRows(orderDao.queryByMap(condition, pageInfo));
		listView.setTotalRows(orderDao.countByMap(condition));
		return listView;
	}
	
}
