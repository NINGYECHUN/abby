package com.esm.service;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.esm.model.User;
import com.esm.util.ListView;
import com.esm.util.PageInfo;

public interface UserService{

	/**
	 * 添加用户.
	 * @param user
	 * @throws NoSuchAlgorithmException 
	 */
    public JSONObject add(User user) throws Exception;
    
	/**
	 * 更新用户.
	 * @param user
	 * @throws NoSuchAlgorithmException 
	 */
    public JSONObject update(User user) throws Exception;
    
	/**
	 * 更新用户登录时间.
	 * @param user
	 * @throws NoSuchAlgorithmException 
	 */
    public JSONObject updateLoginTime(User user) throws Exception;
    
    /**
     * 分页查询所有用户数据.
     * @param condition
     * @return
     */
    public ListView<User> queryByMap(Map<String,Object> condition, PageInfo pageInfo);
    
    /**
     * 变更密码.
     * @param userId 用户id
     * @param oldPassword 原密码
     * @param newPassword 新密码
     */
    public JSONObject changePassword(Long userId,String oldPassword, String newPassword);

    /**
     * 重置密码.
     * @param userId
     * @return
     * @throws Exception 
     */
    public JSONObject doResetPassword(Long userId) throws Exception;

    /**
     * 查看用户是否存在.
     * @param name
     * @param password
     * @return
     */
	User checkIfExist(String name, String password);

}
