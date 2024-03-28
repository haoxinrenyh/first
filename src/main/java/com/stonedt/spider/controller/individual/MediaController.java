package com.stonedt.spider.controller.individual;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.stonedt.spider.util.DataMonitorVO;
import com.stonedt.spider.util.DateUtil;
import com.stonedt.spider.util.UtilConfig;

/**
 * 自媒体
 *
 * @date 2020年2月5日 下午2:26:46
 */
@Controller
@RequestMapping("/media")
public class MediaController {

    //	private final String es_url = "http://s1.stonedt.com:6302";
    //private final String es_url = UtilConfig.getURL().substring(0,UtilConfig.getURL().length()-1);
	@Value("${URL2}")
    private  String es_url;
	
    /**
     * 自媒体列表
     */
    @RequestMapping(value = "/list", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String list(Integer pageNum, String keyword) {
        Map<String, String> map = new HashMap<>();
        map.put("size", "10");
        map.put("page", String.valueOf(pageNum));
        map.put("keyword", keyword);
        map.put("times", "");
        map.put("timee", "");
        String response = "";
        Map<String, Object> result = new HashMap<String, Object>();
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        try {
            response = esArticle(map, "/media/wemedialist");
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
            int size = jsonArray.size();
            for (int i = 0; i < size; i++) {
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

    /**
     * 文章列表
     */
    @RequestMapping(value = "/article", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String article(Integer pageNum, String author, String time, String searchkeyword, String platform_name) {
        String times = "", timee = "";
        if (StringUtils.isNotBlank(time)) {
            if ("24h".equals(time)) {
                JSONObject recruitmentMonitorStr = recruitmentMonitorStr(1);
                times = recruitmentMonitorStr.getString("times");
                timee = recruitmentMonitorStr.getString("timee");
            }
            if ("7d".equals(time)) {
                JSONObject recruitmentMonitorStr = recruitmentMonitorStr(7);
                times = recruitmentMonitorStr.getString("times");
                timee = recruitmentMonitorStr.getString("timee");
            }
            if ("30d".equals(time)) {
                JSONObject recruitmentMonitorStr = recruitmentMonitorStr(30);
                times = recruitmentMonitorStr.getString("times");
                timee = recruitmentMonitorStr.getString("timee");
            }
        }
        Map<String, String> map = new HashMap<>();
        map.put("size", "10");
        map.put("page", String.valueOf(pageNum));
        map.put("times", times);
        map.put("timee", timee);
        map.put("searchType", "1");
        map.put("searchkeyword", searchkeyword);
        map.put("author", author);
        map.put("sourcewebsitename", platform_name);
        String response = "";
        Map<String, Object> result = new HashMap<String, Object>();
        List<DataMonitorVO> list = new ArrayList<DataMonitorVO>();
        try {
            response = esArticle(map, "/yqt/qbsearchcontent");
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
            int size = jsonArray.size();
            for (int i = 0; i < size; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                JSONObject jsonObject2 = jsonObject.getJSONObject("_source");
                DataMonitorVO dataMonitorVO = JSON.toJavaObject(jsonObject2, DataMonitorVO.class);
                list.add(dataMonitorVO);
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

    public JSONObject recruitmentMonitorStr(Integer days) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String timee = now.format(dateTimeFormatter);
        if (days == null) {
            days = 0;
        }
        String times = now.minusDays(days).format(dateTimeFormatter);
        JSONObject result = new JSONObject();
        result.put("times", times);
        result.put("timee", timee);
        return result;
    }

    @RequestMapping(value = "/mainstream-media-list", method = RequestMethod.GET)
    public ModelAndView mainstreamMediaList(ModelAndView mv) {
        mv.setViewName("individualMedia/mainstream-media-list");
        return mv;
    }

    @RequestMapping(value = "/mainstream-media-article", method = RequestMethod.GET)
    public ModelAndView mainstreamMediaArtcile(ModelAndView mv) {
        mv.setViewName("individualMedia/mainstream-media-article");
        return mv;
    }

    @RequestMapping(value = "/industry-media-list", method = RequestMethod.GET)
    public ModelAndView industryMediaList(ModelAndView mv) {
        mv.setViewName("individualMedia/industry-media-list");
        return mv;
    }

    @RequestMapping(value = "/industry-media-article", method = RequestMethod.GET)
    public ModelAndView industryMediaArtcile(ModelAndView mv) {
    	mv.addObject("nowArray", DateUtil.getNowDate());
        mv.setViewName("individualMedia/industry-media-article");
        return mv;
    }

    public String esArticle(Map<String, String> map, String api) {
        String sendPost = "";
        try {
            String size = map.get("size");
            String page = map.get("page");
            String keyword = map.get("keyword");
            String sourcewebsitename = map.get("sourcewebsitename");
            if (keyword != null) {
                keyword = URLEncoder.encode(keyword, "UTF-8");
            }
            if (sourcewebsitename != null) {
                sourcewebsitename = URLEncoder.encode(sourcewebsitename, "UTF-8");
            }
            String searchType = map.get("searchType");
            String times = map.get("times");
            String timee = map.get("timee");
            String searchkeyword = map.get("searchkeyword");
            String author = map.get("author");
            String url = es_url + api;
//            String params = "size=" + size + "&page=" + page + "&keyword=" + keyword
//                    + "&searchType=" + searchType
//                    + "&times=" + times + "&timee=" + timee + "&searchkeyword=" + searchkeyword
//                    + "&author=" + author + "&sourcewebsitename=" + sourcewebsitename;
            String params = "size=" + size + "&page=" + page + "&keyword=" + keyword
                    + "&searchType=" + searchType
                    + "&times=" + times + "&timee=" + timee + "&searchkeyword=" + searchkeyword
                    + "&author=" + author + "&sourceWebsite=" + sourcewebsitename;
            System.err.println(url + "?" + params);
            try {
                sendPost = sendPost(url, params);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
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

    //微信公众号列表
    @RequestMapping("toWeiXinPage")
    public ModelAndView toWeiXinPage(ModelAndView mv, @RequestParam("classify") String classify, HttpServletRequest request) {
        mv.addObject("siteName", "微信公众号");
        mv.setViewName("individualMedia/abroadList");
        if (classify.equals("weixinpublic")) {
            mv.addObject("webName", "微信公众号");
            //为了不和门户数据重复先给101
            mv.addObject("classify", "101");
        }
        mv.addObject("nowArray", DateUtil.getNowDate());
        request.setAttribute("LEFT", classify);
        return mv;

    }

}
