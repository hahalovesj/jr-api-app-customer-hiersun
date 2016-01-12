package com.hiersun.jewelry.api.service;

import java.util.Map;

import com.hiersun.jewelry.api.entity.RequestHeader;

public interface BaseService {
	
	
	/**
	 * 是否需要登录
	 */
	public boolean ifValidateLogin();
	
	
	/**
	 * 基础验证
	 * 返回错误码
	 */
	public Integer baseValidateMsgBody(String bodyStr,Long userId);

	/**
	 *  进行处理后返回MSG对象
	 */
	public Map<String, Object> doController(RequestHeader reqHead,String bodyStr,Long userId) throws Exception;
}
