package com.stonedt.spider.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.stonedt.spider.dao.SeekwebDao;
import com.stonedt.spider.entity.Seekweb;
import com.stonedt.spider.service.SeekwebService;
@Service
public class SeekwebServicelmpl implements SeekwebService {
	@Resource
	private SeekwebDao SeekwebDao;
	@Override
	public List<Seekweb> getSeekList(Seekweb Seekweb) {
		// TODO Auto-generated method stub
		return SeekwebDao.getSeekList(Seekweb);
	}
	@Override
	public Seekweb getoneList(Seekweb Seekweb) {
		// TODO Auto-generated method stub
		return SeekwebDao.getoneList(Seekweb);
	}

}
