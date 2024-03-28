package com.stonedt.spider.dao;

import com.stonedt.spider.entity.DataSource;
import com.stonedt.spider.entity.SourceSite;
import com.stonedt.spider.entity.SpiderTestData;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

@Mapper
public interface SourceSiteDao {

	SourceSite searchSiteById(@Param("id") Integer website_id);

	void updatestate(@Param("state") Integer state, @Param("id") Integer id);

	void eliminate(@Param("id") Integer id, @Param("state") Integer state);

	ArrayList<SpiderTestData> seeSection(@Param("id") Integer id);

	void updateUserByID(DataSource ds);

//	List<SpiderFlow> searchSpiderFlows(@Param("website_id")Integer website_id);

}
