package com.hiersun.jewelry.api.entity.request;

import com.hiersun.jewelry.api.entity.Body;

public class Request2007 extends Body{

	private Long goodsID;
	
	

	public Long getGoodsID() {
		return goodsID;
	}



	public void setGoodsID(Long goodsID) {
		this.goodsID = goodsID;
	}



	@Override
	public int volidateValue() {
		// TODO Auto-generated method stub
		return 0;
	}

}
