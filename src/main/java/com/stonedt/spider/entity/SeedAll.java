package com.stonedt.spider.entity;

import java.util.Date;

public class SeedAll {
	private String id;
	private String seed_name;			//'任务名',
	private String seed_type;			//'任务类型，1代表html 2代表ajax 3代表jsonp',
	private String seed_status;			//'任务状态1代表正常，0代表关闭',
	private String seed_flag;			//'任务标记',
	private String website_id;			//'任务对应网站ID',
	private String create_user_id;		//'创建者ID',
	private Date create_date;			//'创建时间',
	private String first_type;			//'一级分类(例：热点概念  b2)',
	private String second_type;			//'文章分类',
	private String selenium_flag;		//'Selenium 1：使用    0：不使用',
	private String daili_flag;			//'代理：1：使用    0：不使用',
	private String seed_first_type;		//'文章一级分类，（政府，科技，金融）',
	private String level;
	private String website_name;		//对应网站名称
	private int totalnum;
	private int todaynum;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSeed_name() {
		return seed_name;
	}
	public void setSeed_name(String seed_name) {
		this.seed_name = seed_name;
	}
	public String getSeed_type() {
		return seed_type;
	}
	public void setSeed_type(String seed_type) {
		this.seed_type = seed_type;
	}
	public String getSeed_status() {
		return seed_status;
	}
	public void setSeed_status(String seed_status) {
		this.seed_status = seed_status;
	}
	public String getSeed_flag() {
		return seed_flag;
	}
	public void setSeed_flag(String seed_flag) {
		this.seed_flag = seed_flag;
	}
	public String getWebsite_id() {
		return website_id;
	}
	public void setWebsite_id(String website_id) {
		this.website_id = website_id;
	}
	public String getCreate_user_id() {
		return create_user_id;
	}
	public void setCreate_user_id(String create_user_id) {
		this.create_user_id = create_user_id;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	public String getFirst_type() {
		return first_type;
	}
	public void setFirst_type(String first_type) {
		this.first_type = first_type;
	}
	public String getSecond_type() {
		return second_type;
	}
	public void setSecond_type(String second_type) {
		this.second_type = second_type;
	}
	public String getSelenium_flag() {
		return selenium_flag;
	}
	public void setSelenium_flag(String selenium_flag) {
		this.selenium_flag = selenium_flag;
	}
	public String getDaili_flag() {
		return daili_flag;
	}
	public void setDaili_flag(String daili_flag) {
		this.daili_flag = daili_flag;
	}
	public String getSeed_first_type() {
		return seed_first_type;
	}
	public void setSeed_first_type(String seed_first_type) {
		this.seed_first_type = seed_first_type;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getWebsite_name() {
		return website_name;
	}
	public void setWebsite_name(String website_name) {
		this.website_name = website_name;
	}
	public int getTotalnum() {
		return totalnum;
	}
	public void setTotalnum(int totalnum) {
		this.totalnum = totalnum;
	}
	public int getTodaynum() {
		return todaynum;
	}
	public void setTodaynum(int todaynum) {
		this.todaynum = todaynum;
	}
	@Override
	public String toString() {
		return "SeedAll [id=" + id + ", seed_name=" + seed_name + ", seed_type=" + seed_type + ", seed_status="
				+ seed_status + ", seed_flag=" + seed_flag + ", website_id=" + website_id + ", create_user_id="
				+ create_user_id + ", create_date=" + create_date + ", first_type=" + first_type + ", second_type="
				+ second_type + ", selenium_flag=" + selenium_flag + ", daili_flag=" + daili_flag + ", seed_first_type="
				+ seed_first_type + ", level=" + level + ", website_name=" + website_name + ", totalnum=" + totalnum
				+ ", todaynum=" + todaynum + "]";
	}
	
	
}
