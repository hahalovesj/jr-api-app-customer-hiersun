package com.hiersun.jewelry.api.entity;

/**
 * @author lilong
 *
 */
public abstract class Header {

	/** 流水号 */
	public Long messageID;

	/** 时间戳 */
	public Long timeStamp;

	/** 接口编码 */
	public String transactionType;


	public abstract int volidateValue();

	public Long getMessageID() {
		return messageID;
	}

	public void setMessageID(Long messageID) {
		this.messageID = messageID;
	}

	public Long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

}
