package com.hiersun.jewelry.api.entity.vo;

public class ServiceMoneyMap {
	
	public ServiceMoneyMap(){}
	
	public ServiceMoneyMap(Long pID,double jdMoney,double fxMoney,double jdAndFxMoney){
		this.pID = pID;
		this.jdAndFxMoney = jdAndFxMoney;
		this.jdMoney = jdMoney;
		this.fxMoney = fxMoney;
		
	}
	
	private Long pID;
	
	private double jdMoney;
	
	private double fxMoney;
	
	private double jdAndFxMoney;
	

	public Long getpID() {
		return pID;
	}

	public void setpID(Long pID) {
		this.pID = pID;
	}

	public double getJdMoney() {
		return jdMoney;
	}

	public void setJdMoney(double jdMoney) {
		this.jdMoney = jdMoney;
	}

	public double getFxMoney() {
		return fxMoney;
	}

	public void setFxMoney(double fxMoney) {
		this.fxMoney = fxMoney;
	}

	public double getJdAndFxMoney() {
		return jdAndFxMoney;
	}

	public void setJdAndFxMoney(double jdAndFxMoney) {
		this.jdAndFxMoney = jdAndFxMoney;
	}
	
	

}
