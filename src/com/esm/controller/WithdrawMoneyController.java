package com.esm.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.esm.model.User;
import com.esm.model.WithdrawMoney;
import com.esm.service.WithdrawMoneyService;
import com.esm.util.ListView;
import com.esm.util.MethodUtil;
import com.esm.util.PageInfo;
import com.esm.util.TransforUtil;

/**
 * =======================================================================
 * Overview : 体系管理controller
 * 
 * Business Rules :
 * 
 * Comments :
 * ======================================================================= 
 * CHGNO              DATE             PROGRAMMER  DESCRIPTION
 * ---------------------- ---------------------------------------
 *  01			  2017-8-21            宁业春              新建
 * ========================================================================
 */

@Controller
@RequestMapping("/withdrawMoney")
public class WithdrawMoneyController {
	
	@Resource
	private WithdrawMoneyService withdrawMoneyService;
	
	/**
	 * 查询提现信息. 
	 * @param request 请求
	 * @param response 返回
	 * @return 返回结果
	 */
	@RequestMapping(value = "/queryByMap")
	@ResponseBody
	public JSONObject queryByMap(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> condition = TransforUtil.transRMapToMap(request.getParameterMap());
		String onlySelf = request.getParameter("onlySelf");
		if("true".equals(onlySelf)) {
			User user = (User) request.getSession().getAttribute("user");
			condition.put("userId", user.getId());
		}
		// 获取查询条件
		PageInfo pageInfo = new PageInfo(Integer.parseInt(request.getParameter("start")), Integer.parseInt(request.getParameter("limit")));
		ListView<User> listView = withdrawMoneyService.queryByMap(condition, pageInfo);
		JSONObject object = MethodUtil.fromObject(listView);
		return object;
	}
	
	
	/**
	 * 保存或更新数据.
	 * @param request  请求数据
	 * @param response 返回数据
	 * @return 返回结果
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	public JSONObject insert(HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject jsonObj = new JSONObject();
		Boolean success = true;
		try{
			WithdrawMoney withdrawMoney = new WithdrawMoney();
			TransforUtil.transFromMapToBean(request.getParameterMap(), withdrawMoney);
			User user = (User) request.getSession().getAttribute("user");
			withdrawMoney.setUserId(user.getId());
			withdrawMoneyService.add(withdrawMoney);
		}catch(Exception e){
			e.printStackTrace();
			success = false;
			jsonObj.put("msg", TransforUtil.deleteExceptionString(e.getMessage()));
		}finally{
			jsonObj.put("success", success);
		}
		return jsonObj;
	}
	
	/**
	 * 结算.
	 * @param request  请求数据
	 * @param response 返回数据
	 * @return 返回结果
	 */
	@RequestMapping(value = "/doDealBatch")
	@ResponseBody
	public JSONObject doDeal(HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject jsonObj = new JSONObject();
		Boolean success = true;
		try{
			String judgeType = request.getParameter("judgeType");
			WithdrawMoney withdrawMoney = new WithdrawMoney();
			TransforUtil.transFromMapToBean(request.getParameterMap(), withdrawMoney);
			User user = (User) request.getSession().getAttribute("user");
			withdrawMoney.setDealBy(user.getId());
			String ids = request.getParameter("ids");
			withdrawMoneyService.doDealBatch(judgeType, withdrawMoney,ids);
		}catch(Exception e){
			e.printStackTrace();
			success = false;
			jsonObj.put("msg", TransforUtil.deleteExceptionString(e.getMessage()));
		}finally{
			jsonObj.put("success", success);
		}
		return jsonObj;
	}
	
}
