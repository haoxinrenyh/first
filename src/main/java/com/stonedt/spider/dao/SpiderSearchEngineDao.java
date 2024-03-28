package com.stonedt.spider.dao;

import java.util.List;
import java.util.Map;

import com.stonedt.spider.entity.AllocationEntity;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SpiderSearchEngineDao {

	List<AllocationEntity> getSearchEngineConfigList();

	void add(AllocationEntity allocationEntity);

	List<AllocationEntity> getConfig_name();

	List<AllocationEntity> getConfiglist(@Param("map") Map<String, Object> map);

	// 编辑 数据回显
	List<AllocationEntity> sumEditor(String id);

	void Editor(AllocationEntity allocationEntity);

	void runcole(AllocationEntity allocationEntity);

}
