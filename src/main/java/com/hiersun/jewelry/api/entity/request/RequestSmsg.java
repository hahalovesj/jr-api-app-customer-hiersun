package com.hiersun.jewelry.api.entity.request;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.hiersun.jewelry.api.entity.Body;
import com.hiersun.jewelry.api.util.ValidateUtil;

public class RequestSmsg extends Body implements Serializable {
	private static final long serialVersionUID = 9179929337975999978L;

	private String mobile;

	private String acctionType;

	public String getAcctionType() {
		return acctionType;
	}

	public void setAcctionType(String acctionType) {
		this.acctionType = acctionType;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * 验证body参数
	 */
	@Override
	public int volidateValue() {
		if (StringUtils.isEmpty(this.getMobile())) {
			return 100103;
		} else if (!ValidateUtil.isMobile(this.getMobile())) {
			return 100104;
		}
		if (StringUtils.isEmpty(this.getAcctionType())) {
			return 900008;
		}
		return 0;
	}
}
