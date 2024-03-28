package com.stonedt.spider.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

public class ExceptionUtils {
	//private static final Logger logger = LoggerFactory.getLogger(ExceptionUtils.class);

//	private static String ExceptionUrl = "http://127.0.0.1:6305/monitor/exception/receive"; 
	private static String ExceptionUrl = "http://192.168.71.64:6410/monitor/exception/receive"; 
	public static void submit(JSONObject exceptionOb) {
		exceptionOb.put("exception_service_name", "爬虫任务定时发送");
		exceptionOb.put("exception_time",DateUtil.getDate());
		sendPostRaw(exceptionOb.toJSONString(), ExceptionUrl, "UTF-8");
		
	}
	
	
    /**
     * post 以raw的方式发送请求
     */
    public static String sendPostRaw(String params, String url, String encoding) {
        String body = "";
        try {
            //创建httpclient对象
            CloseableHttpClient client = HttpClients.createDefault();
            //创建post方式请求对象
            HttpPost httpPost = new HttpPost(url);

            //装填参数
            StringEntity s = new StringEntity(params, encoding);
            s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json;charset=UTF-8"));
            //设置参数到请求对象中
            httpPost.setEntity(s);
           // logger.info("请求地址：" + url);

            //设置header信息
            httpPost.setHeader("Content-type", "application/json;charset=UTF-8");
            httpPost.setHeader("User-Agent",getRandomAgent());

            CloseableHttpResponse response = client.execute(httpPost);  //执行请求操作，并拿到结果（同步阻塞）
            HttpEntity entity = response.getEntity();//获取结果实体
            if (entity != null) {
                body = EntityUtils.toString(entity, encoding);  //按指定编码转换结果实体为String类型
            }
            EntityUtils.consume(entity);
            response.close();  //释放链接
        } catch (Exception e) {
            e.printStackTrace();
        }
        return body;
    }
    
    
	/**
	 * 随机一个Agent
	 * @return
	 */
	public static String getRandomAgent()
	{
		List<String> RandomAgent = new ArrayList<String>();
		RandomAgent.add("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:34.0) Gecko/20100101 Firefox/34.0");
		RandomAgent.add("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");
		RandomAgent.add("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36");
		RandomAgent.add("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.64 Safari/537.11");
		RandomAgent.add("Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US) AppleWebKit/534.16 (KHTML, like Gecko) Chrome/10.0.648.133 Safari/534.16");
		int sign = (int)(0+Math.random()*(5));
		return RandomAgent.get(sign);
	}
}
