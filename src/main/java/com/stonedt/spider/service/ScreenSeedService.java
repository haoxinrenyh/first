package com.stonedt.spider.service;

import java.util.List;

import com.stonedt.spider.entity.ScreenWord;
import com.stonedt.spider.util.ResultUtil;

public interface ScreenSeedService {

	List<ScreenWord> getList();

	String getStringList();

	ResultUtil remove(Integer id);

	ResultUtil add(String word);

	String getforeignStringList();

}
