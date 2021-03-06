package com.hiersun.jewelry.api.entity.response;

import com.hiersun.jewelry.api.entity.ResponseBody;

public class Response2008 extends ResponseBody {

	private Long goodsID;
	private String orderName;
	private Double goodsPrice;
	private String orderNO;
	private String orderDesc;
	private Double orderPrice;

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public String getOrderName() {
		return orderName;
	}

	public String getOrderDesc() {
		return orderDesc;
	}

	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}

	public Double getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(Double orderPrice) {
		this.orderPrice = orderPrice;
	}

	public String getOrderNO() {
		return orderNO;
	}

	public void setOrderNO(String orderNO) {
		this.orderNO = orderNO;
	}

	public Double getGoodsPrice() {
		return goodsPrice;
	}

	public Long getGoodsID() {
		return goodsID;
	}

	public void setGoodsID(Long goodsID) {
		this.goodsID = goodsID;
	}

}
