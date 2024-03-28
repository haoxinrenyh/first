package com.stonedt.spider.dao;

import com.stonedt.spider.entity.DataSource;
import com.stonedt.spider.entity.Websiteshie;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WebsiteshieDao {

	List<Websiteshie> Webcheak(Websiteshie Websiteshie);

	void Webadd(Websiteshie Websiteshie);

	void delte(String id);

	// 通过二级域名查询站点
	DataSource seleteWebsiteByTwoDomain(@Param("two_domain") String two_domain);

	DataSource findCheckUpdateWeb(@Param("two_domain") String two_domain,@Param("id")int id);

	Integer updateWebSite(@Param("dataSource") DataSource dataSource);

	Integer insertWebSite(@Param("dataSource") DataSource dataSource);
}
