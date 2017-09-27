package com.esm.util;

public class PageInfo {
	
	/**
	 * 从第几页开始.
	 */
	private Integer start;
	
	/**
	 * 需要查几条数据.
	 */
	private Integer limit;
	
	public PageInfo(){};
	
	public PageInfo(Integer start,Integer limit) {
		this.start = start;
		this.limit = limit;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}
}
