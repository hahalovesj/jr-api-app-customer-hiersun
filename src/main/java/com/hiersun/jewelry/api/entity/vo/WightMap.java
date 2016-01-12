package com.hiersun.jewelry.api.entity.vo;

import java.util.List;

public class WightMap {
	
	public WightMap(){
		
	}
	
	public WightMap(Long pID,List<Wight> wightList){
		this.pID = pID;
		this.wightList = wightList;
	}

	private Long pID;
	
	private List<Wight> wightList;

	public Long getpID() {
		return pID;
	}

	public void setpID(Long pID) {
		this.pID = pID;
	}

	public List<Wight> getWightList() {
		return wightList;
	}

	public void setWightList(List<Wight> wightList) {
		this.wightList = wightList;
	}
	
	
}
