package com.stonedt.spider.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.stonedt.spider.util.ElasticSearchUtil;
import com.stonedt.spider.util.UtilConfig;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class KnowledgeServiceImpl {
    /**
     * 请求类型数据
     */
    public String getTypeData(JSONObject json) {
        String responseData = "";
        try {
            String times = json.getString("times");
            String timee = json.getString("timee");
            String size = json.getString("size");
            String page = json.getString("page");
            String type = json.getString("type");

            String params = "?&times=" + times + "&timee=" + timee + "&size=" + size + "&page=" + page + "&type=" + type;
            String api = ElasticSearchUtil.patentinformationtype;
            String url = UtilConfig.getURL2() + api;
            System.out.println(url + params);
            String response = ElasticSearchUtil.sendPost1(url, params);
            System.out.println(response);
            JSONObject responseJson = JSON.parseObject(response);
            JSONObject aggregationsJson = responseJson.getJSONObject("aggregations");
            JSONObject group_by_tagsJson = aggregationsJson.getJSONObject("group_by_tags");
            JSONArray bucketsArray = group_by_tagsJson.getJSONArray("buckets");
            responseData = bucketsArray.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseData;
    }

    /**
     * 请求列表数据
     */
    public String getKnowledgeListData(JSONObject json) {
        JSONObject retuenObj = new JSONObject();
        try {
            String times = json.getString("times");
            String timee = json.getString("timee");
            String size = json.getString("size");
            String page = json.getString("page");
            String type = json.getString("type");

            String params = "?&times=" + times + "&timee=" + timee + "&size=" + size + "&page=" + page + "&type=" + type;
            String api = ElasticSearchUtil.patentlist;
            String url = UtilConfig.getURL2() + api;
            System.out.println(url + params);
            String response = ElasticSearchUtil.sendPost1(url, params);
            System.out.println(response);
            JSONObject responseJson = JSON.parseObject(response);
            Integer totalCount = responseJson.getInteger("count");
            Integer page_count = responseJson.getInteger("page_count");
            Integer pageNum = responseJson.getInteger("page");
            retuenObj.put("totalData", totalCount);
            retuenObj.put("totalPage", page_count);
            retuenObj.put("pagenum", pageNum);

            JSONArray dataArray = responseJson.getJSONArray("data");
            JSONArray retuenArray = new JSONArray();
            for (int i = 0; i < dataArray.size(); i++) {
                JSONObject dataObj = (JSONObject) dataArray.get(i);
                JSONObject _sourceObj = dataObj.getJSONObject("_source");
                retuenArray.add(_sourceObj);
            }
            retuenObj.put("list", retuenArray);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return retuenObj.toString();
    }


    /**
     * 获取专利详情
     */
    public JSONObject getPatentDetail(String article_public_id) {
        JSONObject returnObj = new JSONObject();
        try {
            String params = "?&article_public_id=" + article_public_id;
            String api = ElasticSearchUtil.getpatentdetail;
            String url = UtilConfig.getURL2() + api;
            System.out.println(url + params);
            String response = ElasticSearchUtil.sendPost1(url, params);
            System.out.println(response);
            JSONObject responseObj = JSON.parseObject(response);
            String title = responseObj.getString("title");
            String applyDay = responseObj.getString("applyDay");
            String applyMark = responseObj.getString("applyMark");
            String imgUrl = responseObj.getString("imgUrl");
            String content_html = responseObj.getString("content_html");
            returnObj.put("title", title);
            returnObj.put("applyDay", applyDay);
            returnObj.put("applyMark", applyMark);
            returnObj.put("imgUrl", imgUrl);
            returnObj.put("content_html", content_html);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnObj;
    }
}
