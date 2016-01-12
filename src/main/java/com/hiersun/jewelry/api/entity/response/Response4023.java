package com.hiersun.jewelry.api.entity.response;

import java.util.List;

import com.hiersun.jewelry.api.entity.ResponseBody;
import com.hiersun.jewelry.api.entity.vo.ResponseJrdsGood;

public class Response4023 extends ResponseBody{
	private List<ResponseJrdsGood> orderList;
	private Integer  pageNO;
	private Boolean isEnd;
	
	
	
	
 
	
	public List<ResponseJrdsGood> getOrderList() {
		return orderList;
	}
	public void setOrderList(List<ResponseJrdsGood> orderList) {
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
