package com.hiersun.jewelry.api.entity.vo;

import java.util.List;
import java.util.Map;

public class ResponseOrder {

	private List<Map<String, Object>> goodsPicList;

	private double goodsPrice;

	private double goodsBuyPrice;

	private String goodsName;

	private String orderNO;

	private String freight;

	public List<Map<String, Object>> getGoodsPicList() {
		return goodsPicList;
	}

	public void setGoodsPicList(List<Map<String, Object>> goodsPicList) {
		this.goodsPicList = goodsPicList;
	}

	public double getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(double goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public double getGoodsBuyPrice() {
		return goodsBuyPrice;
	}

	public void setGoodsBuyPrice(double goodsBuyPrice) {
		this.goodsBuyPrice = goodsBuyPrice;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getOrderNO() {
		return orderNO;
	}

	public void setOrderNO(String orderNO) {
		this.orderNO = orderNO;
	}

	public String getFreight() {
		return freight;
	}

	public void setFreight(String freight) {
		this.freight = freight;
	}

}
