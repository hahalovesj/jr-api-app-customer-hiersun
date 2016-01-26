package com.hiersun.jewelry.api.entity.request;

import com.hiersun.jewelry.api.entity.Body;

public class RequestImgDown extends Body  {
	
	private String Id;
	
	private String fileDir;
	
	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getFileDir() {
		return fileDir;
	}

	public void setFileDir(String fileDir) {
		this.fileDir = fileDir;
	}

	@Override
	public int volidateValue() {
		// TODO Auto-generated method stub
		return 0;
	}
	

}
