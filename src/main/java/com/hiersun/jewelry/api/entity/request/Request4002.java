package com.hiersun.jewelry.api.entity.request;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.hiersun.jewelry.api.entity.Body;

public class Request4002 extends Body implements Serializable{
	
	/**
	* @Fields serialVersionUID : TODO()
	*/ 
	private static final long serialVersionUID = -1171920935325054676L;
	
	String feedbackContent;
	
	
	public String getFeedbackContent() {
		return feedbackContent;
	}


	public void setFeedbackContent(String feedbackContent) {
		this.feedbackContent = feedbackContent;
	}


	@Override
	public int volidateValue() {
		if(StringUtils.isEmpty(this.getFeedbackContent())){
			return 400201;
		}
		return 0;
	}

}
