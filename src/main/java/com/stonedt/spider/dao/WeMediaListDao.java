package com.stonedt.spider.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.stonedt.spider.entity.WeMedia;
import com.stonedt.spider.entity.platform;

@Mapper
public interface WeMediaListDao {

	public List<WeMedia> Getmedia(WeMedia WeMedia);

	public List<platform> Getplatform(platform platform);

	public List<platform> platname(platform platform);
}
