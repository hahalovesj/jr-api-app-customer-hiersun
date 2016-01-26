package com.hiersun.jewelry.api.entity.request;

import org.springframework.util.StringUtils;

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
		if (StringUtils.isEmpty(this.getGoodsID())) {
			return 900008;
		}
		if (StringUtils.isEmpty(this.getFreight())) {
			return 900008;
		}
		if (StringUtils.isEmpty(this.getAddressID())) {
			return 900008;
		}
		if (StringUtils.isEmpty(this.getSendBy())) {
			return 900008;
		}
		// if(StringUtils.isEmpty(this.getConsumerMsg())){
		// return 900008;
		// }
		if (StringUtils.isEmpty(this.getGoodsToken())) {
			return 900008;
		}

		return 0;
	}

}
