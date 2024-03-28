package com.stonedt.spider.entity;

public class ResearchReport {
	
	private int id;
	private String publish_time;
	private String title;
	private String url;
	private String firstname;
	private String name;
	private String rate;
	private String org;
	private String pagenum;
	private String industry;//行业
	private String authorList;
	private Integer seedid;
	private String rtype;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPublish_time() {
		return publish_time;
	}
	public void setPublish_time(String publish_time) {
		this.publish_time = publish_time;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getOrg() {
		return org;
	}
	public void setOrg(String org) {
		this.org = org;
	}
	public String getPagenum() {
		return pagenum;
	}
	public void setPagenum(String pagenum) {
		this.pagenum = pagenum;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getAuthorList() {
		return authorList;
	}
	public void setAuthorList(String authorList) {
		this.authorList = authorList;
	}
	public Integer getSeedid() {
		return seedid;
	}
	public void setSeedid(Integer seedid) {
		this.seedid = seedid;
	}
	public String getRtype() {
		return rtype;
	}
	public void setRtype(String rtype) {
		this.rtype = rtype;
	}
	public ResearchReport(int id, String publish_time, String title, String url, String firstname, String name,
			String rate, String org, String pagenum, String industry, String authorList, Integer seedid, String rtype) {
		super();
		this.id = id;
		this.publish_time = publish_time;
		this.title = title;
		this.url = url;
		this.firstname = firstname;
		this.name = name;
		this.rate = rate;
		this.org = org;
		this.pagenum = pagenum;
		this.industry = industry;
		this.authorList = authorList;
		this.seedid = seedid;
		this.rtype = rtype;
	}
	public ResearchReport() {
		super();
	}
	
	
	
}
