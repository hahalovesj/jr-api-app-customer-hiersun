package com.hiersun.jewelry.api.entity.request;

import com.hiersun.jewelry.api.constant.StatusMap;
import com.hiersun.jewelry.api.entity.Body;

public class Request4010  extends Body {

	/**
	 * 订单分类(代付款0,待发货1,贷服务2,待收货3,已完成4)
	 */
	private Integer orderTypeCode;
	/**
	 * 分页页码
	 */
	private Integer pageNO;
	
	@Override
	public int volidateValue() {
		if(orderTypeCode == null
				|| StatusMap.SERVICE_ORDER_STAUTECODE_MAP.get(orderTypeCode) == null){
			return 900008;
		}
		if(pageNO == null){
			return 900008;
		}
		return 0;
	}

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

	
	
	

}
