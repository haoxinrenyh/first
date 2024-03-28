package com.stonedt.spider.serviceImpl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stonedt.spider.dao.LandingUserLogDao;
import com.stonedt.spider.entity.StonedtUser;
import com.stonedt.spider.entity.UserLandingLog;
import com.stonedt.spider.service.LandingUserLogService;

@Service("landingUserLogService")
public class LandingUserLogServiceImpl implements LandingUserLogService {
	
	@Autowired
	private LandingUserLogDao landingUserLogDao;

	@Override
	public void save(StonedtUser cheakUser) {
		// TODO Auto-generated method stub
		UserLandingLog userLandingLog = new UserLandingLog();
		userLandingLog.setLanding_date(new Date());
		userLandingLog.setUser(cheakUser.getUsername());
		userLandingLog.setUser_name(cheakUser.getName());
		userLandingLog.setUser_id(cheakUser.getId());
		userLandingLog.setInstitution_name(cheakUser.getCompany());
		landingUserLogDao.insertOrUpdateUserLandingLog(userLandingLog);
	}

	@Override
	public List<UserLandingLog> findUserLandingLogs() {
		// TODO Auto-generated method stub
		return landingUserLogDao.queryUserLandingLogAll();
	}
}
