package com.hiersun.jewelry.api.entity.response;

import java.util.ArrayList;

import com.hiersun.jewelry.api.entity.ResponseBody;

public class Response2010 extends ResponseBody {

	private ArrayList<String> hotWords;

	public ArrayList<String> getHotWords() {
		return hotWords;
	}

	public void setHotWords(ArrayList<String> hotWords) {
		this.hotWords = hotWords;
	}

}
