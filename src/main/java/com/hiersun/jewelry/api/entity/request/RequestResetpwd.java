package com.hiersun.jewelry.api.entity.request;

import org.apache.commons.lang3.StringUtils;

import com.hiersun.jewelry.api.entity.Body;
import com.hiersun.jewelry.api.util.ValidateUtil;

public class RequestResetpwd extends Body {

	private String password;

	private String veriCode;

	private String sMsgAcctionType;
	
	private String mobile;

	@Override
	public int volidateValue() {
		if (StringUtils.isEmpty(mobile)) {
			return 100103;
		}
		if (StringUtils.isEmpty(password)) {
			return 900008;
		}
		if (StringUtils.isEmpty(veriCode)) {
			return 100201;
		}
		if (!ValidateUtil.isNumber(this.getVeriCode())) {
			return 100201;
		}
		if (StringUtils.isEmpty(sMsgAcctionType)) {
			return 900008;
		}
		return 0;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	

}
