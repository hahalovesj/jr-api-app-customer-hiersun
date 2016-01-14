package com.hiersun.jewelry.api.entity.request;

import org.springframework.util.StringUtils;

import com.hiersun.jewelry.api.entity.Body;

public class Request2006 extends Body {
	private Long goodsID;
	private Long msgToUserID;
	private String msgFromUserName;
	private String msgToUserName;
	private String msgContent;
	private Long goodsUserID;

	public Long getGoodsUserID() {
		return goodsUserID;
	}

	public void setGoodsUserID(Long goodsUserID) {
		this.goodsUserID = goodsUserID;
	}

	public Long getMsgToUserID() {
		return msgToUserID;
	}

	public void setMsgToUserID(Long msgToUserID) {
		this.msgToUserID = msgToUserID;
	}

	public void setGoodsID(Long goodsID) {
		this.goodsID = goodsID;
	}

	public String getMsgFromUserName() {
		return msgFromUserName;
	}

	public void setMsgFromUserName(String msgFromUserName) {
		this.msgFromUserName = msgFromUserName;
	}

	public String getMsgToUserName() {
		return msgToUserName;
	}

	public void setMsgToUserName(String msgToUserName) {
		this.msgToUserName = msgToUserName;
	}

	public Long getGoodsID() {
		return goodsID;
	}

	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	@Override
	public int volidateValue() {
		if (StringUtils.isEmpty(this.getMsgContent())) {
			return 200601;
		}
		if (StringUtils.isEmpty(this.getGoodsID())) {
			return 200602;
		}
		if (StringUtils.isEmpty(this.getGoodsUserID())) {
			return 200603;
		}
		if (StringUtils.isEmpty(this.getMsgFromUserName())) {
			return 200604;
		}
		return 0;
	}

}
