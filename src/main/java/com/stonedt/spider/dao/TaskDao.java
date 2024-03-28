package com.stonedt.spider.dao;

import com.stonedt.spider.entity.SpiderFlowTask;
import com.stonedt.spider.entity.SpiderFlowTemplate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface TaskDao {

    List<SpiderFlowTask> taskPage(@Param("limit")String limit, @Param("status") Integer status, @Param("level") Integer level , @Param("keyword") String keyword );

    int taskCount(@Param("status") Integer status , @Param("level") Integer level, @Param("keyword") String keyword );

    SpiderFlowTask taskInfo(@Param("id")int id);

    int saveTask(@Param("task")SpiderFlowTask task);

    int updateTask(@Param("task")SpiderFlowTask task);

    List<SpiderFlowTask> taskSpiderCronPage(@Param("limit") String  limit , @Param("seed_status") Integer seed_status, @Param("cron")String cron);
    List<Map<String,Object>> taskSpiderCronPage_map(@Param("limit") String  limit , @Param("seed_status") Integer seed_status, @Param("cron")String cron);

}
