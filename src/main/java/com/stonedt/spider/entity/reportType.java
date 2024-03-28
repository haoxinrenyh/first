package com.stonedt.spider.entity;

import java.io.Serializable;

public class reportType implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String rtype;
	private Integer num;
	public String getRtype() {
		return rtype;
	}
	public void setRtype(String rtype) {
		this.rtype = rtype;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public reportType(String rtype, Integer num) {
		super();
		this.rtype = rtype;
		this.num = num;
	}
	public reportType() {
		super();
	}
	

	
}
