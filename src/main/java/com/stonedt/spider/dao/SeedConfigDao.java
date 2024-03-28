package com.stonedt.spider.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.stonedt.spider.entity.SpiderSeed;
import com.stonedt.spider.entity.SpiderSeedConfig;

@Mapper
public interface SeedConfigDao {
	int insertSeedconfig(SpiderSeedConfig spiderSeedConfig);

	List<SpiderSeedConfig> listseedconfig(Integer seedId);

	int updateSeedConfig(SpiderSeedConfig spiderSeedConfig);

	SpiderSeedConfig getseedconfig(Integer seedid);

	SpiderSeed selectSeedAtrticleType(Integer seedid);

	int closeMapping(Integer id);

	int insertForeignSeedconfig(SpiderSeedConfig spiderSeedConfig);

	SpiderSeedConfig getforeignseedconfig(Integer seedid);

	SpiderSeed selectForeignSeedAtrticleType(Integer seedid);

	int updateForeignSeedConfig(SpiderSeedConfig spiderSeedConfig);

	int insertSeedconfigtest(SpiderSeedConfig spiderSeedConfig);

	SpiderSeedConfig getseedconfigtest(Integer seedid);

	SpiderSeed selectSeedAtrticleTypetest(Integer seedid);

	void updateseedsflag(@Param("successids") List<Integer> successids);
}
