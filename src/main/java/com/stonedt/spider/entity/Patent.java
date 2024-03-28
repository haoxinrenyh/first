package com.stonedt.spider.entity;

import java.io.Serializable;
import java.util.Date;

public class Patent implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id; 
	private String code;   
	private String firm_code;  
	private String trademark_json; 
	private String patent_json;
	private String certificate_json;   
	private String copyright_json; 
	private String software_json;  
	private String website_json;   
	private Date update_date;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getFirm_code() {
		return firm_code;
	}
	public void setFirm_code(String firm_code) {
		this.firm_code = firm_code;
	}
	public String getTrademark_json() {
		return trademark_json;
	}
	public void setTrademark_json(String trademark_json) {
		this.trademark_json = trademark_json;
	}
	public String getPatent_json() {
		return patent_json;
	}
	public void setPatent_json(String patent_json) {
		this.patent_json = patent_json;
	}
	public String getCertificate_json() {
		return certificate_json;
	}
	public void setCertificate_json(String certificate_json) {
		this.certificate_json = certificate_json;
	}
	public String getCopyright_json() {
		return copyright_json;
	}
	public void setCopyright_json(String copyright_json) {
		this.copyright_json = copyright_json;
	}
	public String getSoftware_json() {
		return software_json;
	}
	public void setSoftware_json(String software_json) {
		this.software_json = software_json;
	}
	public String getWebsite_json() {
		return website_json;
	}
	public void setWebsite_json(String website_json) {
		this.website_json = website_json;
	}
	public Date getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}
	
	
}
