package com.stonedt.spider.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.stonedt.spider.entity.SpiderArticle;

public class EsUtils {
	
	private final String APP_ID = "6";
	private final String TOKEN = "ggxHmSorkt60nQ238v0A==";
	//private static String es = "http://s3.stonedt.com:8333";
//	private static String es = "http://s1.stonedt.com:6302";
	private static String es = UtilConfig.getURL().substring(0,UtilConfig.getURL().length()-1);
	public static void main(String[] args) throws UnsupportedEncodingException, IOException {
		Map<String, String> map = new HashMap<>();
        map.put("page", "1");
        map.put("size", "10");
        map.put("websiteid", "578");
        map.put("otherseedid", "4484");
        
        Map<String, Object> bryes = bryes(map);
        System.out.println(bryes);
	}
	
	public static JSONObject dataoverview(Map<String, String> map) throws IOException {
		//size、page、keyword、emotionalIndex、starttime、endtime可选参数  不需要就传空字符串
		String times = map.get("times");
		String timee = map.get("timee");
		String classify = map.get("classify");
		String url = es + "/yqt/sourcewebsitesearch";
		String params = "times="+times+"&timee="+timee+"&classify="+classify;
		PrintWriter out = null;
		BufferedReader in = null;
		URL realUrl = new URL(url);
        URLConnection conn = realUrl.openConnection();  
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		conn.setDoOutput(true);
		conn.setDoInput(true);
		out = new PrintWriter(conn.getOutputStream());
		out.print(params);
		out.flush();
		in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
		StringBuilder response = new StringBuilder();
		String line;
		while((line=in.readLine())!=null){
			response.append(line);
		}
		try {
			if (out != null) {
				out.close();
			}
			if (in != null) {
				in.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		JSONObject parseObject = JSON.parseObject(response.toString());
		
		return parseObject;
	}
	public static JSONObject dataOverviewInvite(Map<String, String> map) throws IOException {
		//size、page、keyword、emotionalIndex、starttime、endtime可选参数  不需要就传空字符串
		String times = map.get("times");
		String timee = map.get("timee");
		String url = es + "/jobs/jobslist";
		String params = "times="+times+"&timee="+timee;
		PrintWriter out = null;
		BufferedReader in = null;
		URL realUrl = new URL(url);
        URLConnection conn = realUrl.openConnection();  
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		conn.setDoOutput(true);
		conn.setDoInput(true);
		out = new PrintWriter(conn.getOutputStream());
		out.print(params);
		out.flush();
		in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
		StringBuilder response = new StringBuilder();
		String line;
		while((line=in.readLine())!=null){
			response.append(line);
		}
		try {
			if (out != null) {
				out.close();
			}
			if (in != null) {
				in.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		JSONObject parseObject = JSON.parseObject(response.toString());
		
		return parseObject;
	}
	/**
	 * bry获取es文章数据
	 * @author 	ZhaoHengxing
	 * @date  2019年8月8日 下午4:10:07
	 * @param map
	 * @return
	 * @throws IOException
	 */
	public static Map<String, Object> bryes(Map<String, String> map) throws IOException {
		//size、page、keyword、emotionalIndex、starttime、endtime可选参数  不需要就传空字符串
		String size = map.get("size");
		String page = map.get("page");
		String websiteid = map.get("websiteid");
		String otherseedid = map.get("otherseedid");//必传，可以传多个或一个，多个以英文逗号分隔		
		String url = es + "/yys/qbsearchcontent";
		String params = "page="+page+"&size="+size+"&otherseedid="+otherseedid+"&websiteid=" + websiteid;
		System.err.println(url+params);
		PrintWriter out = null;
		BufferedReader in = null;
		URL realUrl = new URL(url);
        URLConnection conn = realUrl.openConnection();  
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		conn.setDoOutput(true);
		conn.setDoInput(true);
		out = new PrintWriter(conn.getOutputStream());
		out.print(params);
		out.flush();
		in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
		StringBuilder response = new StringBuilder();
		String line;
		while((line=in.readLine())!=null){
			response.append(line);
		}
		try {
			if (out != null) {
				out.close();
			}
			if (in != null) {
				in.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		Map<String, Object> map2 = new HashMap<String, Object>();
		List<SpiderArticle> list = new ArrayList<SpiderArticle>();
		JSONObject parseObject = JSON.parseObject(response.toString());
		map2.put("count", parseObject.get("count").toString());
		map2.put("pages", parseObject.get("page_count").toString());
		map2.put("pageNum", parseObject.get("page").toString());
		map2.put("size", parseObject.get("size").toString());
		JSONArray parseArray = JSON.parseArray(parseObject.getString("data"));
		int size2 = parseArray.size();
		if (size2 != 0) {
			for (int i = 0; i < size2; i++) {
				JSONObject parseObject2 = JSONObject.parseObject(parseArray.get(i).toString());
				JSONObject parseObject3 = JSON.parseObject(parseObject2.getString("_source"));
				SpiderArticle temporaryArticle = JSON.toJavaObject(parseObject3, SpiderArticle.class);
				list.add(temporaryArticle);
			}
		}
		System.err.println(list);
		map2.put("list", list);
		return map2;
	}
	
	/**
	 * 获取bry文章详情
	 * @author 	ZhaoHengxing
	 * @date  2019年8月8日 下午5:15:24
	 * @return
	 * @throws IOException
	 */
	public Map<String, String> brycontent(String article_public_id) throws IOException {
		//article_public_id = "1159368314259247158";
		String url = es + "/es/getArticledetail";
		String params = "app_id="+APP_ID+"&token="+TOKEN+"&article_public_id="+article_public_id;
		PrintWriter out = null;
		BufferedReader in = null;
		URL realUrl = new URL(url);
        URLConnection conn = realUrl.openConnection();  
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		conn.setDoOutput(true);
		conn.setDoInput(true);
		out = new PrintWriter(conn.getOutputStream());
		out.print(params);
		out.flush();
		in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
		StringBuilder response = new StringBuilder();
		String line;
		while((line=in.readLine())!=null){
			response.append(line);
		}
		try {
			if (out != null) {
				out.close();
			}
			if (in != null) {
				in.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		JSONObject parseObject = JSON.parseObject(response.toString());
		Map<String, String> map = new HashMap<>();
		map.put("publish_time", parseObject.getString("publish_time"));
		map.put("text", parseObject.getString("text"));
		map.put("title", parseObject.getString("title"));
		map.put("source_url", parseObject.getString("source_url"));
		map.put("source_name", parseObject.getString("source_name"));
		return map;
	}
}
