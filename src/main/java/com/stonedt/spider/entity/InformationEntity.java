package com.stonedt.spider.entity;

import java.io.Serializable;

public class InformationEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String title;
	private String content;
	private String contenthtml;
	private String originwebsitename;
	private String source_name;
	private String publish_time;
	private String outLine;
	private String seed_name;
	private String picUrl;
	private Integer article_id;
	private String publishtime;
	private String picC;
	private String seed_id;
	private String source_url;
	private String keyword;
	private String stock;
	private String plate;
	private int emotiontype;
	private Integer article_type;
	private Integer collect_id;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSource_url() {
		return source_url;
	}
	public void setSource_url(String source_url) {
		this.source_url = source_url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getContenthtml() {
		return contenthtml;
	}
	public void setContenthtml(String contenthtml) {
		this.contenthtml = contenthtml;
	}
	public String getOriginwebsitename() {
		return originwebsitename;
	}
	public void setOriginwebsitename(String originwebsitename) {
		this.originwebsitename = originwebsitename;
	}
	public String getSource_name() {
		return source_name;
	}
	public void setSource_name(String source_name) {
		this.source_name = source_name;
	}
	public String getPublish_time() {
		return publish_time;
	}
	public void setPublish_time(String publish_time) {
		this.publish_time = publish_time;
	}
	public String getOutLine() {
		return outLine;
	}
	public void setOutLine(String outLine) {
		this.outLine = outLine;
	}
	public String getSeed_name() {
		return seed_name;
	}
	public void setSeed_name(String seed_name) {
		this.seed_name = seed_name;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public Integer getArticle_id() {
		return article_id;
	}
	public void setArticle_id(Integer article_id) {
		this.article_id = article_id;
	}
	public String getPublishtime() {
		return publishtime;
	}
	public void setPublishtime(String publishtime) {
		this.publishtime = publishtime;
	}
	public String getPicC() {
		return picC;
	}
	public void setPicC(String picC) {
		this.picC = picC;
	}
	public String getSeed_id() {
		return seed_id;
	}
	public void setSeed_id(String seed_id) {
		this.seed_id = seed_id;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getStock() {
		return stock;
	}
	public void setStock(String stock) {
		this.stock = stock;
	}
	public String getPlate() {
		return plate;
	}
	public void setPlate(String plate) {
		this.plate = plate;
	}
	public int getEmotiontype() {
		return emotiontype;
	}
	public void setEmotiontype(int emotiontype) {
		this.emotiontype = emotiontype;
	}	
	public Integer getArticle_type() {
		return article_type;
	}
	public void setArticle_type(Integer article_type) {
		this.article_type = article_type;
	}
	public Integer getCollect_id() {
		return collect_id;
	}
	public void setCollect_id(Integer collect_id) {
		this.collect_id = collect_id;
	}
	public InformationEntity(Integer id, String title, String content, String contenthtml, String originwebsitename,
			String source_name, String publish_time, String outLine, String seed_name, String picUrl,
			Integer article_id, String publishtime, String picC, String seed_id, String source_url, String keyword,
			String stock, String plate, int emotiontype, Integer article_type, Integer collect_id) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.contenthtml = contenthtml;
		this.originwebsitename = originwebsitename;
		this.source_name = source_name;
		this.publish_time = publish_time;
		this.outLine = outLine;
		this.seed_name = seed_name;
		this.picUrl = picUrl;
		this.article_id = article_id;
		this.publishtime = publishtime;
		this.picC = picC;
		this.seed_id = seed_id;
		this.source_url = source_url;
		this.keyword = keyword;
		this.stock = stock;
		this.plate = plate;
		this.emotiontype = emotiontype;
		this.article_type = article_type;
		this.collect_id = collect_id;
	}
	public InformationEntity() {
		super();
	}
	@Override
	public String toString() {
		return "InformationEntity [id=" + id + ", title=" + title + ", content=" + content + ", contenthtml="
				+ contenthtml + ", originwebsitename=" + originwebsitename + ", source_name=" + source_name
				+ ", publish_time=" + publish_time + ", outLine=" + outLine + ", seed_name=" + seed_name + ", picUrl="
				+ picUrl + ", article_id=" + article_id + ", publishtime=" + publishtime + ", picC=" + picC
				+ ", seed_id=" + seed_id + ", source_url=" + source_url + ", keyword=" + keyword + ", stock=" + stock
				+ ", plate=" + plate + ", emotiontype=" + emotiontype + ", article_type=" + article_type
				+ ", collect_id=" + collect_id + "]";
	}
	
}
