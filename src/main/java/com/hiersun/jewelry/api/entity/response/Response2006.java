package com.hiersun.jewelry.api.entity.response;

import java.util.List;
import java.util.Map;

import com.hiersun.jewelry.api.entity.ResponseBody;

public class Response2006  extends ResponseBody{

	private Long goodsID;
	
	private Long goodsUserID;
	
	private List<Map<String,Object>> msgList;

	
	public Long getGoodsID() {
		return goodsID;
	}

	public void setGoodsID(Long goodsID) {
		this.goodsID = goodsID;
	}

	public Long getGoodsUserID() {
		return goodsUserID;
	}

	public void setGoodsUserID(Long goodsUserID) {
		this.goodsUserID = goodsUserID;
	}

	public List<Map<String, Object>> getMsgList() {
		return msgList;
	}

	public void setMsgList(List<Map<String, Object>> msgList) {
		this.msgList = msgList;
	}

	
	
}
