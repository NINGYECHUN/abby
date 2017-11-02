package com.esm.controller;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.esm.model.Order;
import com.esm.model.User;
import com.esm.service.OrderService;
import com.esm.service.UserService;
import com.esm.util.ListView;
import com.esm.util.MethodUtil;
import com.esm.util.PageInfo;
import com.esm.util.StringUtil;
import com.esm.util.TransforUtil;
import com.google.gson.JsonObject;

/**
 * =======================================================================
 * Overview : 订单管理controller
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
@RequestMapping("/order")
public class OrderController {
	
	@Resource
	private OrderService orderService;
	
	/**
	 * 查询订单信息 
	 * @param request 请求
	 * @param response 返回
	 * @return 返回结果
	 */
	@RequestMapping(value = "/queryByMap")
	@ResponseBody
	public JSONObject queryByMap(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> condition = TransforUtil.transRMapToMap(request.getParameterMap());
		// 获取查询条件
		PageInfo pageInfo = new PageInfo(Integer.parseInt(request.getParameter("start")), Integer.parseInt(request.getParameter("limit")));
		ListView<Order> listView = orderService.queryByMap(condition, pageInfo);
		JSONObject object = MethodUtil.fromObject(listView);
		return object;
	}
	
	@RequestMapping(value = "/doImport")  
    public void doImport(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
		JSONObject  json = new JSONObject();
		try {
		 	json = orderService.doImport(file, request, response);
		    json.put("success", true);    
		} catch (Exception e) {
			json.put("success", false);
			json.put("msg", StringUtil.deleteExceptionString(e.getMessage()));
		}finally {
			try {
				response.setContentType("text/html;charset=utf-8");
				response.getWriter().write(json.toString());
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
    } 


}
