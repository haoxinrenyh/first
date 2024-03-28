package com.stonedt.spider.scheduled;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.stonedt.spider.config.IpConfiguration;
import com.stonedt.spider.dao.SpiderFlowDao;
import com.stonedt.spider.entity.Rabbitmq;
import com.stonedt.spider.service.SpiderFlowService;
import com.stonedt.spider.util.DateUtil;
import com.stonedt.spider.util.ExceptionUtils;
import com.stonedt.spider.util.MongodbTestSpiderflow;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import sun.misc.BASE64Decoder;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * 爬虫定时执行
 *
 * @author Administrator
 */
@Component
public class SpiderJob extends QuartzJobBean {

	private static final String exchange_priority = "open_spider_exchange_priority";

	private static final String rk_priority = "open_spider_rk_priority";

	private static String LocalIP = getLocalIP();
	private static String LocalPort;

	public static boolean SPIDERJOBSTATUS = true;
	
	@Autowired
	private IpConfiguration projectip;

	public static  SpiderFlowService spiderFlowService;


	@Override
	protected void executeInternal(JobExecutionContext context) {
		System.out.println("executeInternal>>>>>");
		try {
			if (!SPIDERJOBSTATUS) {
				return;
			}
			JobDataMap dataMap = context.getMergedJobDataMap();
			String cron = dataMap.get(SpiderJobManager.JOB_PARAM_NAME).toString();
			if (!cron.equals("0 20 0/2 * * ?")) {
				System.err.println("开启发送："+cron);
				MyThread mt = new MyThread(cron); // 实例化对象
				Thread t = new Thread(mt); // 实例化Thread类对象
				t.start(); // 启动多线程
			}
		} catch (Exception e) {
			e.printStackTrace();
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("exception_level", 1);
			jsonObject.put("exception_type", 7);
			jsonObject.put("exception_msg", sw.toString());
			ExceptionUtils.submit(jsonObject);
		}
	}

	public  void sendmessage_mysql(String cron) {
		System.err.println("进入发送方法");
		if (cron.equals("0 30 9-22/4 * * ?")) {
			System.err.println("===============================================================");
			cron = "0 20 0/2 * * ?";
		}

		int seed_status=1;
		int page = 0;
		int size = 10000;
		try {
			while (true) {
				// 分页查询
				List<Map<String, Object>> search = null;
				try {
					page++;
					System.out.println("正在获取mysql:cron="+cron+" 的数据，当前:" + page + "页");
					String limit = "limit "+(page-1)*size+","+size;
					search = spiderFlowService.saveSpiderFlow(limit,seed_status,cron);

					if (search == null || search.size() == 0) {
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				// 放入大的集合里面
				if (search != null && search.size() != 0) {
					// 解析map数据
					ConnectionFactory connectionFactory = new ConnectionFactory();

					Rabbitmq rabbitmq = spiderFlowService.findRabbitmq();
					connectionFactory.setPassword(rabbitmq.getPassword());
					connectionFactory.setUsername(rabbitmq.getUsername());
					connectionFactory.setPort(rabbitmq.getPort());
					connectionFactory.setHost(rabbitmq.getIp());
					Connection connection = null;
					Channel channel = null;
					int trynum = 0;
					while (trynum < 3) {
						try {
							connection = connectionFactory.newConnection();
							channel = connection.createChannel();
							break;
						} catch (Exception e) {
							trynum++;
							e.printStackTrace();
						}
					}
					for (int i = 0; i < search.size(); i++) {
						try {

							Map<String, Object> map2 = search.get(i);

							String seed_name = map2.get("seed_name").toString();
							if (isNumeric(seed_name)) {
								continue;
							}
							try {
								if(channel == null ){
									if(connection == null){
										connection = connectionFactory.newConnection();
										channel = connection.createChannel();
									}else{
										channel = connection.createChannel();
									}
								}
								send(map2, channel);
								map2 = null;
							} catch (Exception e) {
								e.printStackTrace();
								StringWriter sw = new StringWriter();
								PrintWriter pw = new PrintWriter(sw);
								e.printStackTrace(pw);

								try {
									channel.close();
									connection.close();
								} catch (Exception e2) {
									e2.printStackTrace();
								}
								try {
									connection = connectionFactory.newConnection();
									channel = connection.createChannel();
								} catch (Exception e2) {
									e2.printStackTrace();
								}

								JSONObject jsonObject = new JSONObject();
								jsonObject.put("exception_level", 1);
								jsonObject.put("exception_type", 7);
								jsonObject.put("query_param_one", map2.get("website_name") + "(" + map2.get("website_id") + ")");
								jsonObject.put("query_param_two", map2.get("seed_name") + "(" + map2.get("id") + ")");
								jsonObject.put("exception_msg", sw.toString());
								ExceptionUtils.submit(jsonObject);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					channel.close();
					connection.close();
				}
				// 判断停止条件
					if (search.size() < size) {
					break;
				}
				// 更改开始条数,跳过查询过的
				search = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public static void sendmessage(String cron) {
		System.err.println("进入发送方法");
		if (cron.equals("0 30 9-22/4 * * ?")) {
			System.err.println("===============================================================");
			cron = "0 20 0/2 * * ?";
		}
		Map map = new HashMap();
		map.put("seed_status", "1");
		map.put("cron", cron);
		// 连接mongodb
		MongoClient mongoClient = null;
		DBCollection collection = null;
		try {
			mongoClient = new MongoClient(MongodbTestSpiderflow.MONGO_HOST, MongodbTestSpiderflow.MONGO_PORT);
			DB db = mongoClient.getDB(MongodbTestSpiderflow.MONGO_DB_NAME);
			collection = db.getCollection(MongodbTestSpiderflow.MONGO_COLLECTION_NAME);
			System.out.println("mongoDB连接成功");
			Integer limit = new Integer(10000);
			Integer page = new Integer(1);
			Map<String, Object> gtparam = null;
			while (true) {
				// 分页查询
				List<Map<String, Object>> search = null;
				try {
					search = MongodbTestSpiderflow.search(map, gtparam, limit, null, collection);
					System.out.println("mongoDB正在获取" + map.toString() + "数据，当前:" + page + "页,起始id" + gtparam );
					page++;
					if (search == null || search.size() == 0) {
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				// 放入大的集合里面
				if (search != null && search.size() != 0) {
					// 解析map数据
					ConnectionFactory connectionFactory = new ConnectionFactory();
					Rabbitmq rabbitmq = spiderFlowService.findRabbitmq();
					connectionFactory.setPassword(rabbitmq.getPassword());
					connectionFactory.setUsername(rabbitmq.getUsername());
					connectionFactory.setPort(rabbitmq.getPort());
					connectionFactory.setHost(rabbitmq.getIp());
					Connection connection = null;
					Channel channel = null;
					int trynum = 0;
					while (trynum < 3) {
						try {
							connection = connectionFactory.newConnection();
							channel = connection.createChannel();
							break;
						} catch (Exception e) {
							trynum++;
							e.printStackTrace();
						}
					}
					for (int i = 0; i < search.size(); i++) {
						try {

							Map<String, Object> map2 = search.get(i);
							if (i == (search.size() - 1)) {
								gtparam = new HashMap<>();
								gtparam.put("_id", map2.get("_id"));
								System.out.println("==================================最后id" + map2.get("_id")
										+ "====================================");
							}
							String seed_name = map2.get("seed_name").toString();
							if (isNumeric(seed_name)) {
								continue;
							}
							try {
								if(channel == null ){
									if(connection == null){
										connection = connectionFactory.newConnection();
										channel = connection.createChannel();
									}else{
										channel = connection.createChannel();
									}
								}
								send(map2, channel);
								map2 = null;
							} catch (Exception e) {
								e.printStackTrace();
								StringWriter sw = new StringWriter();
								PrintWriter pw = new PrintWriter(sw);
								e.printStackTrace(pw);

								try {
									channel.close();
									connection.close();
								} catch (Exception e2) {
									e2.printStackTrace();
								}
								try {
									connection = connectionFactory.newConnection();
									channel = connection.createChannel();
								} catch (Exception e2) {
									e2.printStackTrace();
								}

								JSONObject jsonObject = new JSONObject();
								jsonObject.put("exception_level", 1);
								jsonObject.put("exception_type", 7);
								jsonObject.put("query_param_one", map.get("website_name") + "(" + map.get("website_id") + ")");
								jsonObject.put("query_param_two", map.get("seed_name") + "(" + map.get("id") + ")");
								jsonObject.put("exception_msg", sw.toString());
								ExceptionUtils.submit(jsonObject);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					channel.close();
					connection.close();
				}
				// 判断停止条件
				if (search.size() < limit) {
					break;
				}
				// 更改开始条数,跳过查询过的
				search = null;

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mongoClient.close();
			map = null;
			System.out.println("mongoDB连接已关闭");
		}
	}

	public static boolean isNumeric(String str) {
		String regEx = "^-?[0-9]+$";
		Pattern pat = Pattern.compile(regEx);
		Matcher mat = pat.matcher(str);
		if (mat.find()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @return
	 * @throws Exception
	 * @throws @author
	 *             dxk
	 * @date 2021/4/16 16:50 发送配置和参数
	 * @since
	 */
	private static void send(Map<String, Object> map, Channel channel) throws Exception {
		System.out.println("send>>>>>");
		try {
			// create exchange
			channel.exchangeDeclare(exchange_priority, "direct", true);

			//rabbitmq
			Rabbitmq rabbitmq = spiderFlowService.findRabbitmq();

			// create queue with priority
			Map<String, Object> args = new HashMap<String, Object>();
			args.put("x-max-priority", 10);
			channel.queueDeclare(rabbitmq.getQueue_priority(), true, false, false, args);
			channel.queueBind(rabbitmq.getQueue_priority(), exchange_priority, rk_priority);

			try {
				 System.err.println("定时任务发送成功,id=" + map.get("id")+" ,cron="+map.get("cron"));
				String send = parsing(map);
				// System.err.println("定时任务发送成功" + send);
				AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();
				int spider_level = Integer.parseInt(map.get("spider_level").toString());
				builder.priority(spider_level);
				AMQP.BasicProperties properties = builder.build();
				// System.out.println("send___________-:" + send);
				channel.basicPublish(exchange_priority, rk_priority, properties, send.getBytes("utf-8"));
				send = null;
				map = null;
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * @return
	 * @throws @author
	 *             dxk
	 * @date 2021/4/16 16:43 通过cron 获取需要执行的爬虫配置
	 * @since
	 */
	// private static Map<Integer, Map<String, Object>> getSpiderList(String
	// cron) {
	// return SpiderJobManager.spiderList.get(cron);
	// }

	/**
	 * @return
	 * @throws @author
	 *             dxk
	 * @date 2021/4/16 16:47 解析map数据
	 * @since
	 */
	private static String parsing(Map<String, Object> map) {
		// TODO Auto-generated method stub
		Integer id = Integer.parseInt(map.get("id").toString());
		String extend_string_five = (String) map.get("seed_name");
		Integer seed_type = Integer.parseInt(map.get("seed_type").toString());
		Integer seed_status = Integer.parseInt(map.get("seed_status").toString());
		String cron = (String) map.get("cron");
		Integer website_id = Integer.parseInt(map.get("website_id").toString());
		String create_date = map.get("create_date").toString();
		String xml = (String) map.get("xml");
		Integer data_type_id = Integer.parseInt(map.get("data_type_id").toString());
		Integer spider_level = Integer.parseInt(map.get("spider_level").toString());
		String sole_sign = (String) map.get("sole_sign");
		Integer from_smart_crawler = Integer.parseInt(map.get("from_smart_crawler").toString());
		String update_time = map.get("update_time").toString();
		Integer error_num = Integer.parseInt(map.get("error_num").toString());
		String customlable = map.get("customlable") != null ? map.get("customlable").toString() : "";
		String spider_type = map.get("spider_type") != null ? map.get("spider_type").toString() : "2";
		String key_sources_flag = map.get("key_sources_flag") != null ? map.get("key_sources_flag").toString() : "0";
		Integer type = Integer.parseInt(map.get("type").toString());
		Integer temp_id = map.get("temp_id")!=null?Integer.parseInt(map.get("temp_id").toString()) : null;

		String source_name = (String) map.get("website_name");
		String website_ico = map.get("website_ico") != null ? map.get("website_ico").toString() : null;
		String es_type = (String) map.get("estype");
		String es_index = (String) map.get("esindex");
		String hbase_table = (String) map.get("hbase_table");
		String kafka_queue_name = (String) map.get("kafka_queue_name");
		String bloomName = (String) map.get("bloomname");

		JSONObject param = new JSONObject();
		param.put("source_name", source_name);
		param.put("sourcewebsitename", source_name);
		param.put("extend_string_five", extend_string_five);
		param.put("bloomName", bloomName);
		param.put("websitelogo", website_ico);
		param.put("otherwebsiteid", website_id);
		param.put("website_id", website_id);
		param.put("seed_id", id);
		param.put("es_type", es_type);
		param.put("es_index", es_index);
		param.put("hbase_table", hbase_table);
		param.put("kafka_queue_name", kafka_queue_name);
		param.put("customlable", customlable);
		param.put("spider_type", spider_type);
		param.put("key_sources_flag", key_sources_flag);

		param.put("seed_type", seed_type);
		param.put("seed_status", seed_status);
		param.put("cron", cron);
		param.put("create_date", create_date);
		param.put("data_type_id",data_type_id );
		param.put("spider_level", spider_level);
		param.put("sole_sign", sole_sign);
		param.put("from_smart_crawler", from_smart_crawler);
		param.put("update_time", update_time);
		param.put("error_num",error_num );
		param.put("type", type);
		param.put("temp_id", temp_id);


		Object new_website_type = map.get("new_website_type");
		if (new_website_type == null) {
			new_website_type = 8;
		}
		param.put("classify", new_website_type);
		param.put("similarvolume", 1);
		double a = Math.random() * 100;
		DecimalFormat df = new DecimalFormat("0.00");
		param.put("heatvolume", df.format(a));

		String jsonString = param.toJSONString();

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("xml", compress(xml));
		jsonObject.put("param", compress(jsonString));

		JSONObject js = new JSONObject();

		js.put("sendDate", DateUtil.getDate());
		// 获取服务器ip
		js.put("sendIp", LocalIP);
		js.put("sendPort", LocalPort);
		// js.put("sendPort","6180");
		jsonObject.put("sign", js.toJSONString());
		map = null;
		return jsonObject.toJSONString();
	}

	public static boolean isEncoding(String str, String encode) {
		try {
			if (str.equals(new String(str.getBytes(), encode))) {
				return true;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 使用gzip压缩字符串
	 *
	 * @param str
	 *            要压缩的字符串
	 * @return
	 */
	public static String compress(String str) {
		if (str == null || str.length() == 0) {
			return str;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		GZIPOutputStream gzip = null;
		try {
			gzip = new GZIPOutputStream(out);
			gzip.write(str.getBytes("utf-8"));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (gzip != null) {
				try {
					gzip.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return new sun.misc.BASE64Encoder().encode(out.toByteArray());
	}

	/**
	 * 使用gzip解压缩
	 *
	 * @param compressedStr
	 *            压缩字符串
	 * @return
	 */
	public static String uncompress(String compressedStr) {
		if (compressedStr == null) {
			return null;
		}

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in = null;
		GZIPInputStream ginzip = null;
		byte[] compressed = null;
		String decompressed = null;
		try {
			compressed = new sun.misc.BASE64Decoder().decodeBuffer(compressedStr);
			in = new ByteArrayInputStream(compressed);
			ginzip = new GZIPInputStream(in);
			byte[] buffer = new byte[1024];
			int offset = -1;
			while ((offset = ginzip.read(buffer)) != -1) {
				out.write(buffer, 0, offset);
			}
			decompressed = out.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ginzip != null) {
				try {
					ginzip.close();
				} catch (IOException e) {
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
		return decompressed;
	}

	public static String getBASE64(String str) {
		if (str == null)
			return null;
		return (new sun.misc.BASE64Encoder()).encode(str.getBytes());
	}

	public static String getFromBASE64(String str) {
		if (str == null)
			return null;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			byte[] b = decoder.decodeBuffer(str);
			return new String(b);
		} catch (Exception e) {
			return null;
		}
	}

	// 获取ip
	public static String getLocalIP() {
		String localIP = "127.0.0.1";
		try {
			OK: for (Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces(); interfaces
					.hasMoreElements();) {
				NetworkInterface networkInterface = interfaces.nextElement();
				if (networkInterface.isLoopback() || networkInterface.isVirtual() || !networkInterface.isUp()) {
					continue;
				}
				Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
				while (addresses.hasMoreElements()) {
					InetAddress address = addresses.nextElement();
					if (address instanceof Inet4Address) {
						localIP = address.getHostAddress();
						break OK;
					}
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return localIP;
	}


}
