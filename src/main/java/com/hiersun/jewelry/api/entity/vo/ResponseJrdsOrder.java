package com.hiersun.jewelry.api.entity.vo;

public class ResponseJrdsOrder {
	private String createTime;
	private String payTime;
	private String payType;
	private String appraisaledTime;
	private String deliveryedTime;
	private String confirmedTime;
	private String goodsName;
	private String goodsPicUrl;
	private Long orderID;
	private String orderNO;
	private Double orderPrice;
	private Double goodsBuyPrice;
	private String freightDesc;
	private String orderMsg;
	private String orderStatusDes;
	private Integer orderStatusCode;
	private ResponseAddress address;
	private Boolean isAfter;
	private String cancelTime;
	private String confirmedBuyTime;

	public String getConfirmedBuyTime() {
		return confirmedBuyTime;
	}

	public void setConfirmedBuyTime(String confirmedBuyTime) {
		this.confirmedBuyTime = confirmedBuyTime;
	}

	public String getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(String cancelTime) {
		this.cancelTime = cancelTime;
	}

	public Boolean getIsAfter() {
		return isAfter;
	}

	public void setIsAfter(Boolean isAfter) {
		this.isAfter = isAfter;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
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

	public Long getOrderID() {
		return orderID;
	}

	public void setOrderID(Long orderID) {
		this.orderID = orderID;
	}

	public String getOrderNO() {
		return orderNO;
	}

	public void setOrderNO(String orderNO) {
		this.orderNO = orderNO;
	}

	public Double getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(Double orderPrice) {
		this.orderPrice = orderPrice;
	}

	public Double getGoodsBuyPrice() {
		return goodsBuyPrice;
	}

	public void setGoodsBuyPrice(Double goodsBuyPrice) {
		this.goodsBuyPrice = goodsBuyPrice;
	}

	public String getFreightDesc() {
		return freightDesc;
	}

	public void setFreightDesc(String freightDesc) {
		this.freightDesc = freightDesc;
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

	public ResponseAddress getAddress() {
		return address;
	}

	public void setAddress(ResponseAddress address) {
		this.address = address;
	}

}
