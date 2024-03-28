package com.stonedt.spider.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BiddingDao {
	public List<Map<String, Object>> getInformationtype();

}
