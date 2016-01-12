package com.hiersun.jewelry.api.entity.response;

import com.hiersun.jewelry.api.entity.ResponseBody;

public class ResponseLogin extends ResponseBody {

	private String mobile;
	private String jumpTransaction;
	private String token;
	private String userId;
	private RespUser user;
	private String nickName;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getJumpTransaction() {
		return jumpTransaction;
	}

	public void setJumpTransaction(String jumpTransaction) {
		this.jumpTransaction = jumpTransaction;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public RespUser getUser() {
		return user;
	}

	public void setUser(RespUser user) {
		this.user = user;
	}

}
