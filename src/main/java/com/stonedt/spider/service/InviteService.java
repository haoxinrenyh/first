package com.stonedt.spider.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.stonedt.spider.entity.Company;

import java.util.List;
import java.util.Map;

/**
 * @param
 * @purpose:
 * @time: 2020/1/13 14:16
 * @author:
 */
public interface InviteService {
	JSONObject getInviteList(Map<String,String> map);

	JSONArray getSourceList();

	JSONObject getInviteDetail(Map<String,Object> map);

	JSONObject getProviceAsyn(Map<String,String> map);

	JSONObject getCityAsyn(Map<String,String> map);

	JSONObject getListedcompany(Integer pageNum,Integer pageSize);
}
