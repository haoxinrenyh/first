package com.stonedt.spider.entity;

public class Wechat {
	private Integer id;//
	private String name;//微信昵称微博名称
	private String alias;//微信号/微博官网
	private String origin;//微信源/微博资讯来源
	private String avatar_url;//微信头像URL/微博头像url
	private String qrcode_url;//微信二维码URL
	private String introduction;//微信简介
	private String fans_num_estimate;//粉丝量
	private Integer type;//类型，默认为0，1为微信公众号，2为微博
	private String create_date;//资源创建时间
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getAvatar_url() {
		return avatar_url;
	}
	public void setAvatar_url(String avatar_url) {
		this.avatar_url = avatar_url;
	}
	public String getQrcode_url() {
		return qrcode_url;
	}
	public void setQrcode_url(String qrcode_url) {
		this.qrcode_url = qrcode_url;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public String getFans_num_estimate() {
		return fans_num_estimate;
	}
	public void setFans_num_estimate(String fans_num_estimate) {
		this.fans_num_estimate = fans_num_estimate;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	public Wechat(Integer id, String name, String alias, String origin, String avatar_url, String qrcode_url,
			String introduction, String fans_num_estimate, Integer type, String create_date) {
		super();
		this.id = id;
		this.name = name;
		this.alias = alias;
		this.origin = origin;
		this.avatar_url = avatar_url;
		this.qrcode_url = qrcode_url;
		this.introduction = introduction;
		this.fans_num_estimate = fans_num_estimate;
		this.type = type;
		this.create_date = create_date;
	}
	public Wechat() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	
	
}
