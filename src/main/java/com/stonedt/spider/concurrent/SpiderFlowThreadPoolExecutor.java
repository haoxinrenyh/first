package com.stonedt.spider.concurrent;

import com.stonedt.spider.model.SpiderNode;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class SpiderFlowThreadPoolExecutor {

	/**
	 * 最大线程数
	 */
	private int maxThreads;

	/**
	 * 真正线程池
	 */
	private ThreadPoolExecutor executor;

	/**
	 * 线程number计数器
	 */
	private final AtomicInteger poolNumber = new AtomicInteger(1);

	/**
	 * ThreadGroup
	 */
	private static final ThreadGroup SPIDER_FLOW_THREAD_GROUP = new ThreadGroup("spider-flow-group");

	/**
	 * 线程名称前缀
	 */
	private static final String THREAD_POOL_NAME_PREFIX = "spider-flow-";

	public SpiderFlowThreadPoolExecutor(int maxThreads) {
		super();
		this.maxThreads = maxThreads;
		//创建线程池实例
		this.executor = new ThreadPoolExecutor(maxThreads, maxThreads, 10, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), runnable -> {
			//重写线程名称
			return new Thread(SPIDER_FLOW_THREAD_GROUP, runnable, THREAD_POOL_NAME_PREFIX + poolNumber.getAndIncrement());
		});
	}

	public Future<?> submit(Runnable runnable){
		return this.executor.submit(runnable);
	}



	/**
	 * 子线程池
	 */
	public class SubThreadPoolExecutor{

		/**
		 * 线程池大小
		 */
		private int threads;

		/**
		 * 正在执行中的任务
		 */
		private Future<?>[] futures;

		/**
		 * 执行中的数量
		 */
		private AtomicInteger executing = new AtomicInteger(0);

		/**
		 * 是否运行中
		 */
		private volatile boolean running = true;

		/**
		 * 是否提交任务中
		 */
		private volatile boolean submitting = false;

		
		/**
		 * 等待所有线程执行完毕
		 */
		
		
		private int index(){
			for (int i = 0; i < threads; i++) {
				if(futures[i] == null || futures[i].isDone()){
					return i;
				}
			}
			return -1;
		}

		/**
		 * 清除已完成的任务
		 */
		private void removeDoneFuture(){
			for (int i = 0; i < threads; i++) {
				try {
					if(futures[i] != null && futures[i].get(10,TimeUnit.MILLISECONDS) == null){
						futures[i] = null;
					}
				} catch (Throwable t) {
					//忽略异常
				} 
			}
		}

		/**
		 * 等待有空闲线程
		 */
		private void await(){
			while(index() == -1){
				removeDoneFuture();
			}
		}

		

	}
}