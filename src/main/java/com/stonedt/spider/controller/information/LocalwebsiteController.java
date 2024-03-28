package com.stonedt.spider.controller.information;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

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
@RequestMapping("/Localwebsite")
public class LocalwebsiteController {
	
	
	@RequestMapping("run")
	public ModelAndView Localwebsite(ModelAndView mav) {
		mav.addObject("nowArray", DateUtil.getNowDate());
		mav.setViewName("informationData/Local_website");
		return mav;
	}
	
	
	@RequestMapping(value="/data",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject Localwebsitedata(
			@RequestParam(value = "page", defaultValue = "1" ,required = false)Integer page,
			@RequestParam(value = "websitename", defaultValue = "" ,required = false)String websitename) {
		JSONObject json=new JSONObject();
		try {
			String response="";
			String param="";
			
			param="?&page="+page+"&size=10&otherseedid=23056,23064,22952,23009,23011,23065,22955,23016,23066,23067,22958,23068,23069,23070,23071,22989,23028,23031,23072,23073,23033,23074,23075,23076,23077,23036,23047,23048,23049,23038,23044,23045,23046,23079,23080,23040,23041,23042,23043,23078,23050,23051,23052,23053,23054,23055,23057,23058,23059,23060,23061,23062,23063&searchType=1";
			
			if(websitename.equals("腾讯四川大成网")) {
				param="?&page="+page+"&size=10&otherseedid=23056,23064&searchType=1";
			}else if(websitename.equals("腾讯重庆大渝网")) {
				param="?&page="+page+"&size=10&otherseedid=2295223009,23011,23065&searchType=1";
			}else if(websitename.equals("腾讯西安大秦网")) {
				param="?&page="+page+"&size=10&otherseedid=22955,23016,23066,23067&searchType=1";
			}else if(websitename.equals("腾讯湖北大楚网")) {
				param="?&page="+page+"&size=10&otherseedid=22958,23068,23069,23070,23071&searchType=1";	
			}else if(websitename.equals("腾讯福建大闽网")) {
				param="?&page="+page+"&size=10&otherseedid=22989&searchType=1";
			}else if(websitename.equals("腾讯广大大粤网")) {
				param="?&page="+page+"&size=10&otherseedid=23028&searchType=1";
			}else if(websitename.equals("腾讯河南大豫网")) {
				param="?&page="+page+"&size=10&otherseedid=23031,23072,23073&searchType=1";
			}else if(websitename.equals("腾讯湖南大湘网")) {
				param="?&page="+page+"&size=10&otherseedid=23033,23074,23075,23076,23077&searchType=1";
			}else if(websitename.equals("腾讯上海大申网")) {
				param="?&page="+page+"&size=10&otherseedid=23036,23047,23048,23049&searchType=1";
			}else if(websitename.equals("腾讯浙江大浙网")) {
				param="?&page="+page+"&size=10&otherseedid=23038,23044,23045,23046,23079,23080&searchType=1";
			}else if(websitename.equals("腾讯江苏大苏网")) {
				param="?&page="+page+"&size=10&otherseedid=23040,23041,23042,23043,23078&searchType=1";
			}else if(websitename.equals("腾讯辽宁大辽网")) {
				param="?&page="+page+"&size=10&otherseedid=23050,23051,23052,23053&searchType=1";
			}else if(websitename.equals("腾讯北京大燕网")) {
				param="?&page="+page+"&size=10&otherseedid=23054,23055&searchType=1";
			}else if(websitename.equals("腾讯天津大燕网")) {
				param="?&page="+page+"&size=10&otherseedid=23057,23058,23059,23060&searchType=1";
			}else if(websitename.equals("腾讯河北网")) {
				param="?&page="+page+"&size=10&otherseedid=23061,23062,23063&searchType=1";
			}
			response = ElasticSearchUtil.sendPost("yys/qbsearchcontent", param);
			json = JSONObject.parseObject(response);
			Integer count =0;
			Integer page_count =0;
			Integer size =0;
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
