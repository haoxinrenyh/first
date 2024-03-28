package com.stonedt.spider.serviceImpl;

import com.stonedt.spider.dao.TaskDao;
import com.stonedt.spider.entity.SpiderFlow;
import com.stonedt.spider.entity.SpiderFlowTask;
import com.stonedt.spider.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskDao taskDao;

    @Override
    public Map<String, Object> taskPage(String limit, Integer status, Integer level, String keyword) {
        List<SpiderFlowTask> templateList = new ArrayList<>();
        int total = 0;
        try {
            templateList = taskDao.taskPage(limit,status,level,keyword);
            total = taskDao.taskCount(status,level,keyword);
        }catch (Exception e){
            e.printStackTrace();
        }
        Map<String, Object> result = new HashMap<>();
        result.put("list",templateList);
        result.put("total",total);
        return result;
    }

    @Override
    public int taskCount(Integer status, Integer level, String keyword) {
        return taskDao.taskCount(status,level,keyword);
    }

    @Override
    public SpiderFlowTask taskInfo(int id) {
        SpiderFlowTask task = new SpiderFlowTask();
        try {
            if(id>0){
                task = taskDao.taskInfo(id);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return task;
    }

    @Override
    public int saveTask(SpiderFlowTask task) {
        return taskDao.saveTask(task);
    }

    @Override
    public int updateTask(SpiderFlowTask task) {
        return taskDao.updateTask(task);
    }

    @Override
    public List<String> taskSpiderCronList() {
        List<String> list = new ArrayList<>();
        Set hs = new HashSet();

        int seed_status=1;
        int page = 0;
        int size = 10000;
        try {
            while (true){
                page++;
                System.out.println("正在获取mysql数据，当前:" + page + "页");
                String limit = "limit "+(page-1)*size+","+size;

                List<SpiderFlowTask> taskList = new ArrayList<>();
                try {
                    taskList = taskDao.taskSpiderCronPage(limit,seed_status,null);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
                for (SpiderFlowTask task : taskList) {
                    if(task!=null && task.getCron()!=null){
                        hs.add(task.getCron());
                    }
                }
                // 跳出while循环
                if (taskList == null || taskList.size() == 0 || taskList.size() < size) {
                    System.out.println("获取结束!");
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            Iterator it = hs.iterator();
            while (it.hasNext()) {
                list.add(it.next().toString());
            }
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> taskSpiderCronList_map(String  limit ,  Integer seed_status, String cron) {
        return taskDao.taskSpiderCronPage_map(limit,seed_status,cron);
    }
}
