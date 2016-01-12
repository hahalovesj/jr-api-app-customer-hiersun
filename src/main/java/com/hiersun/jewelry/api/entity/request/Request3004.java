package com.hiersun.jewelry.api.entity.request;

import java.util.List;
import java.util.Map;

import com.hiersun.jewelry.api.entity.Body;

public class Request3004 extends Body {
	private List<Map> goodsPicList;
	private String goodsName;
	private String goodsDec;
	private Double goodsBuyPrice;
	private Double goodsPrice;
	private String weight;

	public List<Map> getGoodsPicList() {
		return goodsPicList;
	}

	public void setGoodsPicList(List<Map> goodsPicList) {
		this.goodsPicList = goodsPicList;
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

	public Double getGoodsBuyPrice() {
		return goodsBuyPrice;
	}

	public void setGoodsBuyPrice(Double goodsBuyPrice) {
		this.goodsBuyPrice = goodsBuyPrice;
	}

	public Double getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(Double goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	@Override
	public int volidateValue() {
		// TODO Auto-generated method stub
		return 0;
	}

}
