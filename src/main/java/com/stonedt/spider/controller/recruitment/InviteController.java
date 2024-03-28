package com.stonedt.spider.controller.recruitment;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.stonedt.spider.service.InviteService;
//import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @param
 * @purpose:
 * @time: 2020/1/13 13:36
 * @author:
 */
@Controller
@RequestMapping("/invite")
public class InviteController {
    @Autowired
    InviteService inviteService;


    @RequestMapping(value = "/toInviteList", method = RequestMethod.GET)
    public ModelAndView toInviteList(ModelAndView mv, HttpServletRequest request,
                                     @RequestParam(value = "inviteSource", required = true) String inviteSource,
                                     @RequestParam(value = "inviteType", required = true) String inviteType) {
        mv.addObject("inviteSource", inviteSource);
        request.setAttribute("LEFT", inviteType);
        mv.setViewName("recruitmentData/invite_list");
        return mv;
    }


    @RequestMapping(value = "/inviteListFirst", method = RequestMethod.GET)
    public ModelAndView inviteListFirst(ModelAndView mv,
                                        @RequestParam(value = "pageNum", defaultValue = "1", required = false) String pageNum,
                                        String inviteSource, String searchText, String searchType) {
        Map<String, String> map = new HashMap<>();
        map.put("jobsorigin", inviteSource);
        map.put("searchkeywrord", searchText);
        map.put("searchtype", searchType);
        map.put("size", "20");
        map.put("page", pageNum);
        JSONObject invite = (JSONObject) inviteService.getInviteList(map);
        mv.setViewName("recruitmentData/invite_list");
        mv.addObject("invites", invite);
        System.out.println(invite.toString());
        return mv;
    }


    @RequestMapping(value = "/inviteList", method = RequestMethod.POST)
    @ResponseBody
    public String inviteList(@RequestBody JSONObject data) {
        Map<String, String> map = JSON.parseObject(data.toJSONString(),Map.class);
//        map.put("jobsorigin", inviteSource);
//        map.put("searchkeywrord", searchText);
//        map.put("searchtype", searchType);
        map.put("size", "20");
//        map.put("page", pageNum);
        JSONObject invite = (JSONObject) inviteService.getInviteList(map);
        System.out.println(invite.toString());
        return invite.toString();
    }

    @RequestMapping(value = "/sourceList", method = RequestMethod.GET)
    @ResponseBody
    public JSONArray sourceList() {
        JSONArray sources = inviteService.getSourceList();
        System.out.println(sources.toString());
        return sources;

    }


    /**
     * 获取招聘详情
     */
    @RequestMapping(value = "/inviteDetails", method = RequestMethod.GET)
    public ModelAndView inviteDetails(ModelAndView mv, String record_id) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("record_id", record_id);
        JSONObject responseJson = inviteService.getInviteDetail(map);
        mv.setViewName("recruitmentData/invite_detail");
        mv.addObject("invite", responseJson);
        return mv;
    }


    /**
     * 异步获取省份信息
     * 2020/02/28
     */
    @RequestMapping(value = "/getProviceAsyn", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getProviceAsyn() {
        JSONObject proviceJson = new JSONObject();
        try {
            Map<String, String> map = new HashMap<>();
            proviceJson = inviteService.getProviceAsyn(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return proviceJson;
    }

    /**
     * 异步获取城市信息
     * 2020/02/28
     */
    @RequestMapping(value = "/getCityAsyn", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getCityAsyn(@RequestParam(value = "province") String province) {
        JSONObject proviceJson = new JSONObject();
        try {
            Map<String, String> map = new HashMap<>();
            map.put("province", province);
            proviceJson = inviteService.getCityAsyn(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return proviceJson;
    }

}
