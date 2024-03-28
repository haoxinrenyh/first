package com.stonedt.spider.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.stonedt.spider.entity.Websiteshie;
import com.stonedt.spider.service.WebsiteshieService;

@Controller
@RequestMapping("/shielding")
public class WebsiteshieldingController {
	//注入service
	@Resource
	private WebsiteshieService WebsiteshieService;

	//去屏蔽页面
	@RequestMapping("run")
	public ModelAndView Webcheak(Websiteshie Websiteshie,ModelAndView mav,@RequestParam(defaultValue="1",required=true,value="pageNum")Integer pageNum) {
		PageHelper.startPage(pageNum, 50);
		List<Websiteshie> list=WebsiteshieService.Webcheak(Websiteshie);
		PageInfo<Websiteshie> pageinfo=new PageInfo<>(list);
		
		mav.addObject("pageinfo", pageinfo);
		mav.addObject("list", list);
		mav.setViewName("Website_shielding");
		return mav;
	}
	
	
	//添加屏蔽url方法
	@RequestMapping("Weadd")
	@ResponseBody
	public JSONObject Webcheak(Websiteshie Websiteshie) {
		Date date=new Date();
		
		Websiteshie.setCreate_date(date);
		WebsiteshieService.Webadd(Websiteshie);
		
		JSONObject json=new JSONObject();
		json.put("msg",1);
		return json;
	}
	
	
	//删除屏蔽的url
	@RequestMapping("dlete")
	@ResponseBody
	public JSONObject dlete(String id) {
		if(id!=null) {
		WebsiteshieService.delte(id);
		
		JSONObject json=new JSONObject();
		json.put("msg",1);
		return json;
		}else {
			return null;
		}
	}
}
