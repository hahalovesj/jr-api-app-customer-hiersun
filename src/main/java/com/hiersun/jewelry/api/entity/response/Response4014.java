package com.hiersun.jewelry.api.entity.response;

import java.util.List;

import com.hiersun.jewelry.api.entity.ResponseBody;
import com.hiersun.jewelry.api.entity.vo.LogisticsInfo;
import com.hiersun.jewelry.api.expressinfo.pojo.LogisticsTrackingInfo;

public class Response4014 {

	private String logisticsCompany;
	private String ogisticsNumber;
	private String ogisticsPhone;

	private List<LogisticsInfo> infoList;

	public String getLogisticsCompany() {
		return logisticsCompany;
	}

	public void setLogisticsCompany(String logisticsCompany) {
		this.logisticsCompany = logisticsCompany;
	}

	public String getOgisticsNumber() {
		return ogisticsNumber;
	}

	public void setOgisticsNumber(String ogisticsNumber) {
		this.ogisticsNumber = ogisticsNumber;
	}

	public String getOgisticsPhone() {
		return ogisticsPhone;
	}

	public void setOgisticsPhone(String ogisticsPhone) {
		this.ogisticsPhone = ogisticsPhone;
	}

	public List<LogisticsInfo> getInfoList() {
		return infoList;
	}

	public void setInfoList(List<LogisticsInfo> infoList) {
		this.infoList = infoList;
	}

}
