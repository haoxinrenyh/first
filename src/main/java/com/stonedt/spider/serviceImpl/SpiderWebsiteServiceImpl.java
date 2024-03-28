package com.stonedt.spider.serviceImpl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stonedt.spider.dao.SpiderWebsiteDao;
import com.stonedt.spider.entity.SpiderWebsite;
import com.stonedt.spider.service.SpiderWebsiteService;

@Service
public class SpiderWebsiteServiceImpl implements SpiderWebsiteService {

    @Autowired
    private SpiderWebsiteDao spiderWebDao;

//	@Override
//	public List<Map<String, Object>> listwebsite(int userid) {
//		Date dt=new Date();
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//        String nowTime= df.format(dt);
//		return spiderWebDao.listwebsite(userid,nowTime);
//	}

    @Override
    public List<Map<String, Object>> listwebsite(Map<String, Object> map) {
        Date dt = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String nowTime = df.format(dt);
        return spiderWebDao.listwebsite(map);
    }

    @Override
    public int savewebsite(SpiderWebsite website) {
        // TODO Auto-generated method stub
        return spiderWebDao.savewebsite(website);
    }

    @Override
    public SpiderWebsite getInfo(Integer websiteId) {
        // TODO Auto-generated method stub
        return spiderWebDao.getInfo(websiteId);
    }

    @Override
    public int updatewebsite(SpiderWebsite website) {
        // TODO Auto-generated method stub
        return spiderWebDao.updatewebsite(website);
    }

    @Override
    public int startsMapping(int websiteId) {
        SpiderWebsite website = new SpiderWebsite();
        website.setId(websiteId);
        website.setCapture_type(1);
        return spiderWebDao.updatewebsite(website);
    }

    @Override
    public List<SpiderWebsite> getAllwebsite() {
        return spiderWebDao.getAllwebsite();
    }

    @Override
    public List<Map<String, Object>> foreignlistwebsite(int userid) {
        Date dt = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String nowTime = df.format(dt);
        return spiderWebDao.foreignlistwebsite(userid, nowTime);
    }

    @Override
    public SpiderWebsite getForeignInfo(Integer websiteId) {
        return spiderWebDao.getForeignInfo(websiteId);
    }

    @Override
    public int saveforeignwebsite(SpiderWebsite website) {
        // TODO Auto-generated method stub
        return spiderWebDao.saveforeignwebsite(website);
    }

    @Override
    public int updateforeignwebsite(SpiderWebsite website) {
        // TODO Auto-generated method stub
        return spiderWebDao.updateforeignwebsite(website);
    }

	@Override
	public int startCheckMapping(int websiteId) {
		// TODO Auto-generated method stub
		SpiderWebsite website = new SpiderWebsite();
        website.setId(websiteId);
//        website.setCapture_type(1);
        spiderWebDao.updatemapping(website);
        return spiderWebDao.updatewebsitemapping(website);
	}


}
