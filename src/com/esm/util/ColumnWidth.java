package com.esm.util;

public class ColumnWidth {

	/**
	 * 列号.
	 */
	private Integer columnNum;
	
	/**
	 * 列宽.
	 */
	private Integer width;
	
	/**
	 * 无参构造方法.
	 */
	public ColumnWidth(){
		
	}
	
	/**
    获取columnNum的当前值.
    @return 得到的columnNum的值
    */
	public Integer getColumnNum() {
		return columnNum;
	}
	 /**
    给columnNum赋予新的值.
    @param columnNum 赋予columnNum新的值
    */
	public void setColumnNum(Integer columnNum) {
		this.columnNum = columnNum;
	}
	/**
    获取width的当前值.
    @return 得到的width的值
    */
	public Integer getWidth() {
		return width;
	}
	/**
    给width赋予新的值.
    @param width 赋予width新的值
    */
	public void setWidth(Integer width) {
		this.width = width;
	}
}
