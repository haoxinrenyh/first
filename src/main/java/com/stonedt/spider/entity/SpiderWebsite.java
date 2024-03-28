package com.stonedt.spider.entity;
/**
 * 爬虫网站
 * @author wangyi
 *
 */
public class SpiderWebsite {
	private Integer id;
	private String website_name;//网站名称
	private String website_url;//网站地址
	private String website_remark;//网站备注
	private Integer website_type;//网站类型
	private Integer website_status;//网站状态
	private Integer create_user_id;//创建者ID
	private String create_date;//创建时间
	private String website_ico;
	private String url_contains_str;//列表抓取url必须包含
	private String url_shield_str;//列表抓取url屏蔽
	private String explain_contains_str;//列表抓取必须包含
	private String explain_shield_str;//列表抓取屏蔽
	private String capture_date;
	private Integer capture_type;//生成爬虫配置状态 0未开启 1等待开启 2进行中 3已完成
	private String website_province;
	private String website_city;
	private Integer unfinishedmappings;
	private Integer matchingstatus;

	public Integer getMatchingstatus() {
		return matchingstatus;
	}
	public void setMatchingstatus(Integer matchingstatus) {
		this.matchingstatus = matchingstatus;
	}
	public Integer getUnfinishedmappings() {
		return unfinishedmappings;
	}
	public void setUnfinishedmappings(Integer unfinishedmappings) {
		this.unfinishedmappings = unfinishedmappings;
	}
	public String getWebsite_province() {
		return website_province;
	}
	public void setWebsite_province(String website_province) {
		this.website_province = website_province;
	}
	public String getWebsite_city() {
		return website_city;
	}
	public void setWebsite_city(String website_city) {
		this.website_city = website_city;
	}
	public String getCapture_date() {
		return capture_date;
	}
	public void setCapture_date(String capture_date) {
		this.capture_date = capture_date;
	}
	public Integer getCapture_type() {
		return capture_type;
	}
	public void setCapture_type(Integer capture_type) {
		this.capture_type = capture_type;
	}
	public String getUrl_contains_str() {
		return url_contains_str;
	}
	public void setUrl_contains_str(String url_contains_str) {
		this.url_contains_str = url_contains_str;
	}
	public String getUrl_shield_str() {
		return url_shield_str;
	}
	public void setUrl_shield_str(String url_shield_str) {
		this.url_shield_str = url_shield_str;
	}
	public String getExplain_contains_str() {
		return explain_contains_str;
	}
	public void setExplain_contains_str(String explain_contains_str) {
		this.explain_contains_str = explain_contains_str;
	}
	public String getExplain_shield_str() {
		return explain_shield_str;
	}
	public void setExplain_shield_str(String explain_shield_str) {
		this.explain_shield_str = explain_shield_str;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getWebsite_name() {
		return website_name;
	}
	public void setWebsite_name(String website_name) {
		this.website_name = website_name;
	}
	public String getWebsite_url() {
		return website_url;
	}
	public void setWebsite_url(String website_url) {
		this.website_url = website_url;
	}
	public String getWebsite_remark() {
		return website_remark;
	}
	public void setWebsite_remark(String website_remark) {
		this.website_remark = website_remark;
	}
	public Integer getWebsite_type() {
		return website_type;
	}
	public void setWebsite_type(Integer website_type) {
		this.website_type = website_type;
	}
	public Integer getWebsite_status() {
		return website_status;
	}
	public void setWebsite_status(Integer website_status) {
		this.website_status = website_status;
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
	public String getWebsite_ico() {
		return website_ico;
	}
	public void setWebsite_ico(String website_ico) {
		this.website_ico = website_ico;
	}
	

	public SpiderWebsite(Integer id, String website_name, String website_url, String website_remark,
			Integer website_type, Integer website_status, Integer create_user_id, String create_date,
			String website_ico) {
		super();
		this.id = id;
		this.website_name = website_name;
		this.website_url = website_url;
		this.website_remark = website_remark;
		this.website_type = website_type;
		this.website_status = website_status;
		this.create_user_id = create_user_id;
		this.create_date = create_date;
		this.website_ico = website_ico;
	}
	public SpiderWebsite() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "SpiderWebsite [id=" + id + ", website_name=" + website_name + ", website_url=" + website_url
				+ ", website_remark=" + website_remark + ", website_type=" + website_type + ", website_status="
				+ website_status + ", create_user_id=" + create_user_id + ", create_date=" + create_date
				+ ", website_ico=" + website_ico + ", url_contains_str=" + url_contains_str + ", url_shield_str="
				+ url_shield_str + ", explain_contains_str=" + explain_contains_str + ", explain_shield_str="
				+ explain_shield_str + ", capture_date=" + capture_date + ", capture_type=" + capture_type
				+ ", website_province=" + website_province + ", website_city=" + website_city + ", unfinishedmappings="
				+ unfinishedmappings + ", matchingstatus=" + matchingstatus + "]";
	}
	
	
	
	
	
}
