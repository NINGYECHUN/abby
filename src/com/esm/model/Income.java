package com.esm.model;

/**
* =======================================================================
* Overview : bean
* Business Rules :
* Comments :
* =======================================================================
* CHGNO              DATE             PROGRAMMER  DESCRIPTION
* ---------------------- ---------------------------------------
*  01			       2017-11-05             宁业春              新建
* ========================================================================.
*/
public class Income {
    /**
    主键id.
    */
    private Long id;

    /**
    用户id.
    */
    private Long userId;

    /**
    类型 1-用户当前预期收入 2-用户当前可结算收入 3-下级代理提供的预期收入 4-下级代理提供的可结算收入.
    */
    private Integer type;

    /**
    收入.
    */
    private Double amount;
    
    /**
     * 账号.
     */
    private String account;

    /**
    获取id(主键id)的当前值.
    @return 得到的id的值
    */
    public Long getId() {
        return id;
    }

    /**
    给id(主键id)赋予新的值.
    @param id 赋予id新的值
    */
    public void setId(Long id) {
        this.id = id;
    }

    /**
    获取userId(用户id)的当前值.
    @return 得到的userId的值
    */
    public Long getUserId() {
        return userId;
    }

    /**
    给userId(用户id)赋予新的值.
    @param userId 赋予userId新的值
    */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
    获取type(类型 1-用户当前预期收入 2-用户当前可结算收入 3-下级代理提供的预期收入 4-下级代理提供的可结算收入)的当前值.
    @return 得到的type的值
    */
    public Integer getType() {
        return type;
    }

    /**
    给type(类型 1-用户当前预期收入 2-用户当前可结算收入 3-下级代理提供的预期收入 4-下级代理提供的可结算收入)赋予新的值.
    @param type 赋予type新的值
    */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
    获取amount(收入)的当前值.
    @return 得到的amount的值
    */
    public Double getAmount() {
        return amount;
    }

    /**
    给amount(收入)赋予新的值.
    @param amount 赋予amount新的值
    */
    public void setAmount(Double amount) {
        this.amount = amount;
    }

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
}