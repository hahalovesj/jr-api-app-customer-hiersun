package com.hiersun.jewelry.api.entity.response;

import java.util.List;

import com.hiersun.jewelry.api.entity.ResponseBody;
import com.hiersun.jewelry.api.entity.vo.ResponseAddress;
import com.hiersun.jewelry.api.entity.vo.ResponseServiceOrder;

public class Response4011 extends ResponseBody{
	private ResponseServiceOrder order;
	private ResponseAddress address;
	private String  orderLog;
	public ResponseServiceOrder getOrder() {
		return order;
	}
	public void setOrder(ResponseServiceOrder order) {
		this.order = order;
	}
	public ResponseAddress getAddress() {
		return address;
	}
	public void setAddress(ResponseAddress address) {
		this.address = address;
	}
	public String getOrderLog() {
		return orderLog;
	}
	public void setOrderLog(String orderLog) {
		this.orderLog = orderLog;
	}

	

}
