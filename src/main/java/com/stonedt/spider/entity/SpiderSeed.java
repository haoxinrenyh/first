package com.stonedt.spider.entity;
/**
 * 爬虫种子
 * @author wangyi
 *
 */
public class SpiderSeed {
	private Integer id;
	private String seed_name;//任务名
	private Integer seed_type;//任务类型，1代表html 2代表ajax 3代表jsonp
	private Integer seed_status;//任务状态1代表正常，0代表关闭
	private Integer seed_flag;//任务标记
	private Integer website_id;//任务对应网站ID
	private Integer create_user_id;//创建者ID
	private String create_date;//创建时间
	private String website_name;//任务对应网站名称
	private Integer seed_article_type;
	private Integer selenium_flag;//selenium
	private Integer daili_flag;//daili
	private Integer seed_first_type;
	private Integer vpn_flag;
	private Integer totalnum; //总采集量
	private Integer todaynum; //当天
	private Integer screennum;//过滤文章数量
	
	
	public Integer getVpn_flag() {
		return vpn_flag;
	}
	public void setVpn_flag(Integer vpn_flag) {
		this.vpn_flag = vpn_flag;
	}
	public Integer getScreennum() {
		return screennum;
	}
	public void setScreennum(Integer screennum) {
		this.screennum = screennum;
	}
	public Integer getSeed_first_type() {
		return seed_first_type;
	}
	public void setSeed_first_type(Integer seed_first_type) {
		this.seed_first_type = seed_first_type;
	}
	public Integer getSeed_article_type() {
		return seed_article_type;
	}
	public void setSeed_article_type(Integer seed_article_type) {
		this.seed_article_type = seed_article_type;
	}
	public Integer getSelenium_flag() {
		return selenium_flag;
	}
	public void setSelenium_flag(Integer selenium_flag) {
		this.selenium_flag = selenium_flag;
	}
	public Integer getDaili_flag() {
		return daili_flag;
	}
	public void setDaili_flag(Integer daili_flag) {
		this.daili_flag = daili_flag;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSeed_name() {
		return seed_name;
	}
	public void setSeed_name(String seed_name) {
		this.seed_name = seed_name;
	}
	public Integer getSeed_type() {
		return seed_type;
	}
	public void setSeed_type(Integer seed_type) {
		this.seed_type = seed_type;
	}
	public Integer getSeed_status() {
		return seed_status;
	}
	public void setSeed_status(Integer seed_status) {
		this.seed_status = seed_status;
	}
	public Integer getSeed_flag() {
		return seed_flag;
	}
	public void setSeed_flag(Integer seed_flag) {
		this.seed_flag = seed_flag;
	}
	public Integer getWebsite_id() {
		return website_id;
	}
	public void setWebsite_id(Integer website_id) {
		this.website_id = website_id;
	}
	public Integer getCreate_user_id() {
		return create_user_id;
	}
	public void setCreate_user_id(Integer create_user_id) {
		this.create_user_id = create_user_id;
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	public String getWebsite_name() {
		return website_name;
	}
	public void setWebsite_name(String website_name) {
		this.website_name = website_name;
	}
	public Integer getTotalnum() {
		return totalnum;
	}
	public void setTotalnum(Integer totalnum) {
		this.totalnum = totalnum;
	}
	public Integer getTodaynum() {
		return todaynum;
	}
	public void setTodaynum(Integer todaynum) {
		this.todaynum = todaynum;
	}
}
