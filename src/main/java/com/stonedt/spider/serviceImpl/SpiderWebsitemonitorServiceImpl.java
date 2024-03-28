package com.stonedt.spider.serviceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stonedt.spider.dao.SpiderWebsitemonitorDao;
import com.stonedt.spider.entity.TransferinfoEntity;
import com.stonedt.spider.entity.WebsitespidertypeEntity;
import com.stonedt.spider.service.SpiderWebsitemonitorService;
@Service
public class SpiderWebsitemonitorServiceImpl implements SpiderWebsitemonitorService {

	@Autowired
	private SpiderWebsitemonitorDao spiderWebmonitorDao;

//	@Override
//	public List<Map<String, Object>> listmonitorwebsite() {
//		// TODO Auto-generated method stub
//		return spiderWebmonitorDao.listmonitorwebsite();
//	}

	@Override
	public List<Map<String, Object>> listtypename(Integer websiteId) {
		return spiderWebmonitorDao.listtypename(websiteId);
	}

	@Override
	public List<Map<String, Object>> gettypename() {
		// TODO Auto-generated method stub
		return spiderWebmonitorDao.gettypename();
	}

	@Override
	public int addtransdata(TransferinfoEntity tf) {
		// TODO Auto-generated method stub
		return spiderWebmonitorDao.addtransdata(tf);
	}

	@Override
	public int addwebsitespidertype(WebsitespidertypeEntity wt) {
		// TODO Auto-generated method stub
		return spiderWebmonitorDao.addwebsitespidertype(wt);
	}

	@Override
	public List<Map<String, Object>> listmonitorwebsite(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return spiderWebmonitorDao.listmonitorwebsite(map);
	}

	@Override
	public Map<String, Object> selectSeedInfo(Map<String,Object> map) {
		return spiderWebmonitorDao.selectSeedInfo(map);
	}

	@Override
	public Integer updateSeendInfo(Map<String, Object> map) {
		return spiderWebmonitorDao.updateSeendInfo(map);
	}

	@Override
	public Map<String, Object> getTypeInfo(Map<String, Object> map) {
		return spiderWebmonitorDao.getTypeIndexInfo(map);
	}
}
