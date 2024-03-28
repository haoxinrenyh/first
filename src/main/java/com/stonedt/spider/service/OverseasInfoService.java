package com.stonedt.spider.service;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * 境外数据服务
 */

public interface OverseasInfoService {
    public JSONObject getTwitterList(Map<String, Object> map);

    public JSONObject getOverseasList(Map<String, Object> map);

}
