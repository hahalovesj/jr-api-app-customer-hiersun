package com.hiersun.jewelry.api.entity.response;

import java.util.List;
import java.util.Map;

import com.hiersun.jewelry.api.entity.ResponseBody;
import com.hiersun.jewelry.api.entity.vo.Goods;
import com.hiersun.jewelry.api.user.domain.AddressVo;

public class Response2007 extends ResponseBody {
	public Long userID;

	public String mobile;
	private Goods goods;
	private String goodsToken;
	private int freight;
	private String sendBy;
	private List<Map<String, Object>> addressList;

	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public String getGoodsToken() {
		return goodsToken;
	}

	public void setGoodsToken(String goodsToken) {
		this.goodsToken = goodsToken;
	}

	public int getFreight() {
		return freight;
	}

	public void setFreight(int freight) {
		this.freight = freight;
	}

	public String getSendBy() {
		return sendBy;
	}

	public void setSendBy(String sendBy) {
		this.sendBy = sendBy;
	}

	public List<Map<String, Object>> getAddressList() {
		return addressList;
	}

	public void setAddressList(List<Map<String, Object>> addressList) {
		this.addressList = addressList;
	}

	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}
