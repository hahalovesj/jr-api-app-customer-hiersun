package com.hiersun.jewelry.api.entity.response;

import java.util.Date;

import com.hiersun.jewelry.api.entity.ResponseBody;
import com.hiersun.jewelry.api.entity.vo.BankCardNum;

public class RespUser extends ResponseBody {
	private String mobile;
	private String nickName;
	private String sex;
	private String birthday;
	private BankCardNum bankCardNum;

	private String tokne;

	public String getTokne() {
		return tokne;
	}

	public void setTokne(String tokne) {
		this.tokne = tokne;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public BankCardNum getBankCardNum() {
		return bankCardNum;
	}

	public void setBankCardNum(BankCardNum bankCardNum) {
		this.bankCardNum = bankCardNum;
	}

}
