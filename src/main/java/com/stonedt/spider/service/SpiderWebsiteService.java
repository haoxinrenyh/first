package com.stonedt.spider.service;

import java.util.List;
import java.util.Map;

import com.stonedt.spider.entity.SpiderWebsite;

public interface SpiderWebsiteService {

//	List<Map<String, Object>> listwebsite(int id);

	List<Map<String, Object>> listwebsite(Map<String,Object> map);

	int savewebsite(SpiderWebsite website);

	SpiderWebsite getInfo(Integer websiteId);

	int updatewebsite(SpiderWebsite website);

	int startsMapping(int websiteId);

	List<SpiderWebsite> getAllwebsite();

	List<Map<String, Object>> foreignlistwebsite(int id);

	SpiderWebsite getForeignInfo(Integer websiteId);

	int saveforeignwebsite(SpiderWebsite website);

	int updateforeignwebsite(SpiderWebsite website);

	int startCheckMapping(int websiteId);

	
	

}
