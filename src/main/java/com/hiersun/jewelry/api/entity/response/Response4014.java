package com.hiersun.jewelry.api.entity.response;

import java.util.List;

import com.hiersun.jewelry.api.entity.vo.LogisticsInfo;

public class Response4014 {

	private String logisticsCompany;
	private String logisticsNumber;
	private String logisticsPhone;

	private List<LogisticsInfo> infoList;

	public String getLogisticsCompany() {
		return logisticsCompany;
	}

	public void setLogisticsCompany(String logisticsCompany) {
		this.logisticsCompany = logisticsCompany;
	}

	

	public String getLogisticsNumber() {
		return logisticsNumber;
	}

	public void setLogisticsNumber(String logisticsNumber) {
		this.logisticsNumber = logisticsNumber;
	}

	public String getLogisticsPhone() {
		return logisticsPhone;
	}

	public void setLogisticsPhone(String logisticsPhone) {
		this.logisticsPhone = logisticsPhone;
	}

	public List<LogisticsInfo> getInfoList() {
		return infoList;
	}

	public void setInfoList(List<LogisticsInfo> infoList) {
		this.infoList = infoList;
	}

}
