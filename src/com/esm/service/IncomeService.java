package com.esm.service;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.esm.model.Income;
import com.esm.util.ListView;
import com.esm.util.PageInfo;

public interface IncomeService{

    /**
     * 分页查询所有订单数据.
     * @param condition
     * @return
     */
    public ListView<HashMap<String,Object>> queryByGroup(Map<String,Object> condition, PageInfo pageInfo);

    /**
     * 计算佣金.
     * @param mediaName
     * @param commissionAmount
     * @return
     * @throws Exception 
     */
	JSONObject doCal(String mediaName, Double commissionAmount, String seqExcel, String status) throws Exception;

	/**
	 * 扣除可结算金额.
	 * @param userId
	 * @param amount
	 * @throws Exception
	 */
	void doDeal(Long userId, Double amount) throws Exception;
}
