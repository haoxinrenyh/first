package com.stonedt.spider.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.stonedt.spider.entity.StonedtUser;

@Mapper
public interface IStonedtUserDao {

	/**
	 * 登录验证
	 * 
	 * @param username
	 * @return
	 */
	StonedtUser cheakUser(String username);

	/**
	 * 查询所有用户
	 * 
	 * @return
	 */
	List<StonedtUser> queryUserAll(@Param("power") int power, @Param("id") int id, @Param("keyword") String keyword);

	/**
	 * 修改用户状态
	 * 
	 * @param username
	 * @param status
	 * @return
	 */
	Integer modifyState(@Param("username") String username, @Param("status") Integer status);

	/**
	 * 修改用户
	 *
	 * @param username
	 * @param status
	 * @return
	 */
	Integer updateUser(@Param("username") String username,@Param("name") String name, @Param("status") Integer status ,@Param("power") Integer power,@Param("company") String company);

	/**
	 * 删除用户
	 * 
	 * @param username
	 * @return
	 */
	Integer delStonedtUser(@Param("username") String username);

	/**
	 * 重置密码
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	Integer resetPwd(@Param("username") String username, @Param("password") String password);

	/**
	 * 添加用户
	 * 
	 * @param user
	 * @return
	 */
	Integer insertUser(StonedtUser user);

	/**
	 * 
	 * @Description:判断用户是否存在
	 * @param username @return（展示方法参数和返回值）
	 */
	Integer ifUserExist(@Param("username") String username);

	/**
	 * 
	 * @Description:查询用户数量 @return（展示方法参数和返回值）
	 */
	Integer selectUserCount();

	StonedtUser selectUserByIdAndPassword(@Param("password") String md5, @Param("user_id") Integer user_id);

	int updatePassword(StonedtUser user);

}
