package com.hiersun.jewelry.api.entity.vo;


public class Wight {
	
	public Wight(){}
	
	public Wight(Long wightID,String name){
		this.wightID = wightID;
		this.name = name;
	}
	
	private Long wightID;
	private String name;
 
	
	
	public Long getWightID() {
		return wightID;
	}
	public void setWightID(Long wightID) {
		this.wightID = wightID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	
	
}
