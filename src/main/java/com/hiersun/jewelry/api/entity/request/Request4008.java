package com.hiersun.jewelry.api.entity.request;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.hiersun.jewelry.api.entity.Body;

public class Request4008 extends Body implements Serializable{
	
	private static final long serialVersionUID = -4129181185538936123L;
	
	private String nickName;
	private String sex;
	private String birthday;
	
	
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


	@Override
	public int volidateValue() {
		if(StringUtils.isEmpty(this.nickName) && StringUtils.isEmpty(this.sex)){
			return 400801;
		}
		return 0;
	}

}
