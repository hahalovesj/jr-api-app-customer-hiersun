package com.hiersun.jewelry.api.entity.request;

import com.hiersun.jewelry.api.entity.Body;

public class Request2005 extends Body {

	private Long goodsID;

	private Long goodsUserID;

	public Long getGoodsUserID() {
		return goodsUserID;
	}

	public void setGoodsUserID(Long goodsUserID) {
		this.goodsUserID = goodsUserID;
	}

	public Long getGoodsID() {
		return goodsID;
	}

	public void setGoodsID(Long goodsID) {
		this.goodsID = goodsID;
	}

	@Override
	public int volidateValue() {
		return 0;
	}

}
