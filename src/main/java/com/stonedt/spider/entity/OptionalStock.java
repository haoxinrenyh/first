package com.stonedt.spider.entity;

public class OptionalStock {

	private Integer id;
	private String code;
	private String simple_name;//公司简称
	private String full_name;//公司全称
	private String market;//sz或者sh
	private Integer user_id;
	private Integer stock_id;
	private String industry_sector;//行业板块
	private String establishment_time;//成立时间
	private String dateOfListing;//上市日期；
	private String totalMarket;//总市值
	private Integer recentTrend;//波动
	private String currentpricetoday;//当前股价
	private String openingpricetoday;//开市价格
	private String upsAndDowns;//涨跌额
	private String gain;//涨跌幅度
	private Integer emotiontype;//情感类型
	private Integer percentemotion;//情感百分比
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
	public String getSimple_name() {
		return simple_name;
	}
	public void setSimple_name(String simple_name) {
		this.simple_name = simple_name;
	}
	public String getFull_name() {
		return full_name;
	}
	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}
	public String getMarket() {
		return market;
	}
	public void setMarket(String market) {
		this.market = market;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public Integer getStock_id() {
		return stock_id;
	}
	public void setStock_id(Integer stock_id) {
		this.stock_id = stock_id;
	}
	public String getIndustry_sector() {
		return industry_sector;
	}
	public void setIndustry_sector(String industry_sector) {
		this.industry_sector = industry_sector;
	}
	public String getEstablishment_time() {
		return establishment_time;
	}
	public void setEstablishment_time(String establishment_time) {
		this.establishment_time = establishment_time;
	}
	public String getDateOfListing() {
		return dateOfListing;
	}
	public void setDateOfListing(String dateOfListing) {
		this.dateOfListing = dateOfListing;
	}
	public String getTotalMarket() {
		return totalMarket;
	}
	public void setTotalMarket(String totalMarket) {
		this.totalMarket = totalMarket;
	}
	public Integer getRecentTrend() {
		return recentTrend;
	}
	public void setRecentTrend(Integer recentTrend) {
		this.recentTrend = recentTrend;
	}
	public String getCurrentpricetoday() {
		return currentpricetoday;
	}
	public void setCurrentpricetoday(String currentpricetoday) {
		this.currentpricetoday = currentpricetoday;
	}
	public String getOpeningpricetoday() {
		return openingpricetoday;
	}
	public void setOpeningpricetoday(String openingpricetoday) {
		this.openingpricetoday = openingpricetoday;
	}
	public String getUpsAndDowns() {
		return upsAndDowns;
	}
	public void setUpsAndDowns(String upsAndDowns) {
		this.upsAndDowns = upsAndDowns;
	}
	public String getGain() {
		return gain;
	}
	public void setGain(String gain) {
		this.gain = gain;
	}
	public Integer getEmotiontype() {
		return emotiontype;
	}
	public void setEmotiontype(Integer emotiontype) {
		this.emotiontype = emotiontype;
	}
	public Integer getPercentemotion() {
		return percentemotion;
	}
	public void setPercentemotion(Integer percentemotion) {
		this.percentemotion = percentemotion;
	}
	@Override
	public String toString() {
		return "OptionalStock [id=" + id + ", code=" + code + ", simple_name=" + simple_name + ", full_name="
				+ full_name + ", market=" + market + ", user_id=" + user_id + ", stock_id=" + stock_id
				+ ", industry_sector=" + industry_sector + ", establishment_time=" + establishment_time
				+ ", dateOfListing=" + dateOfListing + ", totalMarket=" + totalMarket + ", recentTrend=" + recentTrend
				+ ", currentpricetoday=" + currentpricetoday + ", openingpricetoday=" + openingpricetoday
				+ ", upsAndDowns=" + upsAndDowns + ", gain=" + gain + ", emotiontype=" + emotiontype
				+ ", percentemotion=" + percentemotion + "]";
	}
	public OptionalStock(Integer id, String code, String simple_name, String full_name, String market, Integer user_id,
			Integer stock_id, String industry_sector, String establishment_time, String dateOfListing,
			String totalMarket, Integer recentTrend, String currentpricetoday, String openingpricetoday,
			String upsAndDowns, String gain, Integer emotiontype, Integer percentemotion) {
		super();
		this.id = id;
		this.code = code;
		this.simple_name = simple_name;
		this.full_name = full_name;
		this.market = market;
		this.user_id = user_id;
		this.stock_id = stock_id;
		this.industry_sector = industry_sector;
		this.establishment_time = establishment_time;
		this.dateOfListing = dateOfListing;
		this.totalMarket = totalMarket;
		this.recentTrend = recentTrend;
		this.currentpricetoday = currentpricetoday;
		this.openingpricetoday = openingpricetoday;
		this.upsAndDowns = upsAndDowns;
		this.gain = gain;
		this.emotiontype = emotiontype;
		this.percentemotion = percentemotion;
	}
	public OptionalStock() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
}
