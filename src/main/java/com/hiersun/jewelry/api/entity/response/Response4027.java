package com.hiersun.jewelry.api.entity.response;

import com.hiersun.jewelry.api.direct.domain.JrdsOrderVo;
import com.hiersun.jewelry.api.entity.ResponseBody;

public class Response4027  extends ResponseBody{
	private JrdsOrderVo jrdsOrderVo;
	
	private String freight;
	

	public JrdsOrderVo getJrdsOrderVo() {
		return jrdsOrderVo;
	}

	public void setJrdsOrderVo(JrdsOrderVo jrdsOrderVo) {
		this.jrdsOrderVo = jrdsOrderVo;
	}

	public String getFreight() {
		return freight;
	}

	public void setFreight(String freight) {
		this.freight = freight;
	}

}
