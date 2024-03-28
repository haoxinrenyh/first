package com.stonedt.spider.service;

import java.util.List;

import com.stonedt.spider.entity.SeedAll;
import com.stonedt.spider.entity.SpiderCount;

public interface SpiderCountService {

	int getNum(SpiderCount count);

	int getTimeNum(SpiderCount count);
	//搜索种子  全部种子id和种子name
	List<SeedAll> seedall(SeedAll SeedAll);
	//拿着种子id查看name  对应网站名称等
	List<SeedAll> seeiddall(SeedAll SeedAll);
	//模糊查询所有种子
	List<SeedAll> dimseed(SeedAll SeedAll);
}
