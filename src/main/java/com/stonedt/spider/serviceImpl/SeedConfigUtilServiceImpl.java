package com.stonedt.spider.serviceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stonedt.spider.dao.SeedConfigUtilDao;
import com.stonedt.spider.entity.MappingSpider;
import com.stonedt.spider.entity.SpiderSeedConfigUtilOb;
import com.stonedt.spider.service.SeedConfigUtilService;
@Service("SeedConfigUtilService")
public class SeedConfigUtilServiceImpl implements SeedConfigUtilService {
	@Autowired
	private SeedConfigUtilDao seedConfigUtilDao;

	@Override
	public List<SpiderSeedConfigUtilOb> getUtilByWebSite(Integer websiteId) {
		return seedConfigUtilDao.selectUtilByWebSite(websiteId);
	}

	@Override
	public int insertWebSiteSeedconfig(SpiderSeedConfigUtilOb spiderSeedConfig) {
		return seedConfigUtilDao.insertWebSiteSeedconfig(spiderSeedConfig);
//		return seedConfigUtilDao.insertWebSiteSeedconfigtest(spiderSeedConfig);
	}

	@Override
	public SpiderSeedConfigUtilOb getDetail(Integer id) {
		return seedConfigUtilDao.getDetail(id);
	}

	@Override
	public int updateWebSiteSeedconfig(SpiderSeedConfigUtilOb spiderSeedConfig) {
		return seedConfigUtilDao.updateWebSiteSeedconfig(spiderSeedConfig);
	}

	@Override
	public List<MappingSpider> getlist(Integer websiteId,Integer type) {
		return seedConfigUtilDao.getlist(websiteId,type);
	}

	@Override
	public List<Map<String, Object>> getMappingSpiderConfigs(List<Integer> ids) {
		return seedConfigUtilDao.getMappingSpiderConfigs(ids);
	}

	@Override
	public List<SpiderSeedConfigUtilOb> getUtilByForeignWebSite(Integer websiteId) {
		// TODO Auto-generated method stub
		return seedConfigUtilDao.getUtilByForeignWebSite(websiteId);
	}

	@Override
	public int updateMappingSpider(Map<String, Object> map) {
		// TODO 自动生成的方法存根
		return seedConfigUtilDao.updateMappingSpider(map);
	}

	@Override
	public int updateUrlName(Map<String, Object> map) {
		// TODO 自动生成的方法存根
		return seedConfigUtilDao.updateUrlName(map);
	}

	@Override
	public int insertUrlName(Map<String, Object> map) {
		// TODO 自动生成的方法存根
		return seedConfigUtilDao.insertUrlName(map);
	}
	
}
