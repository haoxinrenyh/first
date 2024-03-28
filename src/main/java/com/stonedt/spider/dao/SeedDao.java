package com.stonedt.spider.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.stonedt.spider.entity.KeyWord;
import com.stonedt.spider.entity.SecLabel;
import com.stonedt.spider.entity.SeedCountOb;
import com.stonedt.spider.entity.SpiderSeed;

@Mapper
public interface SeedDao {
	int insertseed(SpiderSeed spiderSeed);

	List<SpiderSeed> listseed(Integer websiteId);

	int updateSeed(SpiderSeed spiderSeed);

	SpiderSeed getInfo(Integer seedid);

	int updateSeedstatus(SpiderSeed spiderSeed);

	int removeSeedById(Integer seedid);

	List<SecLabel> getAllLabels();

	/**
	 * 根据类型查询数量
	 * 
	 * @param first_type
	 * @return
	 */
	String getNumByType(@Param(value = "first_type") String first_type);

	/**
	 * 获取昨天没有数据的种子
	 */
	List<SpiderSeed> nulldataseedlist(@Param(value = "date") String date);

	/**
	 * 获取关键词列表
	 */
	List<KeyWord> getKeywordList();

	/**
	 * 根据ID获取关键词信息
	 * 
	 * @param id
	 * @return
	 */
	KeyWord getKeywordById(@Param(value = "id") int id);

	/**
	 * 添加关键词
	 * 
	 * @return
	 */
	int addKeyWord(@Param(value = "keyword") String keyword, @Param(value = "createtime") String createtime);

	List<SpiderSeed> dataseedlist(@Param(value = "min") int min, @Param(value = "max") int max,
			@Param(value = "date") String date, @Param(value = "websiteName") String websiteName);

	List<SeedCountOb> getSeedCountForWeek(@Param(value = "date") String date);

	void updateSpiderSeed(SeedCountOb spiderSeed);

	List<SpiderSeed> foreignlistseed(Integer websiteId);

	SpiderSeed getforeignInfo(Integer seedid);

	int insertforeignseed(SpiderSeed spiderSeed);

	int removeForeignSeedById(Integer seedid);

	int updateForeignSeed(SpiderSeed spiderSeed);

	int updateForeignSeedstatus(SpiderSeed spiderSeed);

	List<SpiderSeed> listseedtest(Integer websiteId);

	int insertseedtest(SpiderSeed spiderSeed);

	int updateSeedstatustest(SpiderSeed spiderSeed);

	SpiderSeed getInfotest(Integer seedid);
}
