package com.stonedt.spider.entity;

import java.util.Date;

public class AllocationEntity {
	//展示
	private String website_name;
	private String config_name;
	private Date create_date;
	private String status;
	private String sum_value;
	private String id;
	
	
	
	
	//添加
	private String url;
	private String search_rule;
	private String url_config;
	private String title_config;
	private String date_config;
	private String text_config;
	private String text_config_type;
	private String spider_type;
	private String request_type;
	private String result_type;
	private String website_id;
	private String isOnSite;
	private String request_config;
	private String update_date;
	
	//开启值
	private String msg;

	public String getWebsite_name() {
		return website_name;
	}

	public void setWebsite_name(String website_name) {
		this.website_name = website_name;
	}

	public String getConfig_name() {
		return config_name;
	}

	public void setConfig_name(String config_name) {
		this.config_name = config_name;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSum_value() {
		return sum_value;
	}

	public void setSum_value(String sum_value) {
		this.sum_value = sum_value;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSearch_rule() {
		return search_rule;
	}

	public void setSearch_rule(String search_rule) {
		this.search_rule = search_rule;
	}

	public String getUrl_config() {
		return url_config;
	}

	public void setUrl_config(String url_config) {
		this.url_config = url_config;
	}

	public String getTitle_config() {
		return title_config;
	}

	public void setTitle_config(String title_config) {
		this.title_config = title_config;
	}

	public String getDate_config() {
		return date_config;
	}

	public void setDate_config(String date_config) {
		this.date_config = date_config;
	}

	public String getText_config() {
		return text_config;
	}

	public void setText_config(String text_config) {
		this.text_config = text_config;
	}

	public String getText_config_type() {
		return text_config_type;
	}

	public void setText_config_type(String text_config_type) {
		this.text_config_type = text_config_type;
	}

	public String getSpider_type() {
		return spider_type;
	}

	public void setSpider_type(String spider_type) {
		this.spider_type = spider_type;
	}

	public String getRequest_type() {
		return request_type;
	}

	public void setRequest_type(String request_type) {
		this.request_type = request_type;
	}

	public String getResult_type() {
		return result_type;
	}

	public void setResult_type(String result_type) {
		this.result_type = result_type;
	}

	public String getWebsite_id() {
		return website_id;
	}

	public void setWebsite_id(String website_id) {
		this.website_id = website_id;
	}

	public String getIsOnSite() {
		return isOnSite;
	}

	public void setIsOnSite(String isOnSite) {
		this.isOnSite = isOnSite;
	}

	public String getRequest_config() {
		return request_config;
	}

	public void setRequest_config(String request_config) {
		this.request_config = request_config;
	}

	public String getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "AllocationEntity [website_name=" + website_name + ", config_name=" + config_name + ", create_date="
				+ create_date + ", status=" + status + ", sum_value=" + sum_value + ", id=" + id + ", url=" + url
				+ ", search_rule=" + search_rule + ", url_config=" + url_config + ", title_config=" + title_config
				+ ", date_config=" + date_config + ", text_config=" + text_config + ", text_config_type="
				+ text_config_type + ", spider_type=" + spider_type + ", request_type=" + request_type
				+ ", result_type=" + result_type + ", website_id=" + website_id + ", isOnSite=" + isOnSite
				+ ", request_config=" + request_config + ", update_date=" + update_date + ", msg=" + msg + "]";
	}

	
	
	
	
	
}
