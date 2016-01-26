package com.hiersun.jewelry.api.entity.request;

import org.apache.commons.lang3.StringUtils;

import com.hiersun.jewelry.api.entity.Body;

public class Request2009 extends Body {
	private String type;
	private Long addressID;
	private String receiver;
	private String receiverMobile;
	private String area;
	private String detailedAddress;
	private boolean isDefault;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getAddressID() {
		return addressID;
	}

	public void setAddressID(Long addressID) {
		this.addressID = addressID;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getReceiverMobile() {
		return receiverMobile;
	}

	public void setReceiverMobile(String receiverMobile) {
		this.receiverMobile = receiverMobile;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getDetailedAddress() {
		return detailedAddress;
	}

	public void setDetailedAddress(String detailedAddress) {
		this.detailedAddress = detailedAddress;
	}

	public boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	@Override
	public int volidateValue() {
		if(StringUtils.isEmpty(this.type))
		{
			return 900008;
		}
		if(StringUtils.isEmpty(this.receiver))
		{
			return 900008;
		}
		if(StringUtils.isEmpty(this.receiverMobile))
		{
			return 900008;
		}
		return 0;
	}
}
