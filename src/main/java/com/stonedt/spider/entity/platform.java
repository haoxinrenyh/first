package com.stonedt.spider.entity;

public class platform {
	//平台
	  private String id;
	  private String platform_name;
	  private String web_site;
	  private String platform_logo;
	  private String type;
	  private String remark;
	  private String province;
	  private String city;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPlatform_name() {
		return platform_name;
	}
	public void setPlatform_name(String platform_name) {
		this.platform_name = platform_name;
	}
	public String getWeb_site() {
		return web_site;
	}
	public void setWeb_site(String web_site) {
		this.web_site = web_site;
	}
	public String getPlatform_logo() {
		return platform_logo;
	}
	public void setPlatform_logo(String platform_logo) {
		this.platform_logo = platform_logo;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	@Override
	public String toString() {
		return "platform [id=" + id + ", platform_name=" + platform_name + ", web_site=" + web_site + ", platform_logo="
				+ platform_logo + ", type=" + type + ", remark=" + remark + ", province=" + province + ", city=" + city
				+ "]";
	}

	
	
}
