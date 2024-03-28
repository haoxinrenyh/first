package com.stonedt.spider.service;

import com.stonedt.spider.entity.DataSource;
import com.stonedt.spider.entity.SpiderFlowDataSource;

import java.util.List;

/**
 * @author 丁祥珂
 * @version V1.0
 * @date 2021/5/6 14:02
 * @Copyright
 */
public interface SpiderFlowDataSourceService {

    //查询所有的数据源
    List<SpiderFlowDataSource> selectListAll();

}
