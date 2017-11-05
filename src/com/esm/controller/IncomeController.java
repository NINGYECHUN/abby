package com.esm.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.esm.model.Income;
import com.esm.model.User;
import com.esm.service.IncomeService;
import com.esm.service.UserService;
import com.esm.util.ListView;
import com.esm.util.MethodUtil;
import com.esm.util.PageInfo;
import com.esm.util.StringUtil;
import com.esm.util.TransforUtil;

/**
 * =======================================================================
 * Overview : 收入controller
 * 
 * Business Rules :
 * 
 * Comments :
 * ======================================================================= 
 * CHGNO              DATE             PROGRAMMER  DESCRIPTION
 * ---------------------- ---------------------------------------
 *  01			  2017-11-21            宁业春              新建
 * ========================================================================
 */

@Controller
@RequestMapping("/income")
public class IncomeController {
	
	@Resource
	private IncomeService incomeService;
	
	/**
	 * 查询用户信息 
	 * @param request 请求
	 * @param response 返回
	 * @return 返回结果
	 */
	@RequestMapping(value = "/queryByGroup")
	@ResponseBody
	public JSONObject queryByGroup(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> condition = TransforUtil.transRMapToMap(request.getParameterMap());
		// 获取查询条件
		PageInfo pageInfo = new PageInfo(Integer.parseInt(request.getParameter("start")), Integer.parseInt(request.getParameter("limit")));
		ListView<HashMap<String,Object>> listView = incomeService.queryByGroup(condition, pageInfo);
		JSONObject object = MethodUtil.fromObject(listView);
		return object;
	}
	
	@RequestMapping(value = "/selectCurrUserIncome")
	@ResponseBody
	public JSONObject selectCurrUserIncome(HttpServletRequest request,
			HttpServletResponse response) {
		// 获取查询条件
		User user = (User) request.getSession().getAttribute("user");
		PageInfo pageInfo = new PageInfo(0,1);
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("userId", user.getId());
		ListView<HashMap<String,Object>> listView = incomeService.queryByGroup(condition, pageInfo);
		JSONObject object = MethodUtil.fromObject(listView);
		return object;
	}
}
