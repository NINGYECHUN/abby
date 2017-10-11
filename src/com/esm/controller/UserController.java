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
import com.esm.service.UserService;
import com.esm.util.ListView;
import com.esm.util.MethodUtil;
import com.esm.util.PageInfo;
import com.esm.util.StringUtil;
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
@RequestMapping("/user")
public class UserController {
	
	@Resource
	private UserService userService;
	
	/**
	 * 查询用户信息 
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
		ListView<User> listView = userService.queryByMap(condition, pageInfo);
		JSONObject object = MethodUtil.fromObject(listView);
		return object;
	}
	
	
	/**
	 * 保存或更新数据.
	 * @param request  请求数据
	 * @param response 返回数据
	 * @return 返回结果
	 */
	@RequestMapping(value = "/insertOrUpdate")
	@ResponseBody
	public JSONObject insert(HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject jsonObj = new JSONObject();
		Boolean success = true;
		try{
			User user = new User();
			TransforUtil.transFromMapToBean(request.getParameterMap(), user);
			if(StringUtil.isEmpty(user.getId())){
				user.setPassword("123456");
				userService.add(user);
			}else{
				userService.update(user);
			}
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
	 * 重置用户密码.
	 * @param request  请求数据
	 * @param response 返回数据
	 * @return 返回结果
	 */
	@RequestMapping(value = "/doResetPassword")
	@ResponseBody
	public JSONObject doResetPassword(HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject jsonObj = new JSONObject();
		Boolean success = true;
		try{
			String id = request.getParameter("id");
			userService.doResetPassword(Long.parseLong(id));
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
	 * 用户变更密码.
	 * @param request  请求数据
	 * @param response 返回数据
	 * @return 返回结果
	 */
	@RequestMapping(value = "/changePassword")
	@ResponseBody
	public JSONObject doChangePassword(HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject jsonObj = new JSONObject();
		Boolean success = true;
		try{
			User user = (User) request.getSession().getAttribute("user");
			String oldPassword = request.getParameter("oldPassword");
			String newPassword1 = request.getParameter("newPassword1");
			String newPassword2 = request.getParameter("newPassword2");
			userService.doChangePassword(user.getId(), oldPassword, newPassword1, newPassword2);
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
