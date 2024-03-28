package com.stonedt.spider.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.stonedt.spider.entity.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.stonedt.spider.constant.Constant;
import com.stonedt.spider.constant.GetWebiste;
import com.stonedt.spider.service.ScreenSeedService;
import com.stonedt.spider.service.SeedService;
import com.stonedt.spider.service.SpiderCountService;
import com.stonedt.spider.service.SpiderWebsiteService;
import com.stonedt.spider.service.SpiderWebsitemonitorService;
import com.stonedt.spider.util.DateUtil;
import com.stonedt.spider.util.ElasticSearchUtil;
import com.stonedt.spider.util.ResultUtil;
import com.stonedt.spider.util.StringSplitUtil;

@Controller
@RequestMapping("/website")
public class SpiderWebsiteController {
    @Autowired
    private ScreenSeedService screenSeedService;
    @Autowired
    private SeedService seedService;
    @Autowired
    private SpiderCountService spiderCountService;
    @Autowired
    private SpiderWebsiteService spiderWebsiteService;
    @Autowired
    private SpiderWebsitemonitorService spiderWebsitemonitorService;


    private static Logger logger = Logger.getLogger(SpiderWebsiteController.class);

    /**
     * 网站列表
     *
     * @return
     */
    @RequestMapping("listwebsite")
    public ModelAndView listAllwebsitedata(ModelAndView mv,
	            @RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
	            @RequestParam(value = "searchText", defaultValue = "", required = false) String searchText,
	            HttpServletResponse response, HttpServletRequest request) {
	request.setAttribute("LEFT", "websitelist");
	PageHelper.startPage(pageNum, 25);
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Map<String,Object> paramMap = new HashMap<>();
	paramMap.put("searchText",searchText);
	List<Map<String, Object>> list = spiderWebsiteService.listwebsite(paramMap);
	
	PageInfo<Map<String, Object>> page = new PageInfo<Map<String, Object>>(list);
	logger.info(list);
	mv.addObject("list", page);
	mv.addObject("searchText", searchText);
	//websitelisttest
	mv.setViewName("websitelist");
	return mv;
	}
//    public ModelAndView listAllwebsitedata(ModelAndView mv,
//                                           @RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
//                                           @RequestParam(value = "searchText", defaultValue = "", required = false) String searchText,
//                                           HttpServletResponse response, HttpServletRequest request) {
//        PageHelper.startPage(pageNum, 25);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////        List<Map<String, Object>> list = spiderWebsiteService.listwebsite(Constant.createid);
//        Map<String,Object> paramMap = new HashMap<>();
//        paramMap.put("searchText",searchText);
//        List<Map<String, Object>> list = spiderWebsiteService.listwebsite(paramMap);
////        for (int i = 0; i < list.size(); i++) {
////            总采集量
////            String id = String.valueOf(list.get(i).get("id"));
////            String param = "";
////            try {
////                param = "otherwebsiteid=" + id;
////            } catch (Exception e) {
////                e.printStackTrace();
////            }
////            String sendPost = "";
////            try {
////                sendPost = ElasticSearchUtil.sendPost("yys/qbsearchbycontentnum", param);
////            } catch (Exception e) {
////                e.printStackTrace();
////            }
////            JSONObject json = new JSONObject();
////            json = JSONObject.parseObject(sendPost);
////            Integer totalnum = Integer.valueOf(String.valueOf(json.get("count")));
////            list.get(i).put("totalnum", totalnum);
//        //今日采集量
////            Date date = new Date();
////            String todayparam = "";
////            try {
////                todayparam = "otherwebsiteid=" + id + "&times=" + sdf.format((date.getTime() - 86400000)) + "&tmiee=" + sdf.format(date.getTime());
////            } catch (Exception e) {
////                e.printStackTrace();
////            }
////            String todayPost = "";
////            try {
////                todayPost = ElasticSearchUtil.sendPost("yys/qbsearchbycontentnum", todayparam);
////            } catch (Exception e) {
////                e.printStackTrace();
////            }
////            JSONObject todayjson = new JSONObject();
////            todayjson = JSONObject.parseObject(todayPost);
////            int todaynum = Integer.valueOf(String.valueOf(todayjson.get("count")));
////            list.get(i).put("todaynum", todaynum);
////        }
//
//        PageInfo<Map<String, Object>> page = new PageInfo<Map<String, Object>>(list);
//        logger.info(list);
//        //ModelAndView mv = new ModelAndView();
//        mv.addObject("list", page);
//        mv.addObject("searchText", searchText);
//        mv.setViewName("websitelist");
//        return mv;
//    }
    
    
    
    /**
     * 网站列表
     *
     * @return
     */
    @RequestMapping("listwebsitetest")
    public ModelAndView listAllwebsiteTestdata(ModelAndView mv,
                                           @RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
                                           @RequestParam(value = "searchText", defaultValue = "", required = false) String searchText,
                                           HttpServletResponse response, HttpServletRequest request) {
    	request.setAttribute("LEFT", "websitelisttest");
        PageHelper.startPage(pageNum, 25);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("searchText",searchText);
        List<Map<String, Object>> list = spiderWebsiteService.listwebsite(paramMap);

        PageInfo<Map<String, Object>> page = new PageInfo<Map<String, Object>>(list);
        logger.info(list);
        mv.addObject("list", page);
        mv.addObject("searchText", searchText);
        //websitelisttest
        mv.setViewName("websitelisttest");
        return mv;
    }


    /**
     * 外国网站列表
     *
     * @return
     */
    @RequestMapping("foreignlistwebsite")
    public ModelAndView Foreignlistwebsite(ModelAndView mv,
                                           @RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum, HttpServletResponse response, HttpServletRequest request) {
        PageHelper.startPage(pageNum, 25);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Map<String, Object>> list = spiderWebsiteService.foreignlistwebsite(Constant.createid);
        for (int i = 0; i < list.size(); i++) {
            //总采集量
            String id = String.valueOf(list.get(i).get("id"));
            String param = "";
            try {
                param = "otherwebsiteid=" + id;
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            String sendPost = "";
            try {
                sendPost = ElasticSearchUtil.sendPost("yys/qbsearchbycontentnum", param);
            } catch (Exception e) {

                e.printStackTrace();
            }
            JSONObject json = new JSONObject();
            json = JSONObject.parseObject(sendPost);
            Integer totalnum = Integer.valueOf(String.valueOf(json.get("count")));
            list.get(i).put("totalnum", totalnum);
            //今日采集量
            Date date = new Date();
            String todayparam = "";
            try {
                todayparam = "otherwebsiteid=" + id + "&times=" + sdf.format((date.getTime() - 86400000)) + "&tmiee=" + sdf.format(date.getTime());
            } catch (Exception e) {
                e.printStackTrace();
            }
            String todayPost = "";
            try {
                todayPost = ElasticSearchUtil.sendPost("yys/qbsearchbycontentnum", todayparam);
            } catch (Exception e) {

                e.printStackTrace();
            }
            JSONObject todayjson = new JSONObject();
            todayjson = JSONObject.parseObject(todayPost);
            int todaynum = Integer.valueOf(String.valueOf(todayjson.get("count")));
            list.get(i).put("todaynum", todaynum);
        }

        PageInfo<Map<String, Object>> page = new PageInfo<Map<String, Object>>(list);
        logger.info(list);
        //ModelAndView mv = new ModelAndView();
        mv.addObject("list", page);
        mv.setViewName("foreignwebsitelist");
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
        //查询现在所有的种子
        SeedAll SeedAll = new SeedAll();
        List<SeedAll> seedall = spiderCountService.seedall(SeedAll);
        mv.addObject("seedall", seedall);
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
     * 添加网站
     *
     * @param mv
     * @param request
     * @return
     */
    @RequestMapping("addforeignwebsite")
    @ResponseBody
    public ResultUtil addforeignwebsite(ModelAndView mv, HttpServletRequest request) {
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
        int count = spiderWebsiteService.saveforeignwebsite(website);
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

    @RequestMapping("toaddforeignwebsite")
    public ModelAndView toaddforeignwebsite() {
        ModelAndView mView = new ModelAndView();
        mView.setViewName("to_add_foreignwebsite");
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

    @RequestMapping("toupdateforeignwebsite")
    public ModelAndView toupdateforeignwebsite(Integer websiteId) {
        ModelAndView mv = new ModelAndView();
        SpiderWebsite info = spiderWebsiteService.getForeignInfo(websiteId);
        mv.addObject("website", info);
        mv.setViewName("to_update_foreignwebsite");
        return mv;
    }


    @RequestMapping("updatewebsite")
    @ResponseBody
    public ResultUtil updatewebsite( HttpServletRequest request) {
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


    @RequestMapping("updateforeignwebsite")
    @ResponseBody
    public ResultUtil updateforeignwebsite(ModelAndView mv, HttpServletRequest request) {
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
        int count = spiderWebsiteService.updateforeignwebsite(website);
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


    //种子列表
    @RequestMapping("seedall")
    @ResponseBody
    public ModelAndView seedall(SeedAll SeedAll, @RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
                                ModelAndView mav) {
        PageHelper.startPage(pageNum, 10);

        List<SeedAll> list = spiderCountService.seeiddall(SeedAll);
        for (int i = 0; i < list.size(); i++) {
            SpiderCount count = new SpiderCount();
            count.setSeed_id(Integer.valueOf(list.get(i).getId()));
            //总采集量
            int totalnum = spiderCountService.getNum(count);
            list.get(i).setTotalnum(totalnum);
            int todaynum = spiderCountService.getTimeNum(count);
            list.get(i).setTodaynum(todaynum);
        }

        List<SeedAll> seedall = spiderCountService.seedall(SeedAll);
        mav.addObject("seedall", seedall);
        PageInfo<SeedAll> page = new PageInfo<SeedAll>(list);
        /*for(int i=0;i<list.size();i++) {
            System.out.println(list.get(i).toString());
		}*/
        mav.addObject("id", SeedAll.getId());
        mav.addObject("list", page);
        mav.setViewName("smartseedlist");
        return mav;
    }


    //模糊查询种子
    @RequestMapping("dimseed")
    @ResponseBody
    public ModelAndView dimseed(SeedAll SeedAll,
                                @RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
                                ModelAndView mav) {

        if (SeedAll.getId() != null) {

            String words = SeedAll.getId();
            String param = "";
            try {
                param = "keyword=" + URLEncoder.encode(words, "utf-8") + "&times=&tmiee=";
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }
            String sendPost = "";
            try {
                sendPost = ElasticSearchUtil.sendPost("yys/qbseednum", param);
            } catch (Exception e) {
                e.printStackTrace();
            }

            List<SpiderSeed> list = new ArrayList<SpiderSeed>();
            int pages = 1;
            if (!StringUtils.isEmpty(sendPost)) {
                JSONObject jsonobject = JSONObject.parseObject(sendPost);
                JSONArray array = jsonobject.getJSONObject("aggregations").getJSONObject("group_by_tags").getJSONArray("buckets");
                JSONArray otherarray = new JSONArray();
                for (int i = 0; i < array.size(); i++) {
                    JSONObject jsonObject = array.getJSONObject(i);
                    if (jsonObject.get("key") != null) {
                        otherarray.add(jsonObject);
                    }
                }
                int size = otherarray.size();
                pages = size / 30;
                if (size % 30 > 0) {
                    pages = pages + 1;
                }
                int max = pageNum * 30;
                if (pageNum * 30 > size) {
                    max = size;
                }
                for (int i = (pageNum - 1) * 30 + 1; i < max; i++) {
                    JSONObject jsonObject = otherarray.getJSONObject(i);
                    Integer integer = jsonObject.getInteger("key");
                    SpiderSeed info = seedService.getInfo(integer);
                    if (info != null) {
                        info.setScreennum(jsonObject.getInteger("doc_count"));
                        list.add(info);
                    }
                }
                PageInfo<SpiderSeed> page = new PageInfo<SpiderSeed>(list);
                mav.addObject("id", words);
                page.setPageNum(pageNum);
                page.setPageSize(30);
                page.setPages(pages);
                mav.addObject("list", page);
            }
        }
        mav.setViewName("seedall");
        return mav;
    }

    /**
     * 网站列表
     *
     * @return
     */
    @RequestMapping("websitemonitorlist")
    public ModelAndView websitemonitorlist(ModelAndView mv,
                                           @RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
                                           @RequestParam(value = "searchdata", defaultValue = "", required = false) String searchdata,
                                           @RequestParam(value = "value", defaultValue = "0", required = false) Integer value,
                                           HttpServletResponse response, HttpServletRequest request) {

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("website_name", searchdata);
        map.put("countnum", value);


        PageHelper.startPage(pageNum, 25);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Map<String, Object>> list = spiderWebsitemonitorService.listmonitorwebsite(map);
        PageInfo<Map<String, Object>> page = new PageInfo<Map<String, Object>>(list);
        logger.info(list);
        mv.addObject("list", page);
        mv.addObject("searchdata", searchdata);
        mv.addObject("value", value);
        mv.setViewName("websitemonitorlist");
        return mv;
    }


    /**
     * 修改种子详情
     * 2020/04/07
     * hjc
     */
    @RequestMapping(value = "/toEditSeedPage")
    public ModelAndView toEditSeedPage(ModelAndView mv, @RequestParam("websiteId") Integer websiteId, @RequestParam("transferid") Integer transferid) {
        mv.setViewName("edit_typename");
        mv.addObject("transferid", transferid);
        mv.addObject("websiteId", websiteId);
        // 查询数据
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("transferid", transferid);
        paramMap.put("websiteprimaryid", websiteId);
        Map<String, Object> responseMap = spiderWebsitemonitorService.selectSeedInfo(paramMap);
        mv.addObject("response", responseMap);
        return mv;
    }


    /**
     * 修改种子信息
     *
     * @param dataJson
     * @return
     */
    @RequestMapping(value = "/editSeedInfo", method = RequestMethod.POST)
    @ResponseBody
    public String editSeedInfo(@RequestBody JSONObject dataJson) {
        Map<String, Object> paramMap = JSONObject.parseObject(dataJson.toJSONString(), Map.class);
        System.out.println(paramMap);
        Integer count = spiderWebsitemonitorService.updateSeendInfo(paramMap);
        System.out.println(count);
        JSONObject response = new JSONObject();
        if (count == -2147482646) {
            response.put("code", "200");
        } else {
            response.put("code", "500");
        }
        return response.toString();
    }


    /**
     * 测试种子是否正常
     */
    @RequestMapping(value = "/testSeedInfo", method = RequestMethod.POST)
    @ResponseBody
    public String testSeedInfo(@RequestBody JSONObject dataJson) {
        String esResponse = "";
        try {
            String paramexample = dataJson.getString("paramexample");  // 请求地址
            String param = dataJson.getString("param"); // 参数
            String type = dataJson.getString("type");
            String index = dataJson.getString("index");
            String hbase_table = dataJson.getString("hbase_table");
            JSONObject paramJson = new JSONObject();
            if (!param.equals("")) {
                paramJson = JSON.parseObject(param);
            }
            paramJson.put("type", type);
            paramJson.put("index", index);
            paramJson.put("hbase_table", hbase_table);
            paramJson.put("searchType", 0);
            paramJson.put("size", 1);

            esResponse = ElasticSearchUtil.sendPostRaw(paramJson.toString(), paramexample);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return esResponse;
    }


    /**
     * 获取索引信息
     */
    @RequestMapping(value = "/getTypeIndexInfo", method = RequestMethod.GET)
    @ResponseBody
    public String getTypeIndexInfo(@RequestParam("typeid") Integer typeid) {
        JSONObject responseJson = null;
        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("typeid", typeid);
            Map<String, Object> responseMap = spiderWebsitemonitorService.getTypeInfo(paramMap);
            if (responseMap != null) {
                responseJson = new JSONObject(responseMap);
            } else {
                responseJson = new JSONObject();
                responseJson.put("code", "500");
                responseJson.put("message", "未配置站点类型！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseJson.toString();
    }
}
