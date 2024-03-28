package com.stonedt.spider.scheduled;

import com.alibaba.fastjson.JSONObject;
import com.stonedt.spider.entity.SpiderFlow;
import com.stonedt.spider.service.SpiderFlowService;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 爬虫定时执行管理
 *
 * @author Administrator
 */
@Component
public class SpiderJobManager {

	//private static Logger logger = LoggerFactory.getLogger(SpiderJobManager.class);

	private final static String JOB_NAME = "OPEN_SPIDER_TASK";

	public final static String JOB_PARAM_NAME = "OPEN_SPIDER_FLOW";

	// 用来存储待执行的爬虫
//    public volatile static Map<String, Map<Integer, Map<String, Object>>> spiderList = new ConcurrentHashMap<>();

	public volatile static List<String> cronList = new ArrayList<String>();

	@Autowired
	private SpiderFlowService spiderFlowService;

	private static SpiderJob spiderJob;

	/**
	 * 调度器
	 */
	private Scheduler scheduler;

	{
		try {
			scheduler = new StdSchedulerFactory().getScheduler();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	@PostConstruct
	public void initSpiderJob() {
		if (!SpiderJob.SPIDERJOBSTATUS) {
			return;
		}

		spiderJob.spiderFlowService=this.spiderFlowService;
		// 查询所有开启定时任务的爬虫
		cronList = spiderFlowService.selectSpiderListJob_mysql();

		// 添加定时任务
		for (String cron : cronList) {
			if (cron.equals("0 20 0/2 * * ?")) {
				addJob("0 30 9-22/4 * * ?");
			} else {
				addJob(cron);
			}
		}
	}

	/**
	 * 新建定时任务
	 *
	 * @param cron 表达式
	 * @return boolean true/false
	 */
	public Date addJob(String cron) {
		System.out.println("addJob>>>>> "+cron);
		try {
			if (!SpiderJob.SPIDERJOBSTATUS) {
				return null;
			}
			JobDetail job = JobBuilder.newJob(SpiderJob.class).withIdentity(getJobKey(cron)).build();
			job.getJobDataMap().put(JOB_PARAM_NAME, cron);

			CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cron)
					.withMisfireHandlingInstructionDoNothing();

			CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(getTriggerKey(cron))
					.withSchedule(cronScheduleBuilder).build();

			Date date = scheduler.scheduleJob(job, trigger);
			scheduler.start();
			return date;
		} catch (SchedulerException e) {
			//logger.error("创建定时任务出错", e);
			return null;
		}
	}

	private JobKey getJobKey(String id) {
		return JobKey.jobKey(JOB_NAME + id);
	}

	private TriggerKey getTriggerKey(String id) {
		return TriggerKey.triggerKey(JOB_NAME + id);
	}

	public boolean remove(String id) {
		try {
			scheduler.deleteJob(getJobKey(id));
			return true;
		} catch (SchedulerException e) {
			//logger.error("删除定时任务失败", e);
			return false;
		}
	}

}
