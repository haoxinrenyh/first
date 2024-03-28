package com.stonedt.spider.dao;

import com.stonedt.spider.entity.BrowserTemplate;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BrowserTemplateDao {

    BrowserTemplate findTemplate(Long id);

}
