package com.stonedt.spider.controller.professor;

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
import com.stonedt.spider.util.ParamUtil;
import com.stonedt.spider.util.UtilConfig;

/**
 * 	人才库
 * @date  2020年2月6日 下午2:43:45
 */
@Controller
@RequestMapping("/talentsDataBase")
public class TalentsDataBaseController {
	
//	private final String es_url = "http://s1.stonedt.com:6303";
//	private final String es_url = UtilConfig.getURL2().substring(0,UtilConfig.getURL2().length()-1);
	@Value("${URL2}")
    private  String es_url;
	
	private final String talentsdatabasebyachievements_api = "/talentsdatabase/talentsdatabasebyachievements";
	private final String talentsdatabaselist_api = "/talentsdatabase/talentsdatabaselist";
	private final String gettalentsdatabasedetail_api =  "/talentsdatabase/gettalentsdatabasedetail";
	
	/**
	 * 	成就筛选
	 */
	@RequestMapping(value = "/talentsdatabasebyachievements", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public String decorateLevels() {
		String url = es_url + talentsdatabasebyachievements_api;
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
	 * 	专家人才列表
	 */
	@RequestMapping(value = "/talentsdatabaselist", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public String thesisnlist(String pageNum, String matchingmode, String searchkeyword,
			String achievements) {
//		System.out.println(pageNum+"  "+matchingmode+"  "+searchkeyword);
		String times = "", timee = "";
//		if (StringUtils.isNotBlank(time)) {
//			if ("24h".equals(time)) {
//				JSONObject recruitmentMonitorStr = recruitmentMonitorStr(1);
//				times = recruitmentMonitorStr.getString("times");
//				timee = recruitmentMonitorStr.getString("timee");
//			}
//			if ("7d".equals(time)) {
//				JSONObject recruitmentMonitorStr = recruitmentMonitorStr(7);
//				times = recruitmentMonitorStr.getString("times");
//				timee = recruitmentMonitorStr.getString("timee");
//			}
//			if ("30d".equals(time)) {
//				JSONObject recruitmentMonitorStr = recruitmentMonitorStr(30);
//				times = recruitmentMonitorStr.getString("times");
//				timee = recruitmentMonitorStr.getString("timee");
//			}
//		}
		Map<String, Object> map = new HashMap<>();
		map.put("timefield", "spider_time");
		map.put("size", "50");
		map.put("index", "stonedt_baidutalentdatabase");
		map.put("type", "infor");
		map.put("times", times);
		map.put("timee", timee);
		map.put("page", pageNum);
		if(matchingmode!=null) {
			List<ParamUtil> and=new ArrayList<ParamUtil>();
			switch (matchingmode) {
			case "姓名":
				and.add(new ParamUtil("title", searchkeyword));
				break;
			case "研究领域":
				and.add(new ParamUtil("field", searchkeyword));
				break;
			case "机构":
				and.add(new ParamUtil("institution", searchkeyword));
				break;
			}
			map.put("and", and);
		}
		JSONObject j=new JSONObject(map);
		System.out.println(j);
		String response = "";
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, String>> list = new ArrayList<>();
		try {
			String url = UtilConfig.getURL2().substring(0,UtilConfig.getURL2().length()-1)+"/commonsearch/superdatalist";
			System.out.println(url);
			System.out.println(j.toJSONString());
			response = OverseasInfo.sendPostRaw(j.toJSONString(),url,"utf-8");
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
	 * 	中科院士列表
	 */
	@RequestMapping(value = "/academicianlist", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public String academicianist(String pageNum, String matchingmode, String time, String searchkeyword,
			String achievements) {
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
		map.put("achievements", achievements);
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
			response = esArticle(map, talentsdatabaselist_api);
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
	 * 	专家人才详情
	 */
	@RequestMapping(value = "/talentDetail", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public String talentDetail(String article_public_id) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("timefield", "spider_time");
		map.put("size", "1");
		map.put("index", "stonedt_baidutalentdatabase");
		map.put("page", "1");
		map.put("type", "infor");
		map.put("times", "");
		map.put("timee", "");
		List<ParamUtil> paramList=new ArrayList<ParamUtil>();
		paramList.add(new ParamUtil("article_public_id", article_public_id));
		map.put("and", paramList);
		JSONObject j=new JSONObject(map);
		System.out.println(j);
		String response = "";
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, String>> list = new ArrayList<>();
		try {
			String url = "http://dx2.stonedt.com:7120/commonsearch/superdatalist";
//			System.out.println(url);
			response = OverseasInfo.sendPostRaw(j.toJSONString(),url,"utf-8");
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
	 * 	中科院士详情
	 */
	@RequestMapping(value = "/academicianDetail", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public String academicianDetail(String article_public_id) {
		String url = es_url + gettalentsdatabasedetail_api;
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
	
	@RequestMapping(value = "/talents-dataBase", method = RequestMethod.GET)
	public ModelAndView mainstreamMediaList(ModelAndView mv) {
		mv.setViewName("professorTalents/talents-dataBase");
		return mv;
	}
	
	@RequestMapping(value = "/talents-dataBase-detail", method = RequestMethod.GET)
	public ModelAndView aaa(ModelAndView mv) {
		mv.setViewName("professorTalents/talents-dataBase-detail");
		return mv;
	}
	@RequestMapping(value = "/academician-detail", method = RequestMethod.GET)
	public ModelAndView academicianDetail(ModelAndView mv) {
		mv.setViewName("professorTalents/academician-detail");
		return mv;
	}
//	学术院士
	@RequestMapping(value = "/academician", method = RequestMethod.GET)
	public ModelAndView academician(ModelAndView mv) {
		mv.setViewName("professorTalents/academician");
		return mv;
	}	
	public String esArticle(Map<String, String> map, String api) {
		String size = map.get("size");
		String matchingmode = map.get("matchingmode");
		String times = map.get("times");
		String timee = map.get("timee");
		String page = map.get("page");
		String keyword = map.get("keyword");
		String achievements = map.get("achievements");
		String url = es_url + api;
		String params = "size=" + size + "&matchingmode=" + matchingmode
				+ "&times=" + times + "&timee=" + timee + "&page=" + page + "&keyword=" + keyword
				+ "&achievements=" + achievements;
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
