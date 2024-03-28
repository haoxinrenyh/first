package com.stonedt.spider.dao;

import com.stonedt.spider.entity.EsRecord;
import com.stonedt.spider.entity.SpiderFlow;

import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface SpiderFlowDao {

	List<SpiderFlow> searchSpiderFlows(@Param("website_id") Integer website_id);

	SpiderFlow searchSpiderFlow(@Param("id") Integer id);

	// 修改爬虫信息
	int updateSpiderFlowById(@Param("spiderFlow") SpiderFlow spiderFlow);

	// 新增爬虫信息
	int insertSpiderFlow(@Param("spiderFlow") SpiderFlow spiderFlow);

	// 根据数据类型获取需要的数据
	Map<String, Object> getTypeJson(@Param("typeid") int typeid, @Param("websiteId") int websiteId);

	// 根据站点id 查询所有的爬虫id
	List<Integer> selectIdByWebId(@Param("webid") Integer webid);

	// 根据爬虫id查询对应的类型和站点信息
	SpiderFlow selectTypeWebById(@Param("id") Integer id);


	List<SpiderFlow> spiderFlowPage(@Param("limit") String  limit , @Param("seed_status") Integer seed_status, @Param("cron")String cron);
	List<Map<String,Object>> spiderFlowPage_map(@Param("limit") String  limit , @Param("seed_status") Integer seed_status, @Param("cron")String cron);

	int checkSoleSign(@Param("id") Integer id,@Param("sole_sign") String sole_sign);

	List<Integer> findWebsiteByCategoryId(@Param("categoryId")int categoryId);

	int insertEsRecord(@Param("list")List<EsRecord> list);

}
