package com.stonedt.spider.entity;

import java.io.Serializable;

public class NewsEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String code;
	private String title;
	private String publishTime;
	private String contentHtml;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}
	public String getContentHtml() {
		return contentHtml;
	}
	public void setContentHtml(String contentHtml) {
		this.contentHtml = contentHtml;
	}
	@Override
	public String toString() {
		return "NewsEntity [id=" + id + ", code=" + code + ", title=" + title + ", publishTime=" + publishTime
				+ ", contentHtml=" + contentHtml + "]";
	}
	
	
	
	
}
