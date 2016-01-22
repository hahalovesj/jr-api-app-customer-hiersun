package com.hiersun.jewelry.api.entity.response;

import com.hiersun.jewelry.api.entity.ResponseBody;

public class ResponseResetpwd  extends ResponseBody{

	private RespUser user;
	public RespUser getUser() {
		return user;
	}
	public void setUser(RespUser user) {
		this.user = user;
	}
	
	

}
