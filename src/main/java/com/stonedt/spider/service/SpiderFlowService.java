package com.stonedt.spider.service;

import com.stonedt.spider.entity.EsRecord;
import com.stonedt.spider.entity.Rabbitmq;
import com.stonedt.spider.entity.SpiderFlow;
import com.stonedt.spider.entity.SpiderPlan;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SpiderFlowService {

//	List<SpiderFlow> searchSpiderFlows(Integer website_id);

	List<SpiderFlow> searchSpiderFlows(Integer website_id, Integer pageno, Integer pageSize);

	SpiderFlow searchSpiderFlow(Integer id);

	// 修改爬虫信息
	int updateSpiderFlowById(SpiderFlow spiderFlow);

	// 修改爬虫信息
	int updateSpiderFlow(SpiderFlow spiderFlow);

	// 新增爬虫信息
	int insertSpiderFlow(SpiderFlow spiderFlow);

	// 获取表达式最近五次运行时间
	List<String> getRecentTriggerTime(String cron, int numTimes);

	// 查询所有开启定时任务的爬虫
	List<String> selectSpiderListJob();

	List<String> selectSpiderListJob_mysql();

//    //根据状态修改定时任务
//	void statusSpiderFlowJob(SpiderFlow spiderFlow);

	// 删除内存中的配置
	void deleteSpiderFlowJob(SpiderFlow spiderFlow);

	// 根据唯一标识查询爬虫
	SpiderFlow selectSpiderBySign(String sole_sign);

	// 根据数据类型获取需要的数据
	Map<String, Object> getTypeJson(int typeid, int websiteId);

	// 根据站点查询站点下的爬虫id集合
	List<Integer> selectIdByWebId(Integer webid);

	List<SpiderFlow> searchSpiderFlowsDisabled(Integer pageNo, Integer pageSize);

	int removeSpiderFlow(int id,int is_del);

	int saveSpiderFlow(SpiderFlow spiderFlow);

	int saveBrowserPlan(SpiderPlan spiderPlan);

	List<Map<String,Object>> saveSpiderFlow(String limit,Integer seed_status,String cron);

	int checkSoleSign(Integer id,  String sole_sign);

	void updateSeedCount(List<EsRecord> recordList);

	Rabbitmq findRabbitmq();

}
