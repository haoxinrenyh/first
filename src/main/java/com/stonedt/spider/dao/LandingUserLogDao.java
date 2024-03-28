package com.stonedt.spider.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.stonedt.spider.entity.UserLandingLog;

@Mapper
public interface LandingUserLogDao {

	void insertOrUpdateUserLandingLog(UserLandingLog userLandingLog);

	List<UserLandingLog> queryUserLandingLogAll();

}
