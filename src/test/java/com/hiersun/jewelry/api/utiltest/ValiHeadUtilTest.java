package com.hiersun.jewelry.api.utiltest;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.hiersun.jewelry.api.dictionary.RecodeMsgMap;
import com.hiersun.jewelry.api.dictionary.TransactionTypeMap;
import com.hiersun.jewelry.api.entity.RequestHeader;
import com.hiersun.jewelry.api.entity.ResponseHeader;
import com.hiersun.jewelry.api.entity.VersionInfo;
import com.hiersun.jewelry.api.util.DateUtil;
import com.hiersun.jewelry.api.util.MD5;
import com.hiersun.jewelry.api.util.ProperContextUtil;

/**
 * 验证客户端传递的参数
 * 
 * @author xueyuan
 *
 */
public class ValiHeadUtilTest {

	/**
	 * 验证token
	 * 
	 * @param msg
	 * @param response
	 * @throws Exception
	 */
	public static void veriToken(String msg) throws Exception {

		// 把json转为map
		Map msgEntity = JSON.parseObject(msg, Map.class);
		// 获取head信息
		String requestHeadStr = JSON.toJSONString(msgEntity.get("head"));
		// 根据head的json串转换成HeadBend
		RequestHeader reqHead = JSON.parseObject(requestHeadStr, RequestHeader.class);

		int transactionType = TransactionTypeMap.TRANSACTION_TYPE_MAP.get(reqHead.getTransactionType());

		// if(transactionType){}

	}

	/**
	 * 
	 * @param msg
	 * @return
	 */
	public static  Map<String,Object> map = new HashMap<String,Object>();
	public static boolean VeriParams(String msg) throws Exception {
		ResponseHeader responseHeader = new ResponseHeader();

		// 请求msg为空
		if (msg == null || msg.trim().length() < 1) {
			return false;
		}
		System.out.println("msg === " + msg);
//		String newMsg = msg.replace(" ", "");
		String newMsg = msg;
		RequestHeader reqHead = null;
		String bodyStr = "";
		try {
			// 把json转为map
			Map msgEntity = JSON.parseObject(newMsg, Map.class);
			// 获取head信息
			String requestHeadStr = JSON.toJSONString(msgEntity.get("head"));
			// 去除body的值
		
			if(newMsg.indexOf("\"body\":")>newMsg.indexOf("\"head\":")){
				bodyStr  = newMsg.substring(newMsg.indexOf("\"body\":")+7, newMsg.length()-1); 
			}else{
				bodyStr  = newMsg.substring(newMsg.indexOf("\"body\":")+7, newMsg.indexOf("\"head\":")-1); 
			}
			

			// 根据head的json串转换成HeadBend
			reqHead = JSON.parseObject(requestHeadStr, RequestHeader.class);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		if (reqHead == null) {
			// 请求head为空
			return false;
		}

		if (reqHead.getTab() == null) {
			// messageId（流水号）为空
			responseHeader.setResCode(900011);
			responseHeader.setMessage(RecodeMsgMap.RECODEMSGMAP.get(900011));

			Map<String, Object> responseMsg = new HashMap<String, Object>();
			responseMsg.put("head", responseHeader);
			responseMsg.put("body", null);
			map = responseMsg;
			return false;
		}

		if (reqHead.getMessageID() == null) {
			// messageId（流水号）为空
			responseHeader.setResCode(900003);
			responseHeader.setMessage(RecodeMsgMap.RECODEMSGMAP.get(900003));

			Map<String, Object> responseMsg = new HashMap<String, Object>();
			responseMsg.put("head", responseHeader);
			responseMsg.put("body", null);
			map = responseMsg;
			return false;
		}

		if (reqHead.getTimeStamp() == null) {
			// timeStamp（时间戳）为空
			responseHeader.setResCode(900004);
			responseHeader.setMessage(RecodeMsgMap.RECODEMSGMAP.get(900004));

			Map<String, Object> responseMsg = new HashMap<String, Object>();
			responseMsg.put("head", responseHeader);
			responseMsg.put("body", null);
			map = responseMsg;
			return false;
		}

		if (reqHead.getTransactionType() == null || reqHead.getTransactionType().trim().length() < 1) {
			// transactionType(接口编码) 为空
			responseHeader.setResCode(900005);
			responseHeader.setMessage(RecodeMsgMap.RECODEMSGMAP.get(900005));

			Map<String, Object> responseMsg = new HashMap<String, Object>();
			responseMsg.put("head", responseHeader);
			responseMsg.put("body", null);
			map = responseMsg;
			return false;
		}

		String tranType = reqHead.getTransactionType();
		// 如果 TransactionType（接口编码）在字典中没有找到
		if (TransactionTypeMap.TRANSACTION_TYPE_MAP.get(tranType) == null) {
			responseHeader.setResCode(900005);
			responseHeader.setMessage(RecodeMsgMap.RECODEMSGMAP.get(900005));

			Map<String, Object> responseMsg = new HashMap<String, Object>();
			responseMsg.put("head", responseHeader);
			responseMsg.put("body", null);
			map = responseMsg;
			return false;
		}

		if (reqHead.getSign() == null || reqHead.getSign().trim().length() < 1) {
			// sign 为空
			responseHeader.setResCode(900006);
			responseHeader.setMessage(RecodeMsgMap.RECODEMSGMAP.get(900006));

			Map<String, Object> responseMsg = new HashMap<String, Object>();
			responseMsg.put("head", responseHeader);
			responseMsg.put("body", null);
			map = responseMsg;
			return false;
		}

		String sign = MD5.MD5Encode(reqHead.getMessageID() + reqHead.getTransactionType() + reqHead.getTimeStamp()
				+ reqHead.getToken() + bodyStr);

		if (!sign.toUpperCase().trim().equals(reqHead.getSign().toUpperCase().trim())) {
			// sign 校验不通过
			responseHeader.setResCode(900006);
			responseHeader.setMessage(RecodeMsgMap.RECODEMSGMAP.get(900006));

			Map<String, Object> responseMsg = new HashMap<String, Object>();
			responseMsg.put("head", responseHeader);
			responseMsg.put("body", null);
			map = responseMsg;
			return false;
		}
		return true;
	}

	/**
	 * url缺少必要请求体时，返回的json
	 * 
	 * @param responseHeader
	 * @return
	 */
	public static boolean responseNullHead(ResponseHeader responseHeader, HttpServletResponse response)
			throws Exception {
		// 请求head为空
		responseHeader.setTimeStamp(DateUtil.getTimeStamp());
		responseHeader.setResCode(900002);
		responseHeader.setMessage(RecodeMsgMap.RECODEMSGMAP.get(900002));

		Map<String, Object> responseMsg = new HashMap<String, Object>();
		responseMsg.put("head", responseHeader);
		responseMsg.put("body", null);
		System.out.println(JSON.toJSONString(responseMsg));
		return false;
	}

	/**
	 * 获取请求body的字符串格式
	 * 
	 * @param msg
	 * @return
	 */
	public static String getBodyStr(String msg) {
		Map msgEntity = JSON.parseObject(msg, Map.class);
		String requestBodyStr = JSON.toJSONString(msgEntity.get("body"));
		return requestBodyStr;
	}

	/**
	 * 获取请求Header实体
	 * 
	 * @param msg
	 * @return Header实体
	 */
	public static RequestHeader getRequestHeader(String msg) {
		Map msgEntity = JSON.parseObject(msg, Map.class);
		String requestHeadStr = JSON.toJSONString(msgEntity.get("head"));
		RequestHeader reqHead = JSON.parseObject(requestHeadStr, RequestHeader.class);
		return reqHead;
	}

	/**
	 * 获取TransactionType码
	 * 
	 * @param msg
	 * @return TransactionType (接口编码)
	 * 
	 */
	public static int getTranTypeCode(String msg) {
		// 把json转为map
		Map msgEntity = JSON.parseObject(msg, Map.class);
		// 获取head信息
		String requestHeadStr = JSON.toJSONString(msgEntity.get("head"));
		// 根据head的json串转换成HeadBend
		RequestHeader reqHead = JSON.parseObject(requestHeadStr, RequestHeader.class);

		return TransactionTypeMap.TRANSACTION_TYPE_MAP.get(reqHead.getTransactionType());
	}

	/**
	 * 检测版本号
	 * 
	 * @param version
	 *            head的版本号
	 * @param terminal
	 *            手机系统 1.IOS 2.安卓
	 */
	public static VersionInfo checkVersion(String version, String terminal) {
		VersionInfo info = new VersionInfo();
		ProperContextUtil proper = new ProperContextUtil();
		String properName = "version.properties";
		String force = proper.getValue(properName, "force");

		String numStr = version.replace(".", "");
		if (terminal.toUpperCase().equals(proper.getValue(properName, "ios"))) {
			// IOS版本号

			String iosVer = proper.getValue(properName, "ios.version").replace(".", "");

			if (Integer.parseInt(iosVer) > Integer.parseInt(numStr)) {
				info.setDownUrl(proper.getValue(properName, "ios.versionUrl"));
				info.setForce(force.equals("true") ? true : false);
				info.setVersion(proper.getValue(properName, "ios.version"));
				info.setVersionDes(proper.getValue(properName, "versionDes"));
			}
			// if (!version.equals(proper.getValue(properName, "ios.version")))
			// {
			// info.setDownUrl(proper.getValue(properName, "ios.versionUrl"));
			// info.setForce(force.equals("true") ? true : false);
			// info.setVersion(proper.getValue(properName, "ios.version"));
			// info.setVersionDes(proper.getValue(properName, "versionDes"));
			// }
		} else {
			// 安卓的版本号
			String iosVer = proper.getValue(properName, "andrion.version").replace(".", "");

			if (Integer.parseInt(iosVer) > Integer.parseInt(numStr)) {
				info.setDownUrl(proper.getValue(properName, "andrion.versionUrl"));
				info.setForce(force.equals("true") ? true : false);
				info.setVersion(proper.getValue(properName, "andrion.version"));
				info.setVersionDes(proper.getValue(properName, "versinDes"));
			}
			// if (!version.equals(proper.getValue(properName,
			// "andrion.version"))) {
			// info.setDownUrl(proper.getValue(properName,
			// "andrion.versionUrl"));
			// info.setForce(force.equals("true") ? true : false);
			// info.setVersion(proper.getValue(properName, "andrion.version"));
			// info.setVersionDes(proper.getValue(properName, "versinDes"));
			// }
		}
		return info;
	}

	/**
	 * 获取TransactionType码
	 * 
	 * @param msg
	 * @return TransactionType (接口编码)
	 * 
	 */
	public static String getTranTypeStr(String msg) {
		// 把json转为map
		Map<String, Object> msgEntity = JSON.parseObject(msg, Map.class);
		// 获取head信息
		String requestHeadStr = JSON.toJSONString(msgEntity.get("head"));
		// 根据head的json串转换成HeadBend
		RequestHeader reqHead = JSON.parseObject(requestHeadStr, RequestHeader.class);

		return reqHead.getTransactionType();
	}

}
