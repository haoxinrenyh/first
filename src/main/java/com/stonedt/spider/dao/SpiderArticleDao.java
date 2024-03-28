package com.stonedt.spider.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.stonedt.spider.entity.SpiderArticle;

@Mapper
public interface SpiderArticleDao {

	List<SpiderArticle> listArticle(Integer seedid);

}
