package com.hiersun.jewelry.api.entity.request;

import org.apache.commons.lang3.StringUtils;

import com.hiersun.jewelry.api.entity.Body;
import com.hiersun.jewelry.api.util.ValidateUtil;

public class RequestLogin extends Body {
	private String mobile;
	private String password;
	private String pushMsgID;
	private String jumpTransaction;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPushMsgID() {
		return pushMsgID;
	}

	public void setPushMsgID(String pushMsgID) {
		this.pushMsgID = pushMsgID;
	}

	public String getJumpTransaction() {
		return jumpTransaction;
	}

	public void setJumpTransaction(String jumpTransaction) {
		this.jumpTransaction = jumpTransaction;
	}

	@Override
	public int volidateValue() {
		if (StringUtils.isEmpty(this.getMobile())) {
			return 100103;
		}
		if (!ValidateUtil.isMobile(this.getMobile())) {
			return 100104;
		}
		if (StringUtils.isEmpty(this.getPassword())) {
			return 900008;
		}
		if (StringUtils.isEmpty(this.getPushMsgID())) {
			return 900008;
		}
		if (StringUtils.isEmpty(this.getJumpTransaction())) {
			return 900008;
		}
		return 0;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}
