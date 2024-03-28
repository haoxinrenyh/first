package com.stonedt.spider.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.stonedt.spider.entity.KeyWord;
import com.stonedt.spider.entity.SecLabel;
import com.stonedt.spider.entity.SpiderSeed;

public interface SeedService {
	int insertseed(SpiderSeed spiderSeed);
	List<SpiderSeed> listseed(Integer websiteId);
	int updateSeed(SpiderSeed spiderSeed);
	SpiderSeed getInfo(Integer seedid);
	SpiderSeed getforeignInfo(Integer seedid);
	int updateSeedstatus(SpiderSeed spiderSeed);
	int removeSeedById(Integer seedid);
	
	List<SecLabel> getAllLabels();
	
	/**
	 * 根据类型查询数量
	 * @param first_type
	 * @return
	 */
	String getNumByType(String first_type);
	
	/**
	 * 获取没有数据的种子
	 */
	List<SpiderSeed> nulldataseedlist();
	
	/**
	 * 获取关键词列表
	 */
	List<KeyWord> getKeywordList();
	
	/**
	 * 根据ID获取关键词信息
	 * @param id
	 * @return
	 */
	KeyWord getKeywordById(int id);
	
	/**
	 * 添加关键词
	 * @return
	 */
	int addKeyWord(String keyword);
	List<SpiderSeed> dataseedlist(int min, int max, String date,String websiteName);
	List<SpiderSeed> foreignlistseed(Integer websiteId);
	int insertforeignseed(SpiderSeed spiderSeed);
	int removeForeignSeedById(Integer seedid);
	int updateForeignSeed(SpiderSeed spiderSeed);
	int updateForeignSeedstatus(SpiderSeed spiderSeed);
	List<SpiderSeed> listseedtest(Integer websiteId);
	int insertseedtest(SpiderSeed spiderSeed);
	int updateSeedstatustest(SpiderSeed spiderSeed);
	SpiderSeed getInfotest(Integer seedid);
}
