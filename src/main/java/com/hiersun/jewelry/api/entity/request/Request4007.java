package com.hiersun.jewelry.api.entity.request;

import java.io.Serializable;

import org.springframework.util.StringUtils;

import com.hiersun.jewelry.api.entity.Body;
import com.hiersun.jewelry.api.util.bank.CheckBankCard;

public class Request4007 extends Body implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO()
	 */
	private static final long serialVersionUID = -884300404703940633L;

	private String userRealName;

	private String bankName;

	private String bankCardNum;

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getUserRealName() {
		return userRealName;
	}

	public void setUserRealName(String userRealName) {
		this.userRealName = userRealName;
	}

	public String getBankCardNum() {
		return bankCardNum;
	}

	public void setBankCardNum(String bankCardNum) {
		this.bankCardNum = bankCardNum;
	}

	@Override
	public int volidateValue() {
		if (StringUtils.isEmpty(this.getBankCardNum())) {
			return 400701;
		}
		
		if (!CheckBankCard.checkBankCard(this.getBankCardNum())) {
			return 400008;
		}

		if (StringUtils.isEmpty(this.getBankName())) {
			return 400702;
		}
		if (StringUtils.isEmpty(this.getUserRealName())) {
			return 400703;
		}
		return 0;
	}

}
