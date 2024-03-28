package com.stonedt.spider.service;

import java.util.List;

import com.stonedt.spider.entity.SpiderSeed;
import com.stonedt.spider.entity.SpiderSeedConfig;

public interface SeedConfigService {
	/**
	 * 调用Dao层新增种子配置
	 * @author lujun
	 */
	
	int insertSeedconfig(SpiderSeedConfig spiderSeedConfig);
	
	int updateSeedConfig(SpiderSeedConfig spiderSeedConfig);

	List<SpiderSeedConfig> listseedconfig(Integer seedId);

	SpiderSeedConfig getseedconfig(Integer seedid);
	
	SpiderSeed selectSeedAtrticleType(Integer seedid);

	int closeMapping(int parseInt);

	int insertForeignSeedconfig(SpiderSeedConfig spiderSeedConfig);

	SpiderSeedConfig getforeignseedconfig(Integer seedid);

	SpiderSeed selectForeignSeedAtrticleType(Integer seedid);

	int updateForeignSeedConfig(SpiderSeedConfig spiderSeedConfig);

	int insertSeedconfigtest(SpiderSeedConfig spiderSeedConfig);

	SpiderSeedConfig getseedconfigtest(Integer seedid);

	SpiderSeed selectSeedAtrticleTypetest(Integer seedid);

	void updateSeedsFlag(List<Integer> successids);
}
