package com.stonedt.spider.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.stonedt.spider.entity.ResultNote;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ResultService {

    Map<String,Object> getStatistic();

    Map<String,Object> page(int from,int size,int category_id,String esIndex);

    Object pageList(int from,int size,int category_id,String esIndex,String keywordStr);

    int getEsCount(int category_id,String esindex,String beginDate, String endDate);

    JSONObject info(String id, String esIndex);

    String list(int category_id, String esIndex, String ids);

    Map<String,Object> getFileCount();

    Map<String,Object> getDataCount();

    List<Map<String, Object>> getResourceCount();

    List<Map<String, Object>> getDataType();


    List<ResultNote> pageResultNote( String limit , String keyword);
    ResultNote infoNote( String english);
    int saveNote( ResultNote note);
    int updateNote( ResultNote note);
    int removeNote( int id);
    int noteCount(String keyword);
}
