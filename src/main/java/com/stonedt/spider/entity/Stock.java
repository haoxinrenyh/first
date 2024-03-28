package com.stonedt.spider.entity;

import java.util.List;

public class Stock {

	private Integer id;
	private String code;
	private String simple_name;//公司简称
	private String full_name;//公司全称
	private String market;//sz或者sh
	private Integer user_id;
	private Integer stock_id;
	private String industry_sector;//行业板块
	private String builddate;//成立时间
	private String listdate;//上市日期；
	private String totalMarket;//总市值
	private Integer recentTrend;//波动
	private String currentpricetoday;//当前股价
	private String openingpricetoday;//开市价格
	private String upsAndDowns;//涨跌额
	private String gain;//涨跌幅度
	private Integer emotiontype;//情感类型
	private Integer percentemotion;//情感百分比

	private String industrychain;//所属产业链

	private Integer stocktype;//1 代表上市 ，2代表深市,3代表新三板
	
	private String boards;
	private List<String> boardList;
	
	private List<String> plateIdList;

	private String english_name;
	private String url;
	private String executives;
	
	//重大利好数量
		private String t_max;
		
		//利好数量
		private String max;
			
		//利中数量
		private String mid;
			
		//利空数量
		private String min;
		//重大利空数量
		private String t_min;
		
		
	
	
	
	public String getT_max() {
			return t_max;
		}

		public void setT_max(String t_max) {
			this.t_max = t_max;
		}

		public String getMax() {
			return max;
		}

		public void setMax(String max) {
			this.max = max;
		}

		public String getMid() {
			return mid;
		}

		public void setMid(String mid) {
			this.mid = mid;
		}

		public String getMin() {
			return min;
		}

		public void setMin(String min) {
			this.min = min;
		}

		public String getT_min() {
			return t_min;
		}

		public void setT_min(String t_min) {
			this.t_min = t_min;
		}

	public String getBoards() {
		return boards;
	}

	public void setBoards(String boards) {
		this.boards = boards;
	}

	public List<String> getBoardList() {
		return boardList;
	}

	public void setBoardList(List<String> boardList) {
		this.boardList = boardList;
	}

	public List<String> getPlateIdList() {
		return plateIdList;
	}

	public void setPlateIdList(List<String> plateIdList) {
		this.plateIdList = plateIdList;
	}

	public String getEnglish_name() {
		return english_name;
	}

	public void setEnglish_name(String english_name) {
		this.english_name = english_name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getExecutives() {
		return executives;
	}

	public void setExecutives(String executives) {
		this.executives = executives;
	}

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

	public String getIndustrychain() {
		return industrychain;
	}

	public void setIndustrychain(String industrychain) {
		this.industrychain = industrychain;
	}

	public Integer getStocktype() {
		return stocktype;
	}

	public void setStocktype(Integer stocktype) {
		this.stocktype = stocktype;
	}

	public Stock(Integer id, String code, String simple_name, String full_name, String market, Integer user_id,
			Integer stock_id, String industry_sector, String builddate, String listdate, String totalMarket,
			Integer recentTrend, String currentpricetoday, String openingpricetoday, String upsAndDowns, String gain,
			Integer emotiontype, Integer percentemotion, String industrychain, Integer stocktype) {
		super();
		this.id = id;
		this.code = code;
		this.simple_name = simple_name;
		this.full_name = full_name;
		this.market = market;
		this.user_id = user_id;
		this.stock_id = stock_id;
		this.industry_sector = industry_sector;
		this.builddate = builddate;
		this.listdate = listdate;
		this.totalMarket = totalMarket;
		this.recentTrend = recentTrend;
		this.currentpricetoday = currentpricetoday;
		this.openingpricetoday = openingpricetoday;
		this.upsAndDowns = upsAndDowns;
		this.gain = gain;
		this.emotiontype = emotiontype;
		this.percentemotion = percentemotion;
		this.industrychain = industrychain;
		this.stocktype = stocktype;
	}

	public Stock() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	
	
	
	
	
}
