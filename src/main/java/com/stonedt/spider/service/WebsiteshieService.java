package com.stonedt.spider.service;

import com.stonedt.spider.entity.DataSource;
import com.stonedt.spider.entity.Websiteshie;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WebsiteshieService {

	List<Websiteshie> Webcheak(Websiteshie Websiteshie);
	
	void Webadd(Websiteshie Websiteshie);
	
	void delte(String id);

    DataSource seleteWebsiteByTwoDomain(String two_domain);

	DataSource findCheckUpdateWeb(String two_domain, int id);

	Integer updateWebSite(DataSource dataSource);

	Integer insertWebSite(DataSource dataSource);
}
