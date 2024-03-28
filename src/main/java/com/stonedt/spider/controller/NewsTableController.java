package com.stonedt.spider.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.stonedt.spider.entity.SecLabel;
import com.stonedt.spider.service.InformationListService;
import com.stonedt.spider.service.NewsTableService;
import com.stonedt.spider.util.HttpUtil;

@Controller
@RequestMapping("/news")
public class NewsTableController {
	@Autowired
	private NewsTableService newsTableService;
	
	@Autowired
	private InformationListService informationListService;
	
	@RequestMapping("/table")
	@ResponseBody
	public String getNewsTable(HttpServletRequest request) throws IOException {
		
		String type = request.getParameter("type");
		String sendpost =null;
		
		//创建map作为调用接口的参数
		Map<String,Object> map = new HashMap<String,Object>();
		List<String> seedList = new ArrayList<String>();
		String url = "http://10.0.50.74:8080/EsMongo/article/get";
		if(type.contains("b")) {
			//是一级分类,获取对应的二级子分类的所有id
			seedList = newsTableService.getSecondType(type);
			System.out.println(seedList.toString());
			//调用接口
			map.put("page", "");
			map.put("beginTime", "");
			map.put("endTime", "");
			map.put("seedList", seedList);
			System.out.println(11111);
			sendpost = HttpUtil.sendpost(url,map);
			System.out.println(sendpost);
			
		}else {
			//不是一级分类，是二级子分类，调用接口
			seedList.add(type);
			map.put("page", "");
			map.put("beginTime", "");
			map.put("endTime", "");
			map.put("seedList", seedList);
//			sendpost = HttpUtil.sendpost(url,map);sss
			System.out.println(map.toString());
			System.out.println(222);
		}
		
		
		return sendpost;
	}
	
	
	/**
	 * 根据用户选择的菜单，获取到对应的该分类下所有的子标签
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getSecLable",produces = "text/plain;charset=utf-8")
	@ResponseBody
	public String getLableByType(@RequestParam(value = "pagenum", defaultValue = "1" ,required = false)Integer pagenum,HttpServletRequest request) {
		Map<String,Object> map = new HashMap<String,Object>();
		Integer seed_id = Integer.parseInt(request.getParameter("seed_id"));
		String first_type = informationListService.getFirstLabel(seed_id);
		PageHelper.startPage(pagenum,45);
		List<SecLabel> labels = newsTableService.getSecLabel(pagenum,first_type);
		PageInfo<SecLabel> page = new PageInfo<SecLabel>(labels);
		int tatol = page.getPages();
		int pageNum = page.getPageNum();
		map.put("list", labels);
		map.put("tatol",tatol);
		map.put("pageNum",pageNum);
		String json = JSONObject.toJSONString(map);
		
		return json;
	}
	
	/**
	 * 根据用户选择的菜单，获取到对应的该分类下所有的子标签
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getSeedIds",produces = "text/plain;charset=utf-8")
	@ResponseBody
	public String getSeedIds(HttpServletRequest request) {
		Map<String,Object> map = new HashMap<String,Object>();
		List<SecLabel> labels = newsTableService.getSeedIds();
		String json = JSONObject.toJSONString(labels);
		return json;
	}
	
	@RequestMapping(value = "/getType",produces = "text/plain;charset=utf-8")
	@ResponseBody
	public String getType(HttpServletRequest request) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("flag", request.getParameter("flag"));
		List<SecLabel> labels = newsTableService.getType(map);
//		System.out.println("种子"+labels.get(0));
		String json = JSONObject.toJSONString(labels);
		return json;
	}
	
	
	
}
