package com.stonedt.spider.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.stonedt.spider.entity.SeedAll;
import com.stonedt.spider.entity.SpiderCount;

@Mapper
public interface SpiderCountDao {

	int getNum(SpiderCount count);

	int getTimeNum(SpiderCount count);

	List<SeedAll> seedall(SeedAll SeedAll);

	List<SeedAll> seeiddall(SeedAll SeedAll);

	List<SeedAll> dimseed(SeedAll SeedAll);

}
