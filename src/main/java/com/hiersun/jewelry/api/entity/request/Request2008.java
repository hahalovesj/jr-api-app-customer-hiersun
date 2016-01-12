package com.hiersun.jewelry.api.entity.request;

import com.hiersun.jewelry.api.entity.Body;

public class Request2008 extends Body {
	private Long goodsID;
	private int freight;
	private String sendBy;
	private String consumerMsg;
	private Long addressID;
	private String goodsToken;

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

	public String getConsumerMsg() {
		return consumerMsg;
	}

	public void setConsumerMsg(String consumerMsg) {
		this.consumerMsg = consumerMsg;
	}


	

	public Long getGoodsID() {
		return goodsID;
	}

	public void setGoodsID(Long goodsID) {
		this.goodsID = goodsID;
	}

	public Long getAddressID() {
		return addressID;
	}

	public void setAddressID(Long addressID) {
		this.addressID = addressID;
	}

	public String getGoodsToken() {
		return goodsToken;
	}

	public void setGoodsToken(String goodsToken) {
		this.goodsToken = goodsToken;
	}

	@Override
	public int volidateValue() {
		// TODO Auto-generated method stub
		return 0;
	}

}
