package com.stonedt.spider.serviceImpl;

import com.stonedt.spider.dao.WebsiteshieDao;
import com.stonedt.spider.entity.DataSource;
import com.stonedt.spider.entity.Websiteshie;
import com.stonedt.spider.service.WebsiteshieService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class WebsiteshieServicelmpl implements WebsiteshieService {
	//注入dao
	@Resource
	private WebsiteshieDao WebsiteshieDao;
	@Override
	public List<Websiteshie> Webcheak(Websiteshie Websiteshie) {
		// TODO Auto-generated method stub
		return WebsiteshieDao.Webcheak(Websiteshie);
	}
	@Override
	public void Webadd(Websiteshie Websiteshie) {
		// TODO Auto-generated method stub
		WebsiteshieDao.Webadd(Websiteshie);
	}
	@Override
	public void delte(String id) {
		// TODO Auto-generated method stub
		WebsiteshieDao.delte(id);
	}

	/**
	 * @author dxk
	 * @date 2021/7/26 17:11
	   通过二级域名查询站点是否存在
	 * @return
	 * @throws
	 * @since
	*/
	@Override
	public DataSource seleteWebsiteByTwoDomain(String two_domain) {
		return WebsiteshieDao.seleteWebsiteByTwoDomain(two_domain);
	}

	@Override
	public DataSource findCheckUpdateWeb(String two_domain, int id) {
		return WebsiteshieDao.findCheckUpdateWeb(two_domain,id);
	}

	/**
	 * @author dxk
	 * @date 2021/7/26 17:19
	   更新数据库站点信息
	 * @return
	 * @throws
	 * @since
	*/
	@Override
	public Integer updateWebSite(DataSource dataSource) {
		return WebsiteshieDao.updateWebSite(dataSource);
	}

	/**
	 * @author dxk
	 * @date 2021/7/26 17:19
	  新增数据库站点
	 * @return
	 * @throws
	 * @since
	*/
	@Override
	public Integer insertWebSite(DataSource dataSource) {
		return WebsiteshieDao.insertWebSite(dataSource);
	}

}
