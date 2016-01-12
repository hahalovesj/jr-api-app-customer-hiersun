package com.hiersun.jewelry.api.entity.vo;


public class Material {
	
	public Material(){
		
	}
	
	
	public Material(Long materialID,String name){
		
		this.materialID = materialID;
		this.name = name;
		
	}

	private Long materialID;
	
	private String name;

	
	public Long getMaterialID() {
		return materialID;
	}


	public void setMaterialID(Long materialID) {
		this.materialID = materialID;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
