package com.hiersun.jewelry.api.entity.request;

import org.springframework.util.StringUtils;

import com.hiersun.jewelry.api.entity.Body;

public class Request2004 extends Body{
	
	private Long goodsID;

	public Long getGoodsID() {
		return goodsID;
	}

	public void setGoodsID(Long goodsID) {
		this.goodsID = goodsID;
	}

	@Override
	public int volidateValue() {
		if(StringUtils.isEmpty(this.goodsID)){
			return 200102;
		}
		return 0;
	}
	
}
