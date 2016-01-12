package com.hiersun.jewelry.api.entity.response;

import java.util.List;

import com.hiersun.jewelry.api.direct.domain.QueryGoodsByParamVo;
import com.hiersun.jewelry.api.direct.pojo.JrdsActivity;
import com.hiersun.jewelry.api.entity.ResponseBody;
import com.hiersun.jewelry.api.entity.VersionInfo;

public class Response2001 extends ResponseBody {

	private List<Object> bannerList;

	private List<Object> goodsList;

	private Integer pageNO;

	private boolean isEnd;

	private VersionInfo versionInfo;

	public VersionInfo getVersionInfo() {
		return versionInfo;
	}

	public void setVersionInfo(VersionInfo versionInfo) {
		this.versionInfo = versionInfo;
	}

	public Integer getPageNO() {
		return pageNO;
	}

	public void setPageNO(Integer pageNO) {
		this.pageNO = pageNO;
	}

	public boolean getIsEnd() {
		return isEnd;
	}

	public void setIsEnd(boolean isEnd) {
		this.isEnd = isEnd;
	}

	public List<Object> getBannerList() {
		return bannerList;
	}

	public void setBannerList(List<Object> bannerList) {
		this.bannerList = bannerList;
	}

	public List<Object> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List<Object> goodsList) {
		this.goodsList = goodsList;
	}

}
