package com.stonedt.spider.service;

import java.util.List;

import com.stonedt.spider.entity.SpiderArticle;
import com.stonedt.spider.entity.Wechat;

public interface WechatService {

	int saveWechat(Wechat weChat);//添加资源

	List<SpiderArticle> listArticle(Integer article_type);//查看资讯

	List<Wechat> listWechat(Wechat wechat);

	List<SpiderArticle> getWeboInformationList(int id);
}
