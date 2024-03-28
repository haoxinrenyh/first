package com.stonedt.spider.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.stonedt.spider.entity.SpiderArticle;
import com.stonedt.spider.entity.Wechat;

@Mapper
public interface WechatDao {
	int saveWechat(Wechat weChat);// 添加资源

	List<SpiderArticle> listArticle(Integer article_type);// 查看资讯

	List<Wechat> listWechat(Wechat wechat);

	List<SpiderArticle> getWeboInformationList(int id);

}
