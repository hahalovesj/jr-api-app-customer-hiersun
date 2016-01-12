package com.hiersun.jewelry.api.entity.response;

import com.hiersun.jewelry.api.entity.ResponseBody;

public class ResponseRegist extends ResponseBody {

	private String mobile;
	private String userId;
	private String jumpTransaction;
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getJumpTransaction() {
		return jumpTransaction;
	}

	public void setJumpTransaction(String jumpTransaction) {
		this.jumpTransaction = jumpTransaction;
	}

}
