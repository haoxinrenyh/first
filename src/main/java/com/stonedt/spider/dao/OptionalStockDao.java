package com.stonedt.spider.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.stonedt.spider.entity.NewsEntity;
import com.stonedt.spider.entity.OptionalStock;
import com.stonedt.spider.entity.Stock;
import com.stonedt.spider.entity.StockSearch;

@Mapper
public interface OptionalStockDao {

	List<OptionalStock> getOptionStockInfo(int userid);

	/**
	 * 插入我的自选股
	 * 
	 * @param code        个股代码
	 * @param simple_name 简称
	 * @param market      上市信息
	 * @param user_id     用户id
	 */
	public void setMyStock(@Param(value = "code") String code, @Param(value = "simple_name") String simple_name,
			@Param(value = "market") String market, @Param(value = "user_id") int user_id,
			@Param(value = "stock_id") Integer stock_id);

	/**
	 * 推荐自选股
	 * 
	 * @return
	 */
	List<Map<String, Object>> getRecommendStock(List<String> list);

	/**
	 * 查询个根据 code
	 * 
	 * @return
	 */
	StockSearch selectStockByCode(@Param("code") String code);

	/**
	 * 删除自选股
	 * 
	 * @param code
	 * @return
	 */
	Integer delMyStock(@Param("code") String code);

	OptionalStock getStockbycode(Map map);

	/**
	 * 判断当前个股是否被收藏
	 * 
	 * @param option
	 * @return
	 */
	int getCount(OptionalStock option);

	/**
	 * 根据编码获取编码公告信息
	 * 
	 * @param noticecode
	 * @return
	 */
	List<Map<String, Object>> getNotice(String noticecode);

	String getstockinfo(String code);

	List<Map<String, Object>> getstocknewsbycode(String code);

	/**
	 * 
	 * @Description:根据codelist查询
	 * @param codelist @return（展示方法参数和返回值）
	 */
	Stock selectStockByCodeAndName(@Param("code") String code);

	String getstockbaseinfo(String code);

	NewsEntity getArticleByArticleId(@Param(value = "articleId") String articleId);

	NewsEntity getLastNews(@Param(value = "publishTime") String publishTime, @Param(value = "code") String code,
			@Param(value = "oldId") String oldId);

	NewsEntity getNextNews(@Param(value = "publishTime") String publishTime, @Param(value = "code") String code,
			@Param(value = "oldId") String oldId);

	List<StockSearch> selectStockByCodeList(@Param("code") String code, @Param("user_id") Integer user_id);
}
