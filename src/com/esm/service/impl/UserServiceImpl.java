package com.esm.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.esm.dao.UserDao;
import com.esm.model.User;
import com.esm.service.UserService;
import com.esm.util.ListView;
import com.esm.util.MethodUtil;
import com.esm.util.PageInfo;
import com.esm.util.StringUtil;

@Service
public class UserServiceImpl implements UserService{
	
	@Resource
	private UserDao userDao;

	@Override
	public JSONObject add(User user) throws Exception {
		
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("accountEq", user.getAccount());
		if(userDao.countByMap(condition) > 0){
			throw new Exception("账号【"+user.getAccount()+"】已存在！");
		};
		JSONObject rs = new JSONObject();
		user.setPassword(MethodUtil.toMD5(user.getPassword()));
		userDao.insert(user);
		return rs;
	}
	
	@Override
	public JSONObject update(User user) throws Exception {
		// TODO Auto-generated method stub
		JSONObject rs = new JSONObject();
		User entity = userDao.selectByPrimaryKey(user.getId());
		entity.setAlipay(user.getAlipay());
		entity.setCommissionRate(user.getCommissionRate());
		entity.setIsAdmin(user.getIsAdmin());
		entity.setName(user.getName());
		entity.setPhone(user.getPhone());
		entity.setPid(user.getPid());
		userDao.updateByPrimaryKey(entity);
		return rs;
	}
	
	@Override
	public JSONObject updateLoginTime(User user) throws Exception {
		// TODO Auto-generated method stub
		JSONObject rs = new JSONObject();
		User entity = userDao.selectByPrimaryKey(user.getId());
		entity.setLastLoginTime(user.getLastLoginTime());
		userDao.updateByPrimaryKey(entity);
		return rs;
	}


	@Override
	public ListView queryByMap(Map<String, Object> condition, PageInfo pageInfo) {
		// TODO Auto-generated method stub
		ListView listView = new ListView();
		listView.setRows(userDao.queryByMap(condition, pageInfo));
		listView.setTotalRows(userDao.countByMap(condition));
		return listView;
	}

	@Override
	public JSONObject changePassword(Long userId, String oldPassword, String newPassword) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject doResetPassword(Long userId) throws Exception {
		// TODO Auto-generated method stub
		JSONObject rs = new JSONObject();
		User user  = userDao.selectByPrimaryKey(userId);
		user.setPassword(MethodUtil.toMD5("123456"));
		userDao.updateByPrimaryKey(user);
		return rs;
	}
	
	/**
	 * 通过账号密码查找用户
	 * @param name
	 * @param password
	 * @return
	 */
	@Override
	public User checkIfExist(String account,String password){
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("accountEq", account);
		condition.put("passwordEq", password);
		List<User> userList =  userDao.selectByMap(condition);
		return StringUtil.isEmpty(userList)?null:userList.get(0);
	}
}
