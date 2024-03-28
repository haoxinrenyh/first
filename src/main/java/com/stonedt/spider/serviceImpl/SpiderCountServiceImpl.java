package com.stonedt.spider.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stonedt.spider.dao.SpiderCountDao;
import com.stonedt.spider.entity.SeedAll;
import com.stonedt.spider.entity.SpiderCount;
import com.stonedt.spider.service.SpiderCountService;
@Service
public class SpiderCountServiceImpl implements SpiderCountService {

	
	@Autowired
	private SpiderCountDao spiderCountDao;
	@Override
	public int getNum(SpiderCount count) {
		
		
		return spiderCountDao.getNum(count);
	}
	@Override
	public int getTimeNum(SpiderCount count) {
		return spiderCountDao.getTimeNum(count);
	}
	@Override
	public List<SeedAll> seedall(SeedAll SeedAll) {
		// TODO Auto-generated method stub
		return spiderCountDao.seedall(SeedAll);
	}
	@Override
	public List<SeedAll> seeiddall(SeedAll SeedAll) {
		// TODO Auto-generated method stub
		return spiderCountDao.seeiddall(SeedAll);
	}
	@Override
	public List<SeedAll> dimseed(SeedAll SeedAll) {
		// TODO Auto-generated method stub
		return spiderCountDao.dimseed(SeedAll);
	}

}
