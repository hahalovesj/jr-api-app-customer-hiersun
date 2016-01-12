package com.hiersun.jewelry.api.entity.request;

import org.springframework.util.StringUtils;

import com.hiersun.jewelry.api.entity.Body;

public class Request4006 extends Body {
	private String bankCardNum;

	public String getBankCardNum() {
		return bankCardNum;
	}

	public void setBankCardNum(String bankCardNum) {
		this.bankCardNum = bankCardNum;
	}

	@Override
	public int volidateValue() {
		if (StringUtils.isEmpty(this.getBankCardNum())) {
			return 400008;
		}
		return 0;
	}

}
