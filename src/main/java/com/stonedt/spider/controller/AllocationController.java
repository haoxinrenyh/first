package com.stonedt.spider.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.stonedt.spider.entity.AllocationEntity;
import com.stonedt.spider.service.SpiderSearchEngineService;

@Controller
@RequestMapping("/allocation")
public class AllocationController {
	//注入service
	@Resource
	private SpiderSearchEngineService spiderSearchEngineService;
	
	
	//去新增配置页面
	@RequestMapping("toadd")
	@ResponseBody
	public ModelAndView to_allocation(ModelAndView mav,String pageNum) {
			
			
			
			List<AllocationEntity> list = spiderSearchEngineService.getConfig_name();
			
			
			JSONObject json = new JSONObject();
			json.put("list",list);
			mav.addObject("pageNum", pageNum);
			mav.addObject("list", list);
			mav.setViewName("to_add_allocation");
			return mav;
		}
	
	//添加方法
	@RequestMapping("create")
	@ResponseBody
	public JSONObject create(AllocationEntity allocationEntity,ModelAndView mav) {
		Date date=new Date();
		//SimpleDateFormat dateFormat= new SimpleDateFormat("YYYY-MM-dd HH:MM:ss");
		allocationEntity.setCreate_date(date);
		
		spiderSearchEngineService.add(allocationEntity);
		
		JSONObject json = new JSONObject();
		json.put("code",1);
		return json;
	}
	
	//编辑页面数据回显
	@RequestMapping("toeditor")
	@ResponseBody
	public ModelAndView toeditor(ModelAndView mav,String id,String pageNum) {
		
		List<AllocationEntity> list = spiderSearchEngineService.sumEditor(id);
		
		List<AllocationEntity> str = spiderSearchEngineService.getConfig_name();
		mav.addObject("str", str);
		mav.addObject("id", id);
		mav.addObject("pageNum", pageNum);
		mav.addObject("list", list);
		mav.setViewName("to_editor_allocation");
		return mav;
	}
	
	@RequestMapping("editor")
	@ResponseBody
	public JSONObject editor(AllocationEntity allocationEntity) {
		Date date=new Date();
	
		SimpleDateFormat dateFormat= new SimpleDateFormat("YYYY-MM-dd HH:MM:ss");
		
		allocationEntity.setUpdate_date(dateFormat.format(date));
		
		spiderSearchEngineService.Editor(allocationEntity);
		
		JSONObject json = new JSONObject();
		json.put("code",1);
		return json;
	}
	
	
	//开启/关闭方法
	@RequestMapping("run")
	@ResponseBody
	public JSONObject run(AllocationEntity allocationEntity) {
		
		if("1".equals(allocationEntity.getMsg())) {
			
			spiderSearchEngineService.runcole(allocationEntity);
			
		}else if("2".equals(allocationEntity.getMsg())) {
			
			spiderSearchEngineService.runcole(allocationEntity);
			
			
		}
		JSONObject json = new JSONObject();
		json.put("code",1);
		return json;
		
	}
}

