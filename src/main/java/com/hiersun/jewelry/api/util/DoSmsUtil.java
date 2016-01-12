package com.hiersun.jewelry.api.util;

import java.util.HashMap;
import java.util.Map;

public class DoSmsUtil {

	public static void doSms(Long veriNumber, String mobile, String acctionType) {

//		Properties props = new Properties();
//		FileInputStream fis = null;
//		String url = "";
//		String contentBegin = "";
//		String contentEnd = "";
//		String appName = "";
//		try {
//			fis = new FileInputStream(new File(Object.class.getResource("/").getPath() + "/context/sms.properties"));
//			props.load(fis);
//			url = props.getProperty("sms.url");
//			contentBegin = props.getProperty("contentBegin");
//			contentEnd = props.getProperty("contentEnd");
//			appName = props.getProperty("appName");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		
		
		Map<String, Object> smsMap = new HashMap<String, Object>();
		smsMap.put("url", "http://192.168.4.91:8088/commonservice/sms/sendmessage");
		Map<String, Object> propertitesMap = new HashMap<String, Object>();
		String content = "您好，您的6位验证码为：" + veriNumber + "【二手珠宝】";
		propertitesMap.put("content", content);
		propertitesMap.put("mobiles", mobile);
		propertitesMap.put("appName", "二手珠宝");
		propertitesMap.put("scenario", acctionType);
		smsMap.put("propertites", propertitesMap);
		try {
			// 发送验证码
			new HttpUtil().doHPPost(smsMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
