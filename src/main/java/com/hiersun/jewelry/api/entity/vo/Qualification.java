package com.hiersun.jewelry.api.entity.vo;

import java.util.List;
import java.util.Map;

public class Qualification {

	private String identifyResult;

	private String beanInfo;

	private List<Map<String, String>> qualiPicList;

	public List<Map<String, String>> getQualiPicList() {
		return qualiPicList;
	}

	public void setQualiPicList(List<Map<String, String>> qualiPicList2) {
		this.qualiPicList = qualiPicList2;
	}

	public String getIsIdentifyResult() {
		return identifyResult;
	}

	public void setIsIdentifyResult(String identifyResult) {
		this.identifyResult = identifyResult;
	}

	public String getBeanInfo() {
		return beanInfo;
	}

	public void setBeanInfo(String beanInfo) {
		this.beanInfo = beanInfo;
	}

}
