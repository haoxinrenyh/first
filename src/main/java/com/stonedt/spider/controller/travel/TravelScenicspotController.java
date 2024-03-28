package com.stonedt.spider.controller.travel;

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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

@Controller
@RequestMapping("/travelscenicspot")
public class TravelScenicspotController {
	/***
	 * 旅游景区
	 * @param mav
	 * @param request
	 * @param type
	 * @return
	 */
	@RequestMapping("/travelscenicspot")
	public ModelAndView travelscenicspot(ModelAndView mav,HttpServletRequest request) {
		mav.setViewName("travelscenicspot/travelscenicspotList");
		request.setAttribute("LEFT", "travelscenicspot");
		return mav;
	}
	
	/**
	 * 	列表
	 */
	@RequestMapping(value = "/travelscenicspotList", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public String travelscenicspotList(String pageNum, String matchingmode, String time, String searchkeyword,
			String gender) {
		String response = "";
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, String>> list = new ArrayList<>();
		try {
			String param = "{\"timefield\":\"spider_time\",\"times\":\"\",\"size\":\"10\",\"and\":[{\"field\":\"website_id\",\"keyword\":\""+1140+"\"}],\"index\":\"stonedt_scenic_area_tickets\",\"page\":"+pageNum+",\"type\":\"infor\",\"timee\":\"\"}";
	    	JSONObject parseObject = JSONObject.parseObject(param);
	    	System.out.println(parseObject);
	    	response = getJsonHtml("http://dx2.stonedt.com:7120/commonsearch/superdatalist",parseObject);
			System.out.println(response);
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
	
	public static String getJsonHtml(String url, JSONObject jsonObject) throws Exception {
		System.err.println(url+jsonObject.toString());
		String result ="";
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		StringEntity postingString = new StringEntity(jsonObject.toString());// json传递
		post.setEntity(postingString);
		post.setHeader("Referer", "http://hotel.qunar.com/cn/shanghai_city/?fromDate=2020-02-13&toDate=2020-02-14&cityName=%E4%B8%8A%E6%B5%B7");
		post.setHeader("Content-type", "application/json");
		HttpResponse response = httpClient.execute(post);
		String content = EntityUtils.toString(response.getEntity());
		result = content;
		System.out.println(result.toString());
		return result;
	}
}
