package com.stonedt.spider.service;

import com.stonedt.spider.entity.WebsiteType;

import java.util.List;
import java.util.Map;

/**
 * @author 丁祥珂
 * @version V1.0
 * @date 2021/4/15 11:28
 * @Copyright
 * 数据类型
 */
public interface WebSiteTypeService {
    //查询id  typename 字段
    List<Map<String, Object>> selectList();

    List<Map<String, Object>> websitetypes(int categoryId);

    List<WebsiteType> page(String limit);

    int pageCount();

    int insertWebsiteType(WebsiteType websitetype);

    int updateWebsiteType(WebsiteType websitetype);

    WebsiteType findOne(WebsiteType websitetype);
}
