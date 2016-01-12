package com.hiersun.jewelry.api.entity.request;

import org.springframework.util.StringUtils;

import com.hiersun.jewelry.api.entity.Body;

public class Request4018 extends Body {

	private Integer orderTypeCode;

	private Integer pageNO;

	public Integer getOrderTypeCode() {
		return orderTypeCode;
	}

	public void setOrderTypeCode(Integer orderTypeCode) {
		this.orderTypeCode = orderTypeCode;
	}

	public Integer getPageNO() {
		return pageNO;
	}

	public void setPageNO(Integer pageNO) {
		this.pageNO = pageNO;
	}

	@Override
	public int volidateValue() {
		if (StringUtils.isEmpty(this.getOrderTypeCode())) {
			return 401801;
		}
		if (StringUtils.isEmpty(this.getPageNO())) {
			return 401802;
		}
		return 0;
	}

}
