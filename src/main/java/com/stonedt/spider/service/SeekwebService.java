package com.stonedt.spider.service;

import java.util.List;

import com.stonedt.spider.entity.Seekweb;

public interface SeekwebService {
	List<Seekweb> getSeekList(Seekweb Seekweb);
	
	Seekweb getoneList(Seekweb Seekweb);
}
