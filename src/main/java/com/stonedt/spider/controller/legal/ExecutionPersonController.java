package com.stonedt.spider.controller.legal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
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
import com.stonedt.spider.util.ParamUtil;
import com.stonedt.spider.util.UtilConfig;


/**
 * 	被执行人
 */
@Controller
@RequestMapping("/executionPerson")
public class ExecutionPersonController {
	
//	private final String es_url = "http://s1.stonedt.com:6303";
	//private final String es_url = UtilConfig.getURL2().substring(0,UtilConfig.getURL2().length()-1);
	
	@Value("${URL2}")
    private  String es_url;
	
	private final String executionpersonbygistUnit_api = "/executionperson/executionpersonbygistUnit";
	private final String executionpersonlist_api = "/executionperson/executionpersonlist";
	//private final String lawyerlist_api=UtilConfig.getURL2().substring(0,UtilConfig.getURL2().length()-1)+"/commonsearch/superdatalist";
	@Value("${URL2}")
    private  String lawyerlist_api;
	
	/**
	 * 	筛选
	 */
	@RequestMapping(value = "/executionpersonbygistUnit", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public String decorateLevels() {
		String url = es_url + executionpersonbygistUnit_api;
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
	@RequestMapping(value = "/executionpersonlist", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public String thesisnlist(String pageNum, String matchingmode, String times,
			String timee, String searchkeyword, String gistUnit) {
//		System.out.println(pageNum+"  "+matchingmode+"  "+times+"  "+timee+"  "+searchkeyword+
//				"  "+gistUnit);
//		String times = "", timee = "";
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
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("timefield", "publishDate");
		map.put("size", "50");
		map.put("index", "stonedt_executionperson");
		map.put("type", "infor");
		map.put("page", pageNum);
		if(times!=null) {
			map.put("times", times);
		}
		if(timee!=null) {
			map.put("timee", timee);
		}
		if(matchingmode!=null || gistUnit!=null) {
			List<ParamUtil> not=new ArrayList<>();
			List<ParamUtil> or=new ArrayList<ParamUtil>();
			List<ParamUtil> and=new ArrayList<ParamUtil>();
			if(matchingmode!=null) {
				not.add(new ParamUtil("age", "0"));
				map.put("ont", not);
				switch (matchingmode) {
				case "地区":
					or.add(new ParamUtil("courtName", searchkeyword));
					or.add(new ParamUtil("areaNameNew", searchkeyword));
					map.put("or", or);
					break;
				case "名称":
					and.add(new ParamUtil("iname", searchkeyword));
					map.put("and", and);
					break;
				}
			}
			if(gistUnit!=null) {
				switch (gistUnit) {
				case "企业":
					and.add(new ParamUtil("age", "0"));
					map.put("and", and);
					break;
				case "个人":
					not.add(new ParamUtil("age", "0"));
					map.put("not", not);
					break;
				}
			}
		}
		String response = "";
		JSONObject j=new JSONObject(map);
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			System.err.println(lawyerlist_api);
			response = OverseasInfo.sendPostRaw( j.toJSONString(),lawyerlist_api+"/commonsearch/superdatalist","utf-8");
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
		return JSON.toJSONString(result);
	}
	
	@RequestMapping(value = "/executionPersonDetail", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public String executionPersonDetail(String article_public_id) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("timefield", "publishDate");
		map.put("size", "1");
		map.put("index", "stonedt_executionperson");
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
	
	@RequestMapping(value = "/executionPerson-detail", method = RequestMethod.GET)
	public ModelAndView executionPersonDetail(ModelAndView mv) {
		mv.setViewName("legalDocument/execution-person-detail");
		return mv;
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
// 被执行人	
	@RequestMapping(value = "/execution-person", method = RequestMethod.GET)
	public ModelAndView mainstreamMediaList(ModelAndView mv) {
		mv.addObject("nowArray", DateUtil.getNowDate());
		mv.setViewName("legalDocument/execution-person");
		return mv;
	}
	
//	法律文书
	@RequestMapping(value = "/legal-document", method = RequestMethod.GET)
	public ModelAndView document(ModelAndView mv) {
		mv.addObject("nowArray", DateUtil.getNowDate());
		mv.setViewName("legalDocument/legal-document");
		return mv;
	}
	
//	全国律所
	@RequestMapping(value = "/law-office", method = RequestMethod.GET)
	public ModelAndView office(ModelAndView mv) {
		mv.setViewName("legalDocument/law-office");
		return mv;
	}

	//全国律所列表页
	@RequestMapping(value = "/law-office-list", method = RequestMethod.POST)
	@ResponseBody
	public String lawofficelist(String pageNum, int searchType, String searchkeyword, String time,
			String gender) {
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
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("size", "15");
		map.put("index", "stonedt_lawfirm");
		map.put("type", "infor");
		map.put("times", "");
		map.put("timee", "");
		map.put("page", pageNum);
		map.put("timefield", "spider_time");
		JSONArray orArr=new JSONArray();
		if(searchType==0 && searchkeyword!=null && searchkeyword!="") {
			JSONObject json2=new JSONObject();
        	json2.put("field", "lawfirm");
            json2.put("keyword", searchkeyword);
            orArr.add(json2);
            JSONObject json3=new JSONObject();
        	json3.put("field", "resperson");
            json3.put("keyword", searchkeyword);
            orArr.add(json3);
            JSONObject json4=new JSONObject();
        	json4.put("field", "lawcity");
            json4.put("keyword", searchkeyword);
            orArr.add(json4);
            map.put("or", orArr);
		}else if(searchType==1 && searchkeyword!=null && searchkeyword!="") {
			JSONObject json2=new JSONObject();
        	json2.put("field", "lawfirm");
            json2.put("keyword", searchkeyword);
            orArr.add(json2);
            map.put("and", orArr);
		}else if(searchType==2 && searchkeyword!=null && searchkeyword!="") {
			JSONObject json2=new JSONObject();
        	json2.put("field", "resperson");
            json2.put("keyword", searchkeyword);
            orArr.add(json2);
            map.put("and", orArr);
		}else if(searchType==3 && searchkeyword!=null && searchkeyword!="") {
			JSONObject json2=new JSONObject();
        	json2.put("field", "lawcity");
            json2.put("keyword", searchkeyword);
            orArr.add(json2);
            map.put("and", orArr);
		}
		JSONObject j=new JSONObject(map);
		System.err.println(j.toString());
		String response = "";
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			response = OverseasInfo.sendPostRaw(j.toJSONString(), lawyerlist_api,"utf-8");
			//response = OverseasInfo.getJsonHtml(lawyerlist_api, j);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("list", list);
			result.put("totalData", 0);
			result.put("totalPage", 1);
		}
		if (StringUtils.isNotBlank(response)) {
			JSONObject parseObject = JSON.parseObject(response);
			//System.err.println(response);
			String totalData = parseObject.getString("count");
			String totalPage = parseObject.getString("page_count");
			JSONArray jsonArray = parseObject.getJSONArray("data");
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				JSONObject jsonObject2 = jsonObject.getJSONObject("_source");
				Map<String, String> params = JSONObject.parseObject(jsonObject.toJSONString(), new TypeReference<Map<String, String>>(){});
				params.put("content", params.get("_source"));
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
	
	
//	全国律师
	@RequestMapping(value = "/lawyer", method = RequestMethod.GET)
	public ModelAndView lawyer(ModelAndView mv) {
		mv.setViewName("legalDocument/lawyer");
		return mv;
	}
// 全国律师列表
	@RequestMapping(value = "/lawyerlist", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public String lawyerlist(String pageNum, String matchingmode, String times,
			String timee, String searchkeyword, String kinds) {
//		System.out.println(times);
//		System.out.println(timee);
//		System.out.println(matchingmode+"   "+searchkeyword);
//		String times = "", timee = "";
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
		JSONObject j = new JSONObject();
		j.put("size", "15");
		j.put("index", "stonedt_law");
		j.put("type", "infor");
		j.put("page", pageNum);
		j.put("timefield", "qualifitime");
		if(times!=null) {
			j.put("times", times);
		}
		if(timee!=null) {
			j.put("timee", timee);
		}
		JSONArray andarray = new JSONArray();
		if(matchingmode!=null || kinds!=null) {
			JSONObject param = new JSONObject();
			if(matchingmode!=null) {
				switch (matchingmode) {
				case "姓名":
					param.put("keyword", searchkeyword);
					param.put("field", "name");
					andarray.add(param);
					break;
				case "律所名称":
					param.put("keyword", searchkeyword);
					param.put("field", "lawfirm");
					andarray.add(param);
					break;
				case "擅长领域":
					param.put("keyword", searchkeyword);
					param.put("field", "goods");
					andarray.add(param);
					break;
				case "城市":
					param.put("keyword", searchkeyword);
					param.put("field", "city");
					andarray.add(param);
					break;
				}
			}
			if(kinds!=null) {
				JSONObject param2 = new JSONObject();
				param2.put("keyword", kinds);
				param2.put("field", "kinds");
				andarray.add(param2);
			}
			j.put("and", andarray);
		}
		System.out.println(j);
		String response = "";
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			response = OverseasInfo.sendPostRaw( j.toJSONString(),lawyerlist_api,"utf-8");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("list", list);
			result.put("totalData", 0);
			result.put("totalPage", 1);
		}
		System.out.println(response);
		if (StringUtils.isNotBlank(response)) {
			JSONObject parseObject = JSON.parseObject(response);
			String totalData = parseObject.getString("count");
			String totalPage = parseObject.getString("page_count");
			JSONArray jsonArray = parseObject.getJSONArray("data");
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				JSONObject jsonObject2 = jsonObject.getJSONObject("_source");
				Map<String, String> params = JSONObject.parseObject(jsonObject.toJSONString(), new TypeReference<Map<String, String>>(){});
				params.put("content", params.get("_source"));
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
	
	@RequestMapping(value = "/lawyerDetail", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public String lawyerDetail(String article_public_id) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("timefield", "qualifitime");
		map.put("size", "1");
		map.put("index", "stonedt_law");
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
	 * 律师详情视图
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "/lawyer-detail", method = RequestMethod.GET)
	public ModelAndView lawyerDetail(ModelAndView mv) {
		mv.setViewName("/legalDocument/lawyer-detail");
		return mv;
	}
	
	public String esArticle(Map<String, Object> map, String api) {
		String size = (String) map.get("size");
		String matchingmode = (String) map.get("matchingmode");
		String times = (String) map.get("times");
		String timee = (String) map.get("timee");
		String page = (String) map.get("page");
		String keyword = (String) map.get("keyword");
		String gistUnit = (String) map.get("gistUnit");
		String url = es_url + api;
		String params = "size=" + size + "&matchingmode=" + matchingmode
				+ "&times=" + times + "&timee=" + timee + "&page=" + page + "&keyword=" + keyword
				+ "&gistUnit=" + gistUnit;
		params = params.replaceAll("null", "");
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
	
	
//	法律文书
	@RequestMapping(value = "/legal-data", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject legal(
			@RequestParam(value = "page", defaultValue = "1" ,required = false)Integer page,
			@RequestParam(value = "searchType")Integer searchType,
			@RequestParam(value = "searchkeyword")String searchkeyword) {
		JSONObject json=new JSONObject();
		try {
			JSONObject end_json=new JSONObject();
			Integer count =0;
			Integer page_count =0;
			Integer size =0;
		    end_json.put("timefield", "spider_time");
		    end_json.put("times", "");
		    end_json.put("timee", "");
		    end_json.put("index", "stonedt_judgment");
		    end_json.put("type", "infor");
		    end_json.put("page", page);
		    end_json.put("size", "10");
		    JSONArray orArr=new JSONArray();
		    if(searchType==0 && searchkeyword!=null && searchkeyword!="") {
		    	JSONObject json1=new JSONObject();
            	json1.put("field", "title");
	            json1.put("keyword", searchkeyword);
	            orArr.add(json1);
	            JSONObject json2=new JSONObject();
            	json2.put("field", "partyNameA");
	            json2.put("keyword", searchkeyword);
	            orArr.add(json2);
	            JSONObject json3=new JSONObject();
            	json3.put("field", "partyNameB");
	            json3.put("keyword", searchkeyword);
	            orArr.add(json3);
	            JSONObject json4=new JSONObject();
            	json4.put("field", "lawNameA");
	            json4.put("keyword", searchkeyword);
	            orArr.add(json4);
	            JSONObject json5=new JSONObject();
            	json5.put("field", "lawNameB");
	            json5.put("keyword", searchkeyword);
	            orArr.add(json5);
	            JSONObject json6=new JSONObject();
            	json6.put("field", "judgeName");
	            json6.put("keyword", searchkeyword);
	            orArr.add(json6);
	            end_json.put("or", orArr);
		    }else if(searchType==1 && searchkeyword!=null && searchkeyword!="") {
		    	JSONObject json2=new JSONObject();
            	json2.put("field", "title");
	            json2.put("keyword", searchkeyword);
	            orArr.add(json2);
	            end_json.put("and", orArr);
		    }else if(searchType==2 && searchkeyword!=null && searchkeyword!="") {
		    	JSONObject json2=new JSONObject();
            	json2.put("field", "partyNameA");
	            json2.put("keyword", searchkeyword);
	            orArr.add(json2);
	            JSONObject json3=new JSONObject();
            	json3.put("field", "partyNameB");
	            json3.put("keyword", searchkeyword);
	            orArr.add(json3);
	            JSONObject json4=new JSONObject();
            	json4.put("field", "lawNameA");
	            json4.put("keyword", searchkeyword);
	            orArr.add(json4);
	            JSONObject json5=new JSONObject();
            	json5.put("field", "lawNameB");
	            json5.put("keyword", searchkeyword);
	            orArr.add(json5);
	            JSONObject json6=new JSONObject();
            	json6.put("field", "judgeName");
	            json6.put("keyword", searchkeyword);
	            orArr.add(json6);
	            end_json.put("or", orArr);
		    }
		    System.err.println(end_json.toString());
		    String url = UtilConfig.getURL2().substring(0,UtilConfig.getURL2().length()-1)+ElasticSearchUtil.superdatalist;
	        //String responseData = getJsonHtml(url,"",end_json);
		    String responseData = OverseasInfo.sendPostRaw(end_json.toJSONString(),url,"utf-8");
	        json = JSONObject.parseObject(responseData);
	        JSONArray news=json.getJSONArray("data");
            count=Integer.valueOf(json.get("count").toString());
			page_count=Integer.valueOf(json.get("page_count").toString());
			size=Integer.valueOf(json.get("size").toString());
			json.put("news",news);
			json.put("count",count);
			json.put("page_count",page_count);
			json.put("page",page);
			json.put("size",size);
            //System.err.println(responseData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	
//	法律文书详情页面
	@RequestMapping(value = "/legal-details", method = RequestMethod.GET)
	public ModelAndView document_details(ModelAndView mav,String article_public_id,HttpServletRequest req) {
	            String url = UtilConfig.getURL2().substring(0,UtilConfig.getURL2().length()-1)+ElasticSearchUtil.getcommondatadetail;
	            JSONObject post_json=new JSONObject();
		            post_json.put("index","stonedt_judgment");
		            post_json.put("type","infor");
		            post_json.put("fieldname","article_public_id");
		            post_json.put("fielddata",article_public_id);
	            String responseData="";
				try {
					responseData = getJsonHtml(url,"",post_json);
				} catch (Exception e) {
					e.printStackTrace();
				}
				JSONObject returnObj = JSON.parseObject(responseData);
	            String content="";
	            for(int i=1;i<12;i++) {
	            	String estend="extend"+i;
	            	content+=returnObj.getString(estend);
	            	returnObj.remove(estend);
	            }
	            returnObj.put("content", content);
	    req.setAttribute("LEFT", "legal-document");
	    mav.addObject("legalData", returnObj);
		mav.setViewName("legalDocument/legal-details");
		return mav;
	}
	

	
	
    public static String getJsonHtml(String url,String Referer, JSONObject jsonObject) throws Exception {
		System.err.println(url+jsonObject.toString());
		String result ="";
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		StringEntity postingString = new StringEntity(jsonObject.toString());// json传递
		post.setEntity(postingString);
		post.setHeader("Referer", Referer);
		post.setHeader("Content-type", "application/json");
		HttpResponse response = httpClient.execute(post);
		String content = EntityUtils.toString(response.getEntity());
		// Log.i("test",content);
		result = content;
		return result;
	}
}
