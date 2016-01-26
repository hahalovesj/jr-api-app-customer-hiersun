package com.hiersun.jewelry.api.entity.request;

import com.hiersun.jewelry.api.entity.Body;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

public class Request3002 extends Body{
	private Integer serviceType;
	private List<Map> goodsPicList;
	private Long addressID;
	private String material;
	private String weight;
	private String goodsName;
	private String goodsDec;
	private Double payFor;

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

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
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

	public Integer getServiceType() {
		return serviceType;
	}

	public void setServiceType(Integer serviceType) {
		this.serviceType = serviceType;
	}

	public Double getPayFor() {
		return payFor;
	}

	public void setPayFor(Double payFor) {
		this.payFor = payFor;
	}

	@Override
	public int volidateValue() {
		if(null == this.getServiceType()){
			return 900008;
		}
		if(this.getGoodsPicList().isEmpty()){
			return 900008;
		}
		if(null == this.getAddressID()){
			return 900008;
		}
		if(StringUtils.isBlank(this.getMaterial())){
			return 900008;
		}
		if(StringUtils.isBlank(this.getMaterial())){
			return 900008;
		}
		if(StringUtils.isBlank(this.getWeight())){
			return 900008;
		}
		if(StringUtils.isBlank(this.getGoodsName())){
			return 900008;
		}
		if(null == this.getPayFor()){
			return 900008;
		}
		return 0;
	}

}
