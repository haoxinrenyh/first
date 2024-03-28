package com.stonedt.spider.entity;

import java.util.Date;

public class Websiteshie {
	private String web_url;

	private String id;

	private Date create_date;

	private String remark;

	public String getWeb_url() {
		return web_url;
	}

	public void setWeb_url(String web_url) {
		this.web_url = web_url;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "Websiteshie [web_url=" + web_url + ", id=" + id + ", create_date=" + create_date + ", remark=" + remark
				+ "]";
	}
	

	
	
}
