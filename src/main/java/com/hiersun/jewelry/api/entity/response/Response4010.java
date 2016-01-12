package com.hiersun.jewelry.api.entity.response;

import java.util.List;

import com.hiersun.jewelry.api.entity.ResponseBody;
import com.hiersun.jewelry.api.entity.vo.ResponseServiceOrder;

public class Response4010 extends ResponseBody{
	private List<ResponseServiceOrder> orderList;
	private Integer  pageNO;
	private Boolean isEnd;
	
	
	
	
 
	public List<ResponseServiceOrder> getOrderList() {
		return orderList;
	}
	public void setOrderList(List<ResponseServiceOrder> orderList) {
		this.orderList = orderList;
	}
	public Integer getPageNO() {
		return pageNO;
	}
	public void setPageNO(Integer pageNO) {
		this.pageNO = pageNO;
	}
	public Boolean getIsEnd() {
		return isEnd;
	}
	public void setIsEnd(Boolean isEnd) {
		this.isEnd = isEnd;
	}


}
