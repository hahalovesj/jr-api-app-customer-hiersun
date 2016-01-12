package com.hiersun.jewelry.api.entity.vo;

import java.util.List;
import java.util.Map;

public class Goods {
	private long goodsID;
	private String goodsNO;
	private String visitTimes;
	private String goodsMsgTimes;
	private Double goodsPrice;
	private Double goodsBuyPrice;
	private String goodsName;
	private String goodsDesc;
	private List<Map<String, Object>> goodsMainPicList;
	private List<Map<String, Object>> goodsPicList;
	private ResultUser user;
	private String goodsPicUrl;

	public String getGoodsPicUrl() {
		return goodsPicUrl;
	}

	public void setGoodsPicUrl(String goodsPicUrl) {
		this.goodsPicUrl = goodsPicUrl;
	}

	public long getGoodsID() {
		return goodsID;
	}

	public void setGoodsID(long goodsID) {
		this.goodsID = goodsID;
	}

	public String getGoodsNO() {
		return goodsNO;
	}

	public void setGoodsNO(String goodsNO) {
		this.goodsNO = goodsNO;
	}

	public String getVisitTimes() {
		return visitTimes;
	}

	public void setVisitTimes(String visitTimes) {
		this.visitTimes = visitTimes;
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

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsDesc() {
		return goodsDesc;
	}

	public void setGoodsDesc(String goodsDesc) {
		this.goodsDesc = goodsDesc;
	}

	public ResultUser getUser() {
		return user;
	}

	public void setUser(ResultUser user) {
		this.user = user;
	}

	public String getGoodsMsgTimes() {
		return goodsMsgTimes;
	}

	public void setGoodsMsgTimes(String goodsMsgTimes) {
		this.goodsMsgTimes = goodsMsgTimes;
	}

	public List<Map<String, Object>> getGoodsMainPicList() {
		return goodsMainPicList;
	}

	public void setGoodsMainPicList(List<Map<String, Object>> goodsMainPicList) {
		this.goodsMainPicList = goodsMainPicList;
	}

	public List<Map<String, Object>> getGoodsPicList() {
		return goodsPicList;
	}

	public void setGoodsPicList(List<Map<String, Object>> goodsPicList) {
		this.goodsPicList = goodsPicList;
	}

}
