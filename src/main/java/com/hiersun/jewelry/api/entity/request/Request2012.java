package com.hiersun.jewelry.api.entity.request;

import org.apache.commons.lang3.StringUtils;

import com.hiersun.jewelry.api.entity.Body;

public class Request2012 extends Body {

	private long addressID;

	public long getAddressID() {
		return addressID;
	}

	public void setAddressID(long addressID) {
		this.addressID = addressID;
	}

	@Override
	public int volidateValue() {
		if(StringUtils.isEmpty(Long.toString(this.addressID)))
		{
			return 900008;
		}
		return 0;
	}

}
