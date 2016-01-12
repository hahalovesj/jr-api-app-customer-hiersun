package com.hiersun.jewelry.api.entity.request;

import org.springframework.util.StringUtils;

import com.hiersun.jewelry.api.entity.Body;

public class Request4011  extends Body {

	private String orderNO;
	
	@Override
	public int volidateValue() {
		if(StringUtils.isEmpty(orderNO)){
			return 900008;
		}
		return 0;
	}

	public String getOrderNO() {
		return orderNO;
	}

	public void setOrderNO(String orderNO) {
		this.orderNO = orderNO;
	}
	
	

}
