package com.hiersun.jewelry.api.dictionary;

import java.util.HashMap;
import java.util.Map;

public class TransactionTypeMap {
	public static Map<String, Integer> TRANSACTION_TYPE_MAP = new HashMap<String, Integer>();
	static {
		// 登陆注册修改密码
		TRANSACTION_TYPE_MAP.put("smsg", 1001);
		TRANSACTION_TYPE_MAP.put("regist", 1002);
		TRANSACTION_TYPE_MAP.put("login", 1003);
		TRANSACTION_TYPE_MAP.put("logout", 1004);
		TRANSACTION_TYPE_MAP.put("resetpwd", 1005);
		// 首页
		TRANSACTION_TYPE_MAP.put("index", 2001);
		TRANSACTION_TYPE_MAP.put("search", 2002);
		TRANSACTION_TYPE_MAP.put("goodsIndex", 2003);
		TRANSACTION_TYPE_MAP.put("goodsInfo", 2004);
		TRANSACTION_TYPE_MAP.put("goodsMsgList", 2005);
		TRANSACTION_TYPE_MAP.put("setGoodsMsg", 2006);
		TRANSACTION_TYPE_MAP.put("buyGoods", 2007);
		TRANSACTION_TYPE_MAP.put("subGoods", 2008);
		TRANSACTION_TYPE_MAP.put("saveAddress", 2009);
		TRANSACTION_TYPE_MAP.put("getHotWord", 2010);
		TRANSACTION_TYPE_MAP.put("addressList", 2011);
		TRANSACTION_TYPE_MAP.put("delAddress", 2012);

		TRANSACTION_TYPE_MAP.put("applyService", 3001);
		TRANSACTION_TYPE_MAP.put("subServiceOrder", 3002);
		TRANSACTION_TYPE_MAP.put("putSaleGoods", 3004);

		TRANSACTION_TYPE_MAP.put("stationMessage", 4001);
		TRANSACTION_TYPE_MAP.put("feedback", 4002);
		TRANSACTION_TYPE_MAP.put("getUserInfo", 4003);
		TRANSACTION_TYPE_MAP.put("validateMsgCode", 4004);
		TRANSACTION_TYPE_MAP.put("bindMobile", 4005);
		TRANSACTION_TYPE_MAP.put("payerInfo", 4006);
		TRANSACTION_TYPE_MAP.put("addPayeeInfo", 4007);
		TRANSACTION_TYPE_MAP.put("changeUserBaseInfo", 4008);
		TRANSACTION_TYPE_MAP.put("changepwd", 4009);
		TRANSACTION_TYPE_MAP.put("serviceOrderList", 4010);
		TRANSACTION_TYPE_MAP.put("serviceOrderInfo", 4011);
		TRANSACTION_TYPE_MAP.put("receivInfo", 4012);
		TRANSACTION_TYPE_MAP.put("putLogistics", 4013);
		TRANSACTION_TYPE_MAP.put("logisticsInfos", 4014);
		TRANSACTION_TYPE_MAP.put("delServiceOrder", 4015);
		TRANSACTION_TYPE_MAP.put("confirmGoods", 4016);
		TRANSACTION_TYPE_MAP.put("getServiceIdentify", 4017);
		TRANSACTION_TYPE_MAP.put("directBuyOrderList", 4018);
		TRANSACTION_TYPE_MAP.put("directBuyOrderInfo", 4019);
		TRANSACTION_TYPE_MAP.put("cancelDirectBuyOrder", 4020);
		TRANSACTION_TYPE_MAP.put("getDirectBuyIdentify", 4021);
		TRANSACTION_TYPE_MAP.put("reviewBuyOrderInfo", 4022);
		TRANSACTION_TYPE_MAP.put("directGoodsList", 4023);
		TRANSACTION_TYPE_MAP.put("directGoodsDec", 4024);
		TRANSACTION_TYPE_MAP.put("cancelOrder", 4025);
		TRANSACTION_TYPE_MAP.put("selectAudit", 4026);
		TRANSACTION_TYPE_MAP.put("directBuyOrderInfo", 4027);

		TRANSACTION_TYPE_MAP.put("updateOrderAddress", 4028);
		TRANSACTION_TYPE_MAP.put("weixinPay", 9527);

		TRANSACTION_TYPE_MAP.put("sale", 5001);

	}

}
