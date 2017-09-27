package com.esm.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.esm.model.User;
import com.esm.service.UserService;
import com.esm.util.MethodUtil;
import com.esm.util.StringUtil;

/**
 */
@Controller
@RequestMapping("sys")
public class SystemController {

	@Resource
	UserService userService;
	
    @RequestMapping("login")
    public void login(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String account = request.getParameter("account");
    	String password = request.getParameter("password");
    	String msg = null;
    	if(StringUtil.isEmpty(account) || StringUtil.isEmpty(password)){
    		msg = "账号或密码不能为空！";
    		MethodUtil.writeJSON(response, msg);
    		return;
    	}
    	User user = userService.checkIfExist(account, MethodUtil.toMD5(password));
    	if(user == null){
    		msg = "用户或密码错误！";
    		MethodUtil.writeJSON(response, msg);
    		return;
    	}
    	user.setLastLoginTime(new Date());
    	userService.updateLoginTime(user);
    	request.getSession().setAttribute("user", user);
    }
    
    @RequestMapping("toHome")
    public String toHome(HttpServletRequest request, HttpServletResponse response){
    	User user = (User) request.getSession().getAttribute("user");
    	if(user != null){
    		if(Integer.valueOf(1).equals(user.getIsAdmin())){
    			return "homeAdmin";
    		}else{
    			return "home";
    		}
    	}else{
    		return "";
    	}
    }
}
