package com.stonedt.spider.dao;

import java.util.List;
import java.util.Map;
import com.stonedt.spider.entity.TransferinfoEntity;
import com.stonedt.spider.entity.WebsitespidertypeEntity;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SpiderWebsitemonitorDao {

	List<Map<String, Object>> listmonitorwebsite();

	List<Map<String, Object>> listtypename(Integer websiteId);

	List<Map<String, Object>> gettypename();

	int addtransdata(TransferinfoEntity tf);

	int addwebsitespidertype(WebsitespidertypeEntity wt);

	List<Map<String, Object>> listmonitorwebsite(Map<String, Object> map);

	Map<String, Object> selectSeedInfo(@Param("map") Map<String, Object> map);

	Integer updateSeendInfo(Map<String, Object> map);

	Map<String, Object> getTypeIndexInfo(Map<String, Object> map);

}
