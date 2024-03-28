package com.stonedt.spider.serviceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stonedt.spider.dao.NewsTableDao;
import com.stonedt.spider.entity.SecLabel;
import com.stonedt.spider.service.NewsTableService;

@Service("NewsTableService")
public class NewsTableServiceImpl implements NewsTableService{
	
	@Autowired
	private NewsTableDao newsTableDao;
	
	
	public List<String> getSecondType(String first_type){
		return newsTableDao.getSecondType(first_type);
	}


	@Override
	public List<SecLabel> getSecLabel(Integer page,String first_type) {
		return newsTableDao.getSecLabels(page,first_type);
	}


	@Override
	public List<SecLabel> getSeedIds() {
		// TODO Auto-generated method stub
		return newsTableDao.getSeedIds();
	}
	
	
	@Override
	public List<SecLabel> getType(Map<String,Object> map) {
		// TODO Auto-generated method stub
		return newsTableDao.getType(map);
	}

}
