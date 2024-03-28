package com.stonedt.spider.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSONObject;
import com.stonedt.spider.entity.NewEntity;
import com.stonedt.spider.entity.ServerResource;
import com.stonedt.spider.entity.SpiderFlow;
import com.stonedt.spider.entity.WebsiteCategory;
import com.stonedt.spider.param.WebParam;
import org.apache.ibatis.annotations.Param;

public interface IServerResourceService {

	/**
	 * 查询全部服务器资源清单与使用状态
	 * @return
	 */
	List<ServerResource> queryServerResourceAll();
	
	List<Map<String, Object>> listGov(Integer type);

	List<Map<String, Object>> listNews(Integer type);

	List<Map<String, Object>> listForum(Integer type);

	List<Map<String, Object>> listNewspaper(Integer type);

	List<Map<String, Object>> listBlog(Integer type);

	List<Map<String, Object>> listWebsite(Integer type);

	int removeWeb(Integer id);

	Map<String,Object> websiteCategoryPage(String limit,String keyword);
	List<NewEntity> list(WebParam params);

	List<Map<String,Object>> webCategory();

	Map<String,Object> findWebInfo( String limit ,  int webId, Integer status, Integer level,Integer type, String keyword);

	void updateWebType(Integer id, Integer webType);

	Set<String> loadSponsorNature();

	int categorySave(String category_name,int user_id);

	WebsiteCategory findOneWebsiteCategory(String category_name );

	WebsiteCategory findOneUpdateWebsiteCategory(String category_name ,int id );

	int UpdateWebsiteCategory(String category_name , int user_id,@Param("id")int id);

	int removeWebsiteCategory(int id);
}
