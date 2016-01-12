package com.hiersun.jewelry.api.entity.request;

import com.hiersun.jewelry.api.entity.Body;

public class Request4031 extends Body {

	/*** 商品编码 ****/
	private String goodNo;
	
	public String getGoodNo() {
		return goodNo;
	}

	public void setGoodNo(String goodNo) {
		this.goodNo = goodNo;
	}

	@Override
	public int volidateValue() {
		// TODO Auto-generated method stub
		return 0;
	}

}
