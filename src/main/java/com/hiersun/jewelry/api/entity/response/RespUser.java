package com.hiersun.jewelry.api.entity.response;

import com.hiersun.jewelry.api.entity.ResponseBody;
import com.hiersun.jewelry.api.entity.vo.BankCardNum;

public class RespUser extends ResponseBody {
	private String mobile;
	private String nickName;
	private String sex;
	private String birthday;
	private BankCardNum bankCardNum;
	private String token;
	private String smallIcon;
	private String bigIcon;

	public String getSmallIcon() {
		return smallIcon;
	}

	public void setSmallIcon(String smallIcon) {
		this.smallIcon = smallIcon;
	}

	public String getBigIcon() {
		return bigIcon;
	}

	public void setBigIcon(String bigIcon) {
		this.bigIcon = bigIcon;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
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
