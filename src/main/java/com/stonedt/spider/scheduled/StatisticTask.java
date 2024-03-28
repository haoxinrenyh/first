package com.stonedt.spider.scheduled;

import com.stonedt.spider.service.MinioService;
import com.stonedt.spider.service.StatisticService;
import com.stonedt.spider.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class StatisticTask {

    @Autowired
    private StatisticService statisticService;

    @Autowired
    private MinioService minioService;

    @Scheduled(cron = "0 0 */2 * * ?")
    public void statisticCount(){
       boolean flag = statisticService.findEsCount();
       if(flag){
           System.out.println(DateUtil.getDate()+" 记录采集结果, 成功!  ");
       }else {
           System.out.println(DateUtil.getDate()+" 记录采集结果, 有误! ");
       }

    }


    @Scheduled(cron = "0 0 3 * * ?")
    public void minioDel(){
        System.out.println(DateUtil.getDate()+ " 删除采集的过期文件!" );
        minioService.minoDel();
    }

}
