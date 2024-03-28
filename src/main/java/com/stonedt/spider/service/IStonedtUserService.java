package com.stonedt.spider.service;

import java.util.List;
import java.util.Map;

import com.stonedt.spider.param.UserParam;
import com.stonedt.spider.entity.StonedtUser;
import com.stonedt.spider.util.ResultUtil;


public interface IStonedtUserService {
	
	/**
	 *   登录验证
	 * @param username
	 * @return
	 */
	StonedtUser cheakUser(String username);
	
	/**
	 *  查询所有用户
	 * @return
	 */
	List<StonedtUser> queryUserAll(int powers,int id,String keyword);

	/**
	 * 修改用户状态
	 * @param username
	 * @param status
	 */
	Integer modifyState(String username, Integer status);

	/**
	 * 修改用户状态
	 */
	Integer updateUser(UserParam userParam);
	
	/**
	 * 删除用户
	 * @param username
	 * @return
	 */
	Integer delStonedtUser(String username);
	
	/**
	 * 重置密码
	 * @param username
	 * @param password
	 * @return
	 */
	Integer resetPwd(String username, String password);

	/**
	 * 添加用户
	 * @param user
	 * @return
	 */
	Integer insertUser(StonedtUser user);
	
	/**
	 * @Description:确认用户是否存在
	 * @param username
	 * @return（展示方法参数和返回值）
	 */
	ResultUtil ifUserExist(String username);
	
	/**
	 * 
	 * @Description:查询用户数量
	 * @return（展示方法参数和返回值）
	 */
	Integer selectUserCount();

	ResultUtil checkPassword(String password, Integer user_id);

	ResultUtil repassword(String password, Integer user_id, String repassword);

	Map<String,Object> changePassword(String password, Integer user_id, String newPassword);
	
}
