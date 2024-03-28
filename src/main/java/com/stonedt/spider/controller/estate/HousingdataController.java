package com.stonedt.spider.controller.estate;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.stonedt.spider.util.DateUtil;
import com.stonedt.spider.util.ElasticSearchUtil;

@Controller
@RequestMapping("/housingdata")
public class HousingdataController {
	
	/***
	 * 房产资讯
	 * @param mav
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/houseinformation",method = RequestMethod.GET)
	public ModelAndView houseinformation(ModelAndView mav,
			@RequestParam(value = "page", defaultValue = "1" ,required = false)Integer page) {
			mav.addObject("nowArray", DateUtil.getNowDate());
			mav.setViewName("estateData/houseinformation_list");
		return mav;
	}

	
	@RequestMapping(value="/houseinformation-data",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject houseinformation2(
			@RequestParam(value = "page", defaultValue = "1" ,required = false)Integer page) {
		JSONObject json=new JSONObject();
		try {
			String response="";
			String param="";
			param="?&page="+page+"&size=10&otherseedid=2411,1285,23083,1313,2200,2201,2426,2427&searchType=1";
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
	
	/***
	 * 房产政策
	 * @param mav
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/housepolicy",method = RequestMethod.GET)
	public ModelAndView housepolicy(ModelAndView mav,
			@RequestParam(value = "page", defaultValue = "1" ,required = false)Integer page) {
			mav.addObject("nowArray", DateUtil.getNowDate());
			mav.setViewName("estateData/housepolicy_list");
		return mav;
	}

	
	@RequestMapping(value="/housepolicy-data",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject housepolicy2(
			@RequestParam(value = "page", defaultValue = "1" ,required = false)Integer page) {
		JSONObject json=new JSONObject();
		try {
			String response="";
			String param="";
			param="?&page="+page+"&size=10&otherseedid=23082,23084&searchType=1";
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
