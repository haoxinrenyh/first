package com.stonedt.spider.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stonedt.spider.dao.SeedDao;
import com.stonedt.spider.entity.KeyWord;
import com.stonedt.spider.entity.SecLabel;
import com.stonedt.spider.entity.SpiderSeed;
import com.stonedt.spider.service.SeedService;
import com.stonedt.spider.util.DateUtil;
@Service("SeedService")
public class SeedServiceImpl implements SeedService {
	@Autowired
	private SeedDao seedDao;
	
	/**
	 * 新增种子连接
	 * @author lujun
	 *
	 */
	@Override
	public int insertseed(SpiderSeed spiderSeed) {
		// TODO Auto-generated method stub
		
		return seedDao.insertseed(spiderSeed);
	}
	
	/**
	 * 查询种子连接
	 * @author lujun
	 *
	 */
	@Override
	public List<SpiderSeed> listseed(Integer websiteId) {
		// TODO Auto-generated method stub
		return seedDao.listseed(websiteId);
	}
	
	
	/**
	 * 修改种子连接
	 * @author lujun
	 *
	 */
	@Override
	public int updateSeed(SpiderSeed spiderSeed) {
		
		return seedDao.updateSeed(spiderSeed);
	}

	@Override
	public SpiderSeed getInfo(Integer seedid) {
		// TODO Auto-generated method stub
		return seedDao.getInfo(seedid);
	}

	@Override
	public int updateSeedstatus(SpiderSeed spiderSeed) {
		// TODO Auto-generated method stub
		return seedDao.updateSeedstatus(spiderSeed);
	}

	@Override
	public int removeSeedById(Integer seedid) {
		// TODO Auto-generated method stub
		return seedDao.removeSeedById(seedid);
	}

	@Override
	public List<SecLabel> getAllLabels() {
		return seedDao.getAllLabels();
	}

	@Override
	public String getNumByType(String first_type) {
		return seedDao.getNumByType(first_type);
	}

	@Override
	public List<SpiderSeed> nulldataseedlist() {
		return seedDao.nulldataseedlist(DateUtil.minusDay(1).substring(0,10));
	}

	@Override
	public List<KeyWord> getKeywordList() {
		return seedDao.getKeywordList();
	}

	@Override
	public KeyWord getKeywordById(int id) {
		return seedDao.getKeywordById(id);
	}

	@Override
	public int addKeyWord(String keyword) {
		return seedDao.addKeyWord(keyword,DateUtil.getDate());
	}

	@Override
	public List<SpiderSeed> dataseedlist(int min, int max, String date,String websiteName) {
		return seedDao.dataseedlist(min,max,date,websiteName);
	}

	@Override
	public List<SpiderSeed> foreignlistseed(Integer websiteId) {
		// TODO Auto-generated method stub
		return seedDao.foreignlistseed(websiteId);
	}

	@Override
	public SpiderSeed getforeignInfo(Integer seedid) {
		// TODO Auto-generated method stub
		return seedDao.getforeignInfo(seedid);
	}

	@Override
	public int insertforeignseed(SpiderSeed spiderSeed) {
		// TODO Auto-generated method stub
		return seedDao.insertforeignseed(spiderSeed);
	}

	@Override
	public int removeForeignSeedById(Integer seedid) {
		// TODO Auto-generated method stub
		return seedDao.removeForeignSeedById(seedid);
	}

	@Override
	public int updateForeignSeed(SpiderSeed spiderSeed) {
		// TODO Auto-generated method stub
		return seedDao.updateForeignSeed(spiderSeed);
	}

	@Override
	public int updateForeignSeedstatus(SpiderSeed spiderSeed) {
		// TODO Auto-generated method stub
		return seedDao.updateForeignSeedstatus(spiderSeed);
	}

	@Override
	public List<SpiderSeed> listseedtest(Integer websiteId) {
		// TODO Auto-generated method stub
		return seedDao.listseedtest(websiteId);
	}

	@Override
	public int insertseedtest(SpiderSeed spiderSeed) {
		// TODO Auto-generated method stub
		return seedDao.insertseedtest(spiderSeed);
	}

	@Override
	public int updateSeedstatustest(SpiderSeed spiderSeed) {
		// TODO Auto-generated method stub
		return seedDao.updateSeedstatustest(spiderSeed);
	}

	@Override
	public SpiderSeed getInfotest(Integer seedid) {
		// TODO Auto-generated method stub
		return seedDao.getInfotest(seedid);
	}

}
