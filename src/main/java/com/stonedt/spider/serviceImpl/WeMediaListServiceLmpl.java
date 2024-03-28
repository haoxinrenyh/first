package com.stonedt.spider.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.stonedt.spider.entity.WeMedia;
import com.stonedt.spider.entity.platform;
import com.stonedt.spider.service.WeMediaListService;
import com.stonedt.spider.dao.WeMediaListDao;
@Service
public class WeMediaListServiceLmpl implements WeMediaListService {
	@Resource
	private WeMediaListDao WeMediaListDao;
	
	@Override
	public List<WeMedia> Getmedia(WeMedia WeMedia) {
		// TODO Auto-generated method stub
		return WeMediaListDao.Getmedia(WeMedia);
	}

	@Override
	public List<platform> Getplatform(platform platform) {
		// TODO Auto-generated method stub
		return WeMediaListDao.Getplatform(platform);
	}

	@Override
	public List<platform> platname(platform platform) {
		// TODO Auto-generated method stub
		return WeMediaListDao.platname(platform);
	}

}
