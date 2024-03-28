package com.stonedt.spider.service;

import java.util.List;
import java.util.Map;

import com.stonedt.spider.entity.SecLabel;

public interface NewsTableService {
	
	public List<String> getSecondType(String first_type);
	
	public List<SecLabel> getSecLabel(Integer page,String first_type);

	public List<SecLabel> getSeedIds();
	
	public List<SecLabel> getType(Map<String,Object> map);
}
