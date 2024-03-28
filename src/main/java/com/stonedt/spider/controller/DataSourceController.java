package com.stonedt.spider.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.stonedt.spider.util.DataMonitorVO;
import com.stonedt.spider.util.DataSourceUtil;
import com.stonedt.spider.util.ElasticSearchUtil;
import com.stonedt.spider.util.UtilConfig;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @date  2019年12月13日 下午5:20:12
 */
@RestController
@RequestMapping("/data-source")
public class DataSourceController extends BaseController {
	
	/**
	 * 站点列表
	 */
	@GetMapping(value = "/article", produces = "application/json;charset=utf-8")
	@ResponseBody
	public String article(String classify, Integer pageNum) {
		Map<String, String> map = new HashMap<>();
		map.put("size", "10");
		map.put("page", String.valueOf(pageNum));
		map.put("keyword", "");
		map.put("stopword", "");
		map.put("searchkeyword", "");
		map.put("origintype", "");
		map.put("classify", classify);
		map.put("emotionalIndex", "");
		map.put("searchType", "1");
		map.put("times", "");
		map.put("timee", "");
		map.put("matchingmodeType", "");
		map.put("precisionType", "");
		map.put("city", "");
		map.put("province", "");
		String response = esArticle(map, "/yqt/qbsearchcontent");
		Map<String, Object> result = new HashMap<String, Object>();
		List<DataMonitorVO> list = new ArrayList<DataMonitorVO>();
		JSONObject parseObject = JSON.parseObject(response);
		String totalData = parseObject.getString("count");
		String totalPage = parseObject.getString("page_count");
		JSONArray jsonArray = parseObject.getJSONArray("data");
		int size = jsonArray.size();
		for (int i = 0; i < size; i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			JSONObject jsonObject2 = jsonObject.getJSONObject("_source");
			DataMonitorVO dataMonitorVO = JSON.toJavaObject(jsonObject2, DataMonitorVO.class);
			list.add(dataMonitorVO);
		}
		if (Integer.parseInt(totalData) > 5000) {
			totalData = "5000";
			totalPage = "500";
		}
		String parm="";
		try {
			parm="classify="+classify;
		} catch (Exception e) {
			e.printStackTrace();
		}
		String sendPost="";
		try {
			sendPost=ElasticSearchUtil.sendPost("/media/sourcewebsitesearch?", parm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject json=new JSONObject();
		json=JSONObject.parseObject(sendPost);
		JSONArray level=json.getJSONObject("aggregations").getJSONObject("group_by_tags").getJSONArray("buckets");
		result.put("level", level);
		result.put("list", list);
		result.put("totalData", totalData);
		result.put("totalPage", totalPage);
		return JSON.toJSONString(result);
	}
	
	/**
	 * 数据源 分类及数量
	 */
	@RequestMapping(value = "/dataSourceCount", produces = "application/json;charset=utf-8")
	@ResponseBody
	public String dataSource() {
		Map<String, String> map = new HashMap<>();
		map.put("keyword", "");
		map.put("emotionalIndex", "");
		map.put("times", "");
		map.put("timee", "");
		map.put("matchingmode", "");
		map.put("stopword", "");
		map.put("matchingmodeType", "");
		map.put("precisionType", "");
		map.put("province", "");
		map.put("city", "");
		String response = esDataSource(map, "/yqt/sourcewebsitesearch");
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		JSONObject parseObject = JSON.parseObject(response);
		JSONArray jsonArray = parseObject.getJSONObject("aggregations").getJSONObject("group_by_tags").getJSONArray("buckets");
		String total = parseObject.getJSONObject("hits").getString("total");
		int size = jsonArray.size();
		List<String> list2 = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			Map<String, Object> map1 = new HashMap<String, Object>();
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			String key = jsonObject.getString("key");
			String doc_count = jsonObject.getString("doc_count");
			String name = DataSourceUtil.dataSourceClassification(key);
			map1.put("key", key);
			map1.put("name", name);
			map1.put("doc_count", doc_count);
			list.add(map1);
			list2.add(key);
		}
		result.put("list", list);
		result.put("all_count", total);
		return JSON.toJSONString(result);
	}
	
	@RequestMapping(value = "/datasource", method = RequestMethod.GET)
	public ModelAndView website_addtactics(@RequestParam(value = "pageNum", defaultValue = "1" ,required = false)Integer pageNum,
			ModelAndView mv,HttpServletRequest request) {
		mv.setViewName("data_source");
		return mv;
	}

	public String esDataSource(Map<String, String> map, String api){
		String keyword = map.get("keyword");
		String emotionalIndex = map.get("emotionalIndex");
		String times = map.get("times");
		String timee = map.get("timee");
		String stopword = map.get("stopword");
		String city = map.get("city");
		if(city != null){
				city = city.replaceFirst("市", "").replaceFirst("自治州", "")
						.replaceFirst("地区", "");
		}
		String province = map.get("province");
		if(province !=null){
			province = province.replaceFirst("市", "").replaceFirst("省", "")
					.replaceFirst("自治区", "").replaceFirst("特别行政区", "");
		}
		String matchingmodeType = map.get("matchingmodeType");
		String url = UtilConfig.getURL().substring(0,UtilConfig.getURL().length()-1) + api;
		String params = "keyword=" + keyword + "&emotionalIndex=" + emotionalIndex + "&times=" + times
				+ "&timee=" + timee + "&matchingmode=" + matchingmodeType + "&stopword=" + stopword + "&province=" +province
				+"&city=" +city;
		System.err.println(url + "?" + params);
		String sendPost = "";
		try {
			sendPost = sendPost(url, params);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return sendPost;
	}
	
	//获取es文章数据列表
		public String esArticle(Map<String, String> map, String api) {
			String size = map.get("size");
			String page = map.get("page");
			String keyword = map.get("keyword");
			String stopword = map.get("stopword");
			String searchkeyword = map.get("searchkeyword");
			String origintype = map.get("origintype");
			String classify = map.get("classify");
			String emotionalIndex = map.get("emotionalIndex");
			String searchType = map.get("searchType");
			String times = map.get("times");
			String timee = map.get("timee");
			String city = map.get("city");
			String province = map.get("province");
			String matchingmodeType = map.get("matchingmodeType");
			String url = UtilConfig.getURL().substring(0,UtilConfig.getURL().length()-1) + api;
			String params = "size=" + size + "&page=" + page + "&keyword=" + keyword + "&stopword=" + stopword
					+ "&searchkeyword=" + searchkeyword + "&origintype=" + origintype + "&classify=" + classify
					+ "&emotionalIndex=" + emotionalIndex + "&searchType=" + searchType
					+ "&times=" + times + "&timee=" + timee + "&matchingmode=" + matchingmodeType + "&province=" +province
					+"&city=" +city ;
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
	
	/***
	 * 
	 * @param keyword 用户搜索内容
	 * @param time	时间筛选
	 * @param sitename	各个网站筛选
	 * @return
	 */
	@RequestMapping("lowersite")
	@ResponseBody
	public String lowersite(@RequestParam(defaultValue="1",required=false,name="pageNum") Integer pageNum,
			@RequestParam(defaultValue="",required=false,name="keyword") String keyword,
			@RequestParam(defaultValue="",required=false,name="time") Integer time,
			@RequestParam(defaultValue="",required=false,name="sitename") String sitename) {
		System.out.println(keyword+"--------"+time+"----------"+sitename+"----------"+pageNum);
		StringBuffer unicode = new StringBuffer();
        for (int i = 0; i < sitename.length(); i++) {
            char c = sitename.charAt(i);  // 取出每一个字符
            unicode.append("\\u" +Integer.toHexString(c));// 转换为unicode
        }
        StringBuffer code = new StringBuffer();
        for (int i = 0; i < keyword.length(); i++) {
            char c = keyword.charAt(i);  // 取出每一个字符
            code.append("\\u" +Integer.toHexString(c));// 转换为unicode
        }
		String parsm="page="+pageNum+"&searchType=1";
		Date date=new Date();
		SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//现在时间
		String times=sim.format(date);
		//时间筛选
		String Timescreen="";
		try {
			if(sitename !="" && sitename!=null) {
				parsm=parsm+"&source_name="+unicode.toString();
			}
			if(keyword !="" && keyword!=null) {
				parsm=parsm+"&keyword="+code.toString();
			}
			if(String.valueOf(time)!="" && time!=null){
				if(time.equals("1") || time==1) {
					long a=date.getTime()-86400000;
					Date b=new Date(a);
					//前一天时间
					Timescreen = sim.format(b);
					parsm=parsm+"&times="+Timescreen+"&timee="+times;
				}else if(time.equals("2") || time==2){
					long a=date.getTime()-604800000;
					Date b=new Date(a);
					//前一周时间
					Timescreen = sim.format(b);
					parsm=parsm+"&times="+Timescreen+"&timee="+times;
				}else if(time.equals("3") || time==3) {
					long a=date.getTime()-2592000000L;
					Date b=new Date(a);
					//前一月时间
					Timescreen = sim.format(b);
					parsm=parsm+"&times="+Timescreen+"&timee="+times;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String sendPost="";
		try {
			sendPost=ElasticSearchUtil.sendPost("yqt/qbsearchcontent?", parsm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject json=new JSONObject();
		json=JSONObject.parseObject(sendPost);
		return JSON.toJSONString(json);
		
	}
}
