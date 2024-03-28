package com.stonedt.spider.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
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
import com.stonedt.spider.constant.Constant;
import com.stonedt.spider.entity.KeyWord;
import com.stonedt.spider.entity.MappingSpider;
import com.stonedt.spider.entity.SecLabel;
import com.stonedt.spider.entity.SpiderArticle;
import com.stonedt.spider.entity.SpiderCount;
import com.stonedt.spider.entity.SpiderSeed;
import com.stonedt.spider.entity.SpiderSeedConfig;
import com.stonedt.spider.entity.SpiderSeedConfigUtilOb;
import com.stonedt.spider.entity.SpiderWebsite;
import com.stonedt.spider.entity.TransferinfoEntity;
import com.stonedt.spider.entity.WebsitespidertypeEntity;
import com.stonedt.spider.service.ScreenSeedService;
import com.stonedt.spider.service.SeedConfigService;
import com.stonedt.spider.service.SeedConfigUtilService;
import com.stonedt.spider.service.SeedService;
import com.stonedt.spider.service.SpiderArticleService;
import com.stonedt.spider.service.SpiderCountService;
import com.stonedt.spider.service.SpiderWebsiteService;
import com.stonedt.spider.service.SpiderWebsitemonitorService;
import com.stonedt.spider.util.ElasticSearchUtil;
import com.stonedt.spider.util.EsUtils;
import com.stonedt.spider.util.HttpUtil;
import com.stonedt.spider.util.ResultUtil;

@Controller
@RequestMapping("/")
public class TypeNameController {

	@Autowired
	private SpiderWebsiteService spiderWebsiteService;
	@Autowired
	private SpiderWebsitemonitorService spiderWebsitemonitorService;
	/*
	 * 查询种子
	 * @author wangyi
	 */
	@RequestMapping("/listtypename")
	public ModelAndView listseed(ModelAndView mv,Integer websiteId,
			@RequestParam(value = "pageNum", defaultValue = "1" ,required = false)Integer pageNum) {

	     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	     //当前网站的基本信息
	     SpiderWebsite spiderwebsite = spiderWebsiteService.getInfo(websiteId);
	     mv.addObject("website", spiderwebsite);
	     //List<SpiderSeedConfigUtilOb> seed_config_utils = seedConfigUtilService.getUtilByWebSite(websiteId);
		//种子列表信息
		PageHelper.startPage(pageNum,10);
		List<Map<String,Object>> list = spiderWebsitemonitorService.listtypename(websiteId);//种子列表
		PageInfo<Map<String,Object>> page = new PageInfo<Map<String,Object>>(list);
		mv.addObject("list", page);
		mv.setViewName("websitetypename");
		return mv;
	}
	/*
	 * 跳转到新增种子页面
	 * @author wangyi
	 */
	@RequestMapping("/toaddtypename")
	public ModelAndView toaddtypename(ModelAndView mv,HttpServletRequest request) {
		Integer websiteId = Integer.parseInt(request.getParameter("websiteId").toString());
		String websitename = request.getParameter("website_name").toString();
		mv.addObject("websiteId", websiteId);
		mv.addObject("websitename",websitename);
		mv.setViewName("build_typename");
		return mv;
	}
	/**
	 * 选择响应的类型
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/gettypename",produces = "text/plain;charset=utf-8")
	@ResponseBody
	public String gettypename(HttpServletRequest request) {
		List<Map<String,Object>> labels = spiderWebsitemonitorService.gettypename();
		String json = JSONObject.toJSONString(labels);
		return json;
	}
	
	/**
	 * 新增站点类型
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addtypename",produces = "text/plain;charset=utf-8")
	@ResponseBody
	public String addtypename(HttpServletRequest request) {
		String code = "200";
		try {
		WebsitespidertypeEntity wt = new WebsitespidertypeEntity();
		
		TransferinfoEntity tf = new TransferinfoEntity();
		int websiteId = Integer.parseInt(request.getParameter("websiteId").toString());//网站id
		int typeid = Integer.parseInt(request.getParameter("typeid").toString());//类型id
		
		tf.setTypeid(typeid);
		String paramexample =  request.getParameter("paramexample").toString();
		String errorparamexample =  request.getParameter("errorparamexample").toString();
		String errorparam = request.getParameter("errorparam").toString();
        //新增查询接口
		tf.setParamexample(paramexample);
		String param = request.getParameter("param").toString();
		tf.setParam(param);
		spiderWebsitemonitorService.addtransdata(tf);
		System.out.println("tf.getId()"+tf.getId());
		wt.setTransferid(tf.getId());
		tf.setParam(errorparam);
		tf.setParamexample(errorparamexample);
		//插入错误日志接口
		spiderWebsitemonitorService.addtransdata(tf);
		//获取错误日志接口id
		wt.setErrorlogtransid(tf.getId());
		wt.setWebsiteprimaryid(websiteId);
		wt.setType(typeid);
		int count = spiderWebsitemonitorService.addwebsitespidertype(wt);
		} catch (Exception e) {
			code = "500";
			e.printStackTrace();
			// TODO: handle exception
		}
		return code;
	}
	
	
	
	
	
	
	
	
	
}
