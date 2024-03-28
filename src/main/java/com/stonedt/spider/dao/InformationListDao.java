package com.stonedt.spider.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.stonedt.spider.entity.InformationEntity;
import com.stonedt.spider.entity.Wechat;

@Mapper
public interface InformationListDao {

	/**
	 * 根据用户选择的标签的id查询相关资讯
	 * 
	 * @param seed_id
	 * @return
	 */
	List<InformationEntity> getInformationList(@Param(value = "seed_id") Integer seed_id);

	/**
	 * 根据用户选择的标签类型查询相关资讯
	 * 
	 * @param article_type
	 * @return
	 */
	List<InformationEntity> getInformationListByType(@Param(value = "article_type") Integer article_type);

	InformationEntity getInformationById(@Param(value = "id") int id);

	/**
	 * 根据用户选择的子分类，查询对应的大分类
	 * 
	 * @param seed_id
	 * @return
	 */
	String getFirstType(@Param(value = "seed_id") int seed_id);

	/**
	 * 根据用户选择的子分类，查询对应的子标签
	 * 
	 * @param seed_id
	 * @return
	 */
	String getSeedName(@Param(value = "seed_id") int seed_id);

	List<InformationEntity> getWechatInformationList(int id);

	Wechat getWechatName(int id);

	List<InformationEntity> getWeboInformationList(int id);

	InformationEntity getLastinfo(InformationEntity infomation);

	InformationEntity getNextinfo(InformationEntity infomation);

	/**
	 * 获取当前页码
	 * 
	 * @param id
	 * @return
	 */
	Integer getPagenumInfo(InformationEntity infomation);

	InformationEntity getLastinfoNew(InformationEntity infomation);

	InformationEntity getNextinfoNew(InformationEntity infomation);

	/**
	 * 获取当前页码
	 * 
	 * @param id
	 * @return
	 */
	Integer getPagenumInfoNew(InformationEntity infomation);

	List<InformationEntity> getNewArticle();

	List<InformationEntity> getNewsByEmotionation(@Param(value = "gradenum") Integer gradenum);
}
