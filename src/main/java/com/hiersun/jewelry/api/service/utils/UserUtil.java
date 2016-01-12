package com.hiersun.jewelry.api.service.utils;

import org.springframework.util.StringUtils;

import com.hiersun.jewelry.api.service.RedisBaseService;

public class UserUtil {

	public static String APP_USERID_CACH_KEY_START = "api.token." ;
	
	public static Long getUsetId(String token,RedisBaseService redisBaseServiceImpl){
		
		String userId = redisBaseServiceImpl.get(APP_USERID_CACH_KEY_START + token);
		
		return StringUtils.isEmpty(userId)?null:Long.parseLong(userId);
	}
	
}
