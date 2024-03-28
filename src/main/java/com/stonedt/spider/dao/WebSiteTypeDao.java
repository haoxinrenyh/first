package com.stonedt.spider.dao;

import java.util.List;
import java.util.Map;

import com.stonedt.spider.entity.EsIndexRatio;
import com.stonedt.spider.entity.EsWebsiteCategoryRatio;
import com.stonedt.spider.entity.WebsiteType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author 丁祥珂
 * @version V1.0
 * @date 2021/4/15 11:29
 * @Copyright 数据类型
 */
@Mapper
public interface WebSiteTypeDao {
	// 查询id typename 字段
	List<Map<String, Object>> selectList();

	List<Map<String, Object>> websitetypes(@Param("categoryId") int categoryId);

	List<WebsiteType> page(@Param("limit")String limit);

	int pageCount();

	int insertWebsiteType(WebsiteType websitetype);

	int updateWebsiteType(WebsiteType websitetype);

	WebsiteType findOne(WebsiteType websitetype);

	List<EsIndexRatio> findWebsiteTypeAll();

	List<EsWebsiteCategoryRatio> findWebsiteCategoryAll();

}
