package com.stonedt.spider.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.stonedt.spider.entity.SpiderFlow;
import com.stonedt.spider.entity.WebsiteCategory;
import com.stonedt.spider.param.WebParam;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;

import com.alibaba.fastjson.JSONObject;
import com.stonedt.spider.entity.NewEntity;
import com.stonedt.spider.entity.ServerResource;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IServerResourceDao {

	/**
	 * 查询全部服务器资源清单与使用状态
	 * 
	 * @return
	 */
	List<ServerResource> queryServerResourceAll();

	List<Map<String, Object>> listDataSource(Integer type);

	List<NewEntity> list(WebParam params);

	List<Map<String,Object>> webCategory();

	NewEntity webInfo(@Param("id")int id);
	List<SpiderFlow> findWebInfoPage(@Param("limit") String limit , @Param("webId")int webId, @Param("status")Integer status, @Param("level")Integer level, @Param("type")Integer type,@Param("keyword")String keyword);
	int findWebInfoCount( @Param("webId")int id, @Param("status")Integer status, @Param("level")Integer level, @Param("type")Integer type,@Param("keyword")String keyword);

	int removeWeb(@Param("id") int id);

	void updateWebType(Map<String, Integer> params);

	Set<String> loadSponsorNature();

	List<WebsiteCategory> websiteCategoryPage(@Param("limit") String limit,@Param("keyword")String keyword);
	int WebsiteCategoryCount(@Param("keyword")String keyword);
	int categorySave(@Param("category_name")String category_name,@Param("user_id")int user_id);

	WebsiteCategory findOneWebsiteCategory(@Param("category_name")String category_name );

	WebsiteCategory findOneUpdateWebsiteCategory(@Param("category_name")String category_name , @Param("id")int id);

	int UpdateWebsiteCategory(@Param("category_name")String category_name,@Param("user_id")int user_id,@Param("id")int id);

	int removeWebsiteCategory(@Param("id")int id);

	int updateError(@Param("info_error")int info_error , @Param("ids")String ids);
}
