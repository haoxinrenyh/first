package com.stonedt.spider.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.stonedt.spider.entity.Seekweb;

@Mapper
public interface SeekwebDao {

	List<Seekweb> getSeekList(Seekweb Seekweb);

	Seekweb getoneList(Seekweb Seekweb);

}
