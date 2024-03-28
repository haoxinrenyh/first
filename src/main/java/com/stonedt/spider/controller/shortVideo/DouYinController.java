package com.stonedt.spider.controller.shortVideo;

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
import com.stonedt.spider.controller.overseas.OverseasInfo;
import com.stonedt.spider.util.DateUtil;
import com.stonedt.spider.util.UtilConfig;

/**
 * 	抖音
 * @date  2020年2月6日 下午2:43:45
 */
@Controller
@RequestMapping("/douYin")
public class DouYinController {
	
//	private final String es_url = "http://s1.stonedt.com:6303";
//	private final String es_url = UtilConfig.getURL2().substring(0,UtilConfig.getURL2().length()-1);
	@Value("${URL2}")
    private  String es_url;
	
	private final String sciencebysourcename_api = "/vedio/sciencebysourcename";
	private final String vediolist_api = "/vedio/vediolist";
	private final String getvediodetail_api =  "/vedio/getvediodetail";
	
	/**
	 * 	来源筛选
	 */
	@RequestMapping(value = "/sciencebysourcename", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public String decorateLevels() {
		String url = es_url + sciencebysourcename_api;
		System.err.println(url);
		JSONArray jsonArray = new JSONArray();
		try {
			String sendPost = sendPost(url, "");
			JSONObject parseObject = JSON.parseObject(sendPost);
			if (parseObject.containsKey("aggregations")) {
				JSONObject jsonObject = parseObject.getJSONObject("aggregations");
				if (jsonObject.containsKey("group_by_tags")) {
					JSONObject jsonObject2 = jsonObject.getJSONObject("group_by_tags");
					jsonArray = jsonObject2.getJSONArray("buckets");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return JSON.toJSONString(jsonArray);
	}
	
	/**
	 * 	列表
	 */
	@RequestMapping(value = "/vediolist", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public String thesisnlist(String pageNum, int matchingmode, String time, String searchkeyword) {
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
		JSONArray array=new JSONArray();
		JSONArray Array=new JSONArray();
		JSONObject mapJson = new JSONObject();
		mapJson.put("timefield","release_time");
		mapJson.put("times","");
		mapJson.put("size","10");
		mapJson.put("index","stonedt_vedio");
		mapJson.put("page", pageNum);
		mapJson.put("type","infor");
		mapJson.put("timee","");
		JSONObject json1=new JSONObject();
		json1.put("field", "website_name");
		json1.put("keyword", "抖音");
        array.add(json1);
        
        if(searchkeyword!=null && searchkeyword!="") {
        	if(matchingmode==1) {//标题
        		JSONObject json2=new JSONObject();
        		json2.put("field", "title");
        		json2.put("keyword", searchkeyword);
                array.add(json2);
        	}else if(matchingmode==2) {//作者
        		JSONObject json2=new JSONObject();
        		json2.put("field", "author");
        		json2.put("keyword", searchkeyword);
                array.add(json2);
        	}else {
        		JSONObject json2=new JSONObject();
        		json2.put("field", "title");
        		json2.put("keyword", searchkeyword);
        		Array.add(json2);
                JSONObject json3=new JSONObject();
        		json3.put("field", "author");
        		json3.put("keyword", searchkeyword);
                Array.add(json3);
                mapJson.put("or",Array);
        	}
        }
		mapJson.put("and",array);
		System.err.println(mapJson.toString());
		String response = "";
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, String>> list = new ArrayList<>();
		try {
			String url = "http://dx2.stonedt.com:7120/commonsearch/superdatalist";
			response =  OverseasInfo.sendPostRaw(mapJson.toJSONString(),url,"utf-8");
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
			if (Integer.parseInt(totalData) > 5000) {
				totalPage = "500";
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
		//System.out.println(list.toString());
		return JSON.toJSONString(result);
	}
	
	
	
	/***
	 * 快手
	 * @param pageNum
	 * @param matchingmode
	 * @param time
	 * @param searchkeyword
	 * @param website_name
	 * @return
	 */
	@RequestMapping(value = "/kuaishouvediolist", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public String KuaiShouthesisnlist(String pageNum, String matchingmode, String time, String searchkeyword) {
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
		//map.put("website_name", website_name);
		map.put("matchingmode", matchingmode);
		map.put("size", "10");
		map.put("times", times);
		map.put("timee", timee);
		map.put("page", pageNum);
		
		map.put("website_name", "%E5%BF%AB%E6%89%8B");
		map.put("keyword", searchkeyword);
		String response = "";
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, String>> list = new ArrayList<>();
		try {
			response = esArticle(map, vediolist_api);
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
			if (Integer.parseInt(totalData) > 5000) {
				totalPage = "500";
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
		String url = es_url + getvediodetail_api;
		String params = "article_public_id=" + article_public_id;
		System.err.println(url + "?" + params);
		JSONObject parseObject = new JSONObject();
		try {
			String sendPost = sendPost(url, params);
			parseObject = JSON.parseObject(sendPost);
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
	
	@RequestMapping(value = "/douyin", method = RequestMethod.GET)
	public ModelAndView mainstreamMediaList(ModelAndView mv) {
		mv.addObject("nowArray", DateUtil.getNowDate());
		mv.setViewName("shortVideo/douyin");
		return mv;
	}
	
	@RequestMapping(value = "/douyin-detail", method = RequestMethod.GET)
	public ModelAndView aaa(ModelAndView mv) {
		mv.setViewName("shortVideo/douyin-detail");
		return mv;
	}
	
	@RequestMapping(value = "/kuaishou", method = RequestMethod.GET)
	public ModelAndView kuaishouMediaList(ModelAndView mv) {
		mv.addObject("nowArray", DateUtil.getNowDate());
		mv.setViewName("shortVideo/kuaishou");
		return mv;
	}
	
	@RequestMapping(value = "/kuaishou-detail", method = RequestMethod.GET)
	public ModelAndView kuaishou(ModelAndView mv) {
		mv.setViewName("shortVideo/kuaishou-detail");
		return mv;
	}
	
	public String esArticle(Map<String, String> map, String api) {
		String size = map.get("size");
		String matchingmode = map.get("matchingmode");
		String times = map.get("times");
		String timee = map.get("timee");
		String page = map.get("page");
		String keyword = map.get("keyword");
		String website_name = map.get("website_name");
		String url = es_url + api;
		String params = "size=" + size + "&matchingmode=" + matchingmode
				+ "&times=" + times + "&timee=" + timee + "&page=" + page + "&keyword=" + keyword
				+ "&website_name=" + website_name;
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
