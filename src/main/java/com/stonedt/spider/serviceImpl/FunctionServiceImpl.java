package com.stonedt.spider.serviceImpl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stonedt.spider.dao.FunctionDao;
import com.stonedt.spider.entity.Function;
import com.stonedt.spider.service.FunctionService;

@Service
public class FunctionServiceImpl implements FunctionService {
	
	
	@Autowired
	private FunctionDao functionDao;

	@Override
	public ArrayList<Function> list() {
		return functionDao.list();
	}

	@Override
	public int removeById(String id) {
		// TODO Auto-generated method stub
		return functionDao.removeById(id);
	}

	@Override
	public Function getById(String id) {
		// TODO Auto-generated method stub
		return functionDao.getById(id);
	}

	

	@Override
	public void save(Function function) {
		functionDao.save(function);
		
	}

	@Override
	public void update(Function function) {
		functionDao.update(function);
		
	}

	

}
