package com.hiersun.jewelry.api.entity.request;

import org.springframework.util.StringUtils;

import com.hiersun.jewelry.api.entity.Body;

public class Request4009 extends Body {
	private String password;
	private String veriCode;
	private String sMsgAcctionType;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getVeriCode() {
		return veriCode;
	}

	public void setVeriCode(String veriCode) {
		this.veriCode = veriCode;
	}

	public String getsMsgAcctionType() {
		return sMsgAcctionType;
	}

	public void setsMsgAcctionType(String sMsgAcctionType) {
		this.sMsgAcctionType = sMsgAcctionType;
	}

	@Override
	public int volidateValue() {
		if(StringUtils.isEmpty(this.getPassword())){
			return 100304;
		}
		if(StringUtils.isEmpty(this.getsMsgAcctionType())){
			return 900008;
		}
		if(StringUtils.isEmpty(this.getVeriCode())){
			return 100201;
		}
		return 0;
	}

}
