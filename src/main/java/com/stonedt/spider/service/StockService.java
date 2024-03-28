package com.stonedt.spider.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.stonedt.spider.entity.Stock;
import com.stonedt.spider.entity.StockFinancial;
import com.stonedt.spider.entity.StockInfo;
import com.stonedt.spider.util.ResultUtil;

public interface StockService {
	
	List<Stock> stockList(Integer stocktype);

	Stock getstockbaseinfo(String code);

	StockInfo getStockInfoByCode(String code);

	StockFinancial getFinancial(String code);

	/**
	 * 营业收入及增速
	 * @param code
	 * @return
	 */
	String getStockRevenue(String code);
	
	String getStockJson(String code);
	/**
	 * 历年收入结构
	 * @param code
	 * @return
	 */
	String getStockEarning(String code);
	
	/**
	 * 扣非净利润及增速
	 * @param code
	 * @return
	 */
	String getStockNetProfit(String code);
	
	/**
	 * 研发投入占比
	 * @param code
	 * @return
	 */
	String getStockResearch(String code);

	ResultUtil getPatent(String code);
	

	//获取十大股东
	JSONObject getShiDa(String company_id);
	
	JSONObject getpersons(String id);
	
	JSONObject companydetail(String company_id);
	//知识产权
	JSONObject getProperty(String company_id);
	
	//法律
	JSONObject getLaw(String company_id,String iname);
}
