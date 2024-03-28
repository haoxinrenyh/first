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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.stonedt.spider.constant.Constant;
import com.stonedt.spider.entity.KeyWord;
import com.stonedt.spider.entity.MappingSpider;
import com.stonedt.spider.entity.SpiderArticle;
import com.stonedt.spider.entity.SpiderCount;
import com.stonedt.spider.entity.SpiderSeed;
import com.stonedt.spider.entity.SpiderSeedConfig;
import com.stonedt.spider.entity.SpiderSeedConfigUtilOb;
import com.stonedt.spider.entity.SpiderWebsite;
import com.stonedt.spider.service.ScreenSeedService;
import com.stonedt.spider.service.SeedConfigService;
import com.stonedt.spider.service.SeedConfigUtilService;
import com.stonedt.spider.service.SeedService;
import com.stonedt.spider.service.SpiderArticleService;
import com.stonedt.spider.service.SpiderCountService;
import com.stonedt.spider.service.SpiderWebsiteService;
import com.stonedt.spider.util.ElasticSearchUtil;
import com.stonedt.spider.util.EsUtils;
import com.stonedt.spider.util.HttpUtil;
import com.stonedt.spider.util.ResultUtil;

@Controller
@RequestMapping("/")
public class SeedController {
	@Autowired
	private SeedService seedService;
	@Autowired
	private SpiderCountService spiderCountService;
	@Autowired
	private SpiderWebsiteService spiderWebsiteService;
	@Autowired
	private SpiderArticleService spiderArticleService;
	@Autowired
	private SeedConfigService seedConfigService;
	@Autowired
	private SeedConfigUtilService seedConfigUtilService;
	@Autowired
	private ScreenSeedService screenSeedService;
	/*
	 * 查询种子
	 * @author wangyi
	 */
	@RequestMapping("/listseed")
	public ModelAndView listseed(ModelAndView mv,Integer websiteId,
			@RequestParam(value = "pageNum", defaultValue = "1" ,required = false)Integer pageNum) {

	     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	     String param ="";
			try {
				param = "otherwebsiteid="+websiteId;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String sendPost ="";
			
			try {
				sendPost = ElasticSearchUtil.sendPost("yys/qbsearchbycontentnum", param);
			} catch (Exception e) {
			
				e.printStackTrace();
			}
			JSONObject json=new JSONObject();
			json=JSONObject.parseObject(sendPost);
			Integer totalnum=Integer.valueOf(json.get("count").toString());
			mv.addObject("totalnum", totalnum);
			//今日采集量
			Date date=new Date();
			String todayparam ="";
			try {
				todayparam = "otherwebsiteid="+websiteId+"&times="+sdf.format((date.getTime()-86400000))+"&tmiee="+sdf.format(date.getTime());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String todayPost ="";
			
			try {
				todayPost = ElasticSearchUtil.sendPost("yys/qbsearchbycontentnum", todayparam);
			} catch (Exception e) {
			
				e.printStackTrace();
			}
			JSONObject todayjson=new JSONObject();
			todayjson=JSONObject.parseObject(todayPost);
			int todaynum  = Integer.valueOf(todayjson.get("count").toString());
			mv.addObject("todaynum", todaynum);
	     //当前网站的基本信息
	     SpiderWebsite spiderwebsite = spiderWebsiteService.getInfo(websiteId);
	     mv.addObject("website", spiderwebsite);
	     List<SpiderSeedConfigUtilOb> seed_config_utils = seedConfigUtilService.getUtilByWebSite(websiteId);
		//种子列表信息
		PageHelper.startPage(pageNum,10);
		List<SpiderSeed> list = seedService.listseed(websiteId);//种子列表
		for (int i = 0; i < list.size(); i++) {
			//总采集
			String pdetail ="";
			try {
				pdetail = "otherseedid="+list.get(i).getId();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String Postdetail ="";
			
			try {
				Postdetail = ElasticSearchUtil.sendPost("yys/qbsearchbycontentnum", pdetail);
			} catch (Exception e) {
			
				e.printStackTrace();
			}
			JSONObject jsonDetail=new JSONObject();
			jsonDetail=JSONObject.parseObject(Postdetail);
			Integer total=Integer.valueOf(jsonDetail.get("count").toString());
			//今日采集量
			String todaydetail ="";
			try {
				todaydetail = "otherseedid="+list.get(i).getId()+"&times="+sdf.format((date.getTime()-86400000))+"&timee="+sdf.format(date.getTime());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String todayPostdetail ="";
			
			try {
				todayPostdetail = ElasticSearchUtil.sendPost("yys/qbsearchbycontentnum", todaydetail);
			} catch (Exception e) {
			
				e.printStackTrace();
			}
			JSONObject todayjsondetail=new JSONObject();
			todayjsondetail=JSONObject.parseObject(todayPostdetail);
			int today  = Integer.valueOf(todayjsondetail.get("count").toString());

			list.get(i).setTotalnum(total);

			list.get(i).setTodaynum(today);
		}
		
		PageInfo<SpiderSeed> page = new PageInfo<SpiderSeed>(list);
		
		mv.addObject("list", page);
		mv.addObject("seed_config_utils",seed_config_utils);
		mv.setViewName("website_info");
		return mv;
	}
	
	
	
	/*
	 * 查询种子
	 * @author wangyi
	 */
	@RequestMapping("/listseedtest")
	public ModelAndView listseedtest(ModelAndView mv,Integer websiteId,
			@RequestParam(value = "pageNum", defaultValue = "1" ,required = false)Integer pageNum) {

	     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	     String param ="";
			try {
				param = "otherwebsiteid="+websiteId;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String sendPost ="";
			
			try {
				sendPost = ElasticSearchUtil.sendPost("yys/qbsearchbycontentnum", param);
			} catch (Exception e) {
			
				e.printStackTrace();
			}
			JSONObject json=new JSONObject();
			json=JSONObject.parseObject(sendPost);
//			Integer totalnum=Integer.valueOf(json.get("count").toString());
//			mv.addObject("totalnum", totalnum);
			//今日采集量
			Date date=new Date();
			String todayparam ="";
			try {
				todayparam = "otherwebsiteid="+websiteId+"&times="+sdf.format((date.getTime()-86400000))+"&tmiee="+sdf.format(date.getTime());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String todayPost ="";
			
			try {
				todayPost = ElasticSearchUtil.sendPost("yys/qbsearchbycontentnum", todayparam);
			} catch (Exception e) {
			
				e.printStackTrace();
			}
			JSONObject todayjson=new JSONObject();
			todayjson=JSONObject.parseObject(todayPost);
			int todaynum  = Integer.valueOf(todayjson.get("count").toString());
			mv.addObject("todaynum", todaynum);
	     //当前网站的基本信息
	     SpiderWebsite spiderwebsite = spiderWebsiteService.getInfo(websiteId);
	     mv.addObject("website", spiderwebsite);
	     List<SpiderSeedConfigUtilOb> seed_config_utils = seedConfigUtilService.getUtilByWebSite(websiteId);
		//种子列表信息
		PageHelper.startPage(pageNum,10);
		List<SpiderSeed> list = seedService.listseedtest(websiteId);//种子列表
		for (int i = 0; i < list.size(); i++) {
			//总采集
			String pdetail ="";
			try {
				pdetail = "otherseedid="+list.get(i).getId();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String Postdetail ="";
			
			try {
				Postdetail = ElasticSearchUtil.sendPost("yys/qbsearchbycontentnum", pdetail);
			} catch (Exception e) {
			
				e.printStackTrace();
			}
			JSONObject jsonDetail=new JSONObject();
			jsonDetail=JSONObject.parseObject(Postdetail);
			Integer total=Integer.valueOf(jsonDetail.get("count").toString());
			//今日采集量
			String todaydetail ="";
			try {
				todaydetail = "otherseedid="+list.get(i).getId()+"&times="+sdf.format((date.getTime()-86400000))+"&timee="+sdf.format(date.getTime());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String todayPostdetail ="";
			
			try {
				todayPostdetail = ElasticSearchUtil.sendPost("yys/qbsearchbycontentnum", todaydetail);
			} catch (Exception e) {
			
				e.printStackTrace();
			}
			JSONObject todayjsondetail=new JSONObject();
			todayjsondetail=JSONObject.parseObject(todayPostdetail);
			int today  = Integer.valueOf(todayjsondetail.get("count").toString());

			list.get(i).setTotalnum(total);

			list.get(i).setTodaynum(today);
		}
		
		PageInfo<SpiderSeed> page = new PageInfo<SpiderSeed>(list);
		
		mv.addObject("list", page);
		mv.addObject("seed_config_utils",seed_config_utils);
		mv.setViewName("website_infotest");
		return mv;
	}
	
	/*
	 * 查询种子
	 * @author wangyi
	 */
	@RequestMapping("/foreignlistseed")
	public ModelAndView foreignlistseed(ModelAndView mv,Integer websiteId,
			@RequestParam(value = "pageNum", defaultValue = "1" ,required = false)Integer pageNum) {

	     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	     String param ="";
			try {
				param = "otherwebsiteid="+websiteId;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String sendPost ="";
			
			try {
				sendPost = ElasticSearchUtil.sendPost("yys/qbsearchbycontentnum", param);
			} catch (Exception e) {
			
				e.printStackTrace();
			}
			JSONObject json=new JSONObject();
			json=JSONObject.parseObject(sendPost);
			Integer totalnum=Integer.valueOf(json.get("count").toString());
			mv.addObject("totalnum", totalnum);
			//今日采集量
			Date date=new Date();
			String todayparam ="";
			try {
				todayparam = "otherwebsiteid="+websiteId+"&times="+sdf.format((date.getTime()-86400000))+"&tmiee="+sdf.format(date.getTime());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String todayPost ="";
			
			try {
				todayPost = ElasticSearchUtil.sendPost("yys/qbsearchbycontentnum", todayparam);
			} catch (Exception e) {
			
				e.printStackTrace();
			}
			JSONObject todayjson=new JSONObject();
			todayjson=JSONObject.parseObject(todayPost);
			int todaynum  = Integer.valueOf(todayjson.get("count").toString());
			mv.addObject("todaynum", todaynum);
	     //当前网站的基本信息
	     SpiderWebsite spiderwebsite = spiderWebsiteService.getForeignInfo(websiteId);
	     mv.addObject("website", spiderwebsite);
	     List<SpiderSeedConfigUtilOb> seed_config_utils = seedConfigUtilService.getUtilByForeignWebSite(websiteId);
		//种子列表信息
		PageHelper.startPage(pageNum,10);
		List<SpiderSeed> list = seedService.foreignlistseed(websiteId);//种子列表
		for (int i = 0; i < list.size(); i++) {
			//总采集
			String pdetail ="";
			try {
				pdetail = "otherseedid="+list.get(i).getId();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String Postdetail ="";
			
			try {
				Postdetail = ElasticSearchUtil.sendPost("yys/qbsearchbycontentnum", pdetail);
			} catch (Exception e) {
			
				e.printStackTrace();
			}
			JSONObject jsonDetail=new JSONObject();
			jsonDetail=JSONObject.parseObject(Postdetail);
			Integer total=Integer.valueOf(jsonDetail.get("count").toString());
			//今日采集量
			String todaydetail ="";
			try {
				todaydetail = "otherseedid="+list.get(i).getId()+"&times="+sdf.format((date.getTime()-86400000))+"&timee="+sdf.format(date.getTime());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String todayPostdetail ="";
			
			try {
				todayPostdetail = ElasticSearchUtil.sendPost("yys/qbsearchbycontentnum", todaydetail);
			} catch (Exception e) {
			
				e.printStackTrace();
			}
			JSONObject todayjsondetail=new JSONObject();
			todayjsondetail=JSONObject.parseObject(todayPostdetail);
			int today  = Integer.valueOf(todayjsondetail.get("count").toString());
			list.get(i).setTotalnum(total);
			list.get(i).setTodaynum(today);
		}
		
		PageInfo<SpiderSeed> page = new PageInfo<SpiderSeed>(list);
		
		mv.addObject("list", page);
		mv.addObject("seed_config_utils",seed_config_utils);
		mv.setViewName("foreignwebsite_info");
		return mv;
	}
	
	
	
	/*
	 * 跳转到新增种子页面
	 * @author wangyi
	 */
	@RequestMapping("/toaddseed")
	public ModelAndView toaddseed(ModelAndView mv,HttpServletRequest request) {
		Integer websiteId = Integer.parseInt(request.getParameter("websiteId").toString());
		String websitename = request.getParameter("website_name").toString();
		mv.addObject("websiteId", websiteId);
		mv.addObject("websitename",websitename);
		mv.setViewName("build_seed");
		return mv;
	}
	
	
	/*
	 * 跳转到新增种子页面
	 * @author wangyi
	 */
	@RequestMapping("/toaddseedtest")
	public ModelAndView toaddseedtest(ModelAndView mv,HttpServletRequest request) {
		Integer websiteId = Integer.parseInt(request.getParameter("websiteId").toString());
		String websitename = request.getParameter("website_name").toString();
		mv.addObject("websiteId", websiteId);
		mv.addObject("websitename",websitename);
		mv.setViewName("build_seed_test");
		return mv;
	}
	
	/*
	 * 跳转到新增种子页面
	 * @author wangyi
	 */
	@RequestMapping("/toaddforeignseed")
	public ModelAndView toaddforeignseed(ModelAndView mv,HttpServletRequest request) {
		Integer websiteId = Integer.parseInt(request.getParameter("websiteId").toString());
		String websitename = request.getParameter("website_name").toString();
		mv.addObject("websiteId", websiteId);
		mv.addObject("websitename",websitename);
		mv.setViewName("build_foreignseed");
		return mv;
	}
	
	/*
	 * 跳转到新增策略页面
	 */
	@RequestMapping("/website_addtactics")
	public ModelAndView website_addtactics(ModelAndView mv,HttpServletRequest request) {
		Integer websiteId = Integer.parseInt(request.getParameter("websiteId").toString());
		String websitename = request.getParameter("website_name").toString();
		mv.addObject("websiteId", websiteId);
		mv.addObject("websitename",websitename);
		mv.setViewName("website_addtactics");
		return mv;
	}
	
	/*
	  * 跳转到查看种子列表页面
	 */
	@RequestMapping("/website_splist")
	public ModelAndView website_splist(ModelAndView mv,HttpServletRequest request,
			@RequestParam(value = "websiteId")Integer websiteId,
			@RequestParam(value = "website_name")String websitename,
			@RequestParam(value = "type", defaultValue = "0" ,required = false)Integer type,
			@RequestParam(value = "pageNum", defaultValue = "1" ,required = false)Integer pageNum) {
		PageHelper.startPage(pageNum,50);
		List<MappingSpider> list = seedConfigUtilService.getlist(websiteId,type);//种子列表
		PageInfo<MappingSpider> page = new PageInfo<MappingSpider>(list);
		List<SpiderSeedConfigUtilOb> seed_config_utils = seedConfigUtilService.getUtilByWebSite(websiteId);
		mv.addObject("list", page);
		mv.addObject("websiteId", websiteId);
		mv.addObject("type",type);
		mv.addObject("websitename",websitename);
		mv.addObject("seed_config_utils",seed_config_utils);
		mv.setViewName("website_splist");
		return mv;
		
	}
	
	
	
	
	
	/*
	 * 新增种子
	 * @author lujun
	 */
	@RequestMapping("/insertseed")
	@ResponseBody
	public ResultUtil insertseed(ModelAndView mv,HttpServletRequest request) {
		SpiderSeed spiderSeed = new SpiderSeed();
		spiderSeed.setSeed_flag(Constant.seedflag);//种子标记
		spiderSeed.setSeed_name(request.getParameter("seedname"));//种子任务名称
		spiderSeed.setSeed_status(0);//种子状态
		spiderSeed.setSeed_type(Integer.parseInt(request.getParameter("seedtype")));//种子类型
		spiderSeed.setWebsite_id(Integer.parseInt(request.getParameter("websiteId")));//网站ID
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		spiderSeed.setCreate_date(sdf.format(new Date()));//更新时间
//		spiderSeed.setSeed_article_type(Integer.parseInt(request.getParameter("seedarticletype")));//文章类型
		spiderSeed.setCreate_user_id(Constant.createid);//创建者ID
		spiderSeed.setDaili_flag(Integer.parseInt(request.getParameter("dailiflag")));
		spiderSeed.setSelenium_flag(Integer.parseInt(request.getParameter("seleniumflag")));
//		spiderSeed.setSeed_first_type(Integer.parseInt(request.getParameter("otherseedid")));
		
		int num=seedService.insertseed(spiderSeed);
		Integer seedid = spiderSeed.getId();//获得种子id
		SpiderSeedConfig spiderSeedConfig=new SpiderSeedConfig();
		spiderSeedConfig.setSeed_spider_type(Integer.parseInt(request.getParameter("seedspidertype")));
		spiderSeedConfig.setCreate_date(sdf.format(new Date()));//创建时间
		spiderSeedConfig.setCreate_user_id(Constant.createid);//创建者ID
		//种子时间配置
		spiderSeedConfig.setSeed_date_config(request.getParameter("seeddateconfig"));
		//种子所属的任务ID
		spiderSeedConfig.setSeed_id(seedid);
		//种子所属网站ID
		spiderSeedConfig.setWebsite_id(Integer.parseInt(request.getParameter("websiteId")));
		spiderSeedConfig.setSeed_interval_config(request.getParameter("seedintervalconfig"));//种子抓取间隔配置
		Integer seed_sleep_config = Integer.parseInt(request.getParameter("seedsleepconfig"));
		spiderSeedConfig.setSeed_sleep_config(seed_sleep_config);//种子睡眠时间
		spiderSeedConfig.setSeed_linkurl_config(request.getParameter("seedlinkurlconfig"));//种子路径配置
		spiderSeedConfig.setSeed_origin_config(request.getParameter("seedoriginconfig"));//种子来源配置
		
		spiderSeedConfig.setSeed_origin_url_config(request.getParameter("seedoriginurlconfig"));//种子来源URL配置
		spiderSeedConfig.setSeed_list_url(request.getParameter("seedurllist"));//种子列表链接
		spiderSeedConfig.setSeed_name(request.getParameter("seedname"));//种子地址名
		spiderSeedConfig.setSeed_author_config(request.getParameter("seedauthorconfig"));//种子作者
		spiderSeedConfig.setSeed_author_url_config(request.getParameter("seedauthorurlconfig"));//种子作者url
		spiderSeedConfig.setSeed_author_avatar_config(request.getParameter("seedauthoravatarconfig"));//种子作者头像
		spiderSeedConfig.setSeed_page_rule_config(request.getParameter("seedpageruleconfig"));//分页规则
		String json = request.getParameter("seedstoragejson");
		String[] split = json.split(",");
		JSONArray array = new JSONArray();
		for (int i = 0; i < split.length; i++) {
			JSONObject jb = new JSONObject();
			jb.put("type", split[i]);
			array.add(jb);
		}
		spiderSeedConfig.setSeed_text_config(request.getParameter("seedtextconfig"));//种子正文配置
		spiderSeedConfig.setSeed_storage_type(array.toJSONString());//存储类型
		spiderSeedConfig.setSeed_text_config_type(Integer.parseInt(request.getParameter("seedtextconfigtype")));//种子正文配置类型（1代表HTML，2代表JSON，3代表JSONB）
		spiderSeedConfig.setSeed_thread_config(Integer.parseInt(request.getParameter("seedthreadconfig")));//种子线程数量配置
//		spiderSeedConfig.setSeed_page_rule_config(request.getParameter("seedpageruleconfig"));//分页
		spiderSeedConfig.setSeed_title_config(request.getParameter("seedtitleconfig"));//种子标题配置
	    spiderSeedConfig.setSeed_date_config(request.getParameter("seeddateconfig"));//种子日期配置
		spiderSeedConfig.setSeed_url_config(request.getParameter("seedurlconfig"));//种子链接配置
		String otherseedid = request.getParameter("otherseedid");
		if(otherseedid != null && !"".equals(otherseedid)){
			spiderSeedConfig.setOther_seed_id(Integer.parseInt(otherseedid));
			spiderSeedConfig.setOther_website_id(Integer.parseInt(request.getParameter("otherwebsiteId")));
		}
		try {
			num=seedConfigService.insertSeedconfig(spiderSeedConfig);
		} catch (Exception e) {
			num = 0;
			seedService.removeSeedById(seedid);
		}
		if(num!=0) {
			return ResultUtil.build(200, "数据插入成功");
		}else{
			return ResultUtil.build(500, "数据插入失败");
		}
//		SpiderSeed spiderSeed = new SpiderSeed();
//		spiderSeed.setSeed_flag(Constant.seedflag);//种子标记
//		spiderSeed.setSeed_name(request.getParameter("seedname"));//种子任务名称
//		spiderSeed.setSeed_status(0);//种子状态
//		spiderSeed.setSeed_type(Integer.parseInt(request.getParameter("seedtype")));//种子类型
//		spiderSeed.setWebsite_id(Integer.parseInt(request.getParameter("websiteId")));//网站ID
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		spiderSeed.setCreate_date(sdf.format(new Date()));//更新时间
////		spiderSeed.setSeed_article_type(Integer.parseInt(request.getParameter("seedarticletype")));//文章类型
//		spiderSeed.setCreate_user_id(Constant.createid);//创建者ID
//		spiderSeed.setDaili_flag(Integer.parseInt(request.getParameter("dailiflag")));
//		spiderSeed.setSelenium_flag(Integer.parseInt(request.getParameter("seleniumflag")));
////		spiderSeed.setSeed_first_type(Integer.parseInt(request.getParameter("otherseedid")));
//		
//		int num=seedService.insertseed(spiderSeed);
//		Integer seedid = spiderSeed.getId();//获得种子id
//		SpiderSeedConfig spiderSeedConfig=new SpiderSeedConfig();
//		spiderSeedConfig.setSeed_spider_type(Integer.parseInt(request.getParameter("seedspidertype")));
//		spiderSeedConfig.setCreate_date(sdf.format(new Date()));//创建时间
//		spiderSeedConfig.setCreate_user_id(Constant.createid);//创建者ID
//		//种子时间配置
//		spiderSeedConfig.setSeed_date_config(request.getParameter("seeddateconfig"));
//		//种子所属的任务ID
//		spiderSeedConfig.setSeed_id(seedid);
//		//种子所属网站ID
//		spiderSeedConfig.setWebsite_id(Integer.parseInt(request.getParameter("websiteId")));
//		spiderSeedConfig.setSeed_interval_config(request.getParameter("seedintervalconfig"));//种子抓取间隔配置
//		Integer seed_sleep_config = Integer.parseInt(request.getParameter("seedsleepconfig"));
//		spiderSeedConfig.setSeed_sleep_config(seed_sleep_config);//种子睡眠时间
//		spiderSeedConfig.setSeed_linkurl_config(request.getParameter("seedlinkurlconfig"));//种子路径配置
//		spiderSeedConfig.setSeed_origin_config(request.getParameter("seedoriginconfig"));//种子来源配置
//		
//		spiderSeedConfig.setSeed_origin_url_config(request.getParameter("seedoriginurlconfig"));//种子来源URL配置
//		spiderSeedConfig.setSeed_list_url(request.getParameter("seedurllist"));//种子列表链接
//		spiderSeedConfig.setSeed_name(request.getParameter("seedname"));//种子地址名
//		spiderSeedConfig.setSeed_author_config(request.getParameter("seedauthorconfig"));//种子作者
//		spiderSeedConfig.setSeed_author_url_config(request.getParameter("seedauthorurlconfig"));//种子作者url
//		spiderSeedConfig.setSeed_author_avatar_config(request.getParameter("seedauthoravatarconfig"));//种子作者头像
////		spiderSeedConfig.setSeed_page_rule_config(request.getParameter("seedpageruleconfig"));//分页规则
//		String json = request.getParameter("seedstoragejson");
//		String[] split = json.split(",");
//		JSONArray array = new JSONArray();
//		for (int i = 0; i < split.length; i++) {
//			JSONObject jb = new JSONObject();
//			jb.put("type", split[i]);
//			array.add(jb);
//		}
//		spiderSeedConfig.setSeed_text_config(request.getParameter("seedtextconfig"));//种子正文配置
//		spiderSeedConfig.setSeed_storage_type(array.toJSONString());//存储类型
//		spiderSeedConfig.setSeed_text_config_type(Integer.parseInt(request.getParameter("seedtextconfigtype")));//种子正文配置类型（1代表HTML，2代表JSON，3代表JSONB）
//		spiderSeedConfig.setSeed_thread_config(Integer.parseInt(request.getParameter("seedthreadconfig")));//种子线程数量配置
//		spiderSeedConfig.setSeed_title_config(request.getParameter("seedtitleconfig"));//种子标题配置
//	    spiderSeedConfig.setSeed_date_config(request.getParameter("seeddateconfig"));//种子日期配置
//		spiderSeedConfig.setSeed_url_config(request.getParameter("seedurlconfig"));//种子链接配置
//		String otherseedid = request.getParameter("otherseedid");
//		if(otherseedid != null && !"".equals(otherseedid)){
//			spiderSeedConfig.setOther_seed_id(Integer.parseInt(otherseedid));
//			spiderSeedConfig.setOther_website_id(Integer.parseInt(request.getParameter("otherwebsiteId")));
//		}
//		try {
//			num=seedConfigService.insertSeedconfig(spiderSeedConfig);
//		} catch (Exception e) {
//			num = 0;
//			seedService.removeSeedById(seedid);
//		}
//		if(num!=0) {
//			return ResultUtil.build(200, "数据插入成功");
//		}else{
//			return ResultUtil.build(500, "数据插入失败");
//		}
	}
	
	
	
	
	/*
	 * 新增种子
	 * @author lujun
	 */
	@RequestMapping("/insertseedtest")
	@ResponseBody
	public ResultUtil insertseedtest(ModelAndView mv,HttpServletRequest request) {
		SpiderSeed spiderSeed = new SpiderSeed();
		spiderSeed.setSeed_flag(Constant.seedflag);//种子标记
		spiderSeed.setSeed_name(request.getParameter("seedname"));//种子任务名称
		spiderSeed.setSeed_status(0);//种子状态
		spiderSeed.setSeed_type(Integer.parseInt(request.getParameter("seedtype")));//种子类型
		spiderSeed.setWebsite_id(Integer.parseInt(request.getParameter("websiteId")));//网站ID
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		spiderSeed.setCreate_date(sdf.format(new Date()));//更新时间
//		spiderSeed.setSeed_article_type(Integer.parseInt(request.getParameter("seedarticletype")));//文章类型
		spiderSeed.setCreate_user_id(Constant.createid);//创建者ID
		spiderSeed.setDaili_flag(Integer.parseInt(request.getParameter("dailiflag")));
		spiderSeed.setSelenium_flag(Integer.parseInt(request.getParameter("seleniumflag")));
//		spiderSeed.setSeed_first_type(Integer.parseInt(request.getParameter("otherseedid")));
		
		int num=seedService.insertseedtest(spiderSeed);
		Integer seedid = spiderSeed.getId();//获得种子id
		SpiderSeedConfig spiderSeedConfig=new SpiderSeedConfig();
		spiderSeedConfig.setSeed_spider_type(Integer.parseInt(request.getParameter("seedspidertype")));
		spiderSeedConfig.setCreate_date(sdf.format(new Date()));//创建时间
		spiderSeedConfig.setCreate_user_id(Constant.createid);//创建者ID
		//种子时间配置
		spiderSeedConfig.setSeed_date_config(request.getParameter("seeddateconfig"));
		//种子所属的任务ID
		spiderSeedConfig.setSeed_id(seedid);
		//种子所属网站ID
		spiderSeedConfig.setWebsite_id(Integer.parseInt(request.getParameter("websiteId")));
		spiderSeedConfig.setSeed_interval_config(request.getParameter("seedintervalconfig"));//种子抓取间隔配置
		Integer seed_sleep_config = Integer.parseInt(request.getParameter("seedsleepconfig"));
		spiderSeedConfig.setSeed_sleep_config(seed_sleep_config);//种子睡眠时间
		spiderSeedConfig.setSeed_linkurl_config(request.getParameter("seedlinkurlconfig"));//种子路径配置
		spiderSeedConfig.setSeed_origin_config(request.getParameter("seedoriginconfig"));//种子来源配置
		
		spiderSeedConfig.setSeed_origin_url_config(request.getParameter("seedoriginurlconfig"));//种子来源URL配置
		spiderSeedConfig.setSeed_list_url(request.getParameter("seedurllist"));//种子列表链接
		spiderSeedConfig.setSeed_name(request.getParameter("seedname"));//种子地址名
		spiderSeedConfig.setSeed_author_config(request.getParameter("seedauthorconfig"));//种子作者
		spiderSeedConfig.setSeed_author_url_config(request.getParameter("seedauthorurlconfig"));//种子作者url
		spiderSeedConfig.setSeed_author_avatar_config(request.getParameter("seedauthoravatarconfig"));//种子作者头像
		spiderSeedConfig.setSeed_page_rule_config(request.getParameter("seedpageruleconfig"));//分页规则
		String json = request.getParameter("seedstoragejson");
		String[] split = json.split(",");
		JSONArray array = new JSONArray();
		for (int i = 0; i < split.length; i++) {
			JSONObject jb = new JSONObject();
			jb.put("type", split[i]);
			array.add(jb);
		}
		spiderSeedConfig.setSeed_text_config(request.getParameter("seedtextconfig"));//种子正文配置
		spiderSeedConfig.setSeed_storage_type(array.toJSONString());//存储类型
		spiderSeedConfig.setSeed_text_config_type(Integer.parseInt(request.getParameter("seedtextconfigtype")));//种子正文配置类型（1代表HTML，2代表JSON，3代表JSONB）
		spiderSeedConfig.setSeed_thread_config(Integer.parseInt(request.getParameter("seedthreadconfig")));//种子线程数量配置
//		spiderSeedConfig.setSeed_page_rule_config(request.getParameter("seedpageruleconfig"));//分页
		spiderSeedConfig.setSeed_title_config(request.getParameter("seedtitleconfig"));//种子标题配置
	    spiderSeedConfig.setSeed_date_config(request.getParameter("seeddateconfig"));//种子日期配置
		spiderSeedConfig.setSeed_url_config(request.getParameter("seedurlconfig"));//种子链接配置
		String otherseedid = request.getParameter("otherseedid");
		if(otherseedid != null && !"".equals(otherseedid)){
			spiderSeedConfig.setOther_seed_id(Integer.parseInt(otherseedid));
			spiderSeedConfig.setOther_website_id(Integer.parseInt(request.getParameter("otherwebsiteId")));
		}
		try {
			num=seedConfigService.insertSeedconfigtest(spiderSeedConfig);
		} catch (Exception e) {
			num = 0;
			seedService.removeSeedById(seedid);
		}
		if(num!=0) {
			return ResultUtil.build(200, "数据插入成功");
		}else{
			return ResultUtil.build(500, "数据插入失败");
		}
	}
	
	
	/*
	 * 新增种子 备份
	 * @author lujun
	 */
//	@RequestMapping("/insertseed")
//	@ResponseBody
//	public ResultUtil insertseed(ModelAndView mv,HttpServletRequest request) {
//		SpiderSeed spiderSeed = new SpiderSeed();
//		spiderSeed.setSeed_flag(Constant.seedflag);//种子标记
//		spiderSeed.setSeed_name(request.getParameter("seedname"));//种子任务名称
//		spiderSeed.setSeed_status(0);//种子状态
//		spiderSeed.setSeed_type(Integer.parseInt(request.getParameter("seedtype")));//种子类型
//		spiderSeed.setWebsite_id(Integer.parseInt(request.getParameter("websiteId")));//网站ID
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		spiderSeed.setCreate_date(sdf.format(new Date()));//更新时间
////		spiderSeed.setSeed_article_type(Integer.parseInt(request.getParameter("seedarticletype")));//文章类型
//		spiderSeed.setCreate_user_id(Constant.createid);//创建者ID
//		spiderSeed.setDaili_flag(Integer.parseInt(request.getParameter("dailiflag")));
//		spiderSeed.setSelenium_flag(Integer.parseInt(request.getParameter("seleniumflag")));
////		spiderSeed.setSeed_first_type(Integer.parseInt(request.getParameter("otherseedid")));
//		
//		int num=seedService.insertseed(spiderSeed);
//		Integer seedid = spiderSeed.getId();//获得种子id
//		SpiderSeedConfig spiderSeedConfig=new SpiderSeedConfig();
//		spiderSeedConfig.setSeed_spider_type(Integer.parseInt(request.getParameter("seedspidertype")));
//		spiderSeedConfig.setCreate_date(sdf.format(new Date()));//创建时间
//		spiderSeedConfig.setCreate_user_id(Constant.createid);//创建者ID
//		//种子时间配置
//		spiderSeedConfig.setSeed_date_config(request.getParameter("seeddateconfig"));
//		//种子所属的任务ID
//		spiderSeedConfig.setSeed_id(seedid);
//		//种子所属网站ID
//		spiderSeedConfig.setWebsite_id(Integer.parseInt(request.getParameter("websiteId")));
//		spiderSeedConfig.setSeed_interval_config(request.getParameter("seedintervalconfig"));//种子抓取间隔配置
//		Integer seed_sleep_config = Integer.parseInt(request.getParameter("seedsleepconfig"));
//		spiderSeedConfig.setSeed_sleep_config(seed_sleep_config);//种子睡眠时间
//		spiderSeedConfig.setSeed_linkurl_config(request.getParameter("seedlinkurlconfig"));//种子路径配置
//		spiderSeedConfig.setSeed_origin_config(request.getParameter("seedoriginconfig"));//种子来源配置
//		
//		spiderSeedConfig.setSeed_origin_url_config(request.getParameter("seedoriginurlconfig"));//种子来源URL配置
//		spiderSeedConfig.setSeed_list_url(request.getParameter("seedurllist"));//种子列表链接
//		spiderSeedConfig.setSeed_name(request.getParameter("seedname"));//种子地址名
//		String json = request.getParameter("seedstoragejson");
//		String[] split = json.split(",");
//		JSONArray array = new JSONArray();
//		for (int i = 0; i < split.length; i++) {
//			JSONObject jb = new JSONObject();
//			jb.put("type", split[i]);
//			array.add(jb);
//		}
//		spiderSeedConfig.setSeed_text_config(request.getParameter("seedtextconfig"));//种子正文配置
//		spiderSeedConfig.setSeed_storage_type(array.toJSONString());//存储类型
//		spiderSeedConfig.setSeed_text_config_type(Integer.parseInt(request.getParameter("seedtextconfigtype")));//种子正文配置类型（1代表HTML，2代表JSON，3代表JSONB）
//		spiderSeedConfig.setSeed_thread_config(Integer.parseInt(request.getParameter("seedthreadconfig")));//种子线程数量配置
//		spiderSeedConfig.setSeed_title_config(request.getParameter("seedtitleconfig"));//种子标题配置
//	    spiderSeedConfig.setSeed_date_config(request.getParameter("seeddateconfig"));//种子日期配置
//		spiderSeedConfig.setSeed_url_config(request.getParameter("seedurlconfig"));//种子链接配置
//		String otherseedid = request.getParameter("otherseedid");
//		if(otherseedid != null && !"".equals(otherseedid)){
//			spiderSeedConfig.setOther_seed_id(Integer.parseInt(otherseedid));
//			spiderSeedConfig.setOther_website_id(Integer.parseInt(request.getParameter("otherwebsiteId")));
//		}
//		try {
//			num=seedConfigService.insertSeedconfig(spiderSeedConfig);
//		} catch (Exception e) {
//			num = 0;
//			seedService.removeSeedById(seedid);
//		}
//		if(num!=0) {
//			return ResultUtil.build(200, "数据插入成功");
//		}else{
//			return ResultUtil.build(500, "数据插入失败");
//		}
//	}
	
	/*
	 * 新增外国网站种子
	 * @author lujun
	 */
	@RequestMapping("/insertforeignseed")
	@ResponseBody
	public ResultUtil insertforeignseed(ModelAndView mv,HttpServletRequest request) {
		SpiderSeed spiderSeed = new SpiderSeed();
		spiderSeed.setSeed_flag(Constant.seedflag);//种子标记
		spiderSeed.setSeed_name(request.getParameter("seedname"));//种子任务名称
		spiderSeed.setSeed_status(0);//种子状态
		spiderSeed.setSeed_type(Integer.parseInt(request.getParameter("seedtype")));//种子类型
		spiderSeed.setWebsite_id(Integer.parseInt(request.getParameter("websiteId")));//网站ID
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		spiderSeed.setCreate_date(sdf.format(new Date()));//更新时间
		spiderSeed.setSeed_article_type(Integer.parseInt(request.getParameter("seedarticletype")));//文章类型
		spiderSeed.setCreate_user_id(Constant.createid);//创建者ID
		spiderSeed.setDaili_flag(Integer.parseInt(request.getParameter("dailiflag")));
		spiderSeed.setSelenium_flag(Integer.parseInt(request.getParameter("seleniumflag")));
		spiderSeed.setSeed_first_type(Integer.parseInt(request.getParameter("otherseedid")));
		spiderSeed.setVpn_flag(Integer.parseInt(request.getParameter("vpnflag")));
		int num=seedService.insertforeignseed(spiderSeed);
		
		Integer seedid = spiderSeed.getId();//获得种子id
		SpiderSeedConfig spiderSeedConfig=new SpiderSeedConfig();
		spiderSeedConfig.setSeed_spider_type(Integer.parseInt(request.getParameter("seedspidertype")));
		spiderSeedConfig.setCreate_date(sdf.format(new Date()));//创建时间
		spiderSeedConfig.setCreate_user_id(Constant.createid);//创建者ID
		//种子时间配置
		spiderSeedConfig.setSeed_date_config(request.getParameter("seeddateconfig"));
		//种子所属的任务ID
		spiderSeedConfig.setSeed_id(seedid);
		//种子所属网站ID
		spiderSeedConfig.setWebsite_id(Integer.parseInt(request.getParameter("websiteId")));
		spiderSeedConfig.setSeed_interval_config(request.getParameter("seedintervalconfig"));//种子抓取间隔配置
		Integer seed_sleep_config = Integer.parseInt(request.getParameter("seedsleepconfig"));
		spiderSeedConfig.setSeed_sleep_config(seed_sleep_config);//种子睡眠时间
		spiderSeedConfig.setSeed_linkurl_config(request.getParameter("seedlinkurlconfig"));//种子路径配置
		spiderSeedConfig.setSeed_origin_config(request.getParameter("seedoriginconfig"));//种子来源配置
		
		spiderSeedConfig.setSeed_origin_url_config(request.getParameter("seedoriginurlconfig"));//种子来源URL配置
		spiderSeedConfig.setSeed_list_url(request.getParameter("seedurllist"));//种子列表链接
		spiderSeedConfig.setSeed_name(request.getParameter("seedname"));//种子地址名
		String json = request.getParameter("seedstoragejson");
		String[] split = json.split(",");
		JSONArray array = new JSONArray();
		for (int i = 0; i < split.length; i++) {
			JSONObject jb = new JSONObject();
			jb.put("type", split[i]);
			array.add(jb);
		}
		spiderSeedConfig.setSeed_text_config(request.getParameter("seedtextconfig"));//种子正文配置
		spiderSeedConfig.setSeed_storage_type(array.toJSONString());//存储类型
		spiderSeedConfig.setSeed_text_config_type(Integer.parseInt(request.getParameter("seedtextconfigtype")));//种子正文配置类型（1代表HTML，2代表JSON，3代表JSONB）
		spiderSeedConfig.setSeed_thread_config(Integer.parseInt(request.getParameter("seedthreadconfig")));//种子线程数量配置
		spiderSeedConfig.setSeed_title_config(request.getParameter("seedtitleconfig"));//种子标题配置
	    spiderSeedConfig.setSeed_date_config(request.getParameter("seeddateconfig"));//种子日期配置
		spiderSeedConfig.setSeed_url_config(request.getParameter("seedurlconfig"));//种子链接配置
		String otherseedid = request.getParameter("otherseedid");
		if(otherseedid != null && !"".equals(otherseedid)){
			spiderSeedConfig.setOther_seed_id(Integer.parseInt(otherseedid));
			spiderSeedConfig.setOther_website_id(Integer.parseInt(request.getParameter("otherwebsiteId")));
		}
		try {
			num=seedConfigService.insertForeignSeedconfig(spiderSeedConfig);
		} catch (Exception e) {
			num = 0;
			seedService.removeForeignSeedById(seedid);
		}
		if(num!=0) {
			return ResultUtil.build(200, "数据插入成功");
		}else{
			return ResultUtil.build(500, "数据插入失败");
		}
	}
	
	
	/*
	 * 修改种子
	 * @author lujun
	 */
	@RequestMapping("/updateSeed")
	@ResponseBody
	public ResultUtil updateSeed(ModelAndView mv,HttpServletRequest request) {
		SpiderSeed spiderSeed = new SpiderSeed();
		spiderSeed.setId(Integer.parseInt(request.getParameter("id")));
		spiderSeed.setSeed_flag(2);//种子标记,2代表修改其他信息
		spiderSeed.setSeed_name(request.getParameter("seedname"));//种子任务名称
		//spiderSeed.setSeed_status(Integer.parseInt("seedstatus"));//种子状态
		spiderSeed.setSeed_type(Integer.parseInt(request.getParameter("seedtype")));//种子类型
		//spiderSeed.setWebsite_id(Integer.parseInt(request.getParameter("websiteId")));//网站ID
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		spiderSeed.setCreate_date(sdf.format(new Date()));//更新时间
		//spiderSeed.setCreate_user_id(Constant.createid);//创建者ID
		int num=seedService.updateSeed(spiderSeed);
		if(num>0) {
			return ResultUtil.build(200, "数据修改成功");
		}else{
			return ResultUtil.build(500, "数据修改失败");
		}
	}
	
	
	@RequestMapping("/startsMapping")
	@ResponseBody
	public ResultUtil startsMapping(ModelAndView mv,HttpServletRequest request) {
		int websiteId = Integer.parseInt(request.getParameter("websiteId"));
		int num=spiderWebsiteService.startsMapping(websiteId);//修改种子状态
		if(num!=0) {
			return ResultUtil.build(200, "数据修改成功");
		}else{
			return ResultUtil.build(500, "数据修改失败");
		}		
	}
	
	
	
	
	@RequestMapping("/startCheckMapping")
	@ResponseBody
	public ResultUtil startCheckMapping(ModelAndView mv,HttpServletRequest request) {
		int websiteId = Integer.parseInt(request.getParameter("websiteId"));
		int num=spiderWebsiteService.startCheckMapping(websiteId);//修改种子状态
		if(num!=0) {
			return ResultUtil.build(200, "数据修改成功");
		}else{
			return ResultUtil.build(500, "数据修改失败");
		}		
	}
	
	/**
	 * 修改种子状态
	 * @param mv
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateSeedflag")
	@ResponseBody
	public ResultUtil updateSeedflag(ModelAndView mv,HttpServletRequest request) {
		SpiderSeed spiderSeed = new SpiderSeed();
		spiderSeed.setId(Integer.parseInt(request.getParameter("id")));
		int status = Integer.parseInt(request.getParameter("status"));
		if(status==1) {
			spiderSeed.setSeed_flag(1);//修改为1代表关闭，
			spiderSeed.setSeed_status(0);//状态为0代表关闭
		}else {
			spiderSeed.setSeed_flag(0);//修改为1代表关闭
			spiderSeed.setSeed_status(1);//状态1代表开启
		}
		int num=seedService.updateSeedstatus(spiderSeed);//修改种子状态
		if(num!=0) {
			return ResultUtil.build(200, "数据修改成功");
		}else{
			return ResultUtil.build(500, "数据修改失败");
		}			
	}
	
	
	/**
	 * 修改种子状态
	 * @param mv
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateSeedflagtest")
	@ResponseBody
	public ResultUtil updateSeedflagtest(ModelAndView mv,HttpServletRequest request) {
		SpiderSeed spiderSeed = new SpiderSeed();
		spiderSeed.setId(Integer.parseInt(request.getParameter("id")));
		int status = Integer.parseInt(request.getParameter("status"));
		if(status==1) {
			spiderSeed.setSeed_flag(1);//修改为1代表关闭，
			spiderSeed.setSeed_status(0);//状态为0代表关闭
		}else {
			spiderSeed.setSeed_flag(0);//修改为1代表关闭
			spiderSeed.setSeed_status(1);//状态1代表开启
		}
		int num=seedService.updateSeedstatustest(spiderSeed);//修改种子状态
		if(num!=0) {
			return ResultUtil.build(200, "数据修改成功");
		}else{
			return ResultUtil.build(500, "数据修改失败");
		}		
	}
	
	
	/**
	 * 修改种子状态
	 * @param mv
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateforeignSeedflag")
	@ResponseBody
	public ResultUtil updateforeignSeedflag(ModelAndView mv,HttpServletRequest request) {
		SpiderSeed spiderSeed = new SpiderSeed();
		spiderSeed.setId(Integer.parseInt(request.getParameter("id")));
		int status = Integer.parseInt(request.getParameter("status"));
		if(status==1) {
			spiderSeed.setSeed_flag(1);//修改为1代表关闭，
			spiderSeed.setSeed_status(0);//状态为0代表关闭
		}else {
			spiderSeed.setSeed_flag(0);//修改为1代表关闭
			spiderSeed.setSeed_status(1);//状态1代表开启
		}
		int num=seedService.updateForeignSeedstatus(spiderSeed);//修改种子状态
		
		if(num!=0) {
			return ResultUtil.build(200, "数据修改成功");
		}else{
			return ResultUtil.build(500, "数据修改失败");
		}		
	}
	
	/**
	 * 得到种子详情
	 * @param mv
	 * @param seedid
	 * @param pageNum
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/getseeddetail")
	public ModelAndView getseeddetail(ModelAndView mv,Integer seedid,Integer websiteId,
			@RequestParam(value = "pageNum", defaultValue = "1" ,required = false)Integer pageNum) throws IOException {
		//今日采集量
	     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	     SpiderCount count = new SpiderCount();
	     count.setSeed_id(seedid);
	     //------------------------------------------------------------------
	     String words= screenSeedService.getStringList();
	     System.err.println(words);
			String param ="";
			Date date=new Date();
			
			try {
				param = "?keyword="+URLEncoder.encode(words, "utf-8")+"&times="+(date.getTime()-86400000)+"&tmiee="+date.getTime();
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String sendPost ="";
			
			try {
				sendPost = ElasticSearchUtil.sendPost("yys/qbwebsiteidnum", param);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(sendPost);
	     //--------------------------------------------------

			//总采集量
			SimpleDateFormat sdfnum = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String params ="";
			try {
				params = "?otherwebsiteid="+websiteId+"&otherseedid="+seedid;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String sendPosts ="";
			
			try {
				sendPosts = ElasticSearchUtil.sendPost("yys/qbsearchbycontentnum", params);
			} catch (Exception e) {
			
				e.printStackTrace();
			}
			JSONObject json=new JSONObject();
			json=JSONObject.parseObject(sendPosts);
			Integer totalnum=Integer.valueOf(json.get("count").toString());
//			System.err.println(totalnum);
			mv.addObject("totalnum", totalnum);
			//今日采集量
			String todayparam ="";
			try {
				todayparam = "?otherwebsiteid="+websiteId+"&otherseedid="+seedid+"&times="+sdfnum.format((date.getTime()-86400000))+"&tmiee="+sdfnum.format(date.getTime());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String todayPost ="";
			
			try {
				todayPost = ElasticSearchUtil.sendPost("yys/qbsearchbycontentnum", todayparam);
			} catch (Exception e) {
			
				e.printStackTrace();
			}
			JSONObject todayjson=new JSONObject();
			
			todayjson=JSONObject.parseObject(todayPost);
			int todaynum  = Integer.valueOf(todayjson.get("count").toString());
//			System.err.println(todaynum);
			mv.addObject("todaynum", todaynum);
	   //--------------------------------------------------
	     //当前种子的基本信息
	     SpiderSeed seed = seedService.getInfo(seedid);
	     mv.addObject("seed", seed);
		/*
		 * PageHelper.startPage(pageNum,10); List<SpiderArticle> list =
		 * spiderArticleService.listArticle(seedid); PageInfo<SpiderArticle> page = new
		 * PageInfo<SpiderArticle>(list);*/
		 
	     Map<String, String> map = new HashMap<>();
	     map.put("page", String.valueOf(pageNum));
	     map.put("size", "10");
	     map.put("websiteid", String.valueOf(websiteId));
	     map.put("otherseedid", String.valueOf(seedid));
	     Map<String, Object> bryes = EsUtils.bryes(map);
	     
	     mv.addObject("list", bryes);
	     mv.setViewName("seed_detail");
		return mv;
		
	}
	
	
	
	
	/**
	 * 得到种子详情
	 * @param mv
	 * @param seedid
	 * @param pageNum
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/getseeddetailtest")
	public ModelAndView getseeddetailtest(ModelAndView mv,Integer seedid,Integer websiteId,
			@RequestParam(value = "pageNum", defaultValue = "1" ,required = false)Integer pageNum) throws IOException {
		//今日采集量
	     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	     SpiderCount count = new SpiderCount();
	     count.setSeed_id(seedid);
	     //------------------------------------------------------------------
	     String words= screenSeedService.getStringList();
	     System.err.println(words);
			String param ="";
			Date date=new Date();
			
			try {
				param = "?keyword="+URLEncoder.encode(words, "utf-8")+"&times="+(date.getTime()-86400000)+"&tmiee="+date.getTime()+"&page="+pageNum;
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String sendPost ="";
			
			try {
				sendPost = ElasticSearchUtil.sendPost("yys/qbwebsiteidnum", param);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(sendPost);
	     //--------------------------------------------------

			//总采集量
			SimpleDateFormat sdfnum = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String params ="";
			try {
				params = "?otherwebsiteid="+websiteId+"&otherseedid="+seedid;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String sendPosts ="";
			
			try {
				sendPosts = ElasticSearchUtil.sendPost("yys/qbsearchbycontentnum", params);
			} catch (Exception e) {
			
				e.printStackTrace();
			}
			JSONObject json=new JSONObject();
			json=JSONObject.parseObject(sendPosts);
			Integer totalnum=Integer.valueOf(json.get("count").toString());
//			System.err.println(totalnum);
			mv.addObject("totalnum", totalnum);
			//今日采集量
			String todayparam ="";
			try {
				todayparam = "?otherwebsiteid="+websiteId+"&otherseedid="+seedid+"&times="+sdfnum.format((date.getTime()-86400000))+"&tmiee="+sdfnum.format(date.getTime());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String todayPost ="";
			
			try {
				todayPost = ElasticSearchUtil.sendPost("yys/qbsearchbycontentnum", todayparam);
			} catch (Exception e) {
			
				e.printStackTrace();
			}
			JSONObject todayjson=new JSONObject();
			
			todayjson=JSONObject.parseObject(todayPost);
			int todaynum  = Integer.valueOf(todayjson.get("count").toString());
//			System.err.println(todaynum);
			mv.addObject("todaynum", todaynum);
	   //--------------------------------------------------
	     //当前种子的基本信息
	     SpiderSeed seed = seedService.getInfotest(seedid);
	     mv.addObject("seed", seed);
		/*
		 * PageHelper.startPage(pageNum,10); List<SpiderArticle> list =
		 * spiderArticleService.listArticle(seedid); PageInfo<SpiderArticle> page = new
		 * PageInfo<SpiderArticle>(list);*/
		 
	     Map<String, String> map = new HashMap<>();
	     map.put("page", String.valueOf(pageNum));
	     map.put("size", "10");
	     map.put("websiteid", String.valueOf(websiteId));
	     map.put("otherseedid", String.valueOf(seedid));
	     Map<String, Object> bryes = EsUtils.bryes(map);
	     
	     mv.addObject("list", bryes);
	     mv.addObject("pageNum", pageNum);
	     mv.setViewName("seed_detail_test");
		return mv;
		
	}


	
	
	/**
	 * 得到种子详情
	 * @param mv
	 * @param seedid
	 * @param pageNum
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/getforeignseeddetail")
	public ModelAndView getforeignseeddetail(ModelAndView mv,Integer seedid,Integer websiteId,
			@RequestParam(value = "pageNum", defaultValue = "1" ,required = false)Integer pageNum) throws IOException {
		//今日采集量
	     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	     SpiderCount count = new SpiderCount();
	     count.setSeed_id(seedid);
	     //------------------------------------------------------------------
	     String words= screenSeedService.getforeignStringList();
	     System.err.println(words);
			String param ="";
			Date date=new Date();
			try {
				param = "?keyword="+URLEncoder.encode(words, "utf-8")+"&times="+(date.getTime()-86400000)+"&tmiee="+date.getTime();
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String sendPost ="";
			
			try {
				sendPost = ElasticSearchUtil.sendPost("yys/qbwebsiteidnum", param);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(sendPost);
	     //--------------------------------------------------

			//总采集量
			SimpleDateFormat sdfnum = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String params ="";
			try {
				params = "?otherwebsiteid="+websiteId+"&otherseedid="+seedid;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String sendPosts ="";
			
			try {
				sendPosts = ElasticSearchUtil.sendPost("yys/qbsearchbycontentnum", params);
			} catch (Exception e) {
			
				e.printStackTrace();
			}
			JSONObject json=new JSONObject();
			json=JSONObject.parseObject(sendPosts);
			Integer totalnum=Integer.valueOf(json.get("count").toString());
			mv.addObject("totalnum", totalnum);
			//今日采集量
			String todayparam ="";
			try {
				todayparam = "?otherwebsiteid="+websiteId+"&otherseedid="+seedid+"&times="+sdfnum.format((date.getTime()-86400000))+"&tmiee="+sdfnum.format(date.getTime());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String todayPost ="";
			
			try {
				todayPost = ElasticSearchUtil.sendPost("yys/qbsearchbycontentnum", todayparam);
			} catch (Exception e) {
			
				e.printStackTrace();
			}
			JSONObject todayjson=new JSONObject();
			todayjson=JSONObject.parseObject(todayPost);
			int todaynum  = Integer.valueOf(todayjson.get("count").toString());
			mv.addObject("todaynum", todaynum);
	   //--------------------------------------------------
	     //当前种子的基本信息
	     SpiderSeed seed = seedService.getforeignInfo(seedid);
	     mv.addObject("seed", seed);
		/*
		 * PageHelper.startPage(pageNum,10); List<SpiderArticle> list =
		 * spiderArticleService.listArticle(seedid); PageInfo<SpiderArticle> page = new
		 * PageInfo<SpiderArticle>(list);*/
		 
	     Map<String, String> map = new HashMap<>();
	     map.put("page", String.valueOf(pageNum));
	     map.put("size", "10");
	     map.put("websiteid", String.valueOf(websiteId));
	     map.put("otherseedid", String.valueOf(seedid));
	     Map<String, Object> bryes = EsUtils.bryes(map);
	     
	     mv.addObject("list", bryes);
	     mv.setViewName("foreignseed_detail");
		return mv;
		
	}
	
	
	/**
	 * 得到关键词详情
	 * @param mv
	 * @param keyword
	 * @param pageNum
	 * @return
	 */
	@RequestMapping("/getkeyworddetail")
	public ModelAndView getkeyworddetail(ModelAndView mv,Integer keywordid,
			@RequestParam(value = "pageNum", defaultValue = "1" ,required = false)Integer pageNum) {
	     //当前种子的基本信息
	     KeyWord keyword = seedService.getKeywordById(keywordid);
	     mv.addObject("keyword", keyword);
	     PageHelper.startPage(pageNum,10);
	     List<SpiderArticle> list = spiderArticleService.listArticle(keywordid);
	     PageInfo<SpiderArticle> page = new PageInfo<SpiderArticle>(list);
	     mv.addObject("list", page);
	     mv.setViewName("keyword_detail");
		return mv;
		
	}
	
	@RequestMapping(value = "/seeseedlogs", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Map getlogs(@RequestParam(value = "spider_seed_id", required = false) String spider_seed_id,
			@RequestParam(value = "spider_website_id", required = false) Integer spider_website_id,
			@RequestParam(value = "spider_logger_level", required = false) Integer spider_logger_level,
			@RequestParam(value = "pageNum", required = false) Integer pageNum) {
//		Map map = new HashMap<>();
//		if(spider_seed_id != null){
//			map.put("spider_seed_id", spider_seed_id);
//		}
//		if(spider_website_id != null){
//			map.put("spider_website_id", spider_website_id);
//		}
//		if(spider_logger_level != null){
//			map.put("spider_logger_level", spider_logger_level);
//		}
//		if(pageNum != null){
//			map.put("pageNum", pageNum);
//		}
//		
//		JSONArray jsonArray = new JSONArray();
//		try {
//			String result= HttpUtil.sendpost("http://106.12.90.154/EsMongo/log/seedetails", map);
//			jsonArray = JSONArray.parseArray("["+result+"]");
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}
//		return jsonArray;
		CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;
        JSONObject jsonObject = null;
        
        try {
            //请求发起客户端
            httpclient = HttpClients.createDefault();
            //参数集合
            List postParams = new ArrayList<>();
            if(spider_seed_id != null){
            	postParams.add(new BasicNameValuePair("spider_seed_id", spider_seed_id));
            }
            if(spider_website_id != null){
            	postParams.add(new BasicNameValuePair("spider_website_id", spider_website_id.toString()));
            }
            if(spider_logger_level != null){
            	postParams.add(new BasicNameValuePair("spider_logger_level", spider_logger_level.toString()));
            }
            if(pageNum!=null){
            	postParams.add(new BasicNameValuePair("pageNum", pageNum.toString()));
            }
            //通过post方式访问
            HttpPost post = new HttpPost("http://192.168.71.21:8080/EsMongo/log/seedetails");
//            spider_seed_id
            HttpEntity paramEntity = new UrlEncodedFormEntity(postParams,"UTF-8");
            post.setEntity(paramEntity);
            response = httpclient.execute(post);
            HttpEntity valueEntity = response.getEntity();
            String content = EntityUtils.toString(valueEntity);
            jsonObject = JSONObject.parseObject(content);
            
            return jsonObject;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(httpclient != null){
                try {
                    httpclient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(response != null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return jsonObject;
	}
	
	/**
	 * 	手动匹配策略
	 */
	@RequestMapping(value = "/change/method", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public int detail(int configId,int MappingSpiderId) {
		int result = 500;
		if(configId!=0) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("configId", configId);
			map.put("MappingSpiderId", MappingSpiderId);
			result = seedConfigUtilService.updateMappingSpider(map);
		}
		
		return result;
	}
	
	/**
	 * 	修改种子名称
	 */
	@RequestMapping(value = "/change/urlname", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public int urlname(String urlname,int MappingSpiderId) {
		int result = 500;
		if(urlname!=null&&urlname!="") {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("url_name", urlname);
			map.put("MappingSpiderId", MappingSpiderId);
			result = seedConfigUtilService.updateUrlName(map);
		}
		
		return result;
	}
	
	/**
	 * 	跳转添加种子URL
	 */
	@RequestMapping("/addurl/urlname")
	public ModelAndView addurl(ModelAndView mv,Integer websiteId, String websitename) {
	     //当前种子的基本信息
	     mv.addObject("websiteId", websiteId);
	     mv.addObject("websitename", websitename);
	     mv.setViewName("to_add_url");
		return mv;
		
	}
	
	/**
	 * 	添加种子URL
	 */
	@RequestMapping(value = "/change/url", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public int changeurlname(String urlname,int websiteId,String url) {
		int result = 500;
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("url_name", urlname);
			map.put("url", url);
			map.put("websiteId", websiteId);
			result = seedConfigUtilService.insertUrlName(map);
		} catch (Exception e) {
			// TODO: handle exception
			e.getMessage();
		}
		
		
		return result;
	}
}
