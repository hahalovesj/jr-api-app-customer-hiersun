package com.hiersun.jewelry.api.entity.vo;

import java.util.List;

public class MaterialMap {
	
	public MaterialMap(){
		
	}
	
	public MaterialMap(Long pID,List<Material> materialList){
		this.pID = pID;
		this.materialList = materialList;
	}

	private Long pID;
	
	private List<Material> materialList;

	public Long getpID() {
		return pID;
	}

	public void setpID(Long pID) {
		this.pID = pID;
	}

	public List<Material> getMaterialList() {
		return materialList;
	}

	public void setMaterialList(List<Material> materialList) {
		this.materialList = materialList;
	}

	
	
	
}
