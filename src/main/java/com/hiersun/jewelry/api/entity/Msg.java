package com.hiersun.jewelry.api.entity;


public class Msg<H extends Header, B extends Body> {

	private H head;

	private B body;

	public H getHead() {
		return head;
	}

	public void setHead(H head) {
		this.head = head;
	}

	public B getBody() {
		return body;
	}

	public void setBody(B body) {
		this.body = body;
	}

}
