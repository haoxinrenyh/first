package com.stonedt.spider.controller.social;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
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
import com.stonedt.spider.controller.legal.ExecutionPersonController;
import com.stonedt.spider.util.DataMonitorVO;
import com.stonedt.spider.util.DateUtil;
import com.stonedt.spider.util.ElasticSearchUtil;
import com.stonedt.spider.util.UtilConfig;

/**
 * 社交媒体
 * @date  2020年2月6日 上午10:14:45
 */
@Controller
@RequestMapping("/socialMedia")
public class SocialMediaController {
	
//	private final String es_url = "http://192.168.71.63:6302";
//	private final String es_url = UtilConfig.getURL().substring(0,UtilConfig.getURL().length()-1);
	@Value("${URL2}")
    private  String es_url;
	
	private final String article_api = "/yqt/qbsearchcontent";
	
	/**
	 * 文章列表
	 */
	@RequestMapping(value = "/article", produces = "application/json;charset=utf-8")
	@ResponseBody
	public String article(Integer pageNum, String sourceName, String time, String searchkeyword, String seedId) {
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
			if ("15d".equals(time)) {
				JSONObject recruitmentMonitorStr = recruitmentMonitorStr(15);
				times = recruitmentMonitorStr.getString("times");
				timee = recruitmentMonitorStr.getString("timee");
			}
		}
		Map<String, String> map = new HashMap<>();
		map.put("size", "10");
		map.put("page", String.valueOf(pageNum));
		map.put("times", times);
		map.put("timee", timee);
		map.put("searchType", "1");
		map.put("searchkeyword", stringToUnicode(searchkeyword));
		map.put("source_name", sourceName);
		map.put("seed_id", seedId);
		String response = "";
		Map<String, Object> result = new HashMap<String, Object>();
		List<DataMonitorVO> list = new ArrayList<DataMonitorVO>();
		try {
			response = esArticle(map, article_api);
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
			int size = jsonArray.size();
			for (int i = 0; i < size; i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				JSONObject jsonObject2 = jsonObject.getJSONObject("_source");
				DataMonitorVO dataMonitorVO = JSON.toJavaObject(jsonObject2, DataMonitorVO.class);
				list.add(dataMonitorVO);
			}
			if (Integer.parseInt(totalData) > 5000) {
				totalPage = "500";
			}
			if (StringUtils.isBlank(totalPage)) {
				totalPage = "1";
			}else if ("0".equals(totalPage )) {
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
		JSONObject json=new JSONObject(result);
		if(seedId.equals("3566")) {
			System.out.println("进入转换");
			JSONArray jsonArray = json.getJSONArray("list");
			for(int i=0;i<jsonArray.size();i++) {
				String content=jsonArray.getJSONObject(i).get("content").toString();
				try {
					Document doc=Jsoup.parse(content);
					content=doc.text();
					jsonArray.getJSONObject(i).remove("content");
					jsonArray.getJSONObject(i).put("content", content);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			json.remove("list");
			json.put("list", jsonArray);
		}
		System.out.println(json.toJSONString());
		return JSON.toJSONString(json);
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
	
	@RequestMapping(value = "/baidu-post-bar", method = RequestMethod.GET)
	public ModelAndView mainstreamMediaList(ModelAndView mv) {
		mv.addObject("nowArray", DateUtil.getNowDate());
		mv.setViewName("socialMedia/baidu-post-bar");
		return mv;
	}
	
	@RequestMapping(value = "/weibo", method = RequestMethod.GET)
	public ModelAndView weibo(ModelAndView mv) {
		mv.addObject("nowArray", DateUtil.getNowDate());
		mv.setViewName("socialMedia/weibo");
		return mv;
	}
	
	public String esArticle(Map<String, String> map, String api) {
		String size = map.get("size");
		String page = map.get("page");
		String keyword = map.get("keyword");
		String searchType = map.get("searchType");
		String times = map.get("times");
		String timee = map.get("timee");
		String searchkeyword = map.get("searchkeyword");
		String source_name = map.get("source_name");
		String seed_id = map.get("seed_id");
		String url = es_url + api;
		String params = "?size=" + size + "&page=" + page + "&keyword=" + keyword 
				+ "&searchType=" + searchType
				+ "&times=" + times + "&timee=" + timee + "&searchkeyword=" + searchkeyword 
				+ "&source_name=" + source_name + "&seed_id=" + seed_id;
		System.err.println(url + params);
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
	
	
	@RequestMapping("/zhihu")
	public ModelAndView Zhihu(ModelAndView mav) {
		mav.addObject("nowArray", DateUtil.getNowDate());
		mav.setViewName("socialMedia/zhihu");
		return mav;
	}
	
	
	@RequestMapping(value="/zhihu-data",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject ZhihuData(
			@RequestParam(value = "page", defaultValue = "1" ,required = false)Integer page) {
		JSONObject json=new JSONObject();
		try {
			String response="";
			String param="";
			param="?times=&timee=&page="+page+"&size=10";
			Integer count =0;
			Integer page_count =0;
			Integer size =0;
				response = sendPost(UtilConfig.getURL2().substring(0,UtilConfig.getURL2().length()-1)+"/knowabout/knowaboutlist", param);
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
	 * 小红书
	 * @param mav
	 * @return
	 * Colin
	 */
	@RequestMapping("/redbook")
	public ModelAndView RedBook(ModelAndView mav) {
		mav.addObject("nowArray", DateUtil.getNowDate());
		mav.setViewName("socialMedia/redbook");
		return mav;
	}
	
	
	@RequestMapping(value="/redbook-data",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject RedBookData(
			@RequestParam(value = "page", defaultValue = "1" ,required = false)Integer page) {
		JSONObject json=new JSONObject();
		try {
			String response="";
			String param="";
			param="?times=&timee=&page="+page+"&size=10";
			Integer count =0;
			Integer page_count =0;
			Integer size =0;
				response = sendPost(UtilConfig.getURL2().substring(0,UtilConfig.getURL2().length()-1)+"/littleredbook/littleredbooklist", param);
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
	 * 地方论坛
	 * @param mav
	 * @return
	 * Colin
	 */
	@RequestMapping("/localbbs")
	public ModelAndView LocalBBS(ModelAndView mav) {
		mav.setViewName("socialMedia/localbbs");
		mav.addObject("nowArray", DateUtil.getNowDate());
		return mav;
	}
	
	
	@RequestMapping(value="/localbbs-data",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject LocalBBSData(
			@RequestParam(value = "page", defaultValue = "1" ,required = false)Integer page) {
		JSONObject json=new JSONObject();
		try {
			String response="";
			String param="";
			param="?times=&timee=&page="+page+"&size=10";
			Integer count =0;
			Integer page_count =0;
			Integer size =0;
				response=sendPost(UtilConfig.getURL2().substring(0,UtilConfig.getURL2().length()-1)+"/forum/forumlist", param);
				System.out.println(response);
				
				json = JSONObject.parseObject(response);
				JSONArray news=json.getJSONArray("data");
				count=Integer.valueOf(json.get("count").toString());
				page_count=Integer.valueOf(json.get("page_count").toString());
				size=Integer.valueOf(json.get("size").toString());
			json=JSONObject.parseObject(response);
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
	
	
	@RequestMapping(value="localbbs-detail",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject LocalBBSdetail(String article_public_id) {
		JSONObject start_json=new JSONObject();
		JSONObject EndJson =new JSONObject();
		try {
			start_json.put("fieldname", "id");
			start_json.put("fielddata", article_public_id);
			start_json.put("index", "stonedt_forum");
			start_json.put("type", "infor");
		        String url = UtilConfig.getURL2().substring(0,UtilConfig.getURL2().length()-1)+ElasticSearchUtil.getcommondatadetail;
	            String responseData = ExecutionPersonController.getJsonHtml(url,"",start_json);
	            EndJson = JSONObject.parseObject(responseData);
	            System.out.println(EndJson.toJSONString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EndJson;
	}
	
	/**
	 * 字符串转unicode
	 * 
	 * @param str
	 * @return
	 */
	public static String stringToUnicode(String str) {
		StringBuffer sb = new StringBuffer();
		char[] c = str.toCharArray();
		for (int i = 0; i < c.length; i++) {
			sb.append("\\u" + Integer.toHexString(c[i]));
		}
		return sb.toString();
	}


}
