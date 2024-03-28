package com.stonedt.spider.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.stonedt.spider.service.OverseasInfoService;
import com.stonedt.spider.util.ElasticSearchUtil;
import com.stonedt.spider.util.UtilConfig;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OverseasInfoServiceImpl implements OverseasInfoService {

    /**
     * 获取推特数据
     *
     * @param map
     * @return
     */
    @Override
    public JSONObject getTwitterList(Map<String, Object> map) {
        JSONObject response = new JSONObject();
        try {
            String page = String.valueOf(map.get("page"));
            String size = String.valueOf(map.get("size"));
            String params = "?&page=" + page + "&size=" + size + "&searchtype=0";
            String api = ElasticSearchUtil.twitterlist;
            String url = UtilConfig.getURL2() + api;
            System.out.println(url + params);
            String responseData = ElasticSearchUtil.sendPost1(url, params);
            if (responseData != null && !responseData.equals("")) {
                JSONObject esResponseJson = JSON.parseObject(responseData);
                Integer totalCount = esResponseJson.getInteger("count");
                Integer pageCount = esResponseJson.getInteger("page_count");
                Integer pageNum = esResponseJson.getInteger("page");
                JSONArray dataArray = esResponseJson.getJSONArray("data");
                JSONArray responseArray = new JSONArray();
                for (int i = 0; i < dataArray.size(); i++) {
                    JSONObject dataJson = (JSONObject) dataArray.get(i);
                    JSONObject _sourceJson = dataJson.getJSONObject("_source");
                    responseArray.add(_sourceJson);
                }
                response.put("totalCount", totalCount);
                response.put("pageCount", pageCount);
                response.put("pageNum", pageNum);
                response.put("data", responseArray);
                response.put("code", 200);
                response.put("msg", "数据成功返回！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JSONArray dataArray = new JSONArray();
            response.put("code", 500);
            response.put("msg", "数据返回异常！");
            response.put("data", dataArray);
        }
        return response;
    }


    /**
     * 获取境外数据列表
     * @param map
     * @return
     */
    @Override
    public JSONObject getOverseasList(Map<String, Object> map) {
        JSONObject response = new JSONObject();
        try {
            String page = String.valueOf(map.get("page"));
            String size = String.valueOf(map.get("size"));
            String otherwebsiteid = String.valueOf(map.get("otherwebsiteid"));

            String params = "?&page=" + page + "&size=" + size + "&searchtype=0&otherwebsiteid="+otherwebsiteid;
            String api = ElasticSearchUtil.foreignmedialist;
            String url = UtilConfig.getURL2() + api;
            System.out.println(url + params);
            String responseData = ElasticSearchUtil.sendPost1(url, params);
            if (responseData != null && !responseData.equals("")) {
                JSONObject esResponseJson = JSON.parseObject(responseData);
                Integer totalCount = esResponseJson.getInteger("count");
                Integer pageCount = esResponseJson.getInteger("page_count");
                Integer pageNum = esResponseJson.getInteger("page");
                JSONArray dataArray = esResponseJson.getJSONArray("data");
                JSONArray responseArray = new JSONArray();
                for (int i = 0; i < dataArray.size(); i++) {
                    JSONObject dataJson = (JSONObject) dataArray.get(i);
                    JSONObject _sourceJson = dataJson.getJSONObject("_source");
                    responseArray.add(_sourceJson);
                }
                response.put("totalCount", totalCount);
                response.put("pageCount", pageCount);
                response.put("pageNum", pageNum);
                response.put("data", responseArray);
                response.put("code", 200);
                response.put("msg", "数据成功返回！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JSONArray dataArray = new JSONArray();
            response.put("code", 500);
            response.put("msg", "数据返回异常！");
            response.put("data", dataArray);
        }
        return response;
    }
}
