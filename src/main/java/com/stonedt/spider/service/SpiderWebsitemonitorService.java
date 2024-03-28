package com.stonedt.spider.service;

import java.util.List;
import java.util.Map;

import com.stonedt.spider.entity.SpiderWebsite;
import com.stonedt.spider.entity.TransferinfoEntity;
import com.stonedt.spider.entity.WebsitespidertypeEntity;

public interface SpiderWebsitemonitorService {

//	List<Map<String, Object>> listmonitorwebsite();

	List<Map<String, Object>> listtypename(Integer websiteId);

	List<Map<String, Object>> gettypename();

	int addtransdata(TransferinfoEntity tf);

	int addwebsitespidertype(WebsitespidertypeEntity wt);

	List<Map<String, Object>> listmonitorwebsite(Map<String, Object> map);

	Map<String, Object> selectSeedInfo(Map<String, Object> map);

	Integer updateSeendInfo(Map<String, Object> map);

	Map<String,Object> getTypeInfo(Map<String,Object> map);

}
