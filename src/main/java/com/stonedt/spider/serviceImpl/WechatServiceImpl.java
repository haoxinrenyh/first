package com.stonedt.spider.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stonedt.spider.dao.WechatDao;
import com.stonedt.spider.entity.SpiderArticle;
import com.stonedt.spider.entity.Wechat;
import com.stonedt.spider.service.WechatService;
@Service
public class WechatServiceImpl implements WechatService {
	@Autowired
	private WechatDao wechatDao;

	

	@Override
	public int saveWechat(Wechat weChat) {
		// TODO Auto-generated method stub
		return wechatDao.saveWechat(weChat);
	}

	@Override
	public List<SpiderArticle> listArticle(Integer article_type) {
		// TODO Auto-generated method stub
		return wechatDao.listArticle(article_type);
	}

	@Override
	public List<Wechat> listWechat(Wechat wechat) {
		// TODO Auto-generated method stub
		return wechatDao.listWechat(wechat);
	}

	@Override
	public List<SpiderArticle> getWeboInformationList(int id) {
		return wechatDao.getWeboInformationList(id);
	}
}
