package com.stonedt.spider.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.stonedt.spider.entity.Function;

@Mapper
public interface FunctionDao {

	ArrayList<Function> list();

	int removeById(String id);

	Function getById(String id);

	void update(Function function);

	void save(Function function);


}
