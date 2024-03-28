package com.stonedt.spider.service;

import java.util.List;
import java.util.Map;

import com.stonedt.spider.entity.MappingSpider;
import com.stonedt.spider.entity.SpiderSeed;
import com.stonedt.spider.entity.SpiderSeedConfig;
import com.stonedt.spider.entity.SpiderSeedConfigUtilOb;

public interface SeedConfigUtilService {

	List<SpiderSeedConfigUtilOb> getUtilByWebSite(Integer websiteId);

	int insertWebSiteSeedconfig(SpiderSeedConfigUtilOb spiderSeedConfig);

	SpiderSeedConfigUtilOb getDetail(Integer id);

	int updateWebSiteSeedconfig(SpiderSeedConfigUtilOb spiderSeedConfig);

	List<MappingSpider> getlist(Integer websiteId, Integer type);

	List<Map<String, Object>> getMappingSpiderConfigs(List<Integer> ids);

	List<SpiderSeedConfigUtilOb> getUtilByForeignWebSite(Integer websiteId);

	int updateMappingSpider(Map<String, Object> map);

	int updateUrlName(Map<String, Object> map);

	int insertUrlName(Map<String, Object> map);

}
