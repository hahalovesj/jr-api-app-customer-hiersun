package com.hiersun.jewelry.api.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.hiersun.jewelry.api.entity.RequestHeader;
import com.hiersun.jewelry.api.entity.ResponseBody;
import com.hiersun.jewelry.api.entity.ResponseHeader;

public class ResponseUtil {
	private static Logger log = Logger.getLogger(ResponseUtil.class);
	/**
	 * 配置公用的返回header
	 * 
	 * @param reqHead
	 * @param resCode
	 * @return
	 */
	public static ResponseHeader getRespHead(RequestHeader reqHead, int resCode) {
		ResponseHeader respHead = new ResponseHeader(resCode);
		respHead.setMessageID(reqHead.getMessageID());
		respHead.setTimeStamp(new Date().getTime());
		respHead.setTransactionType(reqHead.getTransactionType());
		return respHead;
	}

	/**
	 * 返回json
	 */
	public static void ResponseWriter(ResponseHeader respHead, ResponseBody responseBody, HttpServletResponse response)
			throws Exception {
		Map<String, Object> responseMsg = new HashMap<String, Object>();
		responseMsg.put("body", responseBody);
		responseMsg.put("head", respHead);
		String returnString = JSON.toJSONString(responseMsg);
		log.info("return JSON:"+responseMsg);
		response.getWriter().print(returnString);
	}
	
	/**
	 * 返回json
	 */
	public static void ResponseWriter(Map<String, Object> responseMsg,HttpServletResponse response)
			throws Exception {
		response.getWriter().print(JSON.toJSONString(responseMsg));
	}
}
