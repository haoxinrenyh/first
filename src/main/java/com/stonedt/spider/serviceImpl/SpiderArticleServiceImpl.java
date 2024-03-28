package com.stonedt.spider.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stonedt.spider.dao.SpiderArticleDao;
import com.stonedt.spider.entity.SpiderArticle;
import com.stonedt.spider.service.SpiderArticleService;
@Service("SpiderArticleService")
public class SpiderArticleServiceImpl implements SpiderArticleService {

	
	@Autowired
	private SpiderArticleDao spiderArticleDao;
	@Override
	public List<SpiderArticle> listArticle(Integer seedid) {
		// TODO Auto-generated method stub
		return spiderArticleDao.listArticle(seedid);
	}

}
