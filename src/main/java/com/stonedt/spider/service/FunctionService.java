package com.stonedt.spider.service;

import java.util.ArrayList;

import com.stonedt.spider.entity.Function;

public interface FunctionService {

	ArrayList<Function> list();

	int removeById(String id);

	Function getById(String id);


	void save(Function function);

	void update(Function function);

}
