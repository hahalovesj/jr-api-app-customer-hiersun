package com.hiersun.jewelry.api.entity.vo;

import com.hiersun.jewelry.api.entity.ResponseBody;

public class ResponseJrdsGood extends ResponseBody {
	/** 创建时间 */
	private String createTime;
	/** 商品名称 */
	private String goodsName;
	/** 商品主图 */
	private String goodsPicUrl;
	/** 商品ID */
	private Long goodsID;
	/***/
	private String applyNO;
	/** 商品编号 */
	private String goodsNO;
	/** 商品价格 */
	private Double goodsPrice;
	/** 商品金额 */
	private Double goodsBuyPrice;
	/**
	 * 结算金额
	 */
	private Double settlementPrice;
	/**
	 * 商品留言
	 */
	private String orderMsg;

	/**
	 * 订单状态描述
	 */
	private String orderStatusDes;

	/**
	 * 订单状态编码
	 */
	private Integer orderStatusCode;

	/**
	 * 商品日志
	 */
	private String goodsLogs;

	/**
	 * 佣金
	 */
	private Double commissionPrice;
	/**
	 * 支付时间
	 */
	private String payTime;
	/**
	 * 支付方式
	 */
	private String payType;
	/**
	 * 鉴定完成时间
	 */
	private String appraisaledTime;
	/**
	 * 配送时间
	 */
	private String deliveryedTime;
	/**
	 * 确认收货时间
	 */
	private String confirmedTime;

	private String orderNO;
	
	public String getOrderNO() {
		return orderNO;
	}

	public void setOrderNO(String orderNO) {
		this.orderNO = orderNO;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getAppraisaledTime() {
		return appraisaledTime;
	}

	public void setAppraisaledTime(String appraisaledTime) {
		this.appraisaledTime = appraisaledTime;
	}

	public String getDeliveryedTime() {
		return deliveryedTime;
	}

	public void setDeliveryedTime(String deliveryedTime) {
		this.deliveryedTime = deliveryedTime;
	}

	public String getConfirmedTime() {
		return confirmedTime;
	}

	public void setConfirmedTime(String confirmedTime) {
		this.confirmedTime = confirmedTime;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsPicUrl() {
		return goodsPicUrl;
	}

	public void setGoodsPicUrl(String goodsPicUrl) {
		this.goodsPicUrl = goodsPicUrl;
	}

	public Long getGoodsID() {
		return goodsID;
	}

	public void setGoodsID(Long goodsID) {
		this.goodsID = goodsID;
	}

	public String getGoodsNO() {
		return goodsNO;
	}

	public void setGoodsNO(String goodsNO) {
		this.goodsNO = goodsNO;
	}

	public Double getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(Double goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public Double getGoodsBuyPrice() {
		return goodsBuyPrice;
	}

	public void setGoodsBuyPrice(Double goodsBuyPrice) {
		this.goodsBuyPrice = goodsBuyPrice;
	}

	public String getOrderMsg() {
		return orderMsg;
	}

	public void setOrderMsg(String orderMsg) {
		this.orderMsg = orderMsg;
	}

	public String getOrderStatusDes() {
		return orderStatusDes;
	}

	public void setOrderStatusDes(String orderStatusDes) {
		this.orderStatusDes = orderStatusDes;
	}

	public Integer getOrderStatusCode() {
		return orderStatusCode;
	}

	public void setOrderStatusCode(Integer orderStatusCode) {
		this.orderStatusCode = orderStatusCode;
	}

	public Double getSettlementPrice() {
		return settlementPrice;
	}

	public void setSettlementPrice(Double settlementPrice) {
		this.settlementPrice = settlementPrice;
	}

	public String getApplyNO() {
		return applyNO;
	}

	public void setApplyNO(String applyNO) {
		this.applyNO = applyNO;
	}

	public String getGoodsLogs() {
		return goodsLogs;
	}

	public void setGoodsLogs(String goodsLogs) {
		this.goodsLogs = goodsLogs;
	}

	public Double getCommissionPrice() {
		return commissionPrice;
	}

	public void setCommissionPrice(Double commissionPrice) {
		this.commissionPrice = commissionPrice;
	}

}
