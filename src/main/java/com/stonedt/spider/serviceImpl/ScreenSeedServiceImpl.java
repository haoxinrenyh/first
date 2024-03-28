package com.stonedt.spider.serviceImpl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stonedt.spider.dao.ScreenSeedDao;
import com.stonedt.spider.entity.ScreenWord;
import com.stonedt.spider.service.ScreenSeedService;
import com.stonedt.spider.util.ResultUtil;

@Service("ScreenSeedService")
public class ScreenSeedServiceImpl implements ScreenSeedService {

	@Autowired
	ScreenSeedDao screenSeedDao;

	@Override
	public List<ScreenWord> getList() {
		return screenSeedDao.getList();
	}

	@Override
	public String getStringList() {
		return screenSeedDao.getStringList();
	}

	@Override
	public ResultUtil remove(Integer id) {
		try {
			screenSeedDao.remove(id);
		} catch (Exception e) {
			// TODO: handle exception
			return ResultUtil.build(500, e.getMessage());
		}
		return ResultUtil.build(200, "");
	}

	@Override
	public ResultUtil add(String word) {
		String searchWord = screenSeedDao.getbyword(word);
		if(StringUtils.isEmpty(searchWord)){
			screenSeedDao.add(word);
			return ResultUtil.build(200, "");
		}else{
			return ResultUtil.build(201, "该词已存在");
		}
	}

	@Override
	public String getforeignStringList() {
		return screenSeedDao.getforeignStringList();
	}
}
