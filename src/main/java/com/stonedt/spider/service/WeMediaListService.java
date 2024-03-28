package com.stonedt.spider.service;

import java.util.List;

import com.stonedt.spider.entity.WeMedia;
import com.stonedt.spider.entity.platform;

public interface WeMediaListService {
	public List<WeMedia> Getmedia(WeMedia WeMedia);
	
	public List<platform> Getplatform(platform platform);
	
	public List<platform> platname(platform platform);
	
}
