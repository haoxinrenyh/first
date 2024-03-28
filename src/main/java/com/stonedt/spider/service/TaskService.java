package com.stonedt.spider.service;


import com.stonedt.spider.entity.SpiderFlowTask;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TaskService {

    Map<String, Object> taskPage(String limit, Integer status, Integer level , String keyword );

    int taskCount( Integer status,  Integer level ,  String keyword );

    SpiderFlowTask taskInfo(int id);

    int saveTask(SpiderFlowTask task);

    int updateTask(SpiderFlowTask task);

    List<String> taskSpiderCronList();

    List<Map<String,Object>> taskSpiderCronList_map(String  limit ,  Integer seed_status, String cron);

}
