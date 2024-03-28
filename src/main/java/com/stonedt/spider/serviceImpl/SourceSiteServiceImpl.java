package com.stonedt.spider.serviceImpl;

import com.stonedt.spider.dao.SourceSiteDao;
import com.stonedt.spider.entity.DataSource;
import com.stonedt.spider.entity.SourceSite;
import com.stonedt.spider.entity.SpiderTestData;
import com.stonedt.spider.service.SourceSiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SourceSiteServiceImpl implements SourceSiteService {

	@Autowired
	SourceSiteDao sourceSiteDao;

	@Override
	public SourceSite searchSiteById(Integer website_id) {
		return sourceSiteDao.searchSiteById(website_id);
	}

	@Override
	public void state(Integer state, Integer id) {
           sourceSiteDao.updatestate(state,id);
	}

	@Override
	public void eliminate(Integer id) {
		sourceSiteDao.eliminate(id,1);
	}

	@Override
	public ArrayList<SpiderTestData> seeSection(Integer id) {
	ArrayList<SpiderTestData> list= sourceSiteDao.seeSection(id);
		return list;
	}

	@Override
	public void updateUserByID(DataSource ds) {
		sourceSiteDao.updateUserByID(ds);
	}


}
