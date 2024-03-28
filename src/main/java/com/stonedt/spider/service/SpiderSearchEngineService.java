package com.stonedt.spider.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.stonedt.spider.entity.AllocationEntity;
import com.stonedt.spider.entity.SpiderWebsite;

public interface SpiderSearchEngineService {

	List<AllocationEntity> getSearchEngineConfigList();
	
	//拿配置名
	List<AllocationEntity> getConfig_name();

	// 搜索数据
	List<AllocationEntity> getConfiglist(Map<String,Object> map);

	void add(AllocationEntity allocationEntity);

	//编辑 数据回显
	List<AllocationEntity> sumEditor(String id);
	
	void Editor(AllocationEntity allocationEntity);
	
	void runcole(AllocationEntity allocationEntity);

}
