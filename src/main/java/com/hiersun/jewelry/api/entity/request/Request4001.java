package com.hiersun.jewelry.api.entity.request;

import com.hiersun.jewelry.api.entity.Body;

public class Request4001 extends Body {
	
	private Integer pageNo;
	
	private Long memberID;
	

	public Long getMemberID() {
		return memberID;
	}

	public void setMemberID(Long memberID) {
		this.memberID = memberID;
	}

	public Integer getPageNo() {
		return pageNo;
	}
	
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	@Override
	public int volidateValue() {
		// TODO Auto-generated method stub
		return 0;
	}

}
