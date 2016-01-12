package com.hiersun.jewelry.api.entity.vo;

public class PageInfo {

	private int pageNo;

	private int pageTotal;

	private int pageSize;

	private int goodsTotal;

	public PageInfo() {
	}

	public PageInfo(int pageNo, int pageTotal, int pageSize, int goodsTotal) {
		super();
		this.pageNo = pageNo;
		this.pageTotal = pageTotal;
		this.pageSize = pageSize;
		this.goodsTotal = goodsTotal;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageTotal() {
		return pageTotal;
	}

	public void setPageTotal(int pageTotal) {
		this.pageTotal = pageTotal;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getGoodsTotal() {
		return goodsTotal;
	}

	public void setGoodsTotal(int goodsTotal) {
		this.goodsTotal = goodsTotal;
	}

}
