package com.hiersun.jewelry.api.entity;

public abstract class Body {

	private Long userID;

	public Body() {

	}

	public abstract int volidateValue();

	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

	

}
