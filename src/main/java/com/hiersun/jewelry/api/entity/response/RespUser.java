package com.hiersun.jewelry.api.entity.response;

import java.util.Date;

import com.hiersun.jewelry.api.entity.ResponseBody;
import com.hiersun.jewelry.api.entity.vo.BankCardNum;

public class RespUser  extends ResponseBody{
	private String mobile;
	private String nickName;
	private String sex;
	private Date birthday;
	private BankCardNum bankCardNum;

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

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public BankCardNum getBankCardNum() {
		return bankCardNum;
	}

	public void setBankCardNum(BankCardNum bankCardNum) {
		this.bankCardNum = bankCardNum;
	}

}
