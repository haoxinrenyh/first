package com.stonedt.spider.scheduled;

import com.stonedt.spider.service.LogInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class LogInfoTask {

    @Autowired
    private LogInfoService logInfoService;

//    @PostConstruct
    @Scheduled(cron = "0 0 */1 * * ? ")
    public void LogInfoRecord(){
        logInfoService.record();
    }

}
