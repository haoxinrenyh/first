package com.stonedt.spider.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.stonedt.spider.entity.InformationEntity;
import com.stonedt.spider.entity.Wechat;

public interface InformationListService {
	
	/**
	 * 根据用户选择的标签的id查询相关资讯
	 * @param seed_id
	 * @return
	 */
	List<InformationEntity> getInformationList(Integer seed_id);
	
	/**
	 * 根据用户选择的标签类型查询相关资讯
	 * @param seed_id
	 * @return
	 */
	List<InformationEntity> getInformationListByType(Integer article_type);
	
	/**
	 * 根据id查询详情
	 * @param id
	 * @return
	 */
	InformationEntity getInformationById(int id);
	
	InformationEntity getInformationSocialById(int id);
	
	
	/**
	 * 根据用户选择的子分类，查询对应的大分类
	 * @param seed_id
	 * @return
	 */
	String getFirstType(int seed_id);
	
	/**
	 * 根据用户选择的子分类，查询对应的大分类
	 * @param seed_id
	 * @return
	 */
	String getFirstLabel(int seed_id);
	
	
	/**
	 * 根据用户选择的子分类，查询对应的子标签
	 * @param seed_id
	 * @return
	 */
	String getSeedName(int seed_id);

	List<InformationEntity> getWechatInformationList(int id);

	Wechat getWechatName(int id);

	List<InformationEntity> getWeboInformationList(int id);

	InformationEntity getLastinfo(InformationEntity infomation);

	InformationEntity getNextinfo(InformationEntity infomation);

	Integer getPagenumInfo(InformationEntity infomation);
	
	
	InformationEntity getLastinfoNew(InformationEntity infomation);

	InformationEntity getNextinfoNew(InformationEntity infomation);

	Integer getPagenumInfoNew(InformationEntity infomation);
	
	List<InformationEntity> getNewArticle();
	
	List<InformationEntity> getNewsByEmotionation(Integer gradenum);
}
