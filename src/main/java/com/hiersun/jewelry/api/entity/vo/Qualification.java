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

	public String getIdentifyResult() {
		return identifyResult;
	}

	public void setIdentifyResult(String identifyResult) {
		this.identifyResult = identifyResult;
	}

	public void setQualiPicList(List<Map<String, String>> qualiPicList2) {
		this.qualiPicList = qualiPicList2;
	}

	public String getBeanInfo() {
		return beanInfo;
	}

	public void setBeanInfo(String beanInfo) {
		this.beanInfo = beanInfo;
	}

}
