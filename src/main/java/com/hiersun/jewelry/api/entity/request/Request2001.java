package com.hiersun.jewelry.api.entity.request;

import com.hiersun.jewelry.api.entity.Body;

public class Request2001 extends Body {

	private Integer pageNo;

	private String type;

	private boolean isNeed;

	public boolean getIsNeed() {
		return isNeed;
	}

	public void setIsNeed(boolean isNeed) {
		this.isNeed = isNeed;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public int volidateValue() {
		if (this.getPageNo() == null) {
			return 200100;
		}
		if (this.getType() == null || this.getType().trim().length() < 1) {
			return 200101;
		}
		return 0;
	}

}
