package com.stonedt.spider.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.stonedt.spider.entity.SecLabel;

@Mapper
public interface NewsTableDao {

	public List<String> getSecondType(String first_type);

	public List<SecLabel> getSecLabels(@Param(value = "page") Integer page,
			@Param(value = "first_type") String first_type);

	public List<SecLabel> getSeedIds();

	public List<SecLabel> getType(Map<String, Object> map);
}
