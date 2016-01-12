package com.hiersun.jewelry.api.entity.response;

import java.util.List;

import com.hiersun.jewelry.api.direct.domain.QueryGoodsByParamVo;
import com.hiersun.jewelry.api.entity.ResponseBody;

public class Response2002 extends ResponseBody {

	private String keyWord;

	private List<Object> goodsList;

	// private PageInfo pageInfo;

	private int pageNO;

	private boolean isEnd;

	public int getPageNO() {
		return pageNO;
	}

	public void setPageNO(int pageNO) {
		this.pageNO = pageNO;
	}

	public boolean getIsEnd() {
		return isEnd;
	}

	public void setIsEnd(boolean isEnd) {
		this.isEnd = isEnd;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public List<Object> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List<Object> goodsList) {
		this.goodsList = goodsList;
	}

}
