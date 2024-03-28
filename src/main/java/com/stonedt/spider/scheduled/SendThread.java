package com.stonedt.spider.scheduled;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

class MyThread implements Runnable { // 实现Runnable接口，作为线程的实现类
	private String cron; // 表示线程的名称

	public MyThread(String cron) {
		this.cron = cron; // 通过构造方法配置name属性
	}

	public void run() { // 覆写run()方法，作为线程 的操作主体
		try {
			SpiderJob spiderJob = new SpiderJob();
			spiderJob.sendmessage_mysql(cron);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
};

public class SendThread {
};
