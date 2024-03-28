package com.stonedt.spider.entity;

/**
 * 自选股搜索
 * @author Mapeng
 *
 */
public class StockSearch {

	private Integer stock_id;
	private String code;
	private String simple_name;//公司简称
	private String full_name;//公司全称
	private String market;//sz或者sh
	private String currentpricetoday;//当前股价
	private String gain;//涨幅
	private String upsAndDowns;//涨跌额
	private String product;
	private String builddate; //成立时间
	private String listdate;//创建时间
	private String ifoption;
	private String totalMarket;//总市值
	private Integer emotiontype;//情感类型
	private Integer percentemotion;//情感百分比
	
	public Integer getStock_id() {
		return stock_id;
	}
	public void setStock_id(Integer stock_id) {
		this.stock_id = stock_id;
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
	public String getMarket() {
		return market;
	}
	public void setMarket(String market) {
		this.market = market;
	}
	public String getCurrentpricetoday() {
		return currentpricetoday;
	}
	public void setCurrentpricetoday(String currentpricetoday) {
		this.currentpricetoday = currentpricetoday;
	}
	
	public String getFull_name() {
		return full_name;
	}
	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}
	
	public String getGain() {
		return gain;
	}
	public void setGain(String gain) {
		this.gain = gain;
	}
	public String getUpsAndDowns() {
		return upsAndDowns;
	}
	public void setUpsAndDowns(String upsAndDowns) {
		this.upsAndDowns = upsAndDowns;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getBuilddate() {
		return builddate;
	}
	public void setBuilddate(String builddate) {
		this.builddate = builddate;
	}
	public String getListdate() {
		return listdate;
	}
	public void setListdate(String listdate) {
		this.listdate = listdate;
	}
	public String getIfoption() {
		return ifoption;
	}
	public void setIfoption(String ifoption) {
		this.ifoption = ifoption;
	}
	public String getTotalMarket() {
		return totalMarket;
	}
	public void setTotalMarket(String totalMarket) {
		this.totalMarket = totalMarket;
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
	
	
}
