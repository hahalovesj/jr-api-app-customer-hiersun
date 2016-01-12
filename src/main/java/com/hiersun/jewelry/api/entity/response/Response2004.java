package com.hiersun.jewelry.api.entity.response;

import com.hiersun.jewelry.api.entity.vo.Appraisal;
import com.hiersun.jewelry.api.entity.vo.Goods;

public class Response2004 {
	private Goods goods;
	private Appraisal appraisal;
	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public Appraisal getAppraisal() {
		return appraisal;
	}

	public void setAppraisal(Appraisal appraisal) {
		this.appraisal = appraisal;
	}

}
