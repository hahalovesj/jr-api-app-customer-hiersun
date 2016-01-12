package com.hiersun.jewelry.api.entity.response;

import java.util.List;

public class Response2006_1 {

	private String goodsID;

	private String userID;

	private String mobile;

	private String goodsName;

	private String goodsDesc;

	private String goodsToken;

	private List<Object> addressList;

	public String getGoodsID() {
		return goodsID;
	}

	public void setGoodsID(String goodsID) {
		this.goodsID = goodsID;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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

	public String getGoodsToken() {
		return goodsToken;
	}

	public void setGoodsToken(String goodsToken) {
		this.goodsToken = goodsToken;
	}

	public List<Object> getAddressList() {
		return addressList;
	}

	public void setAddressList(List<Object> addressList) {
		this.addressList = addressList;
	}

}
