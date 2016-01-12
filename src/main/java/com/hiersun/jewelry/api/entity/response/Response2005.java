package com.hiersun.jewelry.api.entity.response;

import java.util.List;
import java.util.Map;

import com.hiersun.jewelry.api.direct.domain.DirectGoodMessageVo;
import com.hiersun.jewelry.api.entity.ResponseBody;

public class Response2005  extends ResponseBody{
	
	private Long goodsID;
	
	
	private List<Map<String,Object>> msgList;

	public Long getGoodsID() {
		return goodsID;
	}

	public void setGoodsID(Long goodsID) {
		this.goodsID = goodsID;
	}

	public List<Map<String, Object>> getMsgList() {
		return msgList;
	}

	public void setMsgList(List<Map<String, Object>> msgList) {
		this.msgList = msgList;
	}



	


}
