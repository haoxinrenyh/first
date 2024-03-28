package com.stonedt.spider.entity;
/**
 * 爬虫种子配置
 * @author wangyi
 *
 */
public class SpiderSeedConfig {
	private Integer id;
	private String seed_name;//种子地址名
	private String seed_list_url;//种子列表链接
	private String seed_url_config;//种子链接配置
	private String seed_title_config;//种子标题配置
	private String seed_date_config;//种子时间配置
	private String seed_text_config;//种子正文配置
	private String seed_author_config;//种子作者配置
	private String seed_author_url_config;//种子作者url配置
	private String seed_author_avatar_config;//种子作者头像配置
	private String seed_page_rule_config;//分页规则
	private Integer seed_text_config_type;//种子正文配置类型（1代表HTML，2代表JSON，3代表JSONB）
	private String seed_storage_type;//存储类型
	private String seed_interval_config;//种子抓取间隔配置
	private Integer seed_thread_config;//种子线程数量配置
	private Integer seed_id;//种子所属的任务ID
	private Integer website_id;//种子所属的网站ID
	private Integer create_user_id;//创建者ID
	private String create_date;//创建时间
	private String seed_origin_config;//来源网站名配置
	private Integer seed_sleep_config;//配置url间隔抓取时间
	private String seed_linkurl_config;//配置json相对路径拼接详情页url
	private String seed_origin_url_config;//文章详情原始网站URL
	private Integer seed_spider_type;//添加种子配置的类型（1代表智能提取，2代表手动配置）
	private Integer other_seed_id;
	private Integer other_website_id;
	private Integer seed_article_type;
	private Integer selenium_flag;
	private Integer daili_flag;
	private Integer seed_first_type;
	private String url_reg;
	private Integer vpn_flag;
	private String seed_detail_request_param;
	
	
	
	public String getSeed_detail_request_param() {
		return seed_detail_request_param;
	}
	public void setSeed_detail_request_param(String seed_detail_request_param) {
		this.seed_detail_request_param = seed_detail_request_param;
	}
	public String getSeed_page_rule_config() {
		return seed_page_rule_config;
	}
	public void setSeed_page_rule_config(String seed_page_rule_config) {
		this.seed_page_rule_config = seed_page_rule_config;
	}
	public String getSeed_author_config() {
		return seed_author_config;
	}
	public void setSeed_author_config(String seed_author_config) {
		this.seed_author_config = seed_author_config;
	}
	public String getSeed_author_url_config() {
		return seed_author_url_config;
	}
	public void setSeed_author_url_config(String seed_author_url_config) {
		this.seed_author_url_config = seed_author_url_config;
	}
	public String getSeed_author_avatar_config() {
		return seed_author_avatar_config;
	}
	public void setSeed_author_avatar_config(String seed_author_avatar_config) {
		this.seed_author_avatar_config = seed_author_avatar_config;
	}
	public Integer getVpn_flag() {
		return vpn_flag;
	}
	public void setVpn_flag(Integer vpn_flag) {
		this.vpn_flag = vpn_flag;
	}
	public String getUrl_reg() {
		return url_reg;
	}
	public void setUrl_reg(String url_reg) {
		this.url_reg = url_reg;
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
	public String getSeed_list_url() {
		return seed_list_url;
	}
	public void setSeed_list_url(String seed_list_url) {
		this.seed_list_url = seed_list_url;
	}
	public String getSeed_url_config() {
		return seed_url_config;
	}
	public void setSeed_url_config(String seed_url_config) {
		this.seed_url_config = seed_url_config;
	}
	public String getSeed_title_config() {
		return seed_title_config;
	}
	public void setSeed_title_config(String seed_title_config) {
		this.seed_title_config = seed_title_config;
	}
	public String getSeed_date_config() {
		return seed_date_config;
	}
	public void setSeed_date_config(String seed_date_config) {
		this.seed_date_config = seed_date_config;
	}
	public String getSeed_text_config() {
		return seed_text_config;
	}
	public void setSeed_text_config(String seed_text_config) {
		this.seed_text_config = seed_text_config;
	}
	public Integer getSeed_text_config_type() {
		return seed_text_config_type;
	}
	public void setSeed_text_config_type(Integer seed_text_config_type) {
		this.seed_text_config_type = seed_text_config_type;
	}
	public String getSeed_storage_type() {
		return seed_storage_type;
	}
	public void setSeed_storage_type(String seed_storage_type) {
		this.seed_storage_type = seed_storage_type;
	}
	public String getSeed_interval_config() {
		return seed_interval_config;
	}
	public void setSeed_interval_config(String seed_interval_config) {
		this.seed_interval_config = seed_interval_config;
	}
	public Integer getSeed_thread_config() {
		return seed_thread_config;
	}
	public void setSeed_thread_config(Integer seed_thread_config) {
		this.seed_thread_config = seed_thread_config;
	}
	public Integer getSeed_id() {
		return seed_id;
	}
	public void setSeed_id(Integer seed_id) {
		this.seed_id = seed_id;
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
	public String getSeed_origin_config() {
		return seed_origin_config;
	}
	public void setSeed_origin_config(String seed_origin_config) {
		this.seed_origin_config = seed_origin_config;
	}
	public Integer getSeed_sleep_config() {
		return seed_sleep_config;
	}
	public void setSeed_sleep_config(Integer seed_sleep_config) {
		this.seed_sleep_config = seed_sleep_config;
	}
	public String getSeed_linkurl_config() {
		return seed_linkurl_config;
	}
	public void setSeed_linkurl_config(String seed_linkurl_config) {
		this.seed_linkurl_config = seed_linkurl_config;
	}
	public String getSeed_origin_url_config() {
		return seed_origin_url_config;
	}
	public void setSeed_origin_url_config(String seed_origin_url_config) {
		this.seed_origin_url_config = seed_origin_url_config;
	}
	public Integer getSeed_spider_type() {
		return seed_spider_type;
	}
	public void setSeed_spider_type(Integer seed_spider_type) {
		this.seed_spider_type = seed_spider_type;
	}
	public Integer getOther_seed_id() {
		return other_seed_id;
	}
	public void setOther_seed_id(Integer other_seed_id) {
		this.other_seed_id = other_seed_id;
	}
	public Integer getOther_website_id() {
		return other_website_id;
	}
	public void setOther_website_id(Integer other_website_id) {
		this.other_website_id = other_website_id;
	}
	@Override
	public String toString() {
		return "SpiderSeedConfig [id=" + id + ", seed_name=" + seed_name + ", seed_list_url=" + seed_list_url
				+ ", seed_url_config=" + seed_url_config + ", seed_title_config=" + seed_title_config
				+ ", seed_date_config=" + seed_date_config + ", seed_text_config=" + seed_text_config
				+ ", seed_author_config=" + seed_author_config + ", seed_author_url_config=" + seed_author_url_config
				+ ", seed_author_avatar_config=" + seed_author_avatar_config + ", seed_page_rule_config="
				+ seed_page_rule_config + ", seed_text_config_type=" + seed_text_config_type + ", seed_storage_type="
				+ seed_storage_type + ", seed_interval_config=" + seed_interval_config + ", seed_thread_config="
				+ seed_thread_config + ", seed_id=" + seed_id + ", website_id=" + website_id + ", create_user_id="
				+ create_user_id + ", create_date=" + create_date + ", seed_origin_config=" + seed_origin_config
				+ ", seed_sleep_config=" + seed_sleep_config + ", seed_linkurl_config=" + seed_linkurl_config
				+ ", seed_origin_url_config=" + seed_origin_url_config + ", seed_spider_type=" + seed_spider_type
				+ ", other_seed_id=" + other_seed_id + ", other_website_id=" + other_website_id + ", seed_article_type="
				+ seed_article_type + ", selenium_flag=" + selenium_flag + ", daili_flag=" + daili_flag
				+ ", seed_first_type=" + seed_first_type + ", url_reg=" + url_reg + ", vpn_flag=" + vpn_flag
				+ ", seed_detail_request_param=" + seed_detail_request_param + "]";
	}
	
	
	
}
