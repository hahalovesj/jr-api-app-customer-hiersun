package com.hiersun.jewelry.api.entity.request;

import org.springframework.util.StringUtils;

import com.hiersun.jewelry.api.entity.Body;

public class Request4026 extends Body {

	private String goodsNO;

	@Override
	public int volidateValue() {
		if(StringUtils.isEmpty(goodsNO)){
			return 402401;
		}
		return 0;
	}

	public String getGoodsNO() {
		return goodsNO;
	}

	public void setGoodsNO(String goodsNO) {
		this.goodsNO = goodsNO;
	}

}
