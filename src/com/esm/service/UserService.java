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
     * @param newPassword1 新密码1
     * @param newPassword2 新密码2 
     * @throws Exception 
     */
    public JSONObject doChangePassword(Long userId, String oldPassword, String newPassword1, String newPassword2) throws Exception;

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

	/**
	 * 设置收款设置项.
	 * @param userId
	 * @param alipay
	 * @param gatheringName
	 * @return
	 * @throws Exception
	 */
	JSONObject doGatheringSetting(Long userId, String alipay, String gatheringName) throws Exception;

	/**
	 * 通过用户id获取用户信息.
	 * @param userId
	 * @return
	 */
	User getUserById(Long userId);

}
