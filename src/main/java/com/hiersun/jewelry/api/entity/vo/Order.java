package com.hiersun.jewelry.api.entity.vo;

public class Order {
	private String goodsName;
	private String goodsPicUrl;
	private Long orderID;
	private String orderNO;
	private Double orderPrice;
	private Double goodsBuyPrice;
	private String freightDesc;
	private Review review;
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
	public String getFreightDesc() {
		return freightDesc;
	}
	public void setFreightDesc(String freightDesc) {
		this.freightDesc = freightDesc;
	}
	public Review getReview() {
		return review;
	}
	public void setReview(Review review) {
		this.review = review;
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
	

}
