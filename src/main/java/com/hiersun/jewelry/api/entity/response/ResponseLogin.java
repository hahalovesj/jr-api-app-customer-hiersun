package com.hiersun.jewelry.api.entity.response;

import com.hiersun.jewelry.api.entity.ResponseBody;

public class ResponseLogin extends ResponseBody {

	private String jumpTransaction;

	private RespUser user;

	public String getJumpTransaction() {
		return jumpTransaction;
	}

	public void setJumpTransaction(String jumpTransaction) {
		this.jumpTransaction = jumpTransaction;
	}

	public RespUser getUser() {
		return user;
	}

	public void setUser(RespUser user) {
		this.user = user;
	}

}
