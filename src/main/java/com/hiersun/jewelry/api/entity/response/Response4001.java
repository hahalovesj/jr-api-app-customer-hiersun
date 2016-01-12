package com.hiersun.jewelry.api.entity.response;

import java.util.List;

import com.hiersun.jewelry.api.entity.vo.StationMessage;

public class Response4001 {
	
	private List<StationMessage> stationMessageList;
	
	private int pageNO;
	
	private boolean isEnd;
	
	public List<StationMessage> getStationMessageList() {
		return stationMessageList;
	}
	public void setStationMessageList(List<StationMessage> stationMessageList) {
		this.stationMessageList = stationMessageList;
	}
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
	

}
