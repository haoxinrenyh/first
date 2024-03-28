package com.stonedt.spider.dao;

import com.stonedt.spider.entity.DataSource;
import com.stonedt.spider.entity.SpiderFlowDataSource;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

/**
 * @author 丁祥珂
 * @version V1.0
 * @date 2021/5/6 14:04
 * @Copyright
 */
@Mapper
public interface SpiderFlowDataSourceDao {

	// 查询所有的数据源
	List<SpiderFlowDataSource> selectListAll();

}
