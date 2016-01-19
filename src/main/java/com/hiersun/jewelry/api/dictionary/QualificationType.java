package com.hiersun.jewelry.api.dictionary;

import java.util.HashMap;
import java.util.Map;

public class QualificationType {
	public static Map<Integer, String> QUALIFICATION_TYPE = new HashMap<Integer, String>();

	public static Map<Integer, String> MATCHED_DEGREE = new HashMap<Integer, String>();

	public static Map<Integer, String> PAY_TYPE_MAP = new HashMap<Integer, String>();

	public static Map<Integer, String> STYLE_TUPE_MAP = new HashMap<Integer, String>();
	
	public static Map<Integer, String>  MATERIAL_TYPE = new HashMap<Integer, String>();
	
	public static Map<String, String> SEX_MAP = new HashMap<String, String>();

	static {
		// 证书类型
		QUALIFICATION_TYPE.put(1, "镶嵌钻石分级鉴定证书");
		QUALIFICATION_TYPE.put(2, "钻石分级鉴定证书");
		QUALIFICATION_TYPE.put(3, "贵金属饰品纯度鉴定证书");
		QUALIFICATION_TYPE.put(4, "珠宝玉石首饰鉴定证书");
		QUALIFICATION_TYPE.put(5, "宝玉石首饰鉴定证书");
		QUALIFICATION_TYPE.put(6, "珠宝首饰鉴定证书");
		// 描述信息匹配度
		MATCHED_DEGREE.put(0, "完全一致");
		MATCHED_DEGREE.put(1, "完全不符");
		MATCHED_DEGREE.put(2, "细节差异");
		// 支付方式
		PAY_TYPE_MAP.put(1, "微信支付");
		PAY_TYPE_MAP.put(2, "支付宝支付");
		PAY_TYPE_MAP.put(3, "银联");
		PAY_TYPE_MAP.put(4, "其他");

		// 商品款式类型
		STYLE_TUPE_MAP.put(1, "戒指");
		STYLE_TUPE_MAP.put(2, "项链");
		STYLE_TUPE_MAP.put(3, "手链");
		STYLE_TUPE_MAP.put(4, "手镯");
		STYLE_TUPE_MAP.put(5, "耳饰");
		STYLE_TUPE_MAP.put(6, "情侣款");
		STYLE_TUPE_MAP.put(7, "其他");
		
		MATERIAL_TYPE.put(0, "贵金属");
		MATERIAL_TYPE.put(1, "裸钻");
		MATERIAL_TYPE.put(2, "镶嵌钻石");
		MATERIAL_TYPE.put(3, "钻彩");
		MATERIAL_TYPE.put(4, "祖母绿");
		MATERIAL_TYPE.put(5, "红宝石");
		MATERIAL_TYPE.put(6, "蓝宝石");
		
		SEX_MAP.put("0", "女");
		SEX_MAP.put("1", "男");
	}
}
