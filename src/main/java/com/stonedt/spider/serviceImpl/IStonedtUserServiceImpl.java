package com.stonedt.spider.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.stonedt.spider.param.UserParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stonedt.spider.dao.IStonedtUserDao;
import com.stonedt.spider.entity.StonedtUser;
import com.stonedt.spider.service.IStonedtUserService;
import com.stonedt.spider.util.MD5Util;
import com.stonedt.spider.util.ResultUtil;

@Service("stonedtUserService")
public class IStonedtUserServiceImpl implements IStonedtUserService {
	@Autowired
	private IStonedtUserDao stonedtUserDao;

	/**
	 * 登录验证
	 */
	@Override
	public StonedtUser cheakUser(String username) {
		// TODO Auto-generated method stub
		return stonedtUserDao.cheakUser(username);
	}

	/**
	 * 登录验证
	 */
	@Override
	public List<StonedtUser> queryUserAll(int power,int id,String keyword) {
		// TODO Auto-generated method stub
		List<StonedtUser> result = new ArrayList<>();
		if(id>0){
			result = stonedtUserDao.queryUserAll(power,id,keyword);
		}
		return result;
	}

	/**
	 * 修改用户状态
	 */
	@Override
	public Integer modifyState(String username, Integer status) {
		// TODO Auto-generated method stub
		return stonedtUserDao.modifyState(username, status);
	}

	@Override
	public Integer updateUser(UserParam userParam) {

		return stonedtUserDao.updateUser(
				userParam.getUsername(),
				userParam.getName(),
				userParam.getStatus(),
				userParam.getPower(),
				userParam.getInstitutionName());
	}

	/**
	 * 删除用户
	 */
	@Override
	public Integer delStonedtUser(String username) {
		// TODO Auto-generated method stub
		return stonedtUserDao.delStonedtUser(username);
	}

	/**
	 * 重置密码
	 */
	@Override
	public Integer resetPwd(String username, String password) {
		// TODO Auto-generated method stub
		return stonedtUserDao.resetPwd(username, password);
	}

	/**
	 * 添加用户
	 */
	@Override
	public Integer insertUser(StonedtUser user) {
		// TODO Auto-generated method stub
		int status = 0;

		Integer result = stonedtUserDao.ifUserExist(user.getUsername());
		if(result!=null && result>0 ){
			status = -1;
		}else {
			status = stonedtUserDao.insertUser(user);
		}
		return status;
	}
	
	/**
	 * 确认用户是否存在
	 */
	@Override
	public ResultUtil ifUserExist(String username) {
		// TODO Auto-generated method stub
		Integer result = stonedtUserDao.ifUserExist(username);
		ResultUtil resultUtil = new ResultUtil();
		if(result == 0){
			resultUtil.setStatus(200);
		}else{
			resultUtil.setStatus(500);
		}
		return resultUtil;
	}

	@Override
	public Integer selectUserCount() {
		// TODO Auto-generated method stub
		return stonedtUserDao.selectUserCount();
	}

	@Override
	public ResultUtil checkPassword(String password, Integer user_id) {
		// TODO Auto-generated method stub
		StonedtUser user = stonedtUserDao.selectUserByIdAndPassword(MD5Util.getMD5(password),user_id);
		ResultUtil result = new ResultUtil();
		if(null == user){
			result.setStatus(500);
		}else{
			result.setStatus(200);
		}
		return result;
	}

	@Override
	public ResultUtil repassword(String password, Integer user_id, String repassword) {
		// TODO Auto-generated method stub
		StonedtUser user = stonedtUserDao.selectUserByIdAndPassword(MD5Util.getMD5(password),user_id);
		ResultUtil result = new ResultUtil();
		if(null == user){
			result.setStatus(500);
		}else{
			try {
				user.setPassword(MD5Util.getMD5(repassword));
				stonedtUserDao.updatePassword(user);
				result.setStatus(200);
			} catch (Exception e) {
				// TODO: handle exception
				result.setStatus(500);
			}
		}
		return result;
	}

	@Override
	public Map<String, Object> changePassword(String password, Integer user_id, String newPassword) {
		Map<String, Object> result = new HashMap<>();
		try {
			StonedtUser user = stonedtUserDao.selectUserByIdAndPassword(MD5Util.getMD5(password),user_id);
			if(user!=null ) {
				user.setPassword(MD5Util.getMD5(newPassword));
				int type = stonedtUserDao.updatePassword(user);
				if(type>0){
					result.put("code", 200);
					result.put("msg","修改成功!");
					return result;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		result.put("code",500);
		result.put("msg","修改失败，请检查参数是否有误!");
		return result;
	}


}
