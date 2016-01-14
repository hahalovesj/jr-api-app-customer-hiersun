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
	private Double settlement;
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

	public void setSettlement(Double settlement) {
		this.settlement = settlement;
	}

	public Integer getOrderStatusCode() {
		return orderStatusCode;
	}

	public void setOrderStatusCode(Integer orderStatusCode) {
		this.orderStatusCode = orderStatusCode;
	}

	public Double getSettlement() {
		return settlement;
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
