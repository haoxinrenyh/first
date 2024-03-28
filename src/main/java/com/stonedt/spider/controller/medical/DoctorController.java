package com.stonedt.spider.controller.medical;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
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
 *	医生
 * @date  2020年2月17日 下午3:43:09
 */
@Controller
@RequestMapping("/doctor")
public class DoctorController {
	
//	private final String es_url = "http://s1.stonedt.com:6303";
//	private final String es_url = UtilConfig.getURL2().substring(0,UtilConfig.getURL2().length()-1);
	@Value("${URL2}")
    private  String es_url;
	
	private final String doctorlist_api = "/doctor/doctorlist";
	
	/**
	 * 	列表
	 */
	@RequestMapping(value = "/doctorlist", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public String thesisnlist(String pageNum, String matchingmode, String searchKeyWord) {
		Map<String, Object> map = new HashMap<>();
		map.put("timefield", "spider_time");
		map.put("size", "10");
		map.put("times", "");
		map.put("timee", "");
		map.put("page", pageNum);
		map.put("index", "stonedt_doctor");
		map.put("type", "infor");
		if(matchingmode!=null) {
			List<ParamUtil> paramList=new ArrayList<ParamUtil>();
			switch (matchingmode) {
			case "姓名":
				paramList.add(new ParamUtil("name", searchKeyWord));
				break;
			case "医院":
				paramList.add(new ParamUtil("hospital", searchKeyWord));
				break;
			case "擅长领域":
				paramList.add(new ParamUtil("adept", searchKeyWord));
				break;
			case "所属科室":
				paramList.add(new ParamUtil("department", searchKeyWord));
				break;
			}
			map.put("and", paramList);
		}
		JSONObject j=new JSONObject(map);
		String response = "";
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, String>> list = new ArrayList<>();
		try {
			String url="http://dx2.stonedt.com:7120/commonsearch/superdatalist";
			response = OverseasInfo.sendPostRaw( j.toJSONString(),url,"utf-8");
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
	 * 医生详情数据
	 * @param article_public_id
	 * @return
	 */
	@RequestMapping(value = "/doctorDetail", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public String doctorDetail(String article_public_id) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("timefield", "spider_time");
		map.put("size", "1");
		map.put("index", "stonedt_doctor");
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
	 * 医生详情视图
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "/doctor-detail", method = RequestMethod.GET)
	public ModelAndView doctorDetail(ModelAndView mv) {
		mv.setViewName("/medicalHealth/doctor-detail");
		return mv;
	}
	
	@RequestMapping(value = "/doctor-page", method = RequestMethod.GET)
	public ModelAndView mainstreamMediaList(ModelAndView mv) {
		mv.setViewName("medicalHealth/doctor");
		return mv;
	}
	
	public String esArticle(Map<String, String> map, String api) {
		String size = map.get("size");
		String times = map.get("times");
		String timee = map.get("timee");
		String page = map.get("page");
		String url = es_url + api;
		String params = "size=" + size+ "&times=" + times + "&timee=" + timee + "&page=" + page ;
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
