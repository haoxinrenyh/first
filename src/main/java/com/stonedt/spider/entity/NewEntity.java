package com.stonedt.spider.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class NewEntity {
	private String category_name;
	private Integer sort_field;
	private Integer sort_type;
	private String date;
	private Integer pageNum;
	private Integer pageSize;
	private String keywords;
	private String types;
	private List<Integer> typeList;
	private Integer id;
	private String website_name;
	private String website_url;
	private String sitedomain;
	private String website_remark;
	private Integer new_website_type;
	private String website_ico;
	private Integer mainstream_flag;
	private String icp_info;
	private Date audit_time;
	private String sponsor;
	private String sponsor_nature;
	private Integer website_pr;
	private String one_domain;
	private String two_domain;
	private String website_explain;
	private Integer website_status;
	private Integer create_user_id;
	private Date create_date;
	private String url_contains_str;
	private String url_shield_str;
	private String explain_contains_str;
	private String explain_shield_str;
	private Date capture_date;
	private Integer capture_type;
	private String website_city;
	private String website_province;
	private String websiteprimaryid;
	private Integer matchingstatus;
	private Integer website_type;
	private Integer site_rank;
	private String websitetype;
	private String area;
	private Integer baidu_weight;
	private Integer thas_weight;
	private String alexa_rank;
	private String excel_websitetype;
	private Integer min_pr;
	private Integer max_pr;
	private Integer min_bd;
	private Integer max_bd;
	private Integer min_defined;
	private Integer max_defined;
	
	private String create_user;
	private String update_time;
	private String update_user;

	private int log_error;

	public Integer getSort_field() {
		return sort_field;
	}
	public void setSort_field(Integer sort_field) {
		this.sort_field = sort_field;
	}
	public Integer getSort_type() {
		return sort_type;
	}
	public void setSort_type(Integer sort_type) {
		this.sort_type = sort_type;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getTypes() {
		return types;
	}
	public void setTypes(String types) {
		this.types = types;
	}
	public List<Integer> getTypeList() {
		return typeList;
	}
	public void setTypeList(List<Integer> typeList) {
		this.typeList = typeList;
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
	public Integer getMainstream_flag() {
		return mainstream_flag;
	}
	public void setMainstream_flag(Integer mainstream_flag) {
		this.mainstream_flag = mainstream_flag;
	}
	public String getIcp_info() {
		return icp_info;
	}
	public void setIcp_info(String icp_info) {
		this.icp_info = icp_info;
	}
	public Date getAudit_time() {
		return audit_time;
	}
	public void setAudit_time(Date audit_time) {
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
	public Integer getWebsite_pr() {
		return website_pr;
	}
	public void setWebsite_pr(Integer website_pr) {
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
	public Integer getCreate_user_id() {
		return create_user_id;
	}
	public void setCreate_user_id(Integer create_user_id) {
		this.create_user_id = create_user_id;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
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
	public Date getCapture_date() {
		return capture_date;
	}
	public void setCapture_date(Date capture_date) {
		this.capture_date = capture_date;
	}
	public Integer getCapture_type() {
		return capture_type;
	}
	public void setCapture_type(Integer capture_type) {
		this.capture_type = capture_type;
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
	public String getWebsiteprimaryid() {
		return websiteprimaryid;
	}
	public void setWebsiteprimaryid(String websiteprimaryid) {
		this.websiteprimaryid = websiteprimaryid;
	}
	public Integer getMatchingstatus() {
		return matchingstatus;
	}
	public void setMatchingstatus(Integer matchingstatus) {
		this.matchingstatus = matchingstatus;
	}
	public Integer getWebsite_type() {
		return website_type;
	}
	public void setWebsite_type(Integer website_type) {
		this.website_type = website_type;
	}
	public Integer getSite_rank() {
		return site_rank;
	}
	public void setSite_rank(Integer site_rank) {
		this.site_rank = site_rank;
	}
	public String getWebsitetype() {
		return websitetype;
	}
	public void setWebsitetype(String websitetype) {
		this.websitetype = websitetype;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public Integer getBaidu_weight() {
		return baidu_weight;
	}
	public void setBaidu_weight(Integer baidu_weight) {
		this.baidu_weight = baidu_weight;
	}
	public Integer getThas_weight() {
		return thas_weight;
	}
	public void setThas_weight(Integer thas_weight) {
		this.thas_weight = thas_weight;
	}
	public String getAlexa_rank() {
		return alexa_rank;
	}
	public void setAlexa_rank(String alexa_rank) {
		this.alexa_rank = alexa_rank;
	}
	public String getExcel_websitetype() {
		return excel_websitetype;
	}
	public void setExcel_websitetype(String excel_websitetype) {
		this.excel_websitetype = excel_websitetype;
	}
	public Integer getMin_pr() {
		return min_pr;
	}
	public void setMin_pr(Integer min_pr) {
		this.min_pr = min_pr;
	}
	public Integer getMax_pr() {
		return max_pr;
	}
	public void setMax_pr(Integer max_pr) {
		this.max_pr = max_pr;
	}
	public Integer getMin_bd() {
		return min_bd;
	}
	public void setMin_bd(Integer min_bd) {
		this.min_bd = min_bd;
	}
	public Integer getMax_bd() {
		return max_bd;
	}
	public void setMax_bd(Integer max_bd) {
		this.max_bd = max_bd;
	}
	public Integer getMin_defined() {
		return min_defined;
	}
	public void setMin_defined(Integer min_defined) {
		this.min_defined = min_defined;
	}
	public Integer getMax_defined() {
		return max_defined;
	}
	public void setMax_defined(Integer max_defined) {
		this.max_defined = max_defined;
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
	public NewEntity(Integer sort_field, Integer sort_type, String date, Integer pageNum, String keywords, String types,
			List<Integer> typeList, Integer id, String website_name, String website_url, String sitedomain,
			String website_remark, Integer new_website_type, String website_ico, Integer mainstream_flag,
			String icp_info, Date audit_time, String sponsor, String sponsor_nature, Integer website_pr,
			String one_domain, String two_domain, String website_explain, Integer website_status,
			Integer create_user_id, Date create_date, String url_contains_str, String url_shield_str,
			String explain_contains_str, String explain_shield_str, Date capture_date, Integer capture_type,
			String website_city, String website_province, String websiteprimaryid, Integer matchingstatus,
			Integer website_type, Integer site_rank, String websitetype, String area, Integer baidu_weight,
			Integer thas_weight, String alexa_rank, String excel_websitetype, Integer min_pr, Integer max_pr,
			Integer min_bd, Integer max_bd, Integer min_defined, Integer max_defined, String create_user,
			String update_time, String update_user) {
		super();
		this.sort_field = sort_field;
		this.sort_type = sort_type;
		this.date = date;
		this.pageNum = pageNum;
		this.keywords = keywords;
		this.types = types;
		this.typeList = typeList;
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
		this.create_user_id = create_user_id;
		this.create_date = create_date;
		this.url_contains_str = url_contains_str;
		this.url_shield_str = url_shield_str;
		this.explain_contains_str = explain_contains_str;
		this.explain_shield_str = explain_shield_str;
		this.capture_date = capture_date;
		this.capture_type = capture_type;
		this.website_city = website_city;
		this.website_province = website_province;
		this.websiteprimaryid = websiteprimaryid;
		this.matchingstatus = matchingstatus;
		this.website_type = website_type;
		this.site_rank = site_rank;
		this.websitetype = websitetype;
		this.area = area;
		this.baidu_weight = baidu_weight;
		this.thas_weight = thas_weight;
		this.alexa_rank = alexa_rank;
		this.excel_websitetype = excel_websitetype;
		this.min_pr = min_pr;
		this.max_pr = max_pr;
		this.min_bd = min_bd;
		this.max_bd = max_bd;
		this.min_defined = min_defined;
		this.max_defined = max_defined;
		this.create_user = create_user;
		this.update_time = update_time;
		this.update_user = update_user;
	}
	public NewEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
}
