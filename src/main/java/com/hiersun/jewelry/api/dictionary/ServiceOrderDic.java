package com.hiersun.jewelry.api.dictionary;

import java.util.List;
import java.util.Map;

import com.hiersun.jewelry.api.entity.vo.Material;
import com.hiersun.jewelry.api.entity.vo.MaterialMap;
import com.hiersun.jewelry.api.entity.vo.ServiceMoneyMap;
import com.hiersun.jewelry.api.entity.vo.WightMap;

public class ServiceOrderDic {

	private List<Material> firstTypeList;
	
	private List<MaterialMap> secondTypeList;
	
	private List<WightMap> wightMapList;
	
	private Map<String,String> address;
	
	private List<ServiceMoneyMap> moneyList;
	
	

	public List<Material> getFirstTypeList() {
		return firstTypeList;
	}

	public void setFirstTypeList(List<Material> firstTypeList) {
		this.firstTypeList = firstTypeList;
	}

	public List<MaterialMap> getSecondTypeList() {
		return secondTypeList;
	}

	public void setSecondTypeList(List<MaterialMap> secondTypeList) {
		this.secondTypeList = secondTypeList;
	}

	public List<WightMap> getWightMapList() {
		return wightMapList;
	}

	public void setWightMapList(List<WightMap> wightMapList) {
		this.wightMapList = wightMapList;
	}

	public Map<String, String> getAddress() {
		return address;
	}

	public void setAddress(Map<String, String> address) {
		this.address = address;
	}

	public List<ServiceMoneyMap> getMoneyList() {
		return moneyList;
	}

	public void setMoneyList(List<ServiceMoneyMap> moneyList) {
		this.moneyList = moneyList;
	}
	
	
}
