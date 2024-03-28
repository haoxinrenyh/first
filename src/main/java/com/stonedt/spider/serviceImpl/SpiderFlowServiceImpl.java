package com.stonedt.spider.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.stonedt.spider.dao.BrowserTemplateDao;
import com.stonedt.spider.dao.SpiderFlowDao;
import com.stonedt.spider.entity.*;
import com.stonedt.spider.service.SpiderFlowService;
import com.stonedt.spider.util.BrowserUtil;
import com.stonedt.spider.util.DataUtil;
import com.stonedt.spider.util.DateUtil;
import com.stonedt.spider.util.MongodbTestSpiderflow;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.StringUtils;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerUtils;
import org.quartz.spi.OperableTrigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SpiderFlowServiceImpl implements SpiderFlowService {

	@Value("${rabbitmq.ip}")
	private String ip;

	@Value("${rabbitmq.port}")
	private Integer port;

	@Value("${rabbitmq.username}")
	private String username;

	@Value("${rabbitmq.password}")
	private String password;

	@Value("${rabbitmq.queue}")
	private String queue_priority;
	@Autowired
	SpiderFlowDao spiderFlowDao;

	@Autowired
	private BrowserTemplateDao browserTemplateDao;

//	@Autowired
//	public SpiderJobManager spiderJobManager;

	@Override
	public List<SpiderFlow> searchSpiderFlows(Integer website_id, Integer pageno, Integer pageSize) {
		// TODO Auto-generated method stub
		// List<SpiderFlow> spiderFlows =
		// spiderFlowDao.searchSpiderFlows(website_id);
		// 调用mongo工具类,然后将map转换成spiderflow, 减少代码更改
		Map mongoMap = new HashMap();
		mongoMap.put("website_id", website_id.toString());
		List<Map<String, Object>> maplist = new ArrayList<>();
		// 连接mongodb
		DBCollection collection = null;
		MongoClient mongoClient = null;
		try {
			mongoClient = new MongoClient(MongodbTestSpiderflow.MONGO_HOST, MongodbTestSpiderflow.MONGO_PORT);
			DB db = mongoClient.getDB(MongodbTestSpiderflow.MONGO_DB_NAME);
			collection = db.getCollection(MongodbTestSpiderflow.MONGO_COLLECTION_NAME);
			System.out.println("mongoDB连接成功");
			pageno = pageno == null ? 0 : pageno - 1;
			maplist = MongodbTestSpiderflow.search(mongoMap, pageSize, pageno * pageSize, collection);
			/*
			 * int limit = 1000; int skip = 0; while (true) { //分页查询
			 * List<Map<String, Object>> search =
			 * MongodbTestSpiderflow.search(mongoMap, limit, skip, collection);
			 * //放入大的集合里面 if (search != null && search.size() != 0) {
			 * maplist.addAll(search); } //判断停止条件 if (search == null ||
			 * search.size() == 0 || search.size() < limit) { break; }
			 * //更改开始条数,跳过查询过的 skip += limit;
			 * 
			 * }
			 */
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mongoClient.close();
			System.out.println("mongoDB连接已关闭");
		}
		// 查询出结果转成spiderflow类型
		List<SpiderFlow> spiderFlowList = new ArrayList<>();
		for (Map<String, Object> map : maplist) {
			SpiderFlow spiderFlow = new SpiderFlow();
			try {
				spiderFlow.setId(Integer.parseInt(map.get("id").toString()));
				if (map.get("seed_name") != null) {
					spiderFlow.setSeed_name(map.get("seed_name").toString());
				}
				if (map.get("seed_type") != null) {
					spiderFlow.setSeed_type(Integer.parseInt(map.get("seed_type").toString()));
				}
				if (map.get("seed_status") != null) {
					spiderFlow.setSeed_status(Integer.parseInt(map.get("seed_status").toString()));
				}
				if (map.get("cron") != null) {
					spiderFlow.setCron(map.get("cron").toString());
				}
				if (map.get("website_id") != null) {
					spiderFlow.setWebsite_id(Integer.parseInt(map.get("website_id").toString()));
				}
				if (map.get("create_date") != null) {
					spiderFlow.setCreate_date(DateUtil.parse(map.get("create_date").toString(), null));
				}
				if (map.get("xml") != null) {
					spiderFlow.setXml(map.get("xml").toString());
				}
				if (map.get("data_type_id") != null) {
					spiderFlow.setData_type_id(Integer.parseInt(map.get("data_type_id").toString()));
				}
				if (map.get("spider_level") != null) {
					spiderFlow.setSpider_level(Integer.parseInt(map.get("spider_level").toString()));
				}
				if (map.get("website_name") != null) {
					spiderFlow.setWebsite_name(map.get("website_name").toString());
				}
				if (map.get("website_ico") != null) {
					spiderFlow.setWebsite_ico(map.get("website_ico").toString());
				}
				if (map.get("estype") != null) {
					spiderFlow.setEstype(map.get("estype").toString());
				}
				if (map.get("esindex") != null) {
					spiderFlow.setEsindex(map.get("esindex").toString());
				}
				if (map.get("hbase_table") != null) {
					spiderFlow.setHbase_table(map.get("hbase_table").toString());
				}
				if (map.get("kafka_queue_name") != null) {
					spiderFlow.setKafka_queue_name(map.get("kafka_queue_name").toString());
				}
				if (map.get("bloomname") != null) {
					spiderFlow.setBloomname(map.get("bloomname").toString());
				}
				if (map.get("new_website_type") != null) {
					spiderFlow.setNew_website_type(Integer.parseInt(map.get("new_website_type").toString()));
				}
				if (map.get("from_smart_crawler") != null) {
					spiderFlow.setFrom_smart_crawler(Integer.parseInt(map.get("from_smart_crawler").toString()));
				}
				if (map.get("update_time") != null) {
					spiderFlow.setUpdate_time(DateUtil.parse(map.get("update_time").toString(), null));
				}
				if (map.get("sole_sign") != null) {
					spiderFlow.setSole_sign(map.get("sole_sign").toString());
				}
				if (map.get("typename") != null) {
					spiderFlow.setTypename(map.get("typename").toString());
				}
				if (map.get("error_num") != null) {
					spiderFlow.setErrorNum(Integer.parseInt(map.get("error_num").toString()));
				}
				//创建人
				if (map.get("create_user") != null) {
					spiderFlow.setCreate_user(map.get("create_user").toString());
				}
				//创建人id
				if (map.get("create_user_id") != null) {
					spiderFlow.setCreate_user_id(Integer.parseInt(map.get("create_user_id").toString()));
				}
				//更新人
				if (map.get("update_user") != null) {
					spiderFlow.setUpdate_user(map.get("update_user").toString());
				}
				//更新人id
				if (map.get("update_user_id") != null) {
					spiderFlow.setUpdate_user_id(Integer.parseInt(map.get("update_user_id").toString()));
				}
				spiderFlowList.add(spiderFlow);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (spiderFlowList == null || spiderFlowList.size() == 0) {
			return null;
		}
		return spiderFlowList;
	}

	@Override
	public SpiderFlow searchSpiderFlow(Integer id) {
		SpiderFlow spiderFlow = new SpiderFlow();
		try {
			spiderFlow = spiderFlowDao.searchSpiderFlow(id);
		}catch (Exception e){
			e.printStackTrace();
		}
		return spiderFlow;
	}

	/**
	 * @return
	 * @throws @author
	 *             dxk
	 * @date 2021/4/15 14:05 修改爬虫信息
	 * @since
	 */
	@Override
	@Transactional
	public int updateSpiderFlowById(SpiderFlow spiderFlow) {
		int i = spiderFlowDao.updateSpiderFlowById(spiderFlow);

		// 同步修改到mongo
		Map mongomap = new HashMap();
		mongomap.put("_id", spiderFlow.getId().toString());// spiderflow表的id
		mongomap.put("id", spiderFlow.getId().toString());// spiderflow表的id
		SpiderFlow spiderFlow1 = spiderFlowDao.selectTypeWebById(spiderFlow.getId());
		if (spiderFlow1.getWebsite_id() != null) {
			mongomap.put("website_id", spiderFlow1.getWebsite_id().toString());// data_source表的id
		}
		if (spiderFlow1.getWebsite_name() != null) {
			mongomap.put("website_name", spiderFlow1.getWebsite_name());// data_source
		}
		if (spiderFlow1.getWebsite_ico() != null) {
			mongomap.put("website_ico", spiderFlow1.getWebsite_ico());
		}
		if (spiderFlow1.getNew_website_type() != null) {
			mongomap.put("new_website_type", spiderFlow1.getNew_website_type().toString());
		}
		if (spiderFlow1.getData_type_id() != null) {
			mongomap.put("data_type_id", spiderFlow1.getData_type_id().toString());// websitetype表的id
		}
		if (spiderFlow1.getEstype() != null) {
			mongomap.put("estype", spiderFlow1.getEstype());
		}
		if (spiderFlow1.getEsindex() != null) {
			mongomap.put("esindex", spiderFlow1.getEsindex());
		}
		if (spiderFlow1.getHbase_table() != null) {
			mongomap.put("hbase_table", spiderFlow1.getHbase_table());
		}
		if (spiderFlow1.getKafka_queue_name() != null) {
			mongomap.put("kafka_queue_name", spiderFlow1.getKafka_queue_name());
		}
		if (spiderFlow1.getBloomname() != null) {
			mongomap.put("bloomname", spiderFlow1.getBloomname());
		}
		if (spiderFlow1.getTypename() != null) {
			mongomap.put("typename", spiderFlow1.getTypename());
		}
		if (spiderFlow.getSeed_name() != null) {
			mongomap.put("seed_name", spiderFlow.getSeed_name());
		}
		if (spiderFlow.getSeed_type() != null) {
			mongomap.put("seed_type", spiderFlow.getSeed_type().toString());
		}
		if (spiderFlow.getSeed_status() != null) {
			mongomap.put("seed_status", spiderFlow.getSeed_status().toString());
		}
		if (spiderFlow.getCron() != null) {
			mongomap.put("cron", spiderFlow.getCron());
		}
		if (spiderFlow.getXml() != null) {
			mongomap.put("xml", spiderFlow.getXml());
		}
		if (spiderFlow.getSpider_level() != null) {
			mongomap.put("spider_level", spiderFlow.getSpider_level().toString());
		}
		if (spiderFlow.getSole_sign() != null) {
			mongomap.put("sole_sign", spiderFlow.getSole_sign());
		}
		if (spiderFlow.getSeed_url() != null) {
			mongomap.put("seed_url", spiderFlow.getSeed_url());
		}
		if (spiderFlow.getFrom_smart_crawler() != null) {
			mongomap.put("from_smart_crawler", spiderFlow.getFrom_smart_crawler().toString());
		}
		if (spiderFlow.getErrorNum() != null) {
			mongomap.put("error_num", spiderFlow.getErrorNum().toString());
		}
		if (spiderFlow.getCustomlable() != null) {
			mongomap.put("customlable", spiderFlow.getCustomlable());
		}
		if (spiderFlow.getKey_sources_flag() != null) {
			mongomap.put("key_sources_flag", spiderFlow.getKey_sources_flag());
		}
		if (spiderFlow.getSpider_type() != null) {
			mongomap.put("spider_type", spiderFlow.getSpider_type());
		}
		//创建人
		if (spiderFlow.getCreate_user() != null) {
			mongomap.put("create_user", spiderFlow.getCreate_user());
		}
		//创建人id
		if (spiderFlow.getCreate_user_id() != null) {
			mongomap.put("create_user_id", spiderFlow.getCreate_user_id());
		}
		//更新人
		if (spiderFlow.getUpdate_user() != null) {
			mongomap.put("update_user", spiderFlow.getUpdate_user());
		}
		//更新人id
		if (spiderFlow.getUpdate_user_id() != null) {
			mongomap.put("update_user_id", spiderFlow.getUpdate_user_id());
		}

		mongomap.put("update_time", DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		// 调用工具类方法
		MongodbTestSpiderflow.update(mongomap, null);
		return i;
	}

	@Override
	public int updateSpiderFlow(SpiderFlow spiderFlow) {
		return spiderFlowDao.updateSpiderFlowById(spiderFlow);
	}

	/**
	 * @return
	 * @throws @author
	 *             dxk
	 * @date 2021/4/15 14:13 新增爬虫信息
	 * @since
	 */
	@Override
	public int insertSpiderFlow(SpiderFlow spiderFlow) {
		int i = spiderFlowDao.insertSpiderFlow(spiderFlow);
		insertMongoDbSpiderFlow(spiderFlow);
		return i;
	}

	// 同步新增到mongodb
	public Object insertMongoDbSpiderFlow(SpiderFlow spiderFlow) {
		// 同步新增到mongo
		Map mongomap = new HashMap();
		mongomap.put("_id", spiderFlow.getId().toString());// spiderflow表的id
		mongomap.put("id", spiderFlow.getId().toString());// spiderflow表的id
		// 根据id查询 关联的完整数据
		SpiderFlow spiderFlow1 = spiderFlowDao.selectTypeWebById(spiderFlow.getId());
		if (spiderFlow1.getWebsite_id() != null) {
			mongomap.put("website_id", spiderFlow1.getWebsite_id().toString());// data_source表的id
		}else{
			mongomap.put("website_id","");
		}

		if (spiderFlow1.getWebsite_name() != null) {
			mongomap.put("website_name", spiderFlow1.getWebsite_name());// data_source
		}else{
			mongomap.put("website_name","");
		}

		if (spiderFlow1.getWebsite_ico() != null) {
			mongomap.put("website_ico", spiderFlow1.getWebsite_ico());
		}else{
			mongomap.put("website_ico","");
		}

		if (spiderFlow1.getNew_website_type() != null) {
			mongomap.put("new_website_type", spiderFlow1.getNew_website_type().toString());
		}else{
			mongomap.put("new_website_type","");
		}

		if (spiderFlow1.getData_type_id() != null) {
			mongomap.put("data_type_id", spiderFlow1.getData_type_id().toString());// websitetype表的id
		}else{
			mongomap.put("data_type_id","");
		}


		if (spiderFlow1.getEstype() != null) {
			mongomap.put("estype", spiderFlow1.getEstype());
		}else{
			mongomap.put("estype","");
		}


		if (spiderFlow1.getEsindex() != null) {
			mongomap.put("esindex", spiderFlow1.getEsindex());
		}else{
			mongomap.put("esindex","");
		}


		if (spiderFlow1.getHbase_table() != null) {
			mongomap.put("hbase_table", spiderFlow1.getHbase_table());
		}else{
			mongomap.put("hbase_table","");
		}


		if (spiderFlow1.getKafka_queue_name() != null) {
			mongomap.put("kafka_queue_name", spiderFlow1.getKafka_queue_name());
		}else{
			mongomap.put("kafka_queue_name","");
		}


		if (spiderFlow1.getBloomname() != null) {
			mongomap.put("bloomname", spiderFlow1.getBloomname());
		}else{
			mongomap.put("bloomname","");
		}


		if (spiderFlow1.getTypename() != null) {
			mongomap.put("typename", spiderFlow1.getTypename());
		}else{
			mongomap.put("typename","");
		}

		if (spiderFlow.getSeed_name() != null) {
			mongomap.put("seed_name", spiderFlow.getSeed_name());
		}else{
			mongomap.put("seed_name","");
		}

		if (spiderFlow.getSeed_type() != null) {
			mongomap.put("seed_type", spiderFlow.getSeed_type().toString());
		}else{
			mongomap.put("seed_type","");
		}

		if (spiderFlow.getSeed_status() != null) {
			mongomap.put("seed_status", spiderFlow.getSeed_status().toString());
		}else{
			mongomap.put("seed_status","");
		}

		if (spiderFlow.getCron() != null) {
			mongomap.put("cron", spiderFlow.getCron());
		}else{
			mongomap.put("cron","");
		}

		if (spiderFlow.getXml() != null) {
			mongomap.put("xml", spiderFlow.getXml());
		}else{
			mongomap.put("xml","");
		}

		if (spiderFlow.getSpider_level() != null) {
			mongomap.put("spider_level", spiderFlow.getSpider_level().toString());
		}else{
			mongomap.put("spider_level","");
		}

		if (spiderFlow.getSole_sign() != null) {
			mongomap.put("sole_sign", spiderFlow.getSole_sign());
		}else{
			mongomap.put("sole_sign","");
		}

		if (spiderFlow.getKey_sources_flag() != null) {
			mongomap.put("key_sources_flag", spiderFlow.getKey_sources_flag());
		}else{
			mongomap.put("key_sources_flag","");
		}
		if (spiderFlow.getSpider_type() != null) {
			mongomap.put("spider_type", spiderFlow.getSpider_type());
		}else{
			mongomap.put("spider_type","2");
		}
		
		
		
		if (spiderFlow.getCustomlable() != null) {
			mongomap.put("customlable", spiderFlow.getCustomlable());
		}else{
			mongomap.put("customlable","");
		}
		
		
		if (spiderFlow.getFrom_smart_crawler() != null) {
			mongomap.put("from_smart_crawler", spiderFlow.getFrom_smart_crawler().toString());
		} else {
			mongomap.put("from_smart_crawler", "0");
		}
		if (spiderFlow.getSeed_url() != null) {
			mongomap.put("seed_url", spiderFlow.getSeed_url());
		}
		if (spiderFlow.getErrorNum() != null) {
			mongomap.put("error_num", spiderFlow.getErrorNum().toString());
		} else {
			mongomap.put("error_num", "0");
		}
		mongomap.put("create_date", DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		mongomap.put("update_time", DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));

		// 调用工具类方法
		JSONObject insert = MongodbTestSpiderflow.insert(mongomap, null);
		return insert;
	}

	/**
	 * @return
	 * @throws @author
	 *             dxk
	 * @date 2021/4/15 18:03 获取表达式最近几次运行时间
	 * @since
	 */
	@Override
	public List<String> getRecentTriggerTime(String cron, int numTimes) {
		List<String> list = new ArrayList<>();
		CronTrigger trigger;
		try {
			trigger = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule(cron)).build();
		} catch (Exception e) {
			list.add("cron表达式 " + cron + " 有误：" + e.getCause());
			return list;
		}
		List<Date> dates = TriggerUtils.computeFireTimes((OperableTrigger) trigger, null, numTimes);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (Date date : dates) {
			list.add(dateFormat.format(date));
		}
		return list;
	}

	/**
	 * @return
	 * @throws @author
	 *             dxk
	 * @date 2021/4/16 16:10 查询所有开启定时任务的爬虫
	 * @since
	 */
	@Override
	public List<String> selectSpiderListJob() {
		// 从mongo中查询
		Map map = new HashMap();
		map.put("seed_status", "1");
		List<String> list = new ArrayList();
		Set hs = new HashSet();
		// 连接mongodb
		DBCollection collection = null;
		MongoClient mongoClient = null;
		try {
			mongoClient = new MongoClient(MongodbTestSpiderflow.MONGO_HOST, MongodbTestSpiderflow.MONGO_PORT);
			DB db = mongoClient.getDB(MongodbTestSpiderflow.MONGO_DB_NAME);
			collection = db.getCollection(MongodbTestSpiderflow.MONGO_COLLECTION_NAME);
			System.out.println("mongoDB连接成功");
			int limit = 10000;
			// int skip = 0;
			int page = 0;
			Map<String, Object> gtparam = null;
			while (true) {
				// int page = skip / limit;
				page++;
				System.out.println("mongoDB正在获取数据，当前:" + page + "页");
				// 分页查询
				List<Map<String, Object>> search = null;
				try {
					search = MongodbTestSpiderflow.searchIdanCron(map, gtparam, limit, null, collection);
				} catch (Exception e) {
					e.printStackTrace();
				}
				// 放入大的集合里面
				if (search != null && search.size() != 0) {
					// list.addAll(search);
					for (int i = 0; i < search.size(); i++) {
						// boolean flag = true;
						// for (String cron : list) {
						// if(){
						//
						// }
						// }
						Map<String, Object> map2 = search.get(i);
						hs.add(map2.get("cron"));
						if (i == (search.size() - 1)) {
							gtparam = new HashMap<>();
							gtparam.put("_id", map2.get("_id"));
						}
					}
				}
				// 判断停止条件
				if (search == null || search.size() == 0 || search.size() < limit) {
					break;
				}
				search = null;
				// 更改开始条数,跳过查询过的
				// skip += limit;

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Iterator it = hs.iterator();
			while (it.hasNext()) {
				list.add(it.next().toString());
			}
			mongoClient.close();
			System.out.println("mongoDB连接已关闭");
		}
		return list;
	}

	@Override
	public List<String> selectSpiderListJob_mysql() {
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
				List<SpiderFlow> sfList = new ArrayList<>();

				try {
					sfList = spiderFlowDao.spiderFlowPage(limit,seed_status,null);
				}catch (Exception ex){
					ex.printStackTrace();
				}
				for (SpiderFlow spiderFlow : sfList) {
					if(spiderFlow!=null && spiderFlow.getCron()!=null){
						hs.add(spiderFlow.getCron());
					}
				}
				// 跳出while循环
				if (sfList == null || sfList.size() == 0 || sfList.size() < size) {
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


	/**
	 * @return
	 * @throws @author
	 *             dxk
	 * @date 2021/4/19 15:52 删除内存中的配置
	 * @since
	 */
	@Override
	public void deleteSpiderFlowJob(SpiderFlow spiderFlow) {
		// 修改了cron
		if (StringUtils.isNotBlank(spiderFlow.getCron())) {
			// 查询原cron
			String cron = searchSpiderFlow(spiderFlow.getId()).getCron();
			// // 操作
			// spiderJobManager.removeList(spiderFlow.getId(), cron);
			// // 判断该表达式是否还有需要执行的配置
			// boolean listHave = spiderJobManager.isListHave(cron);
			// if (!listHave) {
			// // 没有 停掉该任务
			// spiderJobManager.remove(cron);
			// }
		}
	}

	/**
	 * @return
	 * @throws @author
	 *             dxk
	 * @date 2021/4/28 16:34 根据唯一标识查询爬虫 未写完
	 * @since
	 */
	@Override
	public SpiderFlow selectSpiderBySign(String sole_sign) {
		return null;
	}

	/**
	 * @return
	 * @throws @author
	 *             dxk
	 * @date 2021/5/18 15:58 根据数据类型获取需要的数据
	 * @since
	 */
	@Override
	public Map<String, Object> getTypeJson(int typeid, int websiteId) {
		Map<String, Object> typeJson = spiderFlowDao.getTypeJson(typeid, websiteId);

		return typeJson;
	}

	// 根据站点查询站点下的爬虫id集合
	@Override
	public List<Integer> selectIdByWebId(Integer webid) {
		// List<Integer> list = spiderFlowDao.selectIdByWebId(webid);
		Map map = new HashMap();
		map.put("website_id", webid.toString());
		List<Map<String, Object>> search = MongodbTestSpiderflow.search(map, null, null, null);
		List<Integer> list = new ArrayList<>();
		for (Map search1 : search) {
			list.add(Integer.parseInt(search1.get("id").toString()));
		}
		return list;
	}

	@Override
	public List<SpiderFlow> searchSpiderFlowsDisabled(Integer pageNo, Integer pageSize) {
		// 调用mongo工具类,然后将map转换成spiderflow, 减少代码更改
		Map mongoMap = new HashMap();
		mongoMap.put("error_num", "5");
		List<Map<String, Object>> maplist = new ArrayList<>();
		// 连接mongodb
		DBCollection collection = null;
		MongoClient mongoClient = null;
		try {
			mongoClient = new MongoClient(MongodbTestSpiderflow.MONGO_HOST, MongodbTestSpiderflow.MONGO_PORT);
			DB db = mongoClient.getDB(MongodbTestSpiderflow.MONGO_DB_NAME);
			collection = db.getCollection(MongodbTestSpiderflow.MONGO_COLLECTION_NAME);
			System.out.println("mongoDB连接成功");
			pageNo = pageNo == null ? 0 : pageNo - 1;
			maplist = MongodbTestSpiderflow.search(null,mongoMap, pageSize, pageNo * pageSize, collection);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mongoClient.close();
			System.out.println("mongoDB连接已关闭");
		}
		// 查询出结果转成spiderflow类型
		List<SpiderFlow> spiderFlowList = new ArrayList<>();
		for (Map<String, Object> map : maplist) {
			SpiderFlow spiderFlow = new SpiderFlow();
			try {
				spiderFlow.setId(Integer.parseInt(map.get("id").toString()));
				if (map.get("seed_name") != null) {
					spiderFlow.setSeed_name(map.get("seed_name").toString());
				}
				if (map.get("seed_type") != null) {
					spiderFlow.setSeed_type(Integer.parseInt(map.get("seed_type").toString()));
				}
				if (map.get("seed_status") != null) {
					spiderFlow.setSeed_status(Integer.parseInt(map.get("seed_status").toString()));
				}
				if (map.get("cron") != null) {
					spiderFlow.setCron(map.get("cron").toString());
				}
				if (map.get("website_id") != null) {
					spiderFlow.setWebsite_id(Integer.parseInt(map.get("website_id").toString()));
				}
				if (map.get("create_date") != null) {
					spiderFlow.setCreate_date(DateUtil.parse(map.get("create_date").toString(), null));
				}
				if (map.get("xml") != null) {
					spiderFlow.setXml(map.get("xml").toString());
				}
				if (map.get("data_type_id") != null) {
					spiderFlow.setData_type_id(Integer.parseInt(map.get("data_type_id").toString()));
				}
				if (map.get("spider_level") != null) {
					spiderFlow.setSpider_level(Integer.parseInt(map.get("spider_level").toString()));
				}
				if (map.get("website_name") != null) {
					spiderFlow.setWebsite_name(map.get("website_name").toString());
				}
				if (map.get("website_ico") != null) {
					spiderFlow.setWebsite_ico(map.get("website_ico").toString());
				}
				if (map.get("estype") != null) {
					spiderFlow.setEstype(map.get("estype").toString());
				}
				if (map.get("esindex") != null) {
					spiderFlow.setEsindex(map.get("esindex").toString());
				}
				if (map.get("hbase_table") != null) {
					spiderFlow.setHbase_table(map.get("hbase_table").toString());
				}
				if (map.get("kafka_queue_name") != null) {
					spiderFlow.setKafka_queue_name(map.get("kafka_queue_name").toString());
				}
				if (map.get("bloomname") != null) {
					spiderFlow.setBloomname(map.get("bloomname").toString());
				}
				if (map.get("new_website_type") != null) {
					spiderFlow.setNew_website_type(Integer.parseInt(map.get("new_website_type").toString()));
				}
				if (map.get("from_smart_crawler") != null) {
					spiderFlow.setFrom_smart_crawler(Integer.parseInt(map.get("from_smart_crawler").toString()));
				}
				if (map.get("update_time") != null) {
					spiderFlow.setUpdate_time(DateUtil.parse(map.get("update_time").toString(), null));
				}
				if (map.get("sole_sign") != null) {
					spiderFlow.setSole_sign(map.get("sole_sign").toString());
				}
				if (map.get("typename") != null) {
					spiderFlow.setTypename(map.get("typename").toString());
				}
				if (map.get("error_num") != null) {
					spiderFlow.setErrorNum(Integer.parseInt(map.get("error_num").toString()));
				}
				spiderFlowList.add(spiderFlow);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (spiderFlowList == null || spiderFlowList.size() == 0) {
			return null;
		}
		return spiderFlowList;
	}

	@Override
	public int removeSpiderFlow(int id, int is_del) {
		SpiderFlow spiderFlow = new SpiderFlow();
		spiderFlow.setId(id);
		spiderFlow.setIs_del(is_del);

		return  spiderFlowDao.updateSpiderFlowById(spiderFlow);
	}

	@Override
	public int saveSpiderFlow(SpiderFlow spiderFlow) {
		return  spiderFlowDao.insertSpiderFlow(spiderFlow);
	}

	@Override
	public int saveBrowserPlan(SpiderPlan spiderPlan) {
		try {
			BrowserTemplate browserTemplate = browserTemplateDao.findTemplate(1l);
			if(browserTemplate==null || !DataUtil.CheckString(browserTemplate.getTemp_xml()) ){
				System.out.println(" 模板不存在");
				return 0;
			}
			String templateXml = browserTemplate.getTemp_xml();

			String xml = BrowserUtil.doXml(spiderPlan,templateXml);
			if( !DataUtil.CheckString(xml)){
				System.out.println(" xml生成失败");
				return 0;
			}

			long time = System.currentTimeMillis();
			Integer website_id = 1005;      //站点类型
			String seed_name = "测试"+time;  //任务名称
			Integer seed_type = 1;          //任务类型 ( 1：html, 2：json, 3：html>cdata )
			Integer seed_status = 0;        //任务状态 ( 1：正常, 0：关闭 )
			String  cron = "0 30 4 * * ?";  //任务执行时间表达式
			Integer data_type_id = 1;       //数据类型id
			Integer spider_level = 3;       //优先级 1~5
			String sole_sign = "plan"+time; //唯一值
			Integer spider_type = 2;
			Integer type = 0;
			Integer key_sources_flag = 1;

			SpiderFlow spiderFlow = new SpiderFlow();
			spiderFlow.setWebsite_id(website_id);
			spiderFlow.setSeed_name(seed_name);
			spiderFlow.setSeed_type(seed_type);
			spiderFlow.setSeed_status(seed_status);
			spiderFlow.setCron(cron);
			spiderFlow.setData_type_id(data_type_id);
			spiderFlow.setSpider_level(spider_level);
			spiderFlow.setSole_sign(sole_sign);
			spiderFlow.setSpider_type(spider_type);
			spiderFlow.setType(type);
			spiderFlow.setKey_sources_flag(key_sources_flag);
			spiderFlow.setXml(xml);
			return  spiderFlowDao.insertSpiderFlow(spiderFlow);

		}catch (Exception e){
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public List<Map<String, Object>> saveSpiderFlow(String limit, Integer seed_status, String cron) {
		return spiderFlowDao.spiderFlowPage_map(limit,seed_status,cron);
	}

	@Override
	public int checkSoleSign(Integer id, String sole_sign) {
		return spiderFlowDao.checkSoleSign(id,sole_sign);
	}

	@Override
	public void updateSeedCount(List<EsRecord> recordList) {
		try {
			if(recordList!=null && recordList.size()>0){
				int insertType =spiderFlowDao.insertEsRecord(recordList);
				System.out.println("记录es上传数据! "+ insertType +"条!");
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public Rabbitmq findRabbitmq() {
		Rabbitmq rabbitmq = new Rabbitmq();
		rabbitmq.setIp(ip);
		rabbitmq.setUsername(username);
		rabbitmq.setPassword(password);
		rabbitmq.setPort(port);
		rabbitmq.setQueue_priority(queue_priority);
		return rabbitmq;
	}

}
