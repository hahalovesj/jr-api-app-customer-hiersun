package com.hiersun.jewelry.api.dictionary;

import java.util.HashMap;
import java.util.Map;

public class TradeLogDesc {

	/**
	 * 约定 String 1_0   1位鉴定、翻新、鉴定和翻新 0 为状态
	 */
	public static Map<String,String> SERVICEORDER_TRADELOG_MAP;
	
	
	static{
		SERVICEORDER_TRADELOG_MAP = new HashMap<String, String>();
		SERVICEORDER_TRADELOG_MAP.put("1_0", "下单时间");
		SERVICEORDER_TRADELOG_MAP.put("2_0", "下单时间");
		SERVICEORDER_TRADELOG_MAP.put("3_0", "下单时间");
		
		SERVICEORDER_TRADELOG_MAP.put("1_1", "付款时间");
		SERVICEORDER_TRADELOG_MAP.put("2_1", "付款时间");
		SERVICEORDER_TRADELOG_MAP.put("3_1", "付款时间");
		
		SERVICEORDER_TRADELOG_MAP.put("1_2", "发货到平台时间");
		SERVICEORDER_TRADELOG_MAP.put("2_2", "发货到平台时间");
		SERVICEORDER_TRADELOG_MAP.put("3_2", "发货到平台时间");
		
		//验证未通的
		SERVICEORDER_TRADELOG_MAP.put("1_3", "鉴定时间");
		SERVICEORDER_TRADELOG_MAP.put("2_3", "鉴定时间");
		SERVICEORDER_TRADELOG_MAP.put("3_3", "鉴定时间");
		//验证未通的
		SERVICEORDER_TRADELOG_MAP.put("1_5", "鉴定时间");
		SERVICEORDER_TRADELOG_MAP.put("2_5", "鉴定时间");
		SERVICEORDER_TRADELOG_MAP.put("3_5", "鉴定时间");
		//待翻新的
		SERVICEORDER_TRADELOG_MAP.put("2_6", "鉴定时间");
		SERVICEORDER_TRADELOG_MAP.put("3_6", "鉴定时间");
		//待配送
		SERVICEORDER_TRADELOG_MAP.put("1_7", "鉴定时间");
		SERVICEORDER_TRADELOG_MAP.put("2_7", "翻新时间");
		SERVICEORDER_TRADELOG_MAP.put("3_7", "翻新时间");
		
		//待配送
		SERVICEORDER_TRADELOG_MAP.put("1_7", "鉴定时间");
		SERVICEORDER_TRADELOG_MAP.put("2_7", "翻新时间");
		SERVICEORDER_TRADELOG_MAP.put("3_7", "翻新时间");	
		
		//待收货
		SERVICEORDER_TRADELOG_MAP.put("1_8", "寄回时间");
		SERVICEORDER_TRADELOG_MAP.put("2_8", "寄回时间");
		SERVICEORDER_TRADELOG_MAP.put("3_8", "寄回时间");	
		
		//待收货
		SERVICEORDER_TRADELOG_MAP.put("1_8", "寄回时间");
		SERVICEORDER_TRADELOG_MAP.put("2_8", "寄回时间");
		SERVICEORDER_TRADELOG_MAP.put("3_8", "寄回时间");	
		
		//已退款 
		SERVICEORDER_TRADELOG_MAP.put("1_11", "退款时间");
		SERVICEORDER_TRADELOG_MAP.put("2_11", "退款时间");
		SERVICEORDER_TRADELOG_MAP.put("3_11", "退款时间");
		//待收货
		SERVICEORDER_TRADELOG_MAP.put("1_10", "交易关闭时间");
		SERVICEORDER_TRADELOG_MAP.put("2_10", "交易关闭时间");
		SERVICEORDER_TRADELOG_MAP.put("3_10", "交易关闭时间");	
	}
}
