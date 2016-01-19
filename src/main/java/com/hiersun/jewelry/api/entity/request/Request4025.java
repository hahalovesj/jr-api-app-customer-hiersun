package com.hiersun.jewelry.api.entity.request;

import com.hiersun.jewelry.api.entity.Body;

public class Request4025 extends Body {

	/*** 商品编码 ***/
	private String goodsNO;

	/*** 商品id ***/
	private Long goodsID;

	/*** 请求类型 ***/
	private String actionType;

	public String getGoodsNO() {
		return goodsNO;
	}

	public void setGoodsNO(String goodsNO) {
		this.goodsNO = goodsNO;
	}

	public Long getGoodsID() {
		return goodsID;
	}

	public void setGoodsID(Long goodsID) {
		this.goodsID = goodsID;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	@Override
	public int volidateValue() {
		// TODO Auto-generated method stub
		return 0;
	}

}
