package com.stonedt.spider.monitor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import com.stonedt.spider.dao.SeedDao;
import com.stonedt.spider.entity.SeedCount;
import com.stonedt.spider.entity.SeedCountOb;
import com.stonedt.spider.util.DateUtil;


@Component   
@EnableScheduling
public class Spider_seed_monitor {
	@Autowired
	private SeedDao seedDao;

	// 需要修改时间
//	@Scheduled(cron = "0 0 0 ? * MON")
	public void start(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		List<String> timesocpe = new ArrayList<String>();
		for (int i = 0; i < 7; i++) {
			timesocpe.add(DateUtil.minusDay(i).substring(0,10));
		}
		List<SeedCountOb> list = seedDao.getSeedCountForWeek(DateUtil.minusDay(8).substring(0,10));
		System.out.println("==================定时任务=========================");
		for (int i = 0; i < list.size(); i++) {
			SeedCountOb spiderSeed = list.get(i);
			List<SeedCount> count = spiderSeed.getCount();
			int sum = 0;
			List<String> timesocpetemp = new ArrayList<>();
			timesocpetemp.addAll(timesocpe);
			int size = count.size();
			if(size >7){
				size =7;
			}
			for (int j = 0; j < size; j++) {
				SeedCount spiderCount = count.get(j);
				String format2 = format.format(spiderCount.getDate());
				
				for (String time : timesocpetemp) {
					if(time.equals(format2)){
						sum += spiderCount.getCount();
						break;
					}
				}
				
			}
			int gathering_value = sum/7;
			if(gathering_value >= 20){
				if(spiderSeed.getLevel() == null || 1 != spiderSeed.getLevel()){
					spiderSeed.setLevel(1);
					seedDao.updateSpiderSeed(spiderSeed);
				}
			}else if (gathering_value >= 5){
				if(spiderSeed.getLevel() == null || 2 != spiderSeed.getLevel()){
					spiderSeed.setLevel(2);
					seedDao.updateSpiderSeed(spiderSeed);
				}
			}else if (sum >10){
				if(spiderSeed.getLevel() == null || 3 != spiderSeed.getLevel()){
					spiderSeed.setLevel(3);
					seedDao.updateSpiderSeed(spiderSeed);
				}
			}else{
				if(spiderSeed.getLevel() == null || 4 != spiderSeed.getLevel()){
 					spiderSeed.setLevel(4);
					seedDao.updateSpiderSeed(spiderSeed);
				}
			}
			System.out.println(spiderSeed);
		}
	}
	
	
	

}