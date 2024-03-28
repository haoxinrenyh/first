package com.stonedt.spider.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.stonedt.spider.constant.Constant;
import com.stonedt.spider.entity.SpiderSeed;
import com.stonedt.spider.entity.SpiderSeedConfig;
import com.stonedt.spider.entity.SpiderSeedConfigUtilOb;
import com.stonedt.spider.service.SeedConfigService;
import com.stonedt.spider.service.SeedConfigUtilService;
import com.stonedt.spider.service.SeedService;
import com.stonedt.spider.util.ResultUtil;

@Controller
@RequestMapping("/websitetactics")
public class SeedConfigUtilController {
	@Autowired
	private SeedConfigUtilService seedConfigUtilService;
	@Autowired
	private SeedService seedService;
	@Autowired
	private SeedConfigService seedConfigService;
	
	/*
	 * 跳转到新增策略页面
	 */
	@RequestMapping("/godit")
	public ModelAndView website_addtactics(ModelAndView mv,HttpServletRequest request) {
		Integer id = Integer.parseInt(request.getParameter("id").toString());
		Integer websiteId = Integer.parseInt(request.getParameter("websiteId").toString());
		String websitename = request.getParameter("website_name").toString();
		SpiderSeedConfigUtilOb scuo = seedConfigUtilService.getDetail(id);
		
		String seed_page_rule_config = scuo.getSeed_page_rule_config();
		Pattern p = Pattern.compile("\\s*|\t|\r|\n");
        Matcher m = p.matcher(seed_page_rule_config);
        seed_page_rule_config = m.replaceAll("");
		//scuo.setSeed_page_rule_config(seed_page_rule_config);
		
		String seed_url_config = scuo.getSeed_url_config();
		System.err.println(seed_url_config);
		Pattern p1 = Pattern.compile("\\s*|\t|\r|\n");
        Matcher m1 = p1.matcher(seed_url_config);
        seed_url_config = m1.replaceAll("");
        System.err.println(seed_url_config);
		//scuo.setSeed_page_rule_config(seed_url_config);
        mv.addObject("id", id);
		mv.addObject("SpiderSeedConfigUtilOb", scuo);
		mv.addObject("websiteId", websiteId);
		mv.addObject("websitename",websitename);
		mv.addObject("seed_page_rule_config",seed_page_rule_config);
		mv.addObject("seed_url_config",seed_url_config);
		mv.setViewName("website_addtacticsmodify");
		return mv;
	}
	
	
	/*
	 * 新增种子
	 * @author wangziqiu
	 */
	@RequestMapping("/insertseed")
	@ResponseBody
	public ResultUtil insertseed(ModelAndView mv,HttpServletRequest request) {
		SpiderSeedConfigUtilOb spiderSeedConfig=new SpiderSeedConfigUtilOb();
		spiderSeedConfig.setSeed_spider_type(Integer.parseInt(request.getParameter("seedspidertype")));
		spiderSeedConfig.setCreate_date(new Date());//创建时间
		spiderSeedConfig.setCreate_user_id(Constant.createid);//创建者ID

		spiderSeedConfig.setSeed_util_name(request.getParameter("seedname"));//种子任务名称
		spiderSeedConfig.setSeed_type(request.getParameter("seedtype"));//种子类型
		spiderSeedConfig.setWebsite_id(Integer.parseInt(request.getParameter("websiteId")));//网站ID
//		spiderSeed.setSeed_article_type(Integer.parseInt(request.getParameter("seedarticletype")));//文章类型
		spiderSeedConfig.setCreate_user_id(Constant.createid);//创建者ID
//		spiderSeed.setSeed_first_type(Integer.parseInt(request.getParameter("otherseedid")));
		
		spiderSeedConfig.setSeed_spider_type(Integer.parseInt(request.getParameter("seedspidertype")));
		spiderSeedConfig.setCreate_user_id(Constant.createid);//创建者ID
		//种子时间配置
		spiderSeedConfig.setSeed_date_config(request.getParameter("seeddateconfig"));
		//种子所属网站ID
		spiderSeedConfig.setWebsite_id(Integer.parseInt(request.getParameter("websiteId")));
		spiderSeedConfig.setSeed_interval_config(request.getParameter("seedintervalconfig"));//种子抓取间隔配置
		Integer seed_sleep_config = Integer.parseInt(request.getParameter("seedsleepconfig"));
		spiderSeedConfig.setSeed_sleep_config(seed_sleep_config);//种子睡眠时间
		spiderSeedConfig.setSeed_linkurl_config(request.getParameter("seedlinkurlconfig"));//种子路径配置
		spiderSeedConfig.setSeed_origin_config(request.getParameter("seedoriginconfig"));//种子来源配置
		
		spiderSeedConfig.setSeed_origin_url_config(request.getParameter("seedoriginurlconfig"));//种子来源URL配置
		spiderSeedConfig.setSeed_list_url(request.getParameter("seedurllist"));//种子列表链接
		spiderSeedConfig.setSeed_author_config(request.getParameter("seedauthorconfig"));//种子作者
		spiderSeedConfig.setSeed_author_url_config(request.getParameter("seedauthorurlconfig"));//种子作者url
		spiderSeedConfig.setSeed_author_avatar_config(request.getParameter("seedauthoravatarconfig"));//种子作者头像
		spiderSeedConfig.setSeed_page_rule_config(request.getParameter("seedpageruleconfig"));//分页规则
		spiderSeedConfig.setSeed_text_config(request.getParameter("seedtextconfig"));//种子正文配置
//		spiderSeedConfig.setSeed_storage_type(array.toJSONString());//存储类型
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
		int num = seedConfigUtilService.insertWebSiteSeedconfig(spiderSeedConfig);
		if(num!=0) {
			return ResultUtil.build(200, "数据插入成功");
		}else{
			return ResultUtil.build(500, "数据插入失败");
		}
	}
	
	
	
	/*
	 * 新增种子
	 * @author wangziqiu
	 */
	@RequestMapping("/insertseedtest")
	@ResponseBody
	public ResultUtil insertseedtest(ModelAndView mv,HttpServletRequest request) {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SpiderSeedConfigUtilOb spiderSeedConfig=new SpiderSeedConfigUtilOb();
		spiderSeedConfig.setSeed_spider_type(Integer.parseInt(request.getParameter("seedspidertype")));
		spiderSeedConfig.setCreate_date(new Date());//创建时间
		spiderSeedConfig.setCreate_user_id(Constant.createid);//创建者ID

		spiderSeedConfig.setSeed_util_name(request.getParameter("seedname"));//种子任务名称
		spiderSeedConfig.setSeed_type(request.getParameter("seedtype"));//种子类型
		spiderSeedConfig.setWebsite_id(Integer.parseInt(request.getParameter("websiteId")));//网站ID
//		spiderSeed.setSeed_article_type(Integer.parseInt(request.getParameter("seedarticletype")));//文章类型
		spiderSeedConfig.setCreate_user_id(Constant.createid);//创建者ID
//		spiderSeed.setSeed_first_type(Integer.parseInt(request.getParameter("otherseedid")));
		
		spiderSeedConfig.setSeed_spider_type(Integer.parseInt(request.getParameter("seedspidertype")));
		spiderSeedConfig.setCreate_user_id(Constant.createid);//创建者ID
		//种子时间配置
		spiderSeedConfig.setSeed_date_config(request.getParameter("seeddateconfig"));
		//种子所属网站ID
		spiderSeedConfig.setWebsite_id(Integer.parseInt(request.getParameter("websiteId")));
		spiderSeedConfig.setSeed_interval_config(request.getParameter("seedintervalconfig"));//种子抓取间隔配置
		Integer seed_sleep_config = Integer.parseInt(request.getParameter("seedsleepconfig"));
		spiderSeedConfig.setSeed_sleep_config(seed_sleep_config);//种子睡眠时间
		spiderSeedConfig.setSeed_linkurl_config(request.getParameter("seedlinkurlconfig"));//种子路径配置
		spiderSeedConfig.setSeed_origin_config(request.getParameter("seedoriginconfig"));//种子来源配置
		
		spiderSeedConfig.setSeed_origin_url_config(request.getParameter("seedoriginurlconfig"));//种子来源URL配置
		spiderSeedConfig.setSeed_list_url(request.getParameter("seedurllist"));//种子列表链接
		spiderSeedConfig.setSeed_author_config(request.getParameter("seedauthorconfig"));//种子作者
		spiderSeedConfig.setSeed_author_url_config(request.getParameter("seedauthorurlconfig"));//种子作者url
		spiderSeedConfig.setSeed_author_avatar_config(request.getParameter("seedauthoravatarconfig"));//种子作者头像
		spiderSeedConfig.setSeed_page_rule_config(request.getParameter("seedpageruleconfig"));//分页规则
		spiderSeedConfig.setSeed_text_config(request.getParameter("seedtextconfig"));//种子正文配置
//		spiderSeedConfig.setSeed_storage_type(array.toJSONString());//存储类型
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
		int num = seedConfigUtilService.insertWebSiteSeedconfig(spiderSeedConfig);
		if(num!=0) {
			return ResultUtil.build(200, "数据插入成功");
		}else{
			return ResultUtil.build(500, "数据插入失败");
		}
	}
	
	
	@RequestMapping("/addseeds")
	@ResponseBody
	public ResultUtil addseeds(@RequestBody String ids) {
		ids = ids.replaceAll("%2C", ",");
		if(ids.endsWith("=")){
			ids = ids.replaceAll("=", "");
		}
		List<Integer> idList = new ArrayList<Integer>();
		String[] split = ids.split(",");
		for (String string : split) {
			idList.add(Integer.parseInt(string));
		}
		List<Map<String, Object>> result= seedConfigUtilService.getMappingSpiderConfigs(idList);
		int all = result.size();
		int success = 0;
		List<Integer> successids= new ArrayList<>();
		for (Map<String, Object> map : result) {
			try {
				String jsonString = JSON.toJSONString(map);
				System.out.println(jsonString);
				seedConfigService.closeMapping(Integer.parseInt(map.get("id").toString()));
				SpiderSeed spiderSeed = new SpiderSeed();
				spiderSeed.setSeed_flag(Constant.seedflag);//种子标记
				spiderSeed.setSeed_name(map.get("url_name").toString());//种子任务名称
				spiderSeed.setSeed_status(1);//种子状态
				Object seed_typeOb = map.get("seed_type");
				Integer seed_type = 1;
				if(null != seed_typeOb){
					seed_type = Integer.parseInt(seed_typeOb.toString());
				}
				spiderSeed.setSeed_type(seed_type);//种子类型
				spiderSeed.setWebsite_id(Integer.parseInt(map.get("website_id").toString()));//网站ID
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				spiderSeed.setCreate_date(sdf.format(new Date()));//更新时间
				spiderSeed.setSeed_article_type(1);//文章类型
				spiderSeed.setCreate_user_id(Constant.createid);//创建者ID
				spiderSeed.setSelenium_flag(0);
				int num=seedService.insertseed(spiderSeed);
				Integer seedid = spiderSeed.getId();//获得种子id
				SpiderSeedConfig spiderSeedConfig=new SpiderSeedConfig();
				spiderSeedConfig.setSeed_spider_type(Integer.parseInt(map.get("seed_spider_type").toString()));
				spiderSeedConfig.setCreate_date(sdf.format(new Date()));//创建时间
				spiderSeedConfig.setCreate_user_id(Constant.createid);//创建者ID
				//种子时间配置
				String seed_date_config=null;
				if(map.get("seed_date_config")!=null){
					seed_date_config = map.get("seed_date_config").toString();
				}
				spiderSeedConfig.setSeed_date_config(seed_date_config);//种子日期配置
				//种子所属的任务ID
				spiderSeedConfig.setSeed_id(seedid);
				//种子所属网站ID
				spiderSeedConfig.setWebsite_id(Integer.parseInt(map.get("website_id").toString()));
				spiderSeedConfig.setSeed_interval_config(map.get("seed_interval_config").toString());//种子抓取间隔配置
				Integer seed_sleep_config = Integer.parseInt(map.get("seed_sleep_config").toString());
				spiderSeedConfig.setSeed_sleep_config(seed_sleep_config);//种子睡眠时间
//			spiderSeedConfig.setSeed_linkurl_config(request.getParameter("seedlinkurlconfig"));//种子路径配置
				String seed_origin_config = null;
				if(map.get("seed_origin_config")!= null){
					seed_origin_config = map.get("seed_origin_config").toString();
				}
				spiderSeedConfig.setSeed_origin_config(seed_origin_config);//种子来源配置
				String seed_origin_url_config = null;
				if(map.get("seed_origin_url_config")!= null){
					seed_origin_url_config = map.get("seed_origin_url_config").toString();
				}
				spiderSeedConfig.setSeed_origin_url_config(seed_origin_url_config);//种子来源URL配置
				spiderSeedConfig.setSeed_list_url(map.get("url").toString());//种子列表链接
				spiderSeedConfig.setSeed_name(map.get("url_name").toString());//种子地址名
				String seed_text_config=null;
				if(map.get("seed_text_config")!=null){
					seed_text_config = map.get("seed_text_config").toString();
				}
				spiderSeedConfig.setSeed_text_config(seed_text_config);//种子正文配置
				spiderSeedConfig.setSeed_storage_type("[{\"type\":\"1\"}]");//存储类型
				Object object5 = map.get("seed_text_config_type");
				if(null != object5){
					try {
						spiderSeedConfig.setSeed_text_config_type(Integer.parseInt(object5.toString()));
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
//			spiderSeedConfig.setSeed_text_config_type(Integer.parseInt(request.getParameter("seedtextconfigtype")));//种子正文配置类型（1代表HTML，2代表JSON，3代表JSONB）
				spiderSeedConfig.setSeed_thread_config(Integer.parseInt(map.get("seed_thread_config").toString()));//种子线程数量配置
				String seed_title_config=null;
				if(map.get("seed_title_config")!=null){
					seed_title_config = map.get("seed_title_config").toString();
				}
				spiderSeedConfig.setSeed_title_config(seed_title_config);//种子标题配置
				Object object = map.get("seed_author_config");
				if(null != object){
					spiderSeedConfig.setSeed_author_config(object.toString());
				}
				Object object1 = map.get("seed_author_url_config");
				if(null != object1){
					spiderSeedConfig.setSeed_author_url_config(object1.toString());
				}
				Object object2 = map.get("seed_author_avatar_config");
				if(null != object2){
					spiderSeedConfig.setSeed_author_avatar_config(object2.toString());
				}
				Object object3 = map.get("seed_page_rule_config");
				if(null != object3){
					spiderSeedConfig.setSeed_page_rule_config(object3.toString());
				}
				Object object4 = map.get("seed_url_config");
				if(null != object4){
					spiderSeedConfig.setSeed_url_config(object4.toString());
				}
				
				
//			spiderSeedConfig.setSeed_url_config(request.getParameter("seedurlconfig"));//种子链接配置
				spiderSeedConfig.setOther_seed_id(spiderSeedConfig.getSeed_id());
				Object object6 = map.get("url_reg");
				if(null != object6){
					spiderSeedConfig.setUrl_reg(object6.toString());
				}
				spiderSeedConfig.setOther_website_id(spiderSeedConfig.getWebsite_id());
				try {
					num=seedConfigService.insertSeedconfig(spiderSeedConfig);
					if(num != 0){
						successids.add(Integer.parseInt(map.get("id").toString()));
						success +=1;
					}
				} catch (Exception e) {
					num = 0;
					seedService.removeSeedById(seedid);
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		seedConfigService.updateSeedsFlag(successids);
		return ResultUtil.build(200, all+"条数据数据，共"+success+"条插入成功");
	}
	
	/*
	 * 新增种子
	 * @author wangziqiu
	 */
	@RequestMapping("/save")
	@ResponseBody
	public ResultUtil save(ModelAndView mv,HttpServletRequest request) {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SpiderSeedConfigUtilOb spiderSeedConfig=new SpiderSeedConfigUtilOb();
		spiderSeedConfig.setId(Integer.parseInt(request.getParameter("id")));
		spiderSeedConfig.setSeed_spider_type(Integer.parseInt(request.getParameter("seedspidertype")));
		spiderSeedConfig.setCreate_date(new Date());//创建时间
		spiderSeedConfig.setCreate_user_id(Constant.createid);//创建者ID
		//种子时间配置
		spiderSeedConfig.setSeed_date_config(request.getParameter("seeddateconfig"));
		//种子所属网站ID
		spiderSeedConfig.setWebsite_id(Integer.parseInt(request.getParameter("websiteId")));
		spiderSeedConfig.setSeed_interval_config(request.getParameter("seedintervalconfig"));//种子抓取间隔配置
		Integer seed_sleep_config = Integer.parseInt(request.getParameter("seedsleepconfig"));
		spiderSeedConfig.setSeed_sleep_config(seed_sleep_config);//种子睡眠时间
		spiderSeedConfig.setSeed_linkurl_config(request.getParameter("seedlinkurlconfig"));//种子路径配置
		spiderSeedConfig.setSeed_origin_config(request.getParameter("seedoriginconfig"));//种子来源配置
		spiderSeedConfig.setSeed_origin_url_config(request.getParameter("seedoriginurlconfig"));//种子来源URL配置
		spiderSeedConfig.setSeed_util_name(request.getParameter("seedname"));//种子地址名
		spiderSeedConfig.setSeed_text_config(request.getParameter("seedtextconfig"));//种子正文配置
		spiderSeedConfig.setSeed_text_config_type(1);//种子正文配置类型（1代表HTML，2代表JSON，3代表JSONB）
		spiderSeedConfig.setSeed_thread_config(Integer.parseInt(request.getParameter("seedthreadconfig")));//种子线程数量配置
		spiderSeedConfig.setSeed_title_config(request.getParameter("seedtitleconfig"));//种子标题配置
	    spiderSeedConfig.setSeed_date_config(request.getParameter("seeddateconfig"));//种子日期配置
	    spiderSeedConfig.setUrl_reg(request.getParameter("url_reg"));
		String otherseedid = request.getParameter("otherseedid");
		if(otherseedid != null && !"".equals(otherseedid)){
			spiderSeedConfig.setOther_seed_id(Integer.parseInt(otherseedid));
			spiderSeedConfig.setOther_website_id(Integer.parseInt(request.getParameter("otherwebsiteId")));
		}
		int num = seedConfigUtilService.updateWebSiteSeedconfig(spiderSeedConfig);
		if(num!=0) {
			return ResultUtil.build(200, "数据插入成功");
		}else{
			return ResultUtil.build(500, "数据插入失败");
		}
	}
	
	
	/*
	 * 修改种子
	 * @author wangziqiu
	 */
	@RequestMapping("/updateseed")
	@ResponseBody
	public ResultUtil updateseed(ModelAndView mv,HttpServletRequest request) {
		SpiderSeedConfigUtilOb spiderSeedConfig=new SpiderSeedConfigUtilOb();
		spiderSeedConfig.setId(Integer.parseInt(request.getParameter("id")));
		spiderSeedConfig.setSeed_spider_type(Integer.parseInt(request.getParameter("seedspidertype")));
		spiderSeedConfig.setCreate_date(new Date());//创建时间
		spiderSeedConfig.setCreate_user_id(Constant.createid);//创建者ID

		spiderSeedConfig.setSeed_util_name(request.getParameter("seedname"));//种子任务名称
		spiderSeedConfig.setSeed_type(request.getParameter("seedtype"));//种子类型
		spiderSeedConfig.setWebsite_id(Integer.parseInt(request.getParameter("websiteId")));//网站ID
//		spiderSeed.setSeed_article_type(Integer.parseInt(request.getParameter("seedarticletype")));//文章类型
		spiderSeedConfig.setCreate_user_id(Constant.createid);//创建者ID
//		spiderSeed.setSeed_first_type(Integer.parseInt(request.getParameter("otherseedid")));
		
		spiderSeedConfig.setSeed_spider_type(Integer.parseInt(request.getParameter("seedspidertype")));
		spiderSeedConfig.setCreate_user_id(Constant.createid);//创建者ID
		//种子时间配置
		spiderSeedConfig.setSeed_date_config(request.getParameter("seeddateconfig"));
		//种子所属网站ID
		spiderSeedConfig.setWebsite_id(Integer.parseInt(request.getParameter("websiteId")));
		spiderSeedConfig.setSeed_interval_config(request.getParameter("seedintervalconfig"));//种子抓取间隔配置
		Integer seed_sleep_config = Integer.parseInt(request.getParameter("seedsleepconfig"));
		spiderSeedConfig.setSeed_sleep_config(seed_sleep_config);//种子睡眠时间
		spiderSeedConfig.setSeed_linkurl_config(request.getParameter("seedlinkurlconfig"));//种子路径配置
		spiderSeedConfig.setSeed_origin_config(request.getParameter("seedoriginconfig"));//种子来源配置
		
		spiderSeedConfig.setSeed_origin_url_config(request.getParameter("seedoriginurlconfig"));//种子来源URL配置
		spiderSeedConfig.setSeed_list_url(request.getParameter("seedurllist"));//种子列表链接
		spiderSeedConfig.setSeed_author_config(request.getParameter("seedauthorconfig"));//种子作者
		spiderSeedConfig.setSeed_author_url_config(request.getParameter("seedauthorurlconfig"));//种子作者url
		spiderSeedConfig.setSeed_author_avatar_config(request.getParameter("seedauthoravatarconfig"));//种子作者头像
		spiderSeedConfig.setSeed_page_rule_config(request.getParameter("seedpageruleconfig"));//分页规则
		spiderSeedConfig.setSeed_text_config(request.getParameter("seedtextconfig"));//种子正文配置
//		spiderSeedConfig.setSeed_storage_type(array.toJSONString());//存储类型
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
		int num = seedConfigUtilService.updateWebSiteSeedconfig(spiderSeedConfig);
		if(num!=0) {
			return ResultUtil.build(200, "数据插入成功");
		}else{
			return ResultUtil.build(500, "数据插入失败");
		}
	}
	
}
