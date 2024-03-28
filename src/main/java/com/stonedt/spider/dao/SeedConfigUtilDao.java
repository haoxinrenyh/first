package com.stonedt.spider.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.stonedt.spider.entity.MappingSpider;
import com.stonedt.spider.entity.SpiderSeed;
import com.stonedt.spider.entity.SpiderSeedConfigUtilOb;

@Mapper
public interface SeedConfigUtilDao {

	List<SpiderSeedConfigUtilOb> selectUtilByWebSite(Integer websiteId);

	SpiderSeedConfigUtilOb getDetail(Integer id);

	int insertWebSiteSeedconfig(SpiderSeedConfigUtilOb spiderSeedConfig);

	int updateWebSiteSeedconfig(SpiderSeedConfigUtilOb spiderSeedConfig);

	List<MappingSpider> getlist(@Param("websiteId") Integer websiteId, @Param("type") Integer type);

	List<Map<String, Object>> getMappingSpiderConfigs(@Param("ids") List<Integer> ids);

	List<SpiderSeedConfigUtilOb> getUtilByForeignWebSite(Integer websiteId);

	int insertWebSiteSeedconfigtest(SpiderSeedConfigUtilOb spiderSeedConfig);

	int updateMappingSpider(Map<String, Object> map);

	int updateUrlName(Map<String, Object> map);

	int insertUrlName(Map<String, Object> map);
}
