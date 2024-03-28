package com.stonedt.spider.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.stonedt.spider.entity.ScreenWord;
import com.stonedt.spider.entity.SpiderSeed;
import com.stonedt.spider.entity.SpiderSeedConfigUtilOb;
import com.stonedt.spider.service.ScreenSeedService;
import com.stonedt.spider.service.SeedService;
import com.stonedt.spider.util.ElasticSearchUtil;
import com.stonedt.spider.util.ResultUtil;

@Controller
@RequestMapping("/screen")
public class ScreenSeedController {
	
	@Autowired
	private ScreenSeedService screenSeedService; 
	@Autowired
	private SeedService seedService;
	
	@RequestMapping("/setup")
	public ModelAndView website_addtactics(@RequestParam(value = "pageNum", defaultValue = "1" ,required = false)Integer pageNum,
			ModelAndView mv,HttpServletRequest request) {
		PageHelper.startPage(pageNum,99);
		List<ScreenWord>list= screenSeedService.getList();
		PageInfo<ScreenWord> page = new PageInfo<ScreenWord>(list);
		mv.addObject("list", page);
		mv.addObject("json",page.getList());
		mv.setViewName("screen_setting");
		return mv;
	}
	
	@RequestMapping("/seedlist")
	public ModelAndView seedlist(@RequestParam(value = "pageNum", defaultValue = "1" ,required = false)Integer pageNum,
			ModelAndView mv,HttpServletRequest request) {
		String words= screenSeedService.getStringList();
		String param ="";
		try {
			param = "keyword="+URLEncoder.encode(words, "utf-8")+"&times=&tmiee=";
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String sendPost ="";
		
		try {
			sendPost = ElasticSearchUtil.sendPost("yys/qbseednum", param);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<SpiderSeed> list = new ArrayList<SpiderSeed>();
		int pages = 1;
		if(!StringUtils.isEmpty(sendPost)){
			JSONObject jsonobject = JSONObject.parseObject(sendPost);
			JSONArray array = jsonobject.getJSONObject("aggregations").getJSONObject("group_by_tags").getJSONArray("buckets");
			JSONArray otherarray = new JSONArray();
			for (int i = 0; i < array.size(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				if(jsonObject.get("key")!=null){
					otherarray.add(jsonObject);
				}
			}
			int size = otherarray.size();
			pages = size/30;
			if(size%30 > 0){
				pages = pages +1;
			}
			int max = pageNum* 30;
			if(pageNum* 30 > size){
				max = size;
			}
			for (int i = (pageNum - 1)* 30 +1; i < max; i++) {
				JSONObject jsonObject = otherarray.getJSONObject(i);
				Integer integer = jsonObject.getInteger("key");
				SpiderSeed info = seedService.getInfo(integer);
				if(info != null){
					info.setScreennum(jsonObject.getInteger("doc_count"));
					list.add(info);
				}
			}
		}
//		List<E> list= seedService.getByIds(seedIds);
		PageInfo<SpiderSeed> page = new PageInfo<SpiderSeed>(list);
		page.setPageNum(pageNum);
		page.setPageSize(30);
		page.setPages(pages);
		mv.addObject("list", page);
		mv.setViewName("screen_list");
		return mv;
	}
	
	@RequestMapping("/remove")
	@ResponseBody
	public ResultUtil remove(Integer id){
		return screenSeedService.remove(id);
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public ResultUtil add(String word){
		return screenSeedService.add(word);
	}
	
	@RequestMapping("/searchArticle")
	@ResponseBody
	public ResultUtil searchArticle(@RequestParam(value = "pageNum", defaultValue = "1" ,required = false)Integer pageNum,
			Integer spider_seed_id,
			HttpServletRequest request) {
		String words= screenSeedService.getStringList();
		String param ="";
		try {
			param = "otherseedid="+spider_seed_id+"&searchType=1&keyword="+URLEncoder.encode(words, "utf-8")+"&times=&tmiee=&size=10&page="+pageNum;
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String sendPost ="";
		
		try {
			sendPost = ElasticSearchUtil.sendPost("yys/qbsearchcontent", param);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("-------------------------------------------------------------------------------------------");
		System.out.println(sendPost);
//		List<SpiderSeed> list = new ArrayList<SpiderSeed>();
		int pages = 1;
		if(!StringUtils.isEmpty(sendPost)){
			JSONObject jsonobject = JSONObject.parseObject(sendPost);
			if(jsonobject.getInteger("code") == 200){
				JSONArray jsonArray = jsonobject.getJSONArray("data");
				List<JSONObject> list = new ArrayList<>();
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject object = jsonArray.getJSONObject(i);
					JSONObject json = object.getJSONObject("_source");
					JSONObject obj = new JSONObject();
					obj.put("title", json.getString("title"));
					String string = json.getString("content");
					if(string.length()>300){
						string = string.substring(0,300)+"...";
					}
					obj.put("publish_time", json.getString("publish_time"));
					obj.put("source_url", json.getString("source_url"));
					obj.put("content", string);
					list.add(obj);
				}
				PageInfo<JSONObject> page = new PageInfo<JSONObject>(list);
				page.setPageNum(pageNum);
				page.setPageSize(10);
				page.setPages(jsonobject.getInteger("page_count"));
				page.setTotal(jsonobject.getInteger("count"));
				System.out.println(page);
				return ResultUtil.build(200, "",page);
			}
			
		}
//		List<E> list= seedService.getByIds(seedIds);
		return ResultUtil.build(500,"false");
	}
	
	@RequestMapping("/searchArticle1")
	@ResponseBody
	public ResultUtil searchArticle1(@RequestParam(value = "pageNum", defaultValue = "1" ,required = false)Integer pageNum,
			Integer spider_seed_id,
			HttpServletRequest request) {
		
		String param ="";
		param = "otherseedid="+spider_seed_id+"&searchType=1&times=&tmiee=&size=10&page="+pageNum;
		String sendPost ="";
		
		try {
			sendPost = ElasticSearchUtil.sendPost("yys/qbsearchcontent", param);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("-------------------------------------------------------------------------------------------");
		System.out.println(sendPost);
//		List<SpiderSeed> list = new ArrayList<SpiderSeed>();
		int pages = 1;
		if(!StringUtils.isEmpty(sendPost)){
			JSONObject jsonobject = JSONObject.parseObject(sendPost);
			if(jsonobject.getInteger("code") == 200){
				JSONArray jsonArray = jsonobject.getJSONArray("data");
				List<JSONObject> list = new ArrayList<>();
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject object = jsonArray.getJSONObject(i);
					JSONObject json = object.getJSONObject("_source");
					JSONObject obj = new JSONObject();
					obj.put("title", json.getString("title"));
					String string = json.getString("content");
					if(string.length()>300){
						string = string.substring(0,300)+"...";
					}
					obj.put("publish_time", json.getString("publish_time"));
					obj.put("source_url", json.getString("source_url"));
					obj.put("content", string);
					list.add(obj);
				}
				PageInfo<JSONObject> page = new PageInfo<JSONObject>(list);
				page.setPageNum(pageNum);
				page.setPageSize(10);
				page.setPages(jsonobject.getInteger("page_count"));
				page.setTotal(jsonobject.getInteger("count"));
				System.out.println(page);
				return ResultUtil.build(200, "",page);
			}
			
		}
//		List<E> list= seedService.getByIds(seedIds);
		return ResultUtil.build(500,"false");
	}
	
	
}
