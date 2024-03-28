package com.stonedt.spider.serviceImpl;

import com.stonedt.spider.scheduled.SpiderJobManager;
import com.stonedt.spider.service.SpiderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpiderServiceImpl implements SpiderService {

    @Autowired
    private SpiderJobManager spiderJobManager;


    @Override
    public boolean checkAddSpiderByCron(String cron) {
        try {
            List<String> cronList = SpiderJobManager.cronList;
            if(StringUtils.isNotBlank(cron)){
                cron = cron.trim();
                boolean flag = true;
                for (int i = 0; i < cronList.size(); i++) {
                    if(cron.equals(cronList.get(i))){
                        flag = false;
                        break;
                    }
                }
                if(flag){
                    spiderJobManager.addJob(cron);
                    System.out.println(">>>>>>>>>> 定时任务: "+cron+" , 添加成功!");
                    return true;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

}
