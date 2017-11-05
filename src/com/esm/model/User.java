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
*  01			       2017-09-27             宁业春              新建
* ========================================================================.
*/
public class User {
    /**
    主键id.
    */
    private Long id;

    /**
    账号.
    */
    private String account;

    /**
    密码.
    */
    private String password;

    /**
    名称.
    */
    private String name;

    /**
    PID.
    */
    private String pid;

    /**
    佣金比率（直接是百分数，比如是10就表示是10%）.
    */
    private Double commissionRate;
    
    /**
     * 佣金比率（只是用来显示给用户看）
     */
    private Double commissionRateShow;

    /**
    是否管理员 1-管理员 0-非管理员.
    */
    private Integer isAdmin;

    /**
    上次登陆时间.
    */
    private Date lastLoginTime;

    /**
    支付宝账号.
    */
    private String alipay;

    /**
    手机号码.
    */
    private String phone;
    
    /**
     * 是否启用 1-启用 0-未启用.
     */
    private Integer isEnable;
    
    /**
     * 收款人名称.
     */
    private String gatheringName;
    
    /**
     * 给上级代理的佣金比率.
     */
    private Double commissionRateToParent;
    
    /**
     * 上级代理用户id.
     */
    private Long parentId;

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
    获取account(账号)的当前值.
    @return 得到的account的值
    */
    public String getAccount() {
        return account;
    }

    /**
    给account(账号)赋予新的值.
    @param account 赋予account新的值
    */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
    获取password(密码)的当前值.
    @return 得到的password的值
    */
    public String getPassword() {
        return password;
    }

    /**
    给password(密码)赋予新的值.
    @param password 赋予password新的值
    */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
    获取name(名称)的当前值.
    @return 得到的name的值
    */
    public String getName() {
        return name;
    }

    /**
    给name(名称)赋予新的值.
    @param name 赋予name新的值
    */
    public void setName(String name) {
        this.name = name;
    }

    /**
    获取pid(PID)的当前值.
    @return 得到的pid的值
    */
    public String getPid() {
        return pid;
    }

    /**
    给pid(PID)赋予新的值.
    @param pid 赋予pid新的值
    */
    public void setPid(String pid) {
        this.pid = pid;
    }

    /**
    获取commissionRate(提成比率（直接是百分数，比如是10就表示是10%）)的当前值.
    @return 得到的commissionRate的值
    */
    public Double getCommissionRate() {
        return commissionRate;
    }

    /**
    给commissionRate(提成比率（直接是百分数，比如是10就表示是10%）)赋予新的值.
    @param commissionRate 赋予commissionRate新的值
    */
    public void setCommissionRate(Double commissionRate) {
        this.commissionRate = commissionRate;
    }

    /**
    获取isAdmin(是否管理员 1-管理员 0-非管理员)的当前值.
    @return 得到的isAdmin的值
    */
    public Integer getIsAdmin() {
        return isAdmin;
    }

    /**
    给isAdmin(是否管理员 1-管理员 0-非管理员)赋予新的值.
    @param isAdmin 赋予isAdmin新的值
    */
    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }

    /**
    获取lastLoginTime(上次登陆时间)的当前值.
    @return 得到的lastLoginTime的值
    */
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    /**
    给lastLoginTime(上次登陆时间)赋予新的值.
    @param lastLoginTime 赋予lastLoginTime新的值
    */
    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    /**
    获取alipay(支付宝账号)的当前值.
    @return 得到的alipay的值
    */
    public String getAlipay() {
        return alipay;
    }

    /**
    给alipay(支付宝账号)赋予新的值.
    @param alipay 赋予alipay新的值
    */
    public void setAlipay(String alipay) {
        this.alipay = alipay;
    }

    /**
    获取phone(手机号码)的当前值.
    @return 得到的phone的值
    */
    public String getPhone() {
        return phone;
    }

    /**
    给phone(手机号码)赋予新的值.
    @param phone 赋予phone新的值
    */
    public void setPhone(String phone) {
        this.phone = phone;
    }

	public Integer getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Integer isEnable) {
		this.isEnable = isEnable;
	}

	public String getGatheringName() {
		return gatheringName;
	}

	public void setGatheringName(String gatheringName) {
		this.gatheringName = gatheringName;
	}

	public Double getCommissionRateToParent() {
		return commissionRateToParent;
	}

	public void setCommissionRateToParent(Double commissionRateToParent) {
		this.commissionRateToParent = commissionRateToParent;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Double getCommissionRateShow() {
		return commissionRateShow;
	}

	public void setCommissionRateShow(Double commissionRateShow) {
		this.commissionRateShow = commissionRateShow;
	}
}