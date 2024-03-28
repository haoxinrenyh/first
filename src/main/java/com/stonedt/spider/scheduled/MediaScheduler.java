package com.stonedt.spider.scheduled;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.stonedt.spider.util.ElasticSearchUtil;
import com.stonedt.spider.util.UtilConfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;


import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

/**
 * 定时更新自媒体数据
 */
@Component
//@Controller
/*@EnableScheduling*/
public class MediaScheduler {
    /*@Scheduled(cron = "40 49 10 * * ? ")*/
    public void checkMediaCount() {
        List<String> keywordList = getKeywords();
//        String keywords[] = keywordStr.split(",");
//        for (int i = 0; i < keywords.length; i++) {
        for (int i = 0; i < keywordList.size(); i++) {
            try {
                String keyword = keywordList.get(i);
//                getMidiaInfo(keyword);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取分页
     */
    public static Integer getMidiaInfoPage(Map<String, String> map) {
        Integer totalPage = 0;
        try {
            String esResponse = esArticle(map, ElasticSearchUtil.wemedialist);
            JSONObject esResponseJson = JSON.parseObject(esResponse);
            String code = esResponseJson.getString("code");
            if (code.equals("200")) {
                Integer page_count = esResponseJson.getInteger("page_count");  // 页数
                return page_count;
            } else {
                System.out.println("es返回出错");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalPage;
    }

    /**
     * 获取行业列表
     *
     * @param args
     */
    public static JSONObject getMidiaInfo(String keyword) {
        JSONObject responseJson = new JSONObject();
        try {
            Map<String, String> map = new HashMap<String, String>();
            map.put("size", "10");
            map.put("page", "1");
            map.put("keyword", keyword);
            map.put("times", "");
            map.put("timee", "");
            Integer totalPage = getMidiaInfoPage(map);  // 请求总页数
            if (totalPage > 500) {
                totalPage = 500;
                System.out.println(keyword);
            }
            for (int i = 0; i < totalPage; i++) {
                Integer index = i + 1;
                map.put("page", index.toString());
                String esResponse = esArticle(map, ElasticSearchUtil.wemedialist);
                JSONObject esResponseJson = JSON.parseObject(esResponse);
                String code = esResponseJson.getString("code");
                if (code.equals("200")) {
                    JSONArray dataArray = esResponseJson.getJSONArray("data");
                    for (int j = 0; j < dataArray.size(); j++) {
                        JSONObject dataJson = (JSONObject) dataArray.get(j);
                        JSONObject _sourceJson = dataJson.getJSONObject("_source");
                        String platform_name = _sourceJson.getString("platform_name");  // 平台
                        String name = _sourceJson.getString("name");
                        String id = _sourceJson.getString("id");
                        getMediaDeatil(platform_name, name, id);
                    }
                } else {
                    System.out.println("es返回出错");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseJson;
    }

    /**
     * 请求详情数据
     *
     * @param args
     */
    public static void getMediaDeatil(String platform_name, String name, String id) {
        try {
            JSONObject paramJson = new JSONObject();
            paramJson.put("timefield", "publish_time");
            paramJson.put("times", "");
            paramJson.put("timee", "");
            paramJson.put("type", "infor");
            paramJson.put("index", "postal");
            JSONArray andArray = new JSONArray();
            JSONObject platform_nameJson = new JSONObject();
            JSONObject nameJson = new JSONObject();
            nameJson.put("field", "author");
            nameJson.put("keyword", name);
            platform_nameJson.put("field", "sourcewebsitename");
            platform_nameJson.put("keyword", platform_name);
            andArray.add(platform_nameJson);
            andArray.add(nameJson);
            paramJson.put("and", andArray);
            String url = UtilConfig.getURL().substring(0,UtilConfig.getURL().length()-1);
            String esResponse = ElasticSearchUtil.sendPostRaw(paramJson.toJSONString(), url + ElasticSearchUtil.superdatalist);
            JSONObject esResponseJson = JSON.parseObject(esResponse);
            String code = esResponseJson.getString("code");
            if (code.equals("200")) {
                Integer count = esResponseJson.getInteger("count");
                editDataCount(id, count);
            } else {
                System.out.println("es接口返回异常");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改数据量
     */
    public static void editDataCount(String id, Integer count) {
        try {
            String param = "?&id=" + id + "&realisticnum=" + count;
            String esResponse = ElasticSearchUtil.sendPost(ElasticSearchUtil.updaterealisticnum, param);
            JSONObject esResponseJson = JSON.parseObject(esResponse);
            String code = esResponseJson.getString("code");
            if (code.equals("200")) {
                System.out.println("当前时间是=====>" + new Date() + "<======>" + id + "<=====更新数据量成功=====>" + count);
            } else {
                System.out.println("当前时间是=====>" + new Date() + "<======>" + id + "<=====更新数据失败=====>" + count);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送列表数据
     *
     * @param map
     * @param api
     * @return
     */
    public static String esArticle(Map<String, String> map, String api) {
        String sendPost = "";
        try {
            String size = map.get("size");
            String page = map.get("page");
            String keyword = map.get("keyword");
            if (keyword != null) {
                keyword = URLEncoder.encode(keyword, "UTF-8");
            }
            String searchType = map.get("searchType");
            String times = map.get("times");
            String timee = map.get("timee");
            String searchkeyword = map.get("searchkeyword");
            String author = map.get("author");
            String url = api;
            String params = "?size=" + size + "&page=" + page + "&keyword=" + keyword
                    + "&searchType=" + searchType
                    + "&times=" + times + "&timee=" + timee + "&searchkeyword=" + searchkeyword
                    + "&author=" + author;
//            System.err.println(url + "?" + params);
            try {
                sendPost = ElasticSearchUtil.sendPost(url, params);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sendPost;
    }


    public static List getKeywords() {
        List<String> list = new ArrayList<String>();
        list.add("人民日报");
        list.add("新华视点");
        list.add("央视新闻");
        list.add("新华网");
        list.add("中国新闻周刊");
        list.add("澎湃新闻");
        list.add("第一财经日报");
        list.add("新京报");
        list.add("中国日报");
        list.add("侠客岛");
        list.add("中国新闻社");
        list.add("侠客岛");
        list.add("央视网快看");
        list.add("新京报我们视频");
        list.add("三联生活周刊");
        list.add("新京报我们视频");
        list.add("财新网");
        list.add("央视网");
        list.add("健康时报");
        list.add("时差视频");
        list.add("梨视频");
        list.add("头条新闻");
        list.add("时间视频");
        list.add("人物");
        list.add("封面新闻");
        list.add("成都商报");
        list.add("深圳商报");
        list.add("长安街知事");
        list.add("当代娱乐");
        list.add("楚天交通广播");
        list.add("潇湘晨报");
        list.add("汽车");
        list.add("游戏");
        list.add("育儿");
        list.add("美食");
        list.add("教育");
        list.add("体育");
        list.add("娱乐");
        list.add("国际");
        list.add("体育");
        list.add("娱乐");
        list.add("社会");
        list.add("财经");
        list.add("时事");
        list.add("科技");
        list.add("情感");
        list.add("汽车");
        list.add("教育");
        list.add("时尚");
        list.add("游戏");
        list.add("军事");
        list.add("旅游");
        list.add("美食");
        list.add("文化");
        list.add("健康");
        list.add("养生");
        list.add("搞笑");
        list.add("家居");
        list.add("动漫");
        list.add("宠物");
        list.add("母婴");
        list.add("育儿");
        list.add("星座");
        list.add("运势");
        list.add("历史");
        list.add("音乐");
        list.add("无人机");
        return list;
    }


    public static void main(String[] args) {
//        checkMediaCount();
    }
}
