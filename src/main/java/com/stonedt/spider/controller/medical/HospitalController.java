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

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.logging.log4j2.Log4j2AbstractLoggerImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.stonedt.spider.controller.overseas.OverseasInfo;
import com.stonedt.spider.util.DateUtil;
import com.stonedt.spider.util.ElasticSearchUtil;
import com.stonedt.spider.util.UtilConfig;

/**
 * 	全国医院
 */
@Controller
@RequestMapping("/hospital")
public class HospitalController {
	
//	private final String es_url = "http://s1.stonedt.com:6303";
	//private final String es_url = UtilConfig.getURL2().substring(0,UtilConfig.getURL2().length()-1);
	@Value("${URL2}")
    private  String es_url;
	
	private final String hospitallist_api = "/hospital/hospitallist";
	
	/**
	 * 	列表
	 */
	@RequestMapping(value = "/hospitallist", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public String thesisnlist(String pageNum,int searchType,String searchkeyword) {
		
//		Map<String, String> map = new HashMap<>();
//		map.put("size", "10");
//		map.put("times", "");
//		map.put("timee", "");
//		map.put("page", pageNum);
//		map.put("matchingmode", matchingmode);
//		map.put("keyword", keyword);
//		System.out.println(map.toString());
		
		JSONArray array=new JSONArray();
		JSONArray Array=new JSONArray();
		JSONObject mapJson = new JSONObject();
		mapJson.put("timefield","spider_time");
		mapJson.put("times","");
		mapJson.put("size","10");
		mapJson.put("index","stonedt_hospital");
		mapJson.put("page", pageNum);
		mapJson.put("type","infor");
		mapJson.put("timee","");
        
        if(searchkeyword!=null && searchkeyword!="") {
        	if(searchType==1) {//医院名
        		JSONObject json2=new JSONObject();
        		json2.put("field", "hospital_name");
        		json2.put("keyword", searchkeyword);
                array.add(json2);
        		mapJson.put("and",array);
        	}else if(searchType==2) {//医院简介
        		JSONObject json2=new JSONObject();
        		json2.put("field", "abstract");
        		json2.put("keyword", searchkeyword);
                array.add(json2);
        		mapJson.put("and",array);
        	}else {
        		JSONObject json2=new JSONObject();
        		json2.put("field", "hospital_name");
        		json2.put("keyword", searchkeyword);
        		Array.add(json2);
                JSONObject json3=new JSONObject();
        		json3.put("field", "abstract");
        		json3.put("keyword", searchkeyword);
                Array.add(json3);
                mapJson.put("or",Array);
        	}
        }
		System.err.println(mapJson.toString());
		String response = "";
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, String>> list = new ArrayList<>();
		try {
			String url = "http://dx2.stonedt.com:7120/commonsearch/superdatalist";
			response =  OverseasInfo.sendPostRaw(mapJson.toJSONString(),url,"utf-8");
			//response = esArticle(map, hospitallist_api);
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
	
	@RequestMapping(value = "/hospital-page", method = RequestMethod.GET)
	public ModelAndView mainstreamMediaList(ModelAndView mv) {
		mv.setViewName("medicalHealth/hospital");
		return mv;
	}
	
	public String esArticle(Map<String, String> map, String api) {
		String size = map.get("size");
		String times = map.get("times");
		String timee = map.get("timee");
		String page = map.get("page");
		String url = es_url + api;
		String params = "size=" + size+ "&times=" + times + "&timee=" + timee + "&page=" + page;
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
	
	
	
	@RequestMapping("/drug")
	public ModelAndView RedBook(ModelAndView mav) {
		mav.addObject("nowArray", DateUtil.getNowDate());
		mav.setViewName("medicalHealth/drug");
		return mav;
	}
	
	
	@RequestMapping(value="/drug-data",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject RedBookData(
			@RequestParam(value = "page", defaultValue = "1" ,required = false)Integer page,
			@RequestParam Integer searchType,
			@RequestParam String searchkeyword) {
		JSONObject json=new JSONObject();
		try {
			String response="";
//			String param="";
//			param="?times=&timee=&page="+page+"&size=10";
			JSONArray array=new JSONArray();
			JSONArray Array=new JSONArray();
			JSONObject mapJson = new JSONObject();
			mapJson.put("timefield","spider_time");
			mapJson.put("times","");
			mapJson.put("size","10");
			mapJson.put("index","stonedt_drugs");
			mapJson.put("page", page);
			mapJson.put("type","infor");
			mapJson.put("timee","");
	        
	        if(searchkeyword!=null && searchkeyword!="") {
	        	if(searchType==1) {//医院名
	        		JSONObject json2=new JSONObject();
	        		json2.put("field", "name");
	        		json2.put("keyword", searchkeyword);
	                array.add(json2);
	        		mapJson.put("and",array);
	        	}else if(searchType==2) {//医院简介
	        		JSONObject json2=new JSONObject();
	        		json2.put("field", "major_function");
	        		json2.put("keyword", searchkeyword);
	                array.add(json2);
	        		mapJson.put("and",array);
	        	}else {
	        		JSONObject json2=new JSONObject();
	        		json2.put("field", "name");
	        		json2.put("keyword", searchkeyword);
	        		Array.add(json2);
	                JSONObject json3=new JSONObject();
	        		json3.put("field", "major_function");
	        		json3.put("keyword", searchkeyword);
	                Array.add(json3);
	                mapJson.put("or",Array);
	        	}
	        }
			System.err.println(mapJson.toString());
			
			try {
				String url = "http://dx2.stonedt.com:7120/commonsearch/superdatalist";
				response =  OverseasInfo.sendPostRaw(mapJson.toJSONString(),url,"utf-8");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			Integer count =0;
			Integer page_count =0;
			Integer size =0;
				json = JSONObject.parseObject(response);
				JSONArray news=json.getJSONArray("data");
				count=Integer.valueOf(json.get("count").toString());
				page_count=Integer.valueOf(json.get("page_count").toString());
				size=Integer.valueOf(json.get("size").toString());
			json.put("news",news);
			json.put("count",count);
			json.put("page_count",page_count);
			json.put("page",page);
			json.put("size",size);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	/***
	 * 医疗资讯
	 * @param mav
	 * @param request
	 * @param type
	 * @return
	 */
	@RequestMapping("/medicalinformation")
	public ModelAndView medicalinformation(ModelAndView mav,HttpServletRequest request,
			@RequestParam(value = "type", defaultValue = "" ,required = false)String type) {
		mav.setViewName("medicalHealth/medical_information");
		mav.addObject("webName", "医疗资讯");
		mav.addObject("nowArray", DateUtil.getNowDate());
		request.setAttribute("LEFT", type);
		return mav;
	}

	
	@RequestMapping(value="/medicalinformation-data",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject medicalinformation_data(
			@RequestParam(value = "page", defaultValue = "1" ,required = false)Integer page) {
		JSONObject json=new JSONObject();
		try {
			String response="";
			String param="";
			param="?&page="+page+"&size=10&otherseedid=23235,23234,23236,23237&searchType=1";	
			Integer count =0;
			Integer page_count =0;
			Integer size =0;
				response = ElasticSearchUtil.sendPost("yys/qbsearchcontent", param);
				json = JSONObject.parseObject(response);
				JSONArray news=json.getJSONArray("data");
				count=Integer.valueOf(json.getString("count"));
				page_count=Integer.valueOf(json.getString("page_count"));
				size=Integer.valueOf(json.getString("size"));
			json.put("news",news);
			json.put("count",count);
			json.put("page_count",page_count);
			json.put("page",page);
			json.put("size",size);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
}
