package com.esm.model;

import java.util.Date;

/**
* =======================================================================
* Overview : bean
* Business Rules :
* Comments :
* =======================================================================
* CHGNO              DATE             PROGRAMMER  DESCRIPTION
* ---------------------- ---------------------------------------
*  01			       2017-10-12             宁业春              新建
* ========================================================================.
*/
public class WithdrawMoney {
    /**
    主键id.
    */
    private Long id;

    /**
    用户id.
    */
    private Long userId;

    /**
    提现金额.
    */
    private Double moneyQty;

    /**
    提现时间.
    */
    private Date withdrawDate;

    /**
    状态 1-结算中 2-结算完成 3-结算失败.
    */
    private Integer status;
    
    /**
    状态名称 结算中|结算完成|结算失败.
    */
    private String statusName;
    
    /**
    用户备注.
    */
    private String rmkUser;

    /**
    管理员备注.
    */
    private String rmkAdmin;
    
    /**
     * 结算时间.
     */
    private Date dealDate;
    
    /**
     * 结算人.
     */
    private Long dealBy;
    
    /**
     * 用户账号.
     */
    private String account;

    /**
     * 用户名称.
     */
    private String name;
    
    /**
     * 支付宝账号.
     */
    private String alipay;
    
    /**
     * 收款人名称.
     */
    private String gatheringName;

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
    获取moneyQty(提现金额)的当前值.
    @return 得到的moneyQty的值
    */
    public Double getMoneyQty() {
        return moneyQty;
    }

    /**
    给moneyQty(提现金额)赋予新的值.
    @param moneyQty 赋予moneyQty新的值
    */
    public void setMoneyQty(Double moneyQty) {
        this.moneyQty = moneyQty;
    }

    /**
    获取withdrawDate(提现时间)的当前值.
    @return 得到的withdrawDate的值
    */
    public Date getWithdrawDate() {
        return withdrawDate;
    }

    /**
    给withdrawDate(提现时间)赋予新的值.
    @param withdrawDate 赋予withdrawDate新的值
    */
    public void setWithdrawDate(Date withdrawDate) {
        this.withdrawDate = withdrawDate;
    }

    /**
    获取status(状态 1-结算中 2-结算完成 3-结算失败)的当前值.
    @return 得到的status的值
    */
    public Integer getStatus() {
        return status;
    }

    /**
    给status(状态 1-结算中 2-结算完成 3-结算失败)赋予新的值.
    @param status 赋予status新的值
    */
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    /**
    获取rmkUser(用户备注)的当前值.
    @return 得到的rmkUser的值
    */
    public String getRmkUser() {
        return rmkUser;
    }

    /**
    给rmkUser(用户备注)赋予新的值.
    @param rmkUser 赋予rmkUser新的值
    */
    public void setRmkUser(String rmkUser) {
        this.rmkUser = rmkUser;
    }

    /**
    获取rmkAdmin(管理员备注)的当前值.
    @return 得到的rmkAdmin的值
    */
    public String getRmkAdmin() {
        return rmkAdmin;
    }

    /**
    给rmkAdmin(管理员备注)赋予新的值.
    @param rmkAdmin 赋予rmkAdmin新的值
    */
    public void setRmkAdmin(String rmkAdmin) {
        this.rmkAdmin = rmkAdmin;
    }

	public Date getDealDate() {
		return dealDate;
	}

	public void setDealDate(Date dealDate) {
		this.dealDate = dealDate;
	}

	public Long getDealBy() {
		return dealBy;
	}

	public void setDealBy(Long dealBy) {
		this.dealBy = dealBy;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlipay() {
		return alipay;
	}

	public void setAlipay(String alipay) {
		this.alipay = alipay;
	}

	public String getGatheringName() {
		return gatheringName;
	}

	public void setGatheringName(String gatheringName) {
		this.gatheringName = gatheringName;
	}
}