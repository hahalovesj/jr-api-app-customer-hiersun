package com.hiersun.jewelry.api.entity.request;

import com.hiersun.jewelry.api.entity.Body;

public class Request4029 extends Body {
	
	private Long goodsID;
	
	private Long userID;
	
	
	public Long getGoodsID() {
		return goodsID;
	}

	public void setGoodsID(Long goodsID) {
		this.goodsID = goodsID;
	}

	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}



	@Override
	public int volidateValue() {
		// TODO Auto-generated method stub
		return 0;
	}
}
