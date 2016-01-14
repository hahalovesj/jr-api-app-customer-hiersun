package com.hiersun.jewelry.api.entity.request;

import org.apache.commons.lang3.StringUtils;

import com.hiersun.jewelry.api.entity.Body;

public class Request4028 extends Body{
	
	private String orderNo;
	
	private Long addressId;
	
	private Integer type;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	
	public Long getAddressId() {
		return addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

	@Override
	public int volidateValue() {
		if(StringUtils.isEmpty(this.getOrderNo())){
			return 400710;
		}
		if(this.getType()==null){
			return 402801;
		}
		return 0;
	}
	

}
