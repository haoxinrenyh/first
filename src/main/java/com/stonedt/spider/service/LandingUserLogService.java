package com.stonedt.spider.service;

import java.util.List;

import com.stonedt.spider.entity.StonedtUser;
import com.stonedt.spider.entity.UserLandingLog;

public interface LandingUserLogService {

	/**
	 * 
	 * @Description:记录用户登陆日志
	 * @param cheakUser（展示方法参数和返回值）
	 */
	void save(StonedtUser cheakUser);
	
	/**
	 * 
	 * @Description:获取用户日子列表
	 * @return（展示方法参数和返回值）
	 */
	List<UserLandingLog> findUserLandingLogs();

}
