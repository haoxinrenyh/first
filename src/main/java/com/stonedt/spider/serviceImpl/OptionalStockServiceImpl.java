package com.stonedt.spider.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stonedt.spider.dao.OptionalStockDao;
import com.stonedt.spider.entity.NewsEntity;
import com.stonedt.spider.entity.OptionalStock;
import com.stonedt.spider.entity.Stock;
import com.stonedt.spider.entity.StockSearch;
import com.stonedt.spider.service.OptionalStockService;

@Service("OptionalStockService")
public class OptionalStockServiceImpl implements OptionalStockService {
	@Autowired
	private OptionalStockDao optionalStockDao;

	@Override
	public List<OptionalStock> getOptionStockInfo(int userid) {

		return optionalStockDao.getOptionStockInfo(userid);
	}

	/**
	 * 插入我的自选股
	 */
	@Override
	public void setMyStock(String code, String simple_name, String market, int user_id, Integer stock_id) {
		optionalStockDao.setMyStock(code, simple_name, market, user_id, stock_id);
	}

	/**
	 * 推荐自选股
	 */
	@Override
	public List<Map<String, Object>> getRecommendStock(List<String> list) {
		// TODO Auto-generated method stub
		return optionalStockDao.getRecommendStock(list);
	}

	/**
	 * 查询个根据 code
	 */
	@Override
	public StockSearch selectStockByCode(String code) {
		// TODO Auto-generated method stub
		return optionalStockDao.selectStockByCode(code);
	}

	/**
	 * 删除自选股
	 */
	@Override
	public Integer delMyStock(String code) {
		// TODO Auto-generated method stub
		return optionalStockDao.delMyStock(code);
	}

	@Override
	public OptionalStock getStockbycode(Map map) {
		return optionalStockDao.getStockbycode(map);
	}

	@Override
	public int getCount(OptionalStock option) {

		return optionalStockDao.getCount(option);
	}

	@Override
	public List<Map<String, Object>> getNotice(String noticecode) {
		return optionalStockDao.getNotice(noticecode);
	}

	@Override
	public String getstockinfo(String code) {
		// TODO Auto-generated method stub
		return optionalStockDao.getstockinfo(code);
	}

	@Override
	public List<Map<String, Object>> getstocknewsbycode(String code) {
		// TODO Auto-generated method stub
		return optionalStockDao.getstocknewsbycode(code);
	}

	/**
	 * 查询根据 code list
	 */
	@Override
	public List<Stock> selectStockByCodes(List<String> codelist) {
		// TODO Auto-generated method stub
		List<Stock> codelists = new ArrayList<>();
		for (String code : codelist) {
			codelists.add(optionalStockDao.selectStockByCodeAndName(code));
		}
		return codelists;
	}

	@Override
	public String getstockbaseinfo(String string) {
		// TODO Auto-generated method stub
		return optionalStockDao.getstockbaseinfo(string);
	}

	@Override
	public NewsEntity getArticleByArticleId(String articleId) {

		return optionalStockDao.getArticleByArticleId(articleId);
	}

	@Override
	public NewsEntity getLastNews(String publishTime, String code, String oldId) {
		// TODO Auto-generated method stub
		return optionalStockDao.getLastNews(publishTime, code, oldId);
	}

	@Override
	public NewsEntity getNextNews(String publishTime, String code, String oldId) {
		// TODO Auto-generated method stub
		return optionalStockDao.getNextNews(publishTime, code, oldId);
	}

	@Override
	public List<StockSearch> selectStockByCodeList(String code, Integer user_id) {
		// TODO Auto-generated method stub
		return optionalStockDao.selectStockByCodeList(code, user_id);
	}

}
