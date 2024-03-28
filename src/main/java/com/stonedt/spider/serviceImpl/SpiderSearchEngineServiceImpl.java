package com.stonedt.spider.serviceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stonedt.spider.dao.SpiderSearchEngineDao;
import com.stonedt.spider.entity.AllocationEntity;
import com.stonedt.spider.service.SpiderSearchEngineService;
@Service
public class SpiderSearchEngineServiceImpl implements SpiderSearchEngineService {
	
	@Autowired
	private SpiderSearchEngineDao spiderSearchEngineDao;

	@Override
	public List<AllocationEntity> getSearchEngineConfigList() {
		return spiderSearchEngineDao.getSearchEngineConfigList();
	}
	/**
	 * 搜搜
	 * @param map
	 * @return
	 */
	@Override
	public List<AllocationEntity> getConfiglist(Map<String, Object> map) {
		List<AllocationEntity> list =  spiderSearchEngineDao.getConfiglist(map);
		return list;
	}

	@Override
	public void add(AllocationEntity allocationEntity) {
		spiderSearchEngineDao.add(allocationEntity);
		
	}

	@Override
	public List<AllocationEntity> getConfig_name() {
		// TODO Auto-generated method stub
		return spiderSearchEngineDao.getConfig_name();
	}




	@Override
	public List<AllocationEntity> sumEditor(String id) {
		// TODO Auto-generated method stub
		return spiderSearchEngineDao.sumEditor(id);
	}

	@Override
	public void Editor(AllocationEntity allocationEntity) {
		spiderSearchEngineDao.Editor(allocationEntity);
		
	}

	@Override
	public void runcole(AllocationEntity allocationEntity) {
		// TODO Auto-generated method stub
		spiderSearchEngineDao.runcole(allocationEntity);
	}


}
