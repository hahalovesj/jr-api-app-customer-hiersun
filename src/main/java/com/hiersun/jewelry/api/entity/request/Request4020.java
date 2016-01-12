package com.hiersun.jewelry.api.entity.request;

import org.apache.commons.lang3.StringUtils;

import com.hiersun.jewelry.api.entity.Body;

public class Request4020 extends Body{
	
	String OrderNo;
	
	String actionType;
	
	
	public String getActionType() {
		return actionType;
	}


	public void setActionType(String actionType) {
		this.actionType = actionType;
	}


	public String getOrderNo() {
		return OrderNo;
	}


	public void setOrderNo(String orderNo) {
		OrderNo = orderNo;
	}


	@Override
	public int volidateValue() {
		if(StringUtils.isEmpty(this.getOrderNo())){
			return 402001;
		}
		if(StringUtils.isEmpty(this.getActionType())){
			return 402002;
		}
		return 0;
	}

}
