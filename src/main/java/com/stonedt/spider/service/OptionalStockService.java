package com.stonedt.spider.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.stonedt.spider.entity.NewsEntity;
import com.stonedt.spider.entity.OptionalStock;
import com.stonedt.spider.entity.Stock;
import com.stonedt.spider.entity.StockSearch;

public interface OptionalStockService {
	
	
	
	/**
	 * 插入我的自选股
	 * @param code 个股代码
	 * @param simple_name 简称
	 * @param market  上市信息
	 * @param user_id  用户id
	 */
	public void setMyStock(String code,String simple_name,String market,int user_id,Integer stock_id);
	
	
	List<OptionalStock> getOptionStockInfo(int userid);

	/**
	 * 推荐自选股
	 * @return
	 */
	List<Map<String,Object>> getRecommendStock(List<String> list);
	
	/**
	 * 查询个根据 code
	 * @return
	 */
	StockSearch selectStockByCode(String code);
	
	/**
	 * 删除自选股
	 * @param code
	 * @return
	 */
	Integer delMyStock(String code);


	public OptionalStock getStockbycode(Map map);


	public int getCount(OptionalStock option);


	public List<Map<String, Object>> getNotice(String noticecode);


	public String getstockinfo(String code);


	public List<Map<String, Object>> getstocknewsbycode(String code);


	public List<Stock> selectStockByCodes(List<String> codelist);


	public String getstockbaseinfo(String string);
	
	NewsEntity getArticleByArticleId(String articleId);
	
	NewsEntity getLastNews(String publishTime,String code,String oldId);
	
	NewsEntity getNextNews(String publishTime,String code,String oldId);


	public List<StockSearch> selectStockByCodeList(String code,Integer user_id);
}
