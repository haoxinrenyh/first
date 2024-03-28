package com.stonedt.spider.service;

import com.stonedt.spider.entity.SpiderFlowTemplate;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TemplateService {

    Map<String,Object> templatePage(String limit , Integer environment, Integer status );

    int templateCount(Integer environment,  Integer status );

    List<Map<String,Object>> templates();

    SpiderFlowTemplate templateInfo(@Param("id")int id);

    int saveTemplate(SpiderFlowTemplate template);

    int updateTemplate(SpiderFlowTemplate template);

    int openTemplate(int id, Integer status,int userId , String userName);

}
