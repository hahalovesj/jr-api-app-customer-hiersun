package com.hiersun.jewelry.api.entity.vo;

public class ResultUser {
	private Long userID;
	private String mobile;
	private String nickName;
	private String icon;
	private String bigIcon;
	private String smallIcon;

	public String getBigIcon() {
		return bigIcon;
	}

	public void setBigIcon(String bigIcon) {
		this.bigIcon = bigIcon;
	}

	public String getSmallIcon() {
		return smallIcon;
	}

	public void setSmallIcon(String smallIcon) {
		this.smallIcon = smallIcon;
	}

	public ResultUser() {
	}

	public ResultUser(Long userID, String mobile, String nickname, String icon) {
		super();
		this.userID = userID;
		this.mobile = mobile;
		this.nickName = nickname;
		this.icon = icon;
	}

	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
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

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

}
