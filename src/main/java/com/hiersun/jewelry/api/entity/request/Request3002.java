package com.hiersun.jewelry.api.entity.request;

import java.util.List;
import java.util.Map;

import com.hiersun.jewelry.api.entity.Body;

public class Request3002 extends Body{
	private int serviceType;
	private List<Map> goodsPicList;
	private Long addressID;
	private Long material;
	private Long weight;
	private String goodsName;
	private String goodsDec;
	private double payFor;

	public int getServiceType() {
		return serviceType;
	}

	public void setServiceType(int serviceType) {
		this.serviceType = serviceType;
	}

	public List<Map> getGoodsPicList() {
		return goodsPicList;
	}

	public void setGoodsPicList(List<Map> goodsPicList) {
		this.goodsPicList = goodsPicList;
	}

	public Long getAddressID() {
		return addressID;
	}

	public void setAddressID(Long addressID) {
		this.addressID = addressID;
	}

	public Long getMaterial() {
		return material;
	}

	public void setMaterial(Long material) {
		this.material = material;
	}

	public Long getWeight() {
		return weight;
	}

	public void setWeight(Long weight) {
		this.weight = weight;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsDec() {
		return goodsDec;
	}

	public void setGoodsDec(String goodsDec) {
		this.goodsDec = goodsDec;
	}

	public double getPayFor() {
		return payFor;
	}

	public void setPayFor(double payFor) {
		this.payFor = payFor;
	}

	@Override
	public int volidateValue() {
		// TODO Auto-generated method stub
		return 0;
	}

}
