package com.hiersun.jewelry.api.entity.request;

import org.springframework.util.StringUtils;

import com.hiersun.jewelry.api.entity.Body;

public class Request4027 extends Body{
	String orderNo;
	
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	@Override
	public int volidateValue() {
		if(StringUtils.isEmpty(this.orderNo)){
			return 402701;
		}
		return 0;
	}

}
