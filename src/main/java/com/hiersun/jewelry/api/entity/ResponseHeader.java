package com.hiersun.jewelry.api.entity;

import java.util.Date;

import com.hiersun.jewelry.api.dictionary.RecodeMsgMap;

/**
 * @author lilong
 *
 */
public class ResponseHeader extends Header {

	private Integer resCode;

	private String message;
	
	public ResponseHeader() {
	}

	public ResponseHeader(Integer resCode) {
		this.timeStamp = new Date().getTime();
		this.resCode = resCode;
		this.message = RecodeMsgMap.RECODEMSGMAP.get(resCode);
	}

	/***
	 * 
	 * @param resCode
	 *            返回码
	 * @param messageID
	 *            消息ID
	 * @param transactionType
	 *            接口编码
	 */
	public ResponseHeader(Integer resCode, Long messageID, String transactionType) {
		this.timeStamp = new Date().getTime();
		this.transactionType = transactionType;
		this.messageID = messageID;
		this.resCode = resCode;
		this.message = RecodeMsgMap.RECODEMSGMAP.get(resCode);
	}

	

	public Integer getResCode() {
		return resCode;
	}

	public void setResCode(Integer resCode) {
		this.resCode = resCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public int volidateValue() {
		// TODO Auto-generated method stub
		return 0;
	}

}
