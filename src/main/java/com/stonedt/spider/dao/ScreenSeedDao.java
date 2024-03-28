package com.stonedt.spider.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.stonedt.spider.entity.ScreenWord;

@Mapper
public interface ScreenSeedDao {

	List<ScreenWord> getList();

	String getStringList();

	void remove(Integer id);

	String getbyword(String word);

	void add(String word);

	String getforeignStringList();

}
