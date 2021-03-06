package com.hiersun.jewelry.api.entity.request;

import org.springframework.util.StringUtils;

import com.hiersun.jewelry.api.entity.Body;

public class Request4013 extends Body {
	
	//订单号
	private Long orderId;
	//快递公司名称
	private String companyName;
	//快递公司编码
	private String companyCode;
	//快递单号
	private String numbers;
	
	/**回寄地址ID*/
	private Long returnAddressId;
	
	public Long getReturnAddressId() {
		return returnAddressId;
	}

	public void setReturnAddressId(Long returnAddressId) {
		this.returnAddressId = returnAddressId;
	}

	/*** 订单编号 ***/
	private String orderNO;
	
	
	public String getOrderNO() {
		return orderNO;
	}

	public void setOrderNO(String orderNO) {
		this.orderNO = orderNO;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getNumbers() {
		return numbers;
	}

	public void setNumbers(String numbers) {
		this.numbers = numbers;
	}

	@Override
	public int volidateValue() {
		if(StringUtils.isEmpty(numbers)){
			return 401302;
		}
		return 0;
	}

}
