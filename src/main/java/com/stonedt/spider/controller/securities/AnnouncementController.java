package com.stonedt.spider.controller.securities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.stonedt.spider.util.ElasticSearchUtil;
import com.stonedt.spider.util.UtilConfig;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

/**
 * 公告
 *
 * @date 2020年2月17日 下午3:43:09
 */
@Controller
@RequestMapping("/announcement")
public class AnnouncementController {

    //	private final String es_url = "http://s1.stonedt.com:6303";
    //private final String es_url = UtilConfig.getURL2().substring(0,UtilConfig.getURL2().length()-1);
	@Value("${URL2}")
    private  String es_url;

    private final String announcementlist_api = "/announcement/announcementlist";

    /**
     * 列表
     */
    @RequestMapping(value = "/announcementlist", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String thesisnlist(String pageNum) {
        Map<String, String> map = new HashMap<>();
        map.put("size", "25");
        map.put("times", "");
        map.put("timee", "");
        map.put("page", pageNum);
        String response = "";
        Map<String, Object> result = new HashMap<String, Object>();
        List<Map<String, String>> list = new ArrayList<>();
        try {
            response = esArticle(map, announcementlist_api);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("list", list);
            result.put("totalData", 0);
            result.put("totalPage", 1);
        }
        if (StringUtils.isNotBlank(response)) {
            JSONObject parseObject = JSON.parseObject(response);
            String totalData = parseObject.getString("count");
            String totalPage = parseObject.getString("page_count");
            JSONArray jsonArray = parseObject.getJSONArray("data");
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                JSONObject jsonObject2 = jsonObject.getJSONObject("_source");
                Map<String, String> params = JSONObject.parseObject(jsonObject2.toJSONString(), new TypeReference<Map<String, String>>() {
                });
                list.add(params);
            }
            if (Integer.parseInt(totalData) > 5000) {
                totalPage = "500";
            }
            if (StringUtils.isBlank(totalPage)) {
                totalPage = "1";
            } else if ("0".equals(totalPage)) {
                totalPage = "1";
            }
            result.put("list", list);
            result.put("totalData", totalData);
            result.put("totalPage", totalPage);
        } else {
            result.put("list", list);
            result.put("totalData", 0);
            result.put("totalPage", 1);
        }
        return JSON.toJSONString(result);
    }

    @RequestMapping(value = "/announcement-page", method = RequestMethod.GET)
    public ModelAndView mainstreamMediaList(ModelAndView mv) {
        mv.setViewName("securitiesCompany/announcement");
        return mv;
    }


    @RequestMapping(value = "/announcement-neep", method = RequestMethod.GET)
    public ModelAndView mainstreamNeepMediaList(ModelAndView mv) {
        mv.setViewName("securitiesCompany/announcement_neep");
        return mv;
    }

    public String esArticle(Map<String, String> map, String api) {
        String size = map.get("size");
        String times = map.get("times");
        String timee = map.get("timee");
        String page = map.get("page");
        String url = es_url + api;
        String otherwebsiteid = "";
        if (map.containsKey("otherwebsiteid")) {
            otherwebsiteid = map.get("otherwebsiteid");
        }
        String params = "size=" + size + "&times=" + times + "&timee=" + timee + "&page=" + page + "&otherwebsiteid=" + otherwebsiteid;
        System.err.println(url + "?" + params);
        String sendPost = "";
        try {
            sendPost = sendPost(url, params);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sendPost;
    }

    public static String sendPost(String url, String params) throws IOException {
        PrintWriter out = null;
        BufferedReader in = null;
        URL realUrl = new URL(url);
        URLConnection conn = realUrl.openConnection();
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        conn.setDoOutput(true);
        conn.setDoInput(true);
        out = new PrintWriter(conn.getOutputStream());
        out.print(params);
        out.flush();
        in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            response.append(line);
        }
        try {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return response.toString();
    }


    /**
     * 列表
     */
    @RequestMapping(value = "/thesisnlistNeep", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String thesisnlistNeep(String pageNum, String otherwebsiteid) {
        Map<String, String> map = new HashMap<>();
        map.put("size", "25");
        map.put("times", "");
        map.put("timee", "");
        map.put("page", pageNum);
        map.put("otherwebsiteid", otherwebsiteid);
        String response = "";
        Map<String, Object> result = new HashMap<String, Object>();
        List<Map<String, String>> list = new ArrayList<>();
        try {
            JSONObject paramJson = new JSONObject();
            paramJson.put("timefield", "reportDate");
            paramJson.put("times", "");
            paramJson.put("size", "25");
            paramJson.put("index", "stonedt_announcement");
            paramJson.put("page", pageNum);
            paramJson.put("type", "infor");
            paramJson.put("timee", "");
            JSONArray andArray = new JSONArray();
            JSONObject fieldJson = new JSONObject();
            fieldJson.put("field", "otherwebsiteid");
            fieldJson.put("keyword", otherwebsiteid);
            andArray.add(fieldJson);
            paramJson.put("and",andArray);
//            response = esArticle(map, announcementlist_api);
            response = ElasticSearchUtil.sendPostRaw(paramJson.toJSONString(),es_url + ElasticSearchUtil.superdatalist);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("list", list);
            result.put("totalData", 0);
            result.put("totalPage", 1);
        }
        if (StringUtils.isNotBlank(response)) {
            JSONObject parseObject = JSON.parseObject(response);
            String totalData = parseObject.getString("count");
            String totalPage = parseObject.getString("page_count");
            JSONArray jsonArray = parseObject.getJSONArray("data");
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                JSONObject jsonObject2 = jsonObject.getJSONObject("_source");
                Map<String, String> params = JSONObject.parseObject(jsonObject2.toJSONString(), new TypeReference<Map<String, String>>() {
                });
                list.add(params);
            }
            if (Integer.parseInt(totalData) > 5000) {
                totalPage = "500";
            }
            if (StringUtils.isBlank(totalPage)) {
                totalPage = "1";
            } else if ("0".equals(totalPage)) {
                totalPage = "1";
            }
            result.put("list", list);
            result.put("totalData", totalData);
            result.put("totalPage", totalPage);
        } else {
            result.put("list", list);
            result.put("totalData", 0);
            result.put("totalPage", 1);
        }
        return JSON.toJSONString(result);
    }

}
