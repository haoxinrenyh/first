package com.stonedt.spider.dao;

import com.stonedt.spider.entity.StonedtUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
	int insertUser(StonedtUser stonedtUser);

	int updateUserStatus(StonedtUser stonedtUser);

	StonedtUser getByOpenId(String openid);
	StonedtUser selectUserByOpenId(String openid);
}
