package com.hiersun.jewelry.api.entity.request;

import com.hiersun.jewelry.api.entity.Body;

public class Request4023  extends Body{
	
	private Integer goodsTypeCode;
	
	private Integer pageNO;

	public Integer getGoodsTypeCode() {
		return goodsTypeCode;
	}

	public void setGoodsTypeCode(Integer goodsTypeCode) {
		this.goodsTypeCode = goodsTypeCode;
	}

	public Integer getPageNO() {
		return pageNO;
	}

	public void setPageNO(Integer pageNO) {
		this.pageNO = pageNO;
	}

	@Override
	public int volidateValue() {
		if(goodsTypeCode == null){
			return 402301;
		}
		if(pageNO == null){
			return 402302;
		}
		return 0;
	}
	
	
	

}
