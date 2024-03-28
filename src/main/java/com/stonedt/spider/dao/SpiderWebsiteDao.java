package com.stonedt.spider.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.stonedt.spider.entity.SpiderWebsite;

@Mapper
public interface SpiderWebsiteDao {

//	List<Map<String, Object>> listwebsite(@Param("userid")int userid,@Param("now")String now);

	List<Map<String, Object>> listwebsite(Map<String, Object> map);

	int savewebsite(SpiderWebsite website);

	SpiderWebsite getInfo(Integer websiteId);

	int updatewebsite(SpiderWebsite website);

	List<SpiderWebsite> getAllwebsite();

	List<Map<String, Object>> foreignlistwebsite(@Param("userid") int userid, @Param("now") String now);

	SpiderWebsite getForeignInfo(Integer websiteId);

	int saveforeignwebsite(SpiderWebsite website);

	int updateforeignwebsite(SpiderWebsite website);

	int updatewebsitemapping(SpiderWebsite website);

	void updatemapping(SpiderWebsite website);

}
