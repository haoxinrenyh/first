package com.stonedt.spider.controller.travel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.stonedt.spider.util.UtilConfig;

/**
 * 	景点
 */
@Controller
@RequestMapping("/attractions")
public class AttractionsController {
	
//	private final String es_url = "http://s1.stonedt.com:6303";
//	private final String es_url = UtilConfig.getURL2().substring(0,UtilConfig.getURL2().length()-1);

	@Value("${URL2}")
    private  String es_url;
	private final String travelist_api = "/trave/travelist";
	private final String gettravedetail_api =  "/trave/gettravedetail";
	
	/**
	 * 	列表
	 */
	@RequestMapping(value = "/travelist", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public String thesisnlist(String pageNum, String matchingmode, String time, String searchkeyword) {
		String times = "", timee = "";
		if (StringUtils.isNotBlank(time)) {
			if ("24h".equals(time)) {
				JSONObject recruitmentMonitorStr = recruitmentMonitorStr(1);
				times = recruitmentMonitorStr.getString("times");
				timee = recruitmentMonitorStr.getString("timee");
			}
			if ("7d".equals(time)) {
				JSONObject recruitmentMonitorStr = recruitmentMonitorStr(7);
				times = recruitmentMonitorStr.getString("times");
				timee = recruitmentMonitorStr.getString("timee");
			}
			if ("30d".equals(time)) {
				JSONObject recruitmentMonitorStr = recruitmentMonitorStr(30);
				times = recruitmentMonitorStr.getString("times");
				timee = recruitmentMonitorStr.getString("timee");
			}
		}
		Map<String, String> map = new HashMap<>();
		map.put("matchingmode", matchingmode);
		map.put("size", "10");
		map.put("times", times);
		map.put("timee", timee);
		map.put("page", pageNum);
		map.put("keyword", searchkeyword);
		String response = "";
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, String>> list = new ArrayList<>();
		try {
			response = esArticle(map, travelist_api);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("list", list);
			result.put("totalData", 0);
			result.put("totalPage", 1);
		}
		if (StringUtils.isNotBlank(response)) {
			JSONObject parseObject = JSON.parseObject(response);
			String totalData = parseObject.getString("count");
			String totalPage = parseObject.getString("page_count");
			JSONArray jsonArray = parseObject.getJSONArray("data");
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				JSONObject jsonObject2 = jsonObject.getJSONObject("_source");
				Map<String, String> params = JSONObject.parseObject(jsonObject2.toJSONString(), new TypeReference<Map<String, String>>(){});
				params.put("content", params.get("abstract"));
				list.add(params);
			}
			if (Integer.parseInt(totalData) > 2000) {
				totalPage = "200";
			}
			if (StringUtils.isBlank(totalPage)) {
				totalPage = "1";
			}else if ("0".equals(totalPage)) {
				totalPage = "1";
			}
			result.put("list", list);
			result.put("totalData", totalData);
			result.put("totalPage", totalPage);
		}else {
			result.put("list", list);
			result.put("totalData", 0);
			result.put("totalPage", 1);
		}
		return JSON.toJSONString(result);
	}
	
	/**
	 * 	详情
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public String detail(String article_public_id) {
		String url = es_url + gettravedetail_api;
		String params = "article_public_id=" + article_public_id;
		System.err.println(url + "?" + params);
		JSONObject parseObject = new JSONObject();
		try {
			String sendPost = sendPost(url, params);
			parseObject = JSON.parseObject(sendPost);
			parseObject.put("content", parseObject.getString("abstract"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return JSON.toJSONString(parseObject);
	}
	
	public JSONObject recruitmentMonitorStr(Integer days) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		String timee = now.format(dateTimeFormatter);
		if (days == null) {
			days = 0;
		}
		String times = now.minusDays(days).format(dateTimeFormatter);
		JSONObject result = new JSONObject();
		result.put("times", times);
		result.put("timee", timee);
		return result;
	}
	
	@RequestMapping(value = "/attractions-page", method = RequestMethod.GET)
	public ModelAndView mainstreamMediaList(ModelAndView mv) {
		mv.setViewName("travelData/attractions");
		return mv;
	}
	
	@RequestMapping(value = "/attractions-detail", method = RequestMethod.GET)
	public ModelAndView aaa(ModelAndView mv) {
		mv.setViewName("travelData/attractions-detail");
		return mv;
	}
	
	public String esArticle(Map<String, String> map, String api) {
		String size = map.get("size");
		String matchingmode = map.get("matchingmode");
		String times = map.get("times");
		String timee = map.get("timee");
		String page = map.get("page");
		String keyword = map.get("keyword");
		String url = es_url + api;
		System.out.println("url:"+url);
		String params = "size=" + size + "&matchingmode=" + matchingmode
				+ "&times=" + times + "&timee=" + timee + "&page=" + page + "&keyword=" + keyword;
		System.err.println(url + "?" + params);
		String sendPost = "";
		try {
			sendPost = sendPost(url, params);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sendPost;
	}

	public static String sendPost(String url, String params) throws IOException {
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
		in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
		StringBuilder response = new StringBuilder();
		String line;
		while((line = in.readLine()) != null){
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
		return response.toString();
	}
}
