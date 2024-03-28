package com.stonedt.spider.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.stonedt.spider.entity.SpiderSeed;
import com.stonedt.spider.entity.SpiderSeedConfig;
import com.stonedt.spider.service.SeedConfigService;
import com.stonedt.spider.service.SeedService;
import com.stonedt.spider.util.ResultUtil;

@Controller
@RequestMapping("/")
public class SeedConfigController {
	@Autowired
	private SeedConfigService seedConfigService;
	@Autowired
	private SeedService seedService;
	/*
	 * 查询种子配置
	 * @author wangyi
	 */
	@RequestMapping("/listseedconfig")
	@ResponseBody
	public ModelAndView listseedconfig(Integer seedId,
			@RequestParam(value = "pageNum", defaultValue = "1" ,required = false)Integer pageNum,ModelAndView mv) {
		PageHelper.startPage(pageNum,10);
		List<SpiderSeedConfig> list = seedConfigService.listseedconfig(seedId);
		PageInfo<SpiderSeedConfig> page = new PageInfo<SpiderSeedConfig>(list);
		mv.addObject("list", page);
		//ModelAndView mv = new ModelAndView("list");
		mv.addObject("list", list);
		return mv;
	}
	/*
	 * 新增种子配置
	 * @author lujun
	 */
	@RequestMapping("/insertSeedconfig")
	@ResponseBody
	public ResultUtil insertSeedconfig(ModelAndView mv,HttpServletRequest request) {
		SpiderSeedConfig spiderSeedConfig=new SpiderSeedConfig();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		spiderSeedConfig.setCreate_date(sdf.format(new Date()));//创建时间
		spiderSeedConfig.setCreate_user_id(Constant.createid);//创建者ID
		
		//种子时间配置
		spiderSeedConfig.setSeed_date_config(request.getParameter("seeddateconfig"));
		//种子所属的任务ID
		spiderSeedConfig.setSeed_id(Integer.parseInt(request.getParameter("seedId")));
		spiderSeedConfig.setSeed_interval_config(request.getParameter("seedintervalconfig"));//种子抓取间隔配置
		spiderSeedConfig.setSeed_list_url(request.getParameter("seedurllist"));//种子列表链接
		spiderSeedConfig.setSeed_name(request.getParameter("seedname"));//种子地址名
		spiderSeedConfig.setSeed_text_config(request.getParameter("seedtextconfig"));//种子正文配置
		spiderSeedConfig.setSeed_text_config_type(Integer.parseInt(request.getParameter("seedtextconfigtype")));//种子正文配置类型（1代表HTML，2代表JSON，3代表JSONB）
		spiderSeedConfig.setSeed_thread_config(Integer.parseInt(request.getParameter("seedthreadconfig")));//种子线程数量配置
		spiderSeedConfig.setSeed_title_config(request.getParameter("seedtitleconfig"));//种子标题配置
		spiderSeedConfig.setSeed_url_config(request.getParameter("seedurlconfig"));//种子链接配置
		int num=seedConfigService.insertSeedconfig(spiderSeedConfig);
		if(num!=0) {
			return ResultUtil.build(200, "数据插入成功");
		}else{
			return ResultUtil.build(500, "数据插入失败");
		}
	}
	/*
	 * 修改种子配置
	 * @author lujun
	 */
	@RequestMapping("/updateSeedConfig")
	@ResponseBody
	public ResultUtil updateSeedConfig(ModelAndView mv,HttpServletRequest request) {
		SpiderSeedConfig spiderSeedConfig=new SpiderSeedConfig();
		SpiderSeed seed = new SpiderSeed();
		//spiderSeedConfig.setSeed_id(Integer.parseInt(request.getParameter("id")));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		spiderSeedConfig.setCreate_date(sdf.format(new Date()));//创建时间
		spiderSeedConfig.setCreate_user_id(Constant.createid);//创建者ID
		spiderSeedConfig.setSeed_spider_type(Integer.parseInt(request.getParameter("seedspidertype")));//设置
		//种子时间配置
		spiderSeedConfig.setSeed_date_config(request.getParameter("seeddateconfig"));
		//种子所属的任务ID
		spiderSeedConfig.setSeed_id(Integer.parseInt(request.getParameter("seedId")));
		seed.setId(Integer.parseInt(request.getParameter("seedId")));
		spiderSeedConfig.setSeed_interval_config(request.getParameter("seedintervalconfig"));//种子抓取间隔配置
		spiderSeedConfig.setSeed_list_url(request.getParameter("seedurllist"));//种子列表链接
		spiderSeedConfig.setSeed_name(request.getParameter("seedname"));//种子地址名
		seed.setSeed_name(request.getParameter("seedname"));
		spiderSeedConfig.setSeed_text_config(request.getParameter("seedtextconfig"));//种子正文配置
		spiderSeedConfig.setSeed_text_config_type(Integer.parseInt(request.getParameter("seedtextconfigtype")));//种子正文配置类型（1代表HTML，2代表JSON，3代表JSONB）
		seed.setSeed_type(Integer.parseInt(request.getParameter("seedtextconfigtype")));
		seed.setSeed_article_type(Integer.parseInt(request.getParameter("seedarticletype")));
		seed.setDaili_flag(Integer.parseInt(request.getParameter("dailiflag")));
		seed.setSelenium_flag(Integer.parseInt(request.getParameter("seleniumflag")));
		spiderSeedConfig.setSeed_thread_config(Integer.parseInt(request.getParameter("seedthreadconfig")));//种子线程数量配置
		spiderSeedConfig.setSeed_title_config(request.getParameter("seedtitleconfig"));//种子标题配置
		spiderSeedConfig.setSeed_url_config(request.getParameter("seedurlconfig"));//种子链接配置
		spiderSeedConfig.setSeed_origin_config(request.getParameter("seedoriginconfig"));//来源网站名
		String parameter = request.getParameter("seedstoragejson");
		String[] split = parameter.split(",");
		JSONArray array = new JSONArray();
		for (int i = 0; i < split.length; i++) {
			JSONObject jb = new JSONObject();
			jb.put("type", split[i]);
			array.add(jb);
		}
		spiderSeedConfig.setSeed_storage_type(array.toJSONString());//存储类型
		spiderSeedConfig.setSeed_origin_url_config(request.getParameter("seedoriginurlconfig"));//来源网站的url
		String otherseedid = request.getParameter("otherseedid");
		if(otherseedid != null && !"".equals(otherseedid)&&!"null".equals(otherseedid)){
			spiderSeedConfig.setOther_seed_id(Integer.parseInt(otherseedid));
			spiderSeedConfig.setOther_website_id(Integer.parseInt(request.getParameter("otherwebsiteId")));
		}
		int updateSeed = seedService.updateSeed(seed);//种子更新
		int num=seedConfigService.updateSeedConfig(spiderSeedConfig);
		if(num!=0) {
			return ResultUtil.build(200, "数据更新成功");
		}else{
			return ResultUtil.build(500, "数据更新失败");
		}
	}
	
	
	@RequestMapping("/updateForeignSeedConfig")
	@ResponseBody
	public ResultUtil updateForeignSeedConfig(ModelAndView mv,HttpServletRequest request) {
		
		SpiderSeedConfig spiderSeedConfig=new SpiderSeedConfig();
		SpiderSeed seed = new SpiderSeed();
		
		//spiderSeedConfig.setSeed_id(Integer.parseInt(request.getParameter("id")));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		spiderSeedConfig.setCreate_date(sdf.format(new Date()));//创建时间
		spiderSeedConfig.setCreate_user_id(Constant.createid);//创建者ID
		spiderSeedConfig.setSeed_spider_type(Integer.parseInt(request.getParameter("seedspidertype")));//设置
		//种子时间配置
		spiderSeedConfig.setSeed_date_config(request.getParameter("seeddateconfig"));
		//种子所属的任务ID
		spiderSeedConfig.setSeed_id(Integer.parseInt(request.getParameter("seedId")));
		seed.setId(Integer.parseInt(request.getParameter("seedId")));
		spiderSeedConfig.setSeed_interval_config(request.getParameter("seedintervalconfig"));//种子抓取间隔配置
		spiderSeedConfig.setSeed_list_url(request.getParameter("seedurllist"));//种子列表链接
		spiderSeedConfig.setSeed_name(request.getParameter("seedname"));//种子地址名
		seed.setSeed_name(request.getParameter("seedname"));
		spiderSeedConfig.setSeed_text_config(request.getParameter("seedtextconfig"));//种子正文配置
		spiderSeedConfig.setSeed_text_config_type(Integer.parseInt(request.getParameter("seedtextconfigtype")));//种子正文配置类型（1代表HTML，2代表JSON，3代表JSONB）
		seed.setSeed_type(Integer.parseInt(request.getParameter("seedtextconfigtype")));
		seed.setSeed_article_type(Integer.parseInt(request.getParameter("seedarticletype")));
		seed.setDaili_flag(Integer.parseInt(request.getParameter("dailiflag")));
		seed.setSelenium_flag(Integer.parseInt(request.getParameter("seleniumflag")));
		seed.setVpn_flag(Integer.parseInt(request.getParameter("vpnflag")));
		System.err.println("sedd"+seed.getVpn_flag());
		spiderSeedConfig.setSeed_thread_config(Integer.parseInt(request.getParameter("seedthreadconfig")));//种子线程数量配置
		spiderSeedConfig.setSeed_title_config(request.getParameter("seedtitleconfig"));//种子标题配置
		spiderSeedConfig.setSeed_url_config(request.getParameter("seedurlconfig"));//种子链接配置
		spiderSeedConfig.setSeed_origin_config(request.getParameter("seedoriginconfig"));//来源网站名
		String parameter = request.getParameter("seedstoragejson");
		String[] split = parameter.split(",");
		JSONArray array = new JSONArray();
		for (int i = 0; i < split.length; i++) {
			JSONObject jb = new JSONObject();
			jb.put("type", split[i]);
			array.add(jb);
		}
		spiderSeedConfig.setVpn_flag(seed.getVpn_flag());
		System.err.println("seedconfig"+spiderSeedConfig.getVpn_flag());
		spiderSeedConfig.setSeed_storage_type(array.toJSONString());//存储类型
		spiderSeedConfig.setSeed_origin_url_config(request.getParameter("seedoriginurlconfig"));//来源网站的url
		String otherseedid = request.getParameter("otherseedid");
		if(otherseedid != null && !"".equals(otherseedid)&&!"null".equals(otherseedid)){
			spiderSeedConfig.setOther_seed_id(Integer.parseInt(otherseedid));
			spiderSeedConfig.setOther_website_id(Integer.parseInt(request.getParameter("otherwebsiteId")));
		}
		
		int updateSeed = seedService.updateForeignSeed(seed);//种子更新
		int num=seedConfigService.updateForeignSeedConfig(spiderSeedConfig);
		if(num!=0) {
			return ResultUtil.build(200, "数据更新成功");
		}else{
			return ResultUtil.build(500, "数据更新失败");
		}
	}
	
	/**
	 * 跳转种子配置页面
	 * @param mv
	 * @param seedid
	 * @return
	 */
	@RequestMapping("/getseedconfig")
	public ModelAndView getseedconfig(ModelAndView mv,Integer seedid) {
		SpiderSeedConfig config = seedConfigService.getseedconfig(seedid);
		try {
			String seed_storage_type = config.getSeed_storage_type();
			if(seed_storage_type != null){
				seed_storage_type=seed_storage_type.replaceAll("\\[","");
				seed_storage_type=seed_storage_type.replaceAll("\\]","");
				seed_storage_type=seed_storage_type.replaceAll("\"","");
				seed_storage_type=seed_storage_type.replaceAll(":","");
				seed_storage_type=seed_storage_type.replaceAll("\\{","");
				seed_storage_type=seed_storage_type.replaceAll("\\}","");
				seed_storage_type=seed_storage_type.replace("type","");
				config.setSeed_storage_type(seed_storage_type);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		config.setSeed_article_type(seedConfigService.selectSeedAtrticleType(seedid).getSeed_article_type());
		config.setSelenium_flag(seedConfigService.selectSeedAtrticleType(seedid).getSelenium_flag());
		config.setDaili_flag(seedConfigService.selectSeedAtrticleType(seedid).getDaili_flag());
		config.setOther_seed_id(seedConfigService.selectSeedAtrticleType(seedid).getSeed_first_type());
		System.err.println(config.getDaili_flag()+"ddd");
		mv.addObject("config", config);
		mv.setViewName("updateconfig");
		return mv;
	}
	
	
	
	/**
	 * 跳转种子配置页面
	 * @param mv
	 * @param seedid
	 * @return
	 */
	@RequestMapping("/getseedconfigtest")
	public ModelAndView getseedconfigtest(ModelAndView mv,Integer seedid) {
		SpiderSeedConfig config = seedConfigService.getseedconfigtest(seedid);
		try {
			String seed_storage_type = config.getSeed_storage_type();
			if(seed_storage_type != null){
				seed_storage_type=seed_storage_type.replaceAll("\\[","");
				seed_storage_type=seed_storage_type.replaceAll("\\]","");
				seed_storage_type=seed_storage_type.replaceAll("\"","");
				seed_storage_type=seed_storage_type.replaceAll(":","");
				seed_storage_type=seed_storage_type.replaceAll("\\{","");
				seed_storage_type=seed_storage_type.replaceAll("\\}","");
				seed_storage_type=seed_storage_type.replace("type","");
				config.setSeed_storage_type(seed_storage_type);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		SpiderSeed selectSeedAtrticleTypetest = seedConfigService.selectSeedAtrticleTypetest(seedid);
		config.setSeed_article_type(selectSeedAtrticleTypetest.getSeed_article_type());
		config.setSelenium_flag(selectSeedAtrticleTypetest.getSelenium_flag());
		config.setDaili_flag(selectSeedAtrticleTypetest.getDaili_flag());
		config.setOther_seed_id(selectSeedAtrticleTypetest.getSeed_first_type());
		String seed_page_rule_config = config.getSeed_page_rule_config();
//		System.out.println(seed_page_rule_config);
//		if(null != seed_page_rule_config){
//			config.setSeed_page_rule_config(seed_page_rule_config.replaceAll("\"", "\\\\\""));
//			System.out.println(config.getSeed_page_rule_config());
//		}
		System.err.println(config.getDaili_flag()+"ddd");
		mv.addObject("config", config);
		mv.setViewName("updateconfigtest");
		return mv;
	}
	
	
	@RequestMapping("/getforeignseedconfig")
	public ModelAndView getforeignseedconfig(ModelAndView mv,Integer seedid) {
		SpiderSeedConfig config = seedConfigService.getforeignseedconfig(seedid);
		String seed_storage_type = config.getSeed_storage_type();
		if(seed_storage_type != null){
			seed_storage_type=seed_storage_type.replaceAll("\\[","");
			seed_storage_type=seed_storage_type.replaceAll("\\]","");
			seed_storage_type=seed_storage_type.replaceAll("\"","");
			seed_storage_type=seed_storage_type.replaceAll(":","");
			seed_storage_type=seed_storage_type.replaceAll("\\{","");
			seed_storage_type=seed_storage_type.replaceAll("\\}","");
			seed_storage_type=seed_storage_type.replace("type","");
			config.setSeed_storage_type(seed_storage_type);
		}
		config.setSeed_article_type(seedConfigService.selectForeignSeedAtrticleType(seedid).getSeed_article_type());
		config.setSelenium_flag(seedConfigService.selectForeignSeedAtrticleType(seedid).getSelenium_flag());
		config.setDaili_flag(seedConfigService.selectForeignSeedAtrticleType(seedid).getDaili_flag());
		config.setOther_seed_id(seedConfigService.selectForeignSeedAtrticleType(seedid).getSeed_first_type());
		mv.addObject("config", config);
		
		mv.setViewName("updateforeignconfig");
		return mv;
	}
	
	
}
