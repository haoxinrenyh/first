package com.stonedt.spider.entity;

import java.io.Serializable;

public class CustomerEntity implements Serializable{
	
	private int id;
	private String customer_name;//客户名称
	private String customer_short_name;//客户简称
	private String legal_representative;//法定代表人
	private String credit_code;//工商信用代码
	private String linkman;//联系人
	private String appid;//
	private String telepehone;//客户联系方式
	private String appsecret;//appsecret
	private String address;//公司地址
	private int status;//合作状态[1合作中2已终止3试运行]
	private String introduction;//公司简介
	private int create_user_id;//创建人id
	private int create_time;//创建时间
	private String industry;//行业
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCustomer_name() {
		return customer_name;
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	public String getCustomer_short_name() {
		return customer_short_name;
	}
	public void setCustomer_short_name(String customer_short_name) {
		this.customer_short_name = customer_short_name;
	}
	public String getLegal_representative() {
		return legal_representative;
	}
	public void setLegal_representative(String legal_representative) {
		this.legal_representative = legal_representative;
	}
	public String getCredit_code() {
		return credit_code;
	}
	public void setCredit_code(String credit_code) {
		this.credit_code = credit_code;
	}
	public String getLinkman() {
		return linkman;
	}
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getTelepehone() {
		return telepehone;
	}
	public void setTelepehone(String telepehone) {
		this.telepehone = telepehone;
	}
	public String getAppsecret() {
		return appsecret;
	}
	public void setAppsecret(String appsecret) {
		this.appsecret = appsecret;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public int getCreate_user_id() {
		return create_user_id;
	}
	public void setCreate_user_id(int create_user_id) {
		this.create_user_id = create_user_id;
	}
	public int getCreate_time() {
		return create_time;
	}
	public void setCreate_time(int create_time) {
		this.create_time = create_time;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public CustomerEntity(int id, String customer_name, String customer_short_name, String legal_representative,
			String credit_code, String linkman, String appid, String telepehone, String appsecret, String address,
			int status, String introduction, int create_user_id, int create_time, String industry) {
		super();
		this.id = id;
		this.customer_name = customer_name;
		this.customer_short_name = customer_short_name;
		this.legal_representative = legal_representative;
		this.credit_code = credit_code;
		this.linkman = linkman;
		this.appid = appid;
		this.telepehone = telepehone;
		this.appsecret = appsecret;
		this.address = address;
		this.status = status;
		this.introduction = introduction;
		this.create_user_id = create_user_id;
		this.create_time = create_time;
		this.industry = industry;
	}
	public CustomerEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
}

