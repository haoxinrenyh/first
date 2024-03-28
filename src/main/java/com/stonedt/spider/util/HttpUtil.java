package com.stonedt.spider.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class HttpUtil {
	public static void main(String[] args) {
		try {
			String string = get("http://app.jg.eastmoney.com/Report/GetReportByTreeNode.do?id=T004003026&request=simpleSearch&cid=7235005477913020&gridTitle=%E5%86%9C%E6%9E%97%E7%89%A7%E6%B8%94&type=6&id=T004003026&requestAction=0&date=&types=T004003026&request=simpleSearch&securitycodes=&columnType=&isShow=false&isSelectStock=false&cid=7235005477913020&pageIndex=1&limit=50&sort=datetime&order=desc%20HTTP/1.1");
			JSONObject parseObject = JSONObject.parseObject(string);
			System.out.println(parseObject.toJSONString());
			
		} catch (ParseException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	  * post请求
	  * @param url
	  * @param map
	  * @throws IOException
	  */
   public static String sendpost(String url,Map<String,Object> map) throws IOException {
   	String str1 = null;
       HttpPost httpRequest = new HttpPost(url);
       List<BasicNameValuePair> nvalues = new ArrayList<>();
       // 请求的参数
       for (Map.Entry<String, Object> entry : map.entrySet()) { 
       	nvalues.add(new BasicNameValuePair(entry.getKey().toString(), entry.getValue().toString()));
		}	
       httpRequest.setEntity(new UrlEncodedFormEntity(nvalues,"utf-8"));
       HttpResponse response = new DefaultHttpClient().execute(httpRequest);

       if (response.getStatusLine().getStatusCode() == 200) {// 获取调用api返回数据
               //doing something
           InputStream content = response.getEntity().getContent();
           InputStreamReader reader = new InputStreamReader(content);
           BufferedReader bufr = new BufferedReader(reader);//缓冲
           String str = null;
           while((str = bufr.readLine()) != null){
           	str1 = str;
           }

       }
		return str1;

   }
   /**
    * 创建get请求
    * @param url
    * @return
    * @throws ParseException
    * @throws IOException
    */
   public static String get(String url) throws ParseException, IOException {
		
		CloseableHttpClient httpclient = HttpClients.createDefault();

		String string = null;
		// 创建http
		HttpGet httpget = new HttpGet(url);
		 RequestConfig config = RequestConfig.custom().setConnectTimeout(5000).build();

		httpget.setConfig(config);
		httpget.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.112 Safari/537.36");
       httpget.setHeader("Cookie", "PHPSESSID=7kub9rjdi1evfvqr136h0rhf95");
		System.out.println("executing request " + httpget.getURI());
		CloseableHttpResponse response = httpclient.execute(httpget);
		// 返回状态
		HttpEntity entity = response.getEntity();
		System.out.println("--------------------------------------");
		// 打印状态值
		System.out.println(response.getStatusLine());
		if (entity != null) {
			System.out.println("Response content length: " + entity.getContentLength());
			string = EntityUtils.toString(entity,"utf-8");
		}
		response.close();
		httpclient.close();
		return string;
	}
   
   
   public static String getDongcai(String url) throws ParseException, IOException {
		
		CloseableHttpClient httpclient = HttpClients.createDefault();

		String string = null;
		// 创建http
		HttpGet httpget = new HttpGet(url);
		 RequestConfig config = RequestConfig.custom().setConnectTimeout(5000).build();

		httpget.setConfig(config);
		httpget.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.112 Safari/537.36");
      httpget.setHeader("Cookie", "PHPSESSID=7kub9rjdi1evfvqr136h0rhf95");
		System.out.println("executing request " + httpget.getURI());
		CloseableHttpResponse response = httpclient.execute(httpget);
		// 返回状态
		HttpEntity entity = response.getEntity();
		System.out.println("--------------------------------------");
		// 打印状态值
		System.out.println(response.getStatusLine());
		if (entity != null) {
			System.out.println("Response content length: " + entity.getContentLength());
			string = EntityUtils.toString(entity,"gbk");
		}
		response.close();
		httpclient.close();
		return string;
	}
   


}
