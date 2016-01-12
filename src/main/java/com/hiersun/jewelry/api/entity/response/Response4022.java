package com.hiersun.jewelry.api.entity.response;

import com.hiersun.jewelry.api.entity.ResponseBody;
import com.hiersun.jewelry.api.entity.vo.Qualification;
import com.hiersun.jewelry.api.entity.vo.ResponseOrder;

public class Response4022 extends ResponseBody {

	private ResponseOrder order;

	private Qualification qualification;

	public ResponseOrder getOrder() {
		return order;
	}

	public void setOrder(ResponseOrder order) {
		this.order = order;
	}

	public Qualification getQualification() {
		return qualification;
	}

	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}

}
