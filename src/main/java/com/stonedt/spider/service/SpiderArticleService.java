package com.stonedt.spider.service;

import java.util.Date;
import java.util.List;

import com.stonedt.spider.entity.SpiderArticle;

public interface SpiderArticleService {

	List<SpiderArticle> listArticle(Integer seedid);
}
