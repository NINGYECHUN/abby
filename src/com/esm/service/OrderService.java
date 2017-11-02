package com.esm.service;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.esm.model.Order;
import com.esm.model.User;
import com.esm.util.ListView;
import com.esm.util.PageInfo;

public interface OrderService{

	/**
	 * 导入订单.
	 * @param user
	 * @throws NoSuchAlgorithmException 
	 */
    public JSONObject doImport(MultipartFile file, HttpServletRequest request,HttpServletResponse response) throws Exception;
    
    /**
     * 分页查询所有订单数据.
     * @param condition
     * @return
     */
    public ListView<Order> queryByMap(Map<String,Object> condition, PageInfo pageInfo);
    
  

}
