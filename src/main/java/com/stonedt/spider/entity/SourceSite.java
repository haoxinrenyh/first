package com.stonedt.spider.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class SourceSite {
	
	private Integer id;
	private String website_name;//  '网站名称',
	private String website_url;//  '网站url',
	private String sitedomain;//  '网站域名',
	private String website_remark;//'网站备注',
	private Integer new_website_type;// '新网站类形\\\\n1微信，2微博，3政务，4论坛，5新闻，6报刊，7客户端，8网站，9外媒，10视频，11博客，12自媒体',
	private String website_ico;//  '网站logo',
	private String mainstream_flag;//'是否是主流网站 1是，2不是',
	private String icp_info;//  'icp备案信息',
	private String audit_time;// '审核通过时间',
	private String sponsor;//  '主办单位名称',
	private String sponsor_nature;//  '主办单位性质',
	private String website_pr;// '站点pr值',
	private String one_domain;//  '一级域名',
	private String two_domain;//  '二级域名',
	private String website_explain;//'站点说明',
	private Integer website_status;// '网站状态',
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")//输出格式
	private Date create_date;// '创建时间',
	private String website_city;//  '网站所在市',
	private String website_province;//  '网站所在省',
	private String site_rank;// '自定义的排名',
	private String baidu_weight;// '百度权重',
	private String thas_weight;// '360权重',
	private String alexa_rank;//alexa 排名
	private Integer intelligent_match;//状态
	
	
	private String create_user;//创建人
	private String update_time;//更新时间
	private String update_user;//update_user
	private Integer update_user_id;//update_user_id
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
	public String getSitedomain() {
		return sitedomain;
	}
	public void setSitedomain(String sitedomain) {
		this.sitedomain = sitedomain;
	}
	public String getWebsite_remark() {
		return website_remark;
	}
	public void setWebsite_remark(String website_remark) {
		this.website_remark = website_remark;
	}
	public Integer getNew_website_type() {
		return new_website_type;
	}
	public void setNew_website_type(Integer new_website_type) {
		this.new_website_type = new_website_type;
	}
	public String getWebsite_ico() {
		return website_ico;
	}
	public void setWebsite_ico(String website_ico) {
		this.website_ico = website_ico;
	}
	public String getMainstream_flag() {
		return mainstream_flag;
	}
	public void setMainstream_flag(String mainstream_flag) {
		this.mainstream_flag = mainstream_flag;
	}
	public String getIcp_info() {
		return icp_info;
	}
	public void setIcp_info(String icp_info) {
		this.icp_info = icp_info;
	}
	public String getAudit_time() {
		return audit_time;
	}
	public void setAudit_time(String audit_time) {
		this.audit_time = audit_time;
	}
	public String getSponsor() {
		return sponsor;
	}
	public void setSponsor(String sponsor) {
		this.sponsor = sponsor;
	}
	public String getSponsor_nature() {
		return sponsor_nature;
	}
	public void setSponsor_nature(String sponsor_nature) {
		this.sponsor_nature = sponsor_nature;
	}
	public String getWebsite_pr() {
		return website_pr;
	}
	public void setWebsite_pr(String website_pr) {
		this.website_pr = website_pr;
	}
	public String getOne_domain() {
		return one_domain;
	}
	public void setOne_domain(String one_domain) {
		this.one_domain = one_domain;
	}
	public String getTwo_domain() {
		return two_domain;
	}
	public void setTwo_domain(String two_domain) {
		this.two_domain = two_domain;
	}
	public String getWebsite_explain() {
		return website_explain;
	}
	public void setWebsite_explain(String website_explain) {
		this.website_explain = website_explain;
	}
	public Integer getWebsite_status() {
		return website_status;
	}
	public void setWebsite_status(Integer website_status) {
		this.website_status = website_status;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	public String getWebsite_city() {
		return website_city;
	}
	public void setWebsite_city(String website_city) {
		this.website_city = website_city;
	}
	public String getWebsite_province() {
		return website_province;
	}
	public void setWebsite_province(String website_province) {
		this.website_province = website_province;
	}
	public String getSite_rank() {
		return site_rank;
	}
	public void setSite_rank(String site_rank) {
		this.site_rank = site_rank;
	}
	public String getBaidu_weight() {
		return baidu_weight;
	}
	public void setBaidu_weight(String baidu_weight) {
		this.baidu_weight = baidu_weight;
	}
	public String getThas_weight() {
		return thas_weight;
	}
	public void setThas_weight(String thas_weight) {
		this.thas_weight = thas_weight;
	}
	public String getAlexa_rank() {
		return alexa_rank;
	}
	public void setAlexa_rank(String alexa_rank) {
		this.alexa_rank = alexa_rank;
	}
	public Integer getIntelligent_match() {
		return intelligent_match;
	}
	public void setIntelligent_match(Integer intelligent_match) {
		this.intelligent_match = intelligent_match;
	}
	public String getCreate_user() {
		return create_user;
	}
	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	public String getUpdate_user() {
		return update_user;
	}
	public void setUpdate_user(String update_user) {
		this.update_user = update_user;
	}
	public Integer getUpdate_user_id() {
		return update_user_id;
	}
	public void setUpdate_user_id(Integer update_user_id) {
		this.update_user_id = update_user_id;
	}
	@Override
	public String toString() {
		return "SourceSite [id=" + id + ", website_name=" + website_name + ", website_url=" + website_url
				+ ", sitedomain=" + sitedomain + ", website_remark=" + website_remark + ", new_website_type="
				+ new_website_type + ", website_ico=" + website_ico + ", mainstream_flag=" + mainstream_flag
				+ ", icp_info=" + icp_info + ", audit_time=" + audit_time + ", sponsor=" + sponsor + ", sponsor_nature="
				+ sponsor_nature + ", website_pr=" + website_pr + ", one_domain=" + one_domain + ", two_domain="
				+ two_domain + ", website_explain=" + website_explain + ", website_status=" + website_status
				+ ", create_date=" + create_date + ", website_city=" + website_city + ", website_province="
				+ website_province + ", site_rank=" + site_rank + ", baidu_weight=" + baidu_weight + ", thas_weight="
				+ thas_weight + ", alexa_rank=" + alexa_rank + ", intelligent_match=" + intelligent_match
				+ ", create_user=" + create_user + ", update_time=" + update_time + ", update_user=" + update_user
				+ ", update_user_id=" + update_user_id + "]";
	}
	public SourceSite(Integer id, String website_name, String website_url, String sitedomain, String website_remark,
			Integer new_website_type, String website_ico, String mainstream_flag, String icp_info, String audit_time,
			String sponsor, String sponsor_nature, String website_pr, String one_domain, String two_domain,
			String website_explain, Integer website_status, Date create_date, String website_city,
			String website_province, String site_rank, String baidu_weight, String thas_weight, String alexa_rank,
			Integer intelligent_match, String create_user, String update_time, String update_user,
			Integer update_user_id) {
		super();
		this.id = id;
		this.website_name = website_name;
		this.website_url = website_url;
		this.sitedomain = sitedomain;
		this.website_remark = website_remark;
		this.new_website_type = new_website_type;
		this.website_ico = website_ico;
		this.mainstream_flag = mainstream_flag;
		this.icp_info = icp_info;
		this.audit_time = audit_time;
		this.sponsor = sponsor;
		this.sponsor_nature = sponsor_nature;
		this.website_pr = website_pr;
		this.one_domain = one_domain;
		this.two_domain = two_domain;
		this.website_explain = website_explain;
		this.website_status = website_status;
		this.create_date = create_date;
		this.website_city = website_city;
		this.website_province = website_province;
		this.site_rank = site_rank;
		this.baidu_weight = baidu_weight;
		this.thas_weight = thas_weight;
		this.alexa_rank = alexa_rank;
		this.intelligent_match = intelligent_match;
		this.create_user = create_user;
		this.update_time = update_time;
		this.update_user = update_user;
		this.update_user_id = update_user_id;
	}
	public SourceSite() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	

	


}
