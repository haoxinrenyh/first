package com.stonedt.spider.controller;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.stonedt.spider.constant.Constant;
import com.stonedt.spider.constant.GetWebiste;
import com.stonedt.spider.entity.AllocationEntity;
import com.stonedt.spider.entity.KeyWord;
import com.stonedt.spider.entity.SpiderCount;
import com.stonedt.spider.entity.SpiderSeed;
import com.stonedt.spider.entity.SpiderWebsite;
import com.stonedt.spider.service.SeedService;
import com.stonedt.spider.service.SpiderCountService;
import com.stonedt.spider.service.SpiderSearchEngineService;
import com.stonedt.spider.service.SpiderWebsiteService;
import com.stonedt.spider.util.DateUtil;
import com.stonedt.spider.util.ResultUtil;
import com.stonedt.spider.util.StringSplitUtil;

@Controller
@RequestMapping("/searchenging")
public class SpiderSearchEngineController {
	@Autowired
	private SeedService seedService;
	@Autowired
	private SpiderCountService spiderCountService;
	@Autowired
	private SpiderWebsiteService spiderWebsiteService;
	@Autowired
	private SpiderSearchEngineService spiderSearchEngineService;
	private static Logger logger = Logger.getLogger(SpiderSearchEngineController.class);

	/**
	 * 网站列表
	 *
	 * @return
	 */
	@RequestMapping("configlist")
	public ModelAndView listAllwebsitedata(ModelAndView mv,
										   @RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum) {
		PageHelper.startPage(pageNum, 25);

		List<AllocationEntity> list = spiderSearchEngineService.getSearchEngineConfigList();

		PageInfo<AllocationEntity> pageinfo = new PageInfo<AllocationEntity>(list);
		mv.addObject("list", list);
		mv.addObject("pageinfo", pageinfo);
		mv.setViewName("searchEngineConfiglist");
		return mv;
	}

	/**
	 * 搜索
	 *
	 * @param allocationEntity
	 * @param mav
	 * @return
	 */
	@RequestMapping(value = "/getConfiglist", method = RequestMethod.GET)
//	@ResponseBody
	public ModelAndView getConfiglist(ModelAndView mv, String keywords,
									  @RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum) {
		Map<String, Object> map = new HashMap<>();
		map.put("keywords", keywords);
		PageHelper.startPage(pageNum, 25);
		List<AllocationEntity> list = spiderSearchEngineService.getConfiglist(map);
		PageInfo<AllocationEntity> pageinfo = new PageInfo<AllocationEntity>(list);
		Integer totalPage = pageinfo.getPages();
		Long totalData = pageinfo.getTotal();
		JSONObject returnObj = new JSONObject();
		returnObj.put("list", list);
		returnObj.put("totalData", totalData);
		returnObj.put("totalPage", totalPage);
		returnObj.put("pageNum", pageNum);
		mv.addObject("list", list);
		mv.addObject("pageinfo", pageinfo);
		mv.addObject("keywords", keywords);
		mv.setViewName("searchEngineConfiglist");
//		return returnObj;
		return mv;
	}

	/**
	 * 种子列表
	 *
	 * @return
	 */
	@RequestMapping("listseed")
	public ModelAndView listAllseeddata(ModelAndView mv,
										@RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum, HttpServletResponse response, HttpServletRequest request) {

		PageHelper.startPage(pageNum, 25);
		List<SpiderSeed> list = seedService.nulldataseedlist();//种子列表
		PageInfo<SpiderSeed> page = new PageInfo<SpiderSeed>(list);
		mv.addObject("list", page);
		mv.setViewName("seedlist");
		return mv;
	}


	@RequestMapping("smartseedlist")
	public ModelAndView smartseedlist(ModelAndView mv,
									  @RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
									  @RequestParam(value = "time", defaultValue = "1", required = false) Integer time,
									  @RequestParam(value = "scope", defaultValue = "1", required = false) Integer scope,
									  @RequestParam(value = "websiteName", defaultValue = "", required = false) String websiteName,
									  HttpServletResponse response, HttpServletRequest request) {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		int days = 0;
		if (time == 2) {
			days = 2;
		} else if (time == 3) {
			days = 6;
		} else if (time == 4) {
			days = 29;
		}
		int min = 0;
		int max = 10;
		if (scope == 2) {
			min = 11;
			max = 20;
		} else if (scope == 3) {
			min = 21;
			max = 50;
		} else if (scope == 4) {
			min = 51;
			max = 99999999;
		}

		String date = DateUtil.minusDay(days).substring(0, 10);
		PageHelper.startPage(pageNum, 25);
		List<SpiderSeed> list = seedService.dataseedlist(min, max, date, websiteName);//种子列表
		for (int i = 0; i < list.size(); i++) {
			SpiderCount count = new SpiderCount();
			count.setSeed_id(list.get(i).getId());
			//总采集量
			int totalnum = spiderCountService.getNum(count);
			list.get(i).setTotalnum(totalnum);
			count.setCreate_date(date);
			int todaynum = spiderCountService.getTimeNum(count);
			list.get(i).setTodaynum(todaynum);
		}
		PageInfo<SpiderSeed> page = new PageInfo<SpiderSeed>(list);
		mv.addObject("list", page);
		mv.addObject("time", time);
		mv.addObject("scope", scope);
		mv.addObject("websiteName", websiteName);
		mv.setViewName("smartseedlist");
		return mv;
	}

	/**
	 * 关键词列表
	 *
	 * @return
	 */
	@RequestMapping("listkeyword")
	public ModelAndView listAllkeyworddata(ModelAndView mv,
										   @RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum, HttpServletResponse response, HttpServletRequest request) {

		PageHelper.startPage(pageNum, 25);
		List<KeyWord> list = seedService.getKeywordList();//关键词列表
		PageInfo<KeyWord> page = new PageInfo<KeyWord>(list);
		mv.addObject("list", page);

		mv.setViewName("keywordlist");
		return mv;
	}

	/**
	 * 添加网站
	 *
	 * @param mv
	 * @param request
	 * @return
	 */
	@RequestMapping("addwebsite")
	@ResponseBody
	public ResultUtil addwebsite(ModelAndView mv, HttpServletRequest request) {
		SpiderWebsite website = new SpiderWebsite();
		String website_name = request.getParameter("website_name");//网站名
		website.setWebsite_name(website_name);
		String website_url = request.getParameter("website_url");//网站url
		website.setWebsite_url(website_url);
		Integer website_type = Integer.parseInt(request.getParameter("website_type").toString());
		website.setWebsite_type(website_type);
		String website_remark = request.getParameter("website_remark");
		website.setWebsite_remark(website_remark);//备注
		//website.setWebsite_type(Constant.websitetype);//网站类型，默认为1
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		website.setWebsite_status(Constant.webspiderstatus);// 网站状态
		website.setCreate_date(sdf.format(new Date()));//创建时间
		website.setCreate_user_id(Constant.createid);
		website.setWebsite_ico(GetWebiste.getIP(website_url) + "/favicon.ico");
		logger.info(website.toString());
		String url_contains_str = request.getParameter("url_contains_str");//列表页url包含
		if (!StringUtils.isEmpty(url_contains_str)) {
			website.setUrl_contains_str(StringSplitUtil.removerEmpty(url_contains_str));
		}
		String url_shield_str = request.getParameter("url_shield_str");//列表页url屏蔽
		if (!StringUtils.isEmpty(url_shield_str)) {
			website.setUrl_shield_str(StringSplitUtil.removerEmpty(url_shield_str));
		}
		String explain_contains_str = request.getParameter("explain_contains_str");//列表页
		if (!StringUtils.isEmpty(explain_contains_str)) {
			website.setExplain_contains_str(StringSplitUtil.removerEmpty(explain_contains_str));
		}
		String explain_shield_str = request.getParameter("explain_shield_str");//列表页屏蔽
		if (!StringUtils.isEmpty(explain_shield_str)) {
			website.setExplain_shield_str(StringSplitUtil.removerEmpty(explain_shield_str));
		}
		String website_province = request.getParameter("website_province");//列表页屏蔽
		if (!StringUtils.isEmpty(website_province)) {
			website.setWebsite_province(StringSplitUtil.removerEmpty(website_province.replaceAll("省", "").replaceAll("市", "").replaceAll("自治区", "")));
		}
		String website_city = request.getParameter("website_city");//列表页屏蔽
		if (!StringUtils.isEmpty(website_city)) {
			website.setWebsite_city(StringSplitUtil.removerEmpty(website_city.replaceFirst("市", "").replace("自治州", "")));
		}
		int count = spiderWebsiteService.savewebsite(website);
		if (count != 0) {
			return ResultUtil.build(200, "数据插入成功");
		} else {
			return ResultUtil.build(500, "数据插入失败");
		}
	}

	/**
	 * 添加关键词
	 *
	 * @param mv
	 * @param request
	 * @return
	 */
	@RequestMapping("addkeyword")
	@ResponseBody
	public ResultUtil addkeyword(ModelAndView mv, HttpServletRequest request) {
		String keyword = request.getParameter("keyword");//关键词
		int count = seedService.addKeyWord(keyword);
		if (count != 0) {
			return ResultUtil.build(200, "数据插入成功");
		} else {
			return ResultUtil.build(500, "数据插入失败");
		}
	}

	@RequestMapping("toaddwebsite")
	public ModelAndView toaddwebsite() {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("to_add_website");
		return mView;
	}

	@RequestMapping("toaddkeyword")
	public ModelAndView toaddkeyword() {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("to_add_keyword");
		return mView;
	}

	@RequestMapping("toupdatewebsite")
	public ModelAndView toupdatewebsite(Integer websiteId) {
		ModelAndView mv = new ModelAndView();
		SpiderWebsite info = spiderWebsiteService.getInfo(websiteId);
		mv.addObject("website", info);
		mv.setViewName("to_update_website");
		return mv;
	}

	@RequestMapping("updatewebsite")
	@ResponseBody
	public ResultUtil updatewebsite(ModelAndView mv, HttpServletRequest request) {
		int id = Integer.parseInt(request.getParameter("id").toString());
		SpiderWebsite website = new SpiderWebsite();
		String website_name = request.getParameter("website_name");//网站名
		website.setWebsite_name(website_name);
		String website_url = request.getParameter("website_url");//网站url
		website.setWebsite_url(website_url);
		String website_remark = request.getParameter("website_remark");
		website.setWebsite_remark(website_remark);//备注

		Integer website_type = Integer.parseInt(request.getParameter("website_type").toString());
		website.setWebsite_type(website_type);
		website.setId(id);
		logger.info(website.toString());
		String url_contains_str = request.getParameter("url_contains_str");//列表页url包含
		if (!StringUtils.isEmpty(url_contains_str)) {
			website.setUrl_contains_str(StringSplitUtil.removerEmpty(url_contains_str));
		}
		String url_shield_str = request.getParameter("url_shield_str");//列表页url屏蔽
		if (!StringUtils.isEmpty(url_shield_str)) {
			website.setUrl_shield_str(StringSplitUtil.removerEmpty(url_shield_str));
		}
		String explain_contains_str = request.getParameter("explain_contains_str");//列表页
		if (!StringUtils.isEmpty(explain_contains_str)) {
			website.setExplain_contains_str(StringSplitUtil.removerEmpty(explain_contains_str));
		}
		String explain_shield_str = request.getParameter("explain_shield_str");//列表页屏蔽
		if (!StringUtils.isEmpty(explain_shield_str)) {
			website.setExplain_shield_str(StringSplitUtil.removerEmpty(explain_shield_str));
		}
		String website_province = request.getParameter("website_province");//列表页屏蔽
		if (!StringUtils.isEmpty(website_province)) {
			website.setWebsite_province(StringSplitUtil.removerEmpty(website_province.replaceAll("省", "").replaceAll("市", "").replaceAll("自治区", "")));
		}
		String website_city = request.getParameter("website_city");//列表页屏蔽
		if (!StringUtils.isEmpty(website_city)) {
			website.setWebsite_city(StringSplitUtil.removerEmpty(website_city.replaceFirst("市", "").replace("自治州", "")));
		}
//		String parameter = request.getParameter("website_logo");
		website.setWebsite_ico(request.getParameter("website_logo"));
		int count = spiderWebsiteService.updatewebsite(website);
		if (count != 0) {
			return ResultUtil.build(200, "数据更新成功");
		} else {
			return ResultUtil.build(500, "数据更新失败");
		}
	}

	@RequestMapping("getAllwebsite")
	@ResponseBody
	public ResultUtil getAllwebsite() {
		List<SpiderWebsite> list = spiderWebsiteService.getAllwebsite();
		return ResultUtil.build(200, "", list);
	}


}
