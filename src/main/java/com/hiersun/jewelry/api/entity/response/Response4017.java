package com.hiersun.jewelry.api.entity.response;

import java.util.List;
import java.util.Map;

import com.hiersun.jewelry.api.entity.ResponseBody;

public class Response4017 extends ResponseBody {
	private List<Map<String, String>> picList;

	private String identifyResult;

	private String certificateType;

	private List<Map<String, String>> certificatePicList;

	private String beanInfo;

	public String getBeanInfo() {
		return beanInfo;
	}

	public void setBeanInfo(String beanInfo) {
		this.beanInfo = beanInfo;
	}

	public List<Map<String, String>> getPicList() {
		return picList;
	}

	public void setPicList(List<Map<String, String>> picList) {
		this.picList = picList;
	}

	public String getIdentifyResult() {
		return identifyResult;
	}

	public void setIdentifyResult(String identifyResult) {
		this.identifyResult = identifyResult;
	}

	public String getCertificateType() {
		return certificateType;
	}

	public void setCertificateType(String certificateType) {
		this.certificateType = certificateType;
	}

	public List<Map<String, String>> getCertificatePicList() {
		return certificatePicList;
	}

	public void setCertificatePicList(List<Map<String, String>> certificatePicList) {
		this.certificatePicList = certificatePicList;
	}

}
