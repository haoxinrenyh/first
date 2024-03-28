package com.stonedt.spider.controller;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.stonedt.spider.entity.MappingSpider;
import com.stonedt.spider.entity.WeMedia;
import com.stonedt.spider.entity.platform;

import java.util.List;

import javax.annotation.Resource;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.stonedt.spider.service.WeMediaListService;
@Controller
@RequestMapping("/wemedialist")
public class WeMediaList {
	@Resource
	private WeMediaListService WeMediaListService;
	
	
	@RequestMapping("run")
	public ModelAndView run(ModelAndView mav,WeMedia WeMedia,
			@RequestParam(defaultValue="1",required=false,value="PageNo") Integer PageNo
			) {
		
		platform platform=new platform();
		//内容
		PageHelper.startPage(PageNo, 10);
		
		List<WeMedia> list=WeMediaListService.Getmedia(WeMedia);
		
		PageInfo<WeMedia> page = new PageInfo<WeMedia>(list);
		
		//平台
		
		List<platform> form=WeMediaListService.Getplatform(platform);
		mav.addObject("form", form);
		mav.addObject("list", page);
		mav.setViewName("web_list");
		return mav;
	}
	
	
	@RequestMapping("screen")
	@ResponseBody
	public JSONObject Webhtml(WeMedia WeMedia,ModelAndView mav,
			@RequestParam(defaultValue="1",required=false,value="PageNo") Integer PageNo,
			@RequestParam(defaultValue="",required=false,value="websiz") String websiz,
			@RequestParam(defaultValue="",required=false,value="website_name") String website_name) {
		
		JSONObject json=new JSONObject();
		platform platform=new platform();
		//内容
		PageHelper.startPage(PageNo, 10);
		WeMedia.setPlatform_id(websiz);
		WeMedia.setName(website_name);
		List<WeMedia> list=WeMediaListService.Getmedia(WeMedia);
		
		PageInfo<WeMedia> page = new PageInfo<WeMedia>(list);
		
		//平台
		mav.addObject("websiz", websiz);
		mav.addObject("website_name", website_name);
		mav.setViewName("web_list");
		List<platform> form=WeMediaListService.Getplatform(platform);
		json.put("page", page);
		json.put("form", form);
		return json;
	}
}
