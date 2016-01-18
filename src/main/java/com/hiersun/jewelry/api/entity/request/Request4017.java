package com.hiersun.jewelry.api.entity.request;

import org.springframework.util.StringUtils;

import com.hiersun.jewelry.api.entity.Body;

public class Request4017 extends Body {
	private String orderNO;



	public String getOrderNO() {
		return orderNO;
	}




	public void setOrderNO(String orderNO) {
		this.orderNO = orderNO;
	}



	@Override
	public int volidateValue() {
		if(StringUtils.isEmpty(this.getOrderNO())){
			return 400710;
		}
		return 0;
	}

}
