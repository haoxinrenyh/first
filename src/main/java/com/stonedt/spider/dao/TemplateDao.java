package com.stonedt.spider.dao;

import com.stonedt.spider.entity.SpiderFlowTemplate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface TemplateDao {

    List<SpiderFlowTemplate> templatePage(@Param("limit")String limit ,@Param("environment") Integer environment, @Param("status") Integer status );

    int templateCount(@Param("environment") Integer environment, @Param("status") Integer status );

    List<Map<String,Object>> templates();

    SpiderFlowTemplate templateInfo(@Param("id")int id);

    int saveTemplate(@Param("template")SpiderFlowTemplate template);

    int updateTemplate(@Param("template")SpiderFlowTemplate template);

}
