package com.stonedt.spider.util;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.*;

/**
 * 
 * @author wzq
 *
 */
public class MongodbTestSpiderflow {

	public static final String MONGO_HOST = "192.168.71.71";
	//public static final String MONGO_HOST = "s1.stonedt.com";
	public static final Integer MONGO_PORT = 27017;
	public static final String MONGO_DB_NAME = "spider";
	public static final String MONGO_COLLECTION_NAME = "stonedt_spider_configuration";

	public static void main(String[] args) {
		//
		// Map<String, Object> eqparam = new HashMap<String, Object>();
		// eqparam.put("_id", "1149416");
		// List<Map<String, Object>> search = search(eqparam, 100, null, null);
		// for (Map<String, Object> map : search) {
		// System.out.println(map);
		// }
		String lists = "1089";
		String[] split = lists.split(",");
		for (String string : split) {
			while (true) {
				// //同步修改到mongo
				Map map = new HashMap();
//				 map.put("seed_status","1");
				// map.put("from_smart_crawler","1");
				// map.put("cron", "0 20 0/2 * * ?");
				 map.put("website_id", string);
				 System.out.println(string);
//				map.put("_id", string);
				List<Map<String, Object>> list = search(map, 10000, 0, null);
				System.err.println(list.size());

				MongoClient mongoClient = null;
				try {
					mongoClient = new MongoClient(MONGO_HOST, MONGO_PORT);
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				DB db = mongoClient.getDB(MONGO_DB_NAME);
				DBCollection collection = db.getCollection(MONGO_COLLECTION_NAME);
				for (Map<String, Object> map2 : list) {
					// System.out.println(map2);
					// remove(map2,collection);
					// map2.put("cron", "0 0 0-23/1 * * ?");
					// map2.put("cron", "0 0 22 * * ?");
//					map2.put("seed_status", "0");
//					JSONObject update = update(map2, collection);
					System.out.println(map2);
//					map2 = null;
				}
				mongoClient.close();
				// break;
				if (list.size() < 10000) {
					System.err.println("结束循环");
					list = null;
					break;
				}
				list = null;
			}

		}

		// 从mongo中查询
		// Map map = new HashMap();
		// map.put("seed_status", "1");
		// List<String> list = new ArrayList();
		// Set hs = new HashSet();
		// // 连接mongodb
		// DBCollection collection = null;
		// MongoClient mongoClient = null;
		// try {
		// mongoClient = new MongoClient(MongodbTestSpiderflow.MONGO_HOST,
		// MongodbTestSpiderflow.MONGO_PORT);
		// DB db = mongoClient.getDB(MongodbTestSpiderflow.MONGO_DB_NAME);
		// collection =
		// db.getCollection(MongodbTestSpiderflow.MONGO_COLLECTION_NAME);
		// System.out.println("mongoDB连接成功");
		// int limit = 10000;
		//// int skip = 0;
		// int page = 0;
		// Map<String,Object> gtparam = null;
		// while (true) {
		//// int page = skip / limit;
		// page++;
		// System.out.println("mongoDB正在获取数据，当前:" + page + "页");
		// // 分页查询
		// List<Map<String, Object>> search = null;
		// try {
		// search = MongodbTestSpiderflow.search(map,gtparam, limit, null,
		// collection);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// // 放入大的集合里面
		// if (search != null && search.size() != 0) {
		//// list.addAll(search);
		// for (int i = 0; i < search.size(); i++) {
		//// boolean flag = true;
		//// for (String cron : list) {
		//// if(){
		////
		//// }
		//// }
		// Map<String, Object> map2 = search.get(i);
		// hs.add(map2.get("cron"));
		// if(i ==(search.size() -1)){
		// gtparam = new HashMap<>();
		// gtparam.put("_id", map2.get("_id"));
		// }
		// }
		// }
		// // 判断停止条件
		// if (search == null || search.size() == 0 || search.size() < limit) {
		// break;
		// }
		// search = null;
		// // 更改开始条数,跳过查询过的
		//// skip += limit;
		//
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// } finally {
		// Iterator it = hs.iterator();
		// while(it.hasNext()) {
		// list.add(it.next().toString());
		// }
		// // mongoClient.close();
		// // System.out.println("mongoDB连接已关闭");
		// }
		// for (String object : list) {
		// System.out.println(object);
		// }
	}

	public static Integer searchCount(Map<String, Object> eqparam, DBCollection collection) throws UnknownHostException {
		boolean clientflag = collection == null ? true : false;
		MongoClient mongoClient = null;
		if (clientflag) {
			mongoClient = new MongoClient(MONGO_HOST, MONGO_PORT);
			DB db = mongoClient.getDB(MONGO_DB_NAME);
			collection = db.getCollection(MONGO_COLLECTION_NAME);
			System.out.println("mongoDB连接成功");
		}

		BasicDBObject basicDBObject = toBasicDBObject(eqparam);
		int count = collection.find(basicDBObject).count();

		if (clientflag) {
			mongoClient.close();
			System.out.println("mongoDB连接已关闭");
		}
		return count;
	}

	public static List<Map<String, Object>> search(Map<String, Object> eqparam, Integer limit, Integer skip,
			DBCollection collection) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		boolean clientflag = collection == null ? true : false;
		MongoClient mongoClient = null;
		if (clientflag) {
			try {
				mongoClient = new MongoClient(MONGO_HOST, MONGO_PORT);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			DB db = mongoClient.getDB(MONGO_DB_NAME);
			collection = db.getCollection(MONGO_COLLECTION_NAME);
			System.out.println("mongoDB连接成功");
		}

		BasicDBObject basicDBObject = toBasicDBObject(eqparam);

		// 改为采集手动配置数据
		// basicDBObject.append("from_smart_crawler", "0");
		DBCursor cursor = null;
		if (skip == null) {
			skip = 0;
		}
		if (null != limit) {
			cursor = collection.find(basicDBObject).skip(skip).limit(limit);
		} else if (skip == 0) {
			cursor = collection.find(basicDBObject);
		} else {
			cursor = collection.find(basicDBObject).skip(skip);
		}
		if (cursor.hasNext()) {
			// 已经存在
			while (cursor.hasNext()) {
				DBObject next = cursor.next();
				Set<String> keySet = next.keySet();
				Map<String, Object> map = new HashMap<>();
				for (String key : keySet) {
					map.put(key, next.get(key));
				}
				next = null;
				result.add(map);
				map = null;
			}
		}
		try {
			Thread.sleep(0);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		cursor.close();
		cursor = null;
		if (clientflag) {
			basicDBObject.clear();
			mongoClient.close();
			System.out.println("mongoDB连接已关闭");
		}
		return result;
	}

	public static List<Map<String, Object>> search(Map<String, Object> eqparam, Map<String, Object> gtparam,
			Integer limit, Integer skip, DBCollection collection) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		boolean clientflag = collection == null ? true : false;
		MongoClient mongoClient = null;
		if (clientflag) {
			try {
				mongoClient = new MongoClient(MONGO_HOST, MONGO_PORT);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			DB db = mongoClient.getDB(MONGO_DB_NAME);
			collection = db.getCollection(MONGO_COLLECTION_NAME);
			System.out.println("mongoDB连接成功");
		}

		BasicDBObject basicDBObject = toBasicDBObject(eqparam);

		// 改为采集手动配置数据
		// basicDBObject.append("from_smart_crawler", "0");

		if (gtparam != null && gtparam.keySet().size() > 0) {
			if (null == basicDBObject) {
				basicDBObject = new BasicDBObject();
			}
			Set<String> ltekeySet = gtparam.keySet();
			for (String ltekey : ltekeySet) {
				BasicDBObject lte = new BasicDBObject();
				lte.put("$gt", gtparam.get(ltekey));
				basicDBObject.append(ltekey, lte);
			}
		}
		DBObject sort = new BasicDBObject("_id", 1);
		DBCursor cursor = null;

		// BasicDBObject searchkey = new BasicDBObject("_id", 1).append("cron",
		// 1);// 指定需要显示列

		if (skip == null) {
			skip = 0;
		}
		if (null != limit) {
			cursor = collection.find(basicDBObject).sort(sort).skip(skip).limit(limit);
		} else if (skip == 0) {
			cursor = collection.find(basicDBObject).sort(sort);
		} else {
			cursor = collection.find(basicDBObject).sort(sort).skip(skip);
		}
		if (cursor.hasNext()) {
			// 已经存在
			while (cursor.hasNext()) {
				DBObject next = cursor.next();
				Set<String> keySet = next.keySet();
				Map<String, Object> map = new HashMap<>();
				for (String key : keySet) {
					map.put(key, next.get(key));
				}
				next = null;
				result.add(map);
				map = null;
			}
		}
		try {
			Thread.sleep(0);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// cursor.remove();
		cursor.close();
		cursor = null;
		if (clientflag) {
			basicDBObject.clear();
			mongoClient.close();
			System.out.println("mongoDB连接已关闭");
		}
		return result;
	}

	public static List<Map<String, Object>> searchIdanCron(Map<String, Object> eqparam, Map<String, Object> gtparam,
			Integer limit, Integer skip, DBCollection collection) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		boolean clientflag = collection == null ? true : false;
		MongoClient mongoClient = null;
		if (clientflag) {
			try {
				mongoClient = new MongoClient(MONGO_HOST, MONGO_PORT);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			DB db = mongoClient.getDB(MONGO_DB_NAME);
			collection = db.getCollection(MONGO_COLLECTION_NAME);
			System.out.println("mongoDB连接成功");
		}

		BasicDBObject basicDBObject = toBasicDBObject(eqparam);

		// 改为采集手动配置数据
		// basicDBObject.append("from_smart_crawler", "0");

		if (gtparam != null && gtparam.keySet().size() > 0) {
			if (null == basicDBObject) {
				basicDBObject = new BasicDBObject();
			}
			Set<String> ltekeySet = gtparam.keySet();
			for (String ltekey : ltekeySet) {
				BasicDBObject lte = new BasicDBObject();
				lte.put("$gt", gtparam.get(ltekey));
				basicDBObject.append(ltekey, lte);
			}
		}
		DBObject sort = new BasicDBObject("_id", 1);
		DBCursor cursor = null;

		BasicDBObject searchkey = new BasicDBObject("_id", 1).append("cron", 1);// 指定需要显示列

		if (skip == null) {
			skip = 0;
		}
		if (null != limit) {
			cursor = collection.find(basicDBObject, searchkey).sort(sort).skip(skip).limit(limit);
		} else if (skip == 0) {
			cursor = collection.find(basicDBObject, searchkey).sort(sort);
		} else {
			cursor = collection.find(basicDBObject, searchkey).sort(sort).skip(skip);
		}
		if (cursor.hasNext()) {
			// 已经存在
			while (cursor.hasNext()) {
				DBObject next = cursor.next();
				Set<String> keySet = next.keySet();
				Map<String, Object> map = new HashMap<>();
				for (String key : keySet) {
					map.put(key, next.get(key));
				}
				next = null;
				result.add(map);
				map = null;
			}
		}
		try {
			Thread.sleep(0);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// cursor.remove();
		cursor.close();
		cursor = null;
		if (clientflag) {
			basicDBObject.clear();
			mongoClient.close();
			System.out.println("mongoDB连接已关闭");
		}
		return result;
	}

	public static JSONObject update(Map<String, Object> map, DBCollection collection) {
		JSONObject result = new JSONObject();
		boolean clientflag = collection == null ? true : false;
		BasicDBObject basicDBObject = toBasicDBObject(map);
		if (null != basicDBObject && null != basicDBObject.get("_id")) {
			MongoClient mongoClient = null;
			if (clientflag) {
				try {
					mongoClient = new MongoClient(MONGO_HOST, MONGO_PORT);
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				DB db = mongoClient.getDB(MONGO_DB_NAME);
				collection = db.getCollection(MONGO_COLLECTION_NAME);
				System.out.println("mongoDB连接成功");
			}
			BasicDBObject searchObj = new BasicDBObject();
			searchObj.put("_id", basicDBObject.get("_id"));
			DBCursor cursor = collection.find(searchObj);
			if (cursor.hasNext()) {
				BasicDBObject updateObj = new BasicDBObject();
				updateObj.put("$set", basicDBObject);
				collection.update(searchObj, updateObj);// 更新
				// // 3.4查询修改后的文档（修改确认）
				DBCursor cursor2 = collection.find(basicDBObject);
				if (cursor2.hasNext()) {
					result.put("code", 200);
					result.put("msg", "修改成功！");
					result.put("data", cursor2.next());
				} else {
					result.put("code", 202);
					result.put("msg", "修改失败！");
				}
			} else {
				result.put("code", 201);
				result.put("msg", "该文档不存在！");
			}
			if (clientflag) {
				mongoClient.close();
				System.out.println("mongoDB连接已关闭");
			}
		} else {
			result.put("code", 203);
			result.put("msg", "参数不正确！");
			result.put("date", map);
		}

		return result;
	}

	public static JSONObject insert(Map<String, Object> map, DBCollection collection) {
		JSONObject result = new JSONObject();
		// DBCollection collection =
		// getclient(MONGO_DB_NAME,MONGO_COLLECTION_NAME);
		boolean clientflag = collection == null ? true : false;
		BasicDBObject basicDBObject = toBasicDBObject(map);
		if (null != basicDBObject && null != basicDBObject.get("_id")) {
			MongoClient mongoClient = null;
			if (clientflag) {
				try {
					mongoClient = new MongoClient(MONGO_HOST, MONGO_PORT);
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				DB db = mongoClient.getDB(MONGO_DB_NAME);
				collection = db.getCollection(MONGO_COLLECTION_NAME);
				System.out.println("mongoDB连接成功");
			}

			DBCursor cursor = collection.find(basicDBObject);
			if (cursor.hasNext()) {
				// 已经存在
				result.put("code", 201);
				result.put("msg", "该文档已存在");
				result.put("data", cursor.next());
			} else {
				collection.insert(basicDBObject);
				DBCursor cursor1 = collection.find(basicDBObject);
				if (cursor1.hasNext()) {
					result.put("code", 202);
					result.put("msg", "插入成功");
					result.put("data", cursor1.next());
				} else {
					result.put("code", 202);
					result.put("msg", "插入失败");
				}
			}
			if (clientflag) {
				mongoClient.close();
				System.out.println("mongoDB连接已关闭");
			}
		} else {
			result.put("code", 203);
			result.put("msg", "参数不正确！");
			result.put("date", map);
		}
		return result;
	}

	public static JSONObject remove(Map<String, Object> map, DBCollection collection) {
		JSONObject result = new JSONObject();
		BasicDBObject newDocument = toBasicDBObject(map);
		boolean clientflag = collection == null ? true : false;
		if (null != newDocument && null != newDocument.get("_id")) {
			MongoClient mongoClient = null;
			if (clientflag) {
				try {
					mongoClient = new MongoClient(MONGO_HOST, MONGO_PORT);
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				DB db = mongoClient.getDB(MONGO_DB_NAME);
				collection = db.getCollection(MONGO_COLLECTION_NAME);
				System.out.println("mongoDB连接成功");
			}

			DBCursor cursor = collection.find(newDocument);
			if (cursor.hasNext()) {
				collection.remove(newDocument);
				// 3.6查询该文档是否存在（删除确认）
				DBCursor cursor3 = collection.find(newDocument);
				if (cursor3.hasNext()) {
					result.put("code", 202);
					result.put("msg", "删除文档失败！");
				} else {
					result.put("code", 200);
					result.put("msg", "删除文档成功！");
				}
			} else {
				result.put("code", 201);
				result.put("msg", "该文档不存在！");
			}
			if (clientflag) {
				mongoClient.close();
				System.out.println("mongoDB连接已关闭");
			}
		} else {
			result.put("code", 203);
			result.put("msg", "参数不正确！");
			result.put("date", map);
		}

		return result;
	}

	private static BasicDBObject toBasicDBObject(Map<String, Object> map) {
		BasicDBObject newDocument = null;
		if (null != map && map.keySet().size() > 0) {
			Set<String> keySet = map.keySet();
			newDocument = new BasicDBObject();
			for (String key : keySet) {
				newDocument.put(key, map.get(key));
			}
		}
		return newDocument;
	}

}