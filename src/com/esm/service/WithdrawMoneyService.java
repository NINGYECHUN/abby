package com.esm.service;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.esm.model.User;
import com.esm.model.WithdrawMoney;
import com.esm.util.ListView;
import com.esm.util.PageInfo;

public interface WithdrawMoneyService{

	/**
	 * 添加提现申请.
	 * @param user
	 * @throws NoSuchAlgorithmException 
	 */
    public JSONObject add(WithdrawMoney withdrawMoney) throws Exception;
    
    /**
     * 分页查询所有用户数据.
     * @param condition
     * @return
     */
    public ListView<User> queryByMap(Map<String,Object> condition, PageInfo pageInfo);
    
    /**
     * 结算.
     * @param judgeType 1-通过 0-驳回
     * @param withdrawMoney
     * @return
     * @throws Exception
     */
    public JSONObject doDeal(String judgeType, WithdrawMoney withdrawMoney) throws Exception;

    /**
     * 批量结算.
     * @param judgeType
     * @param withdrawMoney
     * @param ids
     * @return
     * @throws Exception
     */
	JSONObject doDealBatch(String judgeType, WithdrawMoney withdrawMoney, String ids) throws Exception;

}
