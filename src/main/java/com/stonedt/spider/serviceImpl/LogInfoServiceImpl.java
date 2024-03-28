package com.stonedt.spider.serviceImpl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.stonedt.spider.dao.IServerResourceDao;
import com.stonedt.spider.dao.SourceSiteDao;
import com.stonedt.spider.param.ParamES;
import com.stonedt.spider.service.LogInfoService;
import com.stonedt.spider.util.DateUtil;
import com.stonedt.spider.util.ElasticSearchUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LogInfoServiceImpl implements LogInfoService {

    @Autowired
    private IServerResourceDao iServerResourceDao;

    @Value("${es.result.url}")
    private String es_result_url;

    @Override
    public void record() {
        try {
            Set<Integer> ids = new HashSet<>();

            int size = 100;
            int from = 0;
            int total=0;
            Date date = new Date();
            String beginDate = DateUtil.getDateTime_day(date,-1);
            String endDate = DateUtil.getDateTime_day(date,0);
            do {
                //异常参数
                List<List<ParamES>> paramList = new ArrayList<>();
                List<ParamES> categoryParams = new ArrayList<>();
                categoryParams.add( ElasticSearchUtil.toParamES("term","log_level","ERROR") );
                paramList.add(categoryParams);
                //组装参数
                String params = ElasticSearchUtil.paramArr_map(from,size,"desc",beginDate,endDate,paramList);
                //查询
                String esUrl = es_result_url + "log_info*/infor/_search";
                String body = ElasticSearchUtil.sendEs(params,esUrl);

                JSONObject body_json = ElasticSearchUtil.body_toArray(body);
                if(body_json!=null && body_json.getJSONArray("data")!=null && body_json.getInteger("total")!=null ){
                    total=body_json.getInteger("total");
                    JSONArray data_jsonArr = body_json.getJSONArray("data");
                    for (int i = 0; i < data_jsonArr.size() ; i++) {
                        JSONObject data_json = data_jsonArr.getJSONObject(i);
                        if(data_json!=null && data_json.getJSONObject("_source")!=null){
                            JSONObject source_json = data_json.getJSONObject("_source");
                            if(source_json.getInteger("website_id")!=null){
                                ids.add(source_json.getInteger("website_id"));
                            }
                        }
                    }
                }
                from = from+size;
            }while (from<total);
            if(ids.size()>0){
                String param_ids = "";
                for (Integer id: ids) {
                    if(id!=null){
                        param_ids += ",'"+id+"'";
                    }
                }
                if(param_ids.indexOf(",")!=-1){
                    param_ids = param_ids.substring(1);
                    int updateType = iServerResourceDao.updateError(1,param_ids);
                    System.out.println("更新站点预警状态!  "+updateType+"条更新!");
                }
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
    }
}
