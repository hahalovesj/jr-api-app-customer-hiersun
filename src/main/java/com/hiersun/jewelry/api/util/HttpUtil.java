package com.hiersun.jewelry.api.util;

import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;

/**
 * <p>
 * Title:
 * </p>
 *
 * <p>
 * Description:
 * </p>
 *
 * <p>
 * Copyright: Copyright (c) 2011
 * </p>
 *
 * <p>
 * Company:
 * </p>
 *
 * @author not attributable
 * @version 1.0
 */

public class HttpUtil {

	private final Logger log = Logger.getLogger(HttpUtil.class);
	private String charset = "utf-8";
	private String requestCharset = "utf-8";
	private NameValuePair[] data;

	public String getRequestCharset() {
		return requestCharset;
	}

	public void setRequestCharset(String requestCharset) {
		this.requestCharset = requestCharset;
	}

	public NameValuePair[] getData() {
		return data;
	}

	public void setData(NameValuePair[] data) {
		this.data = data;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String doHPPost(Map<String, Object> msg) throws Exception {

		this.setData(this.getNameValuePairs((Map<String, String>) msg
				.get("propertites")));

		long begin = System.currentTimeMillis();
		long usedTimes = 0;

		MyPostMethod httppost = new MyPostMethod(msg.get("url").toString());
		HttpClient client = new HttpClient();
		client.getHttpConnectionManager().getParams()
				.setConnectionTimeout(10000);

		client.getHttpConnectionManager().getParams().setSoTimeout(20000);

		client.getHttpConnectionManager()
				.getParams()
				.setIntParameter(HttpMethodParams.BUFFER_WARN_TRIGGER_LIMIT,
						10 * 1024 * 1024);
		client.getHttpConnectionManager().getParams()
				.setReceiveBufferSize(10 * 1024 * 1024);

		// new NameValuePair("msg",new String(msg.getBytes("GBK"),"UTF-8"))
		httppost.setRequestBody(getData());

		try {
			client.executeMethod(httppost);
			String response = httppost.getResponseBodyAsString();
			usedTimes = System.currentTimeMillis() - begin;
			log.info("发送信息成功！耗时[" + usedTimes + "]返回[" + response + "] ");

			return response;
		} catch (SocketTimeoutException e) {
			throw new SocketTimeoutException("发送信息：[异常]." + e.getMessage());
		} catch (ConnectTimeoutException ex) {
			throw new Exception("发送信息失败[链接超时]." + ex.getMessage());
		} catch (Exception ex) {
			throw new Exception("发送信息异常[异常] " + ex.getMessage());
		}
	}

	private class MyPostMethod extends PostMethod {
		public MyPostMethod(String url) {
			super(url);
		}

		@Override
		public String getRequestCharSet() {
			return HttpUtil.this.getRequestCharset();
		}
	}

	protected NameValuePair[] getNameValuePairs(Map<String, String> paramsMap)
			throws Exception {
		// TODO Auto-generated method stub
		Set<Entry<String, String>> entrySet = paramsMap.entrySet();
		NameValuePair[] data = new NameValuePair[paramsMap.size()];
		int count = 0;
		for (Entry<String, String> entry : entrySet) {
			data[count] = new NameValuePair(entry.getKey(), entry.getValue());
			count++;
		}
		return data;
	}

	public static void main(String[] args) throws Exception {

		Map<String, Object> msg = new HashMap<String, Object>();
		msg.put("url", "http://localhost:8088/commonservice/sms/sendmessage");
		Map<String, Object> propertitesMap = new HashMap<String, Object>();
		propertitesMap.put("content", "您的6位验证码为：" + 156834 + "【珍宝岛】");
		propertitesMap.put("mobiles", "15652388352");
		propertitesMap.put("appName", "珍宝岛");
		propertitesMap.put("scenario", "注册");
		msg.put("propertites", propertitesMap);

		new HttpUtil().doHPPost(msg);
	}
}
