package com.hiersun.jewelry.api.entity.response;

import com.hiersun.jewelry.api.entity.ResponseBody;
import com.hiersun.jewelry.api.entity.vo.Order;

public class Response4027  extends ResponseBody{
	private Order order;
	
	private String freight;
	


	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public String getFreight() {
		return freight;
	}

	public void setFreight(String freight) {
		this.freight = freight;
	}

}
