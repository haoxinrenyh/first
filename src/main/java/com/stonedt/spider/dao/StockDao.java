package com.stonedt.spider.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.stonedt.spider.entity.Patent;
import com.stonedt.spider.entity.Stock;
import com.stonedt.spider.entity.StockFinancial;
import com.stonedt.spider.entity.StockInfo;

@Mapper
public interface StockDao {
	List<Stock> stockList(Integer stocktype);

	Stock getstockbaseinfo(String code);

	StockInfo getStockInfoByCode(@Param("code") String code);

	StockFinancial getFinancial(@Param("code") String code);

	/**
	 * 营业收入及增速
	 * 
	 * @param code
	 * @return
	 */
	String getStockRevenue(@Param("code") String code);

	/**
	 * 历年收入结构
	 * 
	 * @param code
	 * @return
	 */
	String getStockEarning(@Param("code") String code);

	/**
	 * 扣非净利润及增速
	 * 
	 * @param code
	 * @return
	 */
	String getStockNetProfit(@Param("code") String code);

	/**
	 * 研发投入占比
	 * 
	 * @param code
	 * @return
	 */
	String getStockResearch(@Param("code") String code);

	String getStockJson(@Param("code") String code);

	Patent getPatent(@Param("code") String code);

}
