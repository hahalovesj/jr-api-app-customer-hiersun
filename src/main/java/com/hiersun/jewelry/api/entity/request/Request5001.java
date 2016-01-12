package com.hiersun.jewelry.api.entity.request;

import java.util.List;
import java.util.Map;

public class Request5001 {
	private List<Map> goodsPicList;
	private String weight;
	public String goodsName;
	public String goodsDec;
	private double goodsBuyPrice;
	private double goodsPrice;

	public List<Map> getGoodsPicList() {
		return goodsPicList;
	}

	public void setGoodsPicList(List<Map> goodsPicList) {
		this.goodsPicList = goodsPicList;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsDec() {
		return goodsDec;
	}

	public void setGoodsDec(String goodsDec) {
		this.goodsDec = goodsDec;
	}

	public double getGoodsBuyPrice() {
		return goodsBuyPrice;
	}

	public void setGoodsBuyPrice(double goodsBuyPrice) {
		this.goodsBuyPrice = goodsBuyPrice;
	}


	public double getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(double goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

}
