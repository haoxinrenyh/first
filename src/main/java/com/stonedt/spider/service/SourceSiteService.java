package com.stonedt.spider.service;

import com.stonedt.spider.entity.DataSource;
import com.stonedt.spider.entity.SourceSite;
import com.stonedt.spider.entity.SpiderTestData;

import java.util.ArrayList;

public interface SourceSiteService {

	SourceSite searchSiteById(Integer website_id);

	void state(Integer state,Integer id);

	void eliminate(Integer id);

	ArrayList<SpiderTestData> seeSection(Integer id);

	void updateUserByID(DataSource ds);
}
