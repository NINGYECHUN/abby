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
*  01			       2017-11-02             宁业春              新建
* ========================================================================.
*/
public class Order {
    /**
    主键id.
    */
    private Long id;

    /**
    创建时间.
    */
    private Date createDate;
    
    /**
    创建时间.
    */
    private String createDateStr;

    /**
    商品名称.
    */
    private String goodsName;

    /**
    数量.
    */
    private Integer qty;

    /**
    单价.
    */
    private Double price;

    /**
    状态 订单付款 订单结算 订单失效.
    */
    private String status;

    /**
    收入比率.
    */
    private String incomeRate;

    /**
    分成比率.
    */
    private String dividedRate;

    /**
    付款金额.
    */
    private Double payment;

    /**
    效果预估.
    */
    private Double effectEstimate;

    /**
    结算金额.
    */
    private Double setAmount;

    /**
    预估收入.
    */
    private Double incomeEstimate;

    /**
    佣金比率.
    */
    private String commissionRate;

    /**
    补贴金额.
    */
    private Double subsidyAmount;

    /**
    订单编号.
    */
    private String orderNo;

    /**
    媒体id.
    */
    private String mediaId;

    /**
    媒体名称.
    */
    private String mediaName;

    /**
    广告位id.
    */
    private String adsenseId;

    /**
    广告位名称.
    */
    private String adsenseName;

    /**
    导入时间.
    */
    private Date importDate;

    /**
    导入人id.
    */
    private Long importBy;
    
    /**
     * 用户表上设置的佣金比率.
     */
    private Double commissionRateUser;

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
    获取createDate(创建时间)的当前值.
    @return 得到的createDate的值
    */
    public Date getCreateDate() {
        return createDate;
    }

    /**
    给createDate(创建时间)赋予新的值.
    @param createDate 赋予createDate新的值
    */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
    获取goodsName(商品名称)的当前值.
    @return 得到的goodsName的值
    */
    public String getGoodsName() {
        return goodsName;
    }

    /**
    给goodsName(商品名称)赋予新的值.
    @param goodsName 赋予goodsName新的值
    */
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    /**
    获取qty(数量)的当前值.
    @return 得到的qty的值
    */
    public Integer getQty() {
        return qty;
    }

    /**
    给qty(数量)赋予新的值.
    @param qty 赋予qty新的值
    */
    public void setQty(Integer qty) {
        this.qty = qty;
    }

    /**
    获取price(单价)的当前值.
    @return 得到的price的值
    */
    public Double getPrice() {
        return price;
    }

    /**
    给price(单价)赋予新的值.
    @param price 赋予price新的值
    */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
    获取status(状态 订单付款 订单结算 订单失效)的当前值.
    @return 得到的status的值
    */
    public String getStatus() {
        return status;
    }

    /**
    给status(状态 订单付款 订单结算 订单失效)赋予新的值.
    @param status 赋予status新的值
    */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
    获取incomeRate(收入比率)的当前值.
    @return 得到的incomeRate的值
    */
    public String getIncomeRate() {
        return incomeRate;
    }

    /**
    给incomeRate(收入比率)赋予新的值.
    @param incomeRate 赋予incomeRate新的值
    */
    public void setIncomeRate(String incomeRate) {
        this.incomeRate = incomeRate;
    }

    /**
    获取dividedRate(分成比率)的当前值.
    @return 得到的dividedRate的值
    */
    public String getDividedRate() {
        return dividedRate;
    }

    /**
    给dividedRate(分成比率)赋予新的值.
    @param dividedRate 赋予dividedRate新的值
    */
    public void setDividedRate(String dividedRate) {
        this.dividedRate = dividedRate;
    }

    /**
    获取payment(付款金额)的当前值.
    @return 得到的payment的值
    */
    public Double getPayment() {
        return payment;
    }

    /**
    给payment(付款金额)赋予新的值.
    @param payment 赋予payment新的值
    */
    public void setPayment(Double payment) {
        this.payment = payment;
    }

    /**
    获取effectEstimate(效果预估)的当前值.
    @return 得到的effectEstimate的值
    */
    public Double getEffectEstimate() {
        return effectEstimate;
    }

    /**
    给effectEstimate(效果预估)赋予新的值.
    @param effectEstimate 赋予effectEstimate新的值
    */
    public void setEffectEstimate(Double effectEstimate) {
        this.effectEstimate = effectEstimate;
    }

    /**
    获取setAmount(结算金额)的当前值.
    @return 得到的setAmount的值
    */
    public Double getSetAmount() {
        return setAmount;
    }

    /**
    给setAmount(结算金额)赋予新的值.
    @param setAmount 赋予setAmount新的值
    */
    public void setSetAmount(Double setAmount) {
        this.setAmount = setAmount;
    }

    /**
    获取incomeEstimate(预估收入)的当前值.
    @return 得到的incomeEstimate的值
    */
    public Double getIncomeEstimate() {
        return incomeEstimate;
    }

    /**
    给incomeEstimate(预估收入)赋予新的值.
    @param incomeEstimate 赋予incomeEstimate新的值
    */
    public void setIncomeEstimate(Double incomeEstimate) {
        this.incomeEstimate = incomeEstimate;
    }

    /**
    获取commissionRate(佣金比率)的当前值.
    @return 得到的commissionRate的值
    */
    public String getCommissionRate() {
        return commissionRate;
    }

    /**
    给commissionRate(佣金比率)赋予新的值.
    @param commissionRate 赋予commissionRate新的值
    */
    public void setCommissionRate(String commissionRate) {
        this.commissionRate = commissionRate;
    }

    /**
    获取subsidyAmount(补贴金额)的当前值.
    @return 得到的subsidyAmount的值
    */
    public Double getSubsidyAmount() {
        return subsidyAmount;
    }

    /**
    给subsidyAmount(补贴金额)赋予新的值.
    @param subsidyAmount 赋予subsidyAmount新的值
    */
    public void setSubsidyAmount(Double subsidyAmount) {
        this.subsidyAmount = subsidyAmount;
    }

    /**
    获取orderNo(订单编号)的当前值.
    @return 得到的orderNo的值
    */
    public String getOrderNo() {
        return orderNo;
    }

    /**
    给orderNo(订单编号)赋予新的值.
    @param orderNo 赋予orderNo新的值
    */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
    获取mediaId(媒体id)的当前值.
    @return 得到的mediaId的值
    */
    public String getMediaId() {
        return mediaId;
    }

    /**
    给mediaId(媒体id)赋予新的值.
    @param mediaId 赋予mediaId新的值
    */
    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    /**
    获取mediaName(媒体名称)的当前值.
    @return 得到的mediaName的值
    */
    public String getMediaName() {
        return mediaName;
    }

    /**
    给mediaName(媒体名称)赋予新的值.
    @param mediaName 赋予mediaName新的值
    */
    public void setMediaName(String mediaName) {
        this.mediaName = mediaName;
    }

    /**
    获取adsenseId(广告位id)的当前值.
    @return 得到的adsenseId的值
    */
    public String getAdsenseId() {
        return adsenseId;
    }

    /**
    给adsenseId(广告位id)赋予新的值.
    @param adsenseId 赋予adsenseId新的值
    */
    public void setAdsenseId(String adsenseId) {
        this.adsenseId = adsenseId;
    }

    /**
    获取adsenseName(广告位名称)的当前值.
    @return 得到的adsenseName的值
    */
    public String getAdsenseName() {
        return adsenseName;
    }

    /**
    给adsenseName(广告位名称)赋予新的值.
    @param adsenseName 赋予adsenseName新的值
    */
    public void setAdsenseName(String adsenseName) {
        this.adsenseName = adsenseName;
    }

    /**
    获取importDate(导入时间)的当前值.
    @return 得到的importDate的值
    */
    public Date getImportDate() {
        return importDate;
    }

    /**
    给importDate(导入时间)赋予新的值.
    @param importDate 赋予importDate新的值
    */
    public void setImportDate(Date importDate) {
        this.importDate = importDate;
    }

    /**
    获取importBy(导入人id)的当前值.
    @return 得到的importBy的值
    */
    public Long getImportBy() {
        return importBy;
    }

    /**
    给importBy(导入人id)赋予新的值.
    @param importBy 赋予importBy新的值
    */
    public void setImportBy(Long importBy) {
        this.importBy = importBy;
    }

	public Double getCommissionRateUser() {
		return commissionRateUser;
	}

	public void setCommissionRateUser(Double commissionRateUser) {
		this.commissionRateUser = commissionRateUser;
	}

	public String getCreateDateStr() {
		return createDateStr;
	}

	public void setCreateDateStr(String createDateStr) {
		this.createDateStr = createDateStr;
	}
}