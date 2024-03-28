package com.stonedt.spider.controller.securities;

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

import javax.servlet.http.HttpServletRequest;

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
import com.stonedt.spider.util.UtilConfig;

/** 

* @author 作者 ZouFangHao: 

* @version 创建时间：2020年2月18日 下午4:24:33 

* 类说明 

*/
@Controller
@RequestMapping("/researchReport")
public class ResearchReportController {

	//private final String es_url = UtilConfig.getURL2().substring(0,UtilConfig.getURL2().length()-1);
//	private final String es_url = "http://s1.stonedt.com:6303";
	@Value("${URL2}")
    private  String es_url;
	private final String yanbao_api = "/researchreport/researchreportlist";
	
	@RequestMapping(value = "/research-report", method = RequestMethod.GET)
	public ModelAndView research(ModelAndView mv) {
		mv.setViewName("securitiesCompany/research-report");
		return mv;
	}
	//跳转研报数据详情
	@RequestMapping(value = "/research-report-detail", method = RequestMethod.GET)
	public ModelAndView researchDetail(HttpServletRequest request,ModelAndView mv,String article_public_id,String type) {
		request.setAttribute("article_public_id", article_public_id);
		request.setAttribute("type", type);
		if(type.equals("research")) {
			mv.addObject("LEFT", "yanbao");
			mv.addObject("webName", "研报数据");
		}else if(type.equals("announcement")) {
			mv.addObject("LEFT", "announcement");
			mv.addObject("webName", "公告数据");
		}
		mv.setViewName("securitiesCompany/research-report-detail");
		return mv;
	}
	//获取详情数据
	@RequestMapping(value = "/getresearch-report-detail", method = RequestMethod.POST)
	@ResponseBody
	public String  getDetail(HttpServletRequest request,String type,String article_public_id) {
		String url=UtilConfig.getURL2().substring(0,UtilConfig.getURL2().length()-1)+"/commonsearch/getcommondatadetail";
		JSONObject json=new JSONObject();
		if(type.equals("research")) {
			json.put("index", "stonedt_researchreport");
			
		}else if(type.equals("announcement")) {
			json.put("index", "stonedt_announcement");
			
		}
		json.put("type", "infor");
		json.put("fieldname", "article_public_id");
		json.put("fielddata", article_public_id);
		String data="";
		try {
			data=OverseasInfo.getJsonHtml(url, json);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
	
	@RequestMapping("/report")
	@ResponseBody
	public String get(String pageNum) {
		Map<String, String> map = new HashMap<>();
		map.put("size", "25");
		map.put("times", "");
		map.put("timee", "");
		map.put("page", pageNum);
		String response = "";
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, String>> list = new ArrayList<>();
		try {
			response = esArticle(map, yanbao_api);
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
		System.out.println(JSON.toJSONString(result));
		return JSON.toJSONString(result);
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
