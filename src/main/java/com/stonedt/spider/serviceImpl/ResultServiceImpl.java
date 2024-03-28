package com.stonedt.spider.serviceImpl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.stonedt.spider.dao.SpiderFlowDao;
import com.stonedt.spider.entity.EsKeyword;
import com.stonedt.spider.entity.ResultNote;
import com.stonedt.spider.entity.Statistic;
import com.stonedt.spider.dao.ResultDao;
import com.stonedt.spider.entity.StatisticCount;
import com.stonedt.spider.param.ParamES;
import com.stonedt.spider.service.ResultService;
import com.stonedt.spider.util.DataUtil;
import com.stonedt.spider.util.DateUtil;
import com.stonedt.spider.util.ElasticSearchUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ResultServiceImpl implements ResultService {

    @Autowired
    private ResultDao resultDao;

    @Autowired
    private  SpiderFlowDao spiderFlowDao;

    @Value("${es.result.url}")
    private String es_result_url;

    @Override
    public Map<String, Object> getStatistic() {
        Map<String, Object> result = new HashMap<>();
        try {
            /*Statistic statistic = resultDao.findStatistic();
            if(statistic!=null){
                if(statistic.getFile_json()!=null && statistic.getFile_json().indexOf("{")!=-1){
                    result.put("file_json", JSONObject.parseObject(statistic.getFile_json()) );
                }
                if(statistic.getData_json()!=null && statistic.getData_json().indexOf("{")!=-1){
                    result.put("data_json", JSONObject.parseObject(statistic.getData_json()) );
                }
                if(statistic.getTrend_json()!=null && statistic.getTrend_json().indexOf("{")!=-1){
                    result.put("trend_json", JSONObject.parseObject(statistic.getTrend_json()) );
                }
                if(statistic.getStatus_json()!=null && statistic.getStatus_json().indexOf("[")!=-1){
                    result.put("status_json", JSONObject.parseArray(statistic.getStatus_json()) );
                }
                if(statistic.getType_json()!=null && statistic.getType_json().indexOf("[")!=-1){
                    result.put("type_json", JSONObject.parseArray(statistic.getType_json()) );
                }
            }*/
            //最新数据
            StatisticCount statisticCount = resultDao.findStatisticCount(null);


            //'昨天'比较参数
            String param1 = DateUtil.getDate_day(statisticCount.getCreate_time(),-1)+" 23:59:59";
            String param2 = DateUtil.getDate_day(statisticCount.getCreate_time(),-2)+" 23:59:59";
            StatisticCount data1 = resultDao.findStatisticCount(param1);
            StatisticCount data2 = resultDao.findStatisticCount(param2);

            double es_count_ratio = 0;
            double es_count_day_ratio = 0;
            double es_count_yesterday_ratio = 0;
            if(data1!=null && data2!=null){
                es_count_ratio = esRatio(data1.getEs_count(),data2.getEs_count());
                es_count_day_ratio = esRatio(data1.getEs_count_day(),data2.getEs_count_day());
                es_count_yesterday_ratio = esRatio(data1.getEs_count_yesterday(),data2.getEs_count_yesterday());
            }


            //'近7天'比较参数
            String week_param = DateUtil.getDate_day(statisticCount.getCreate_time(),-7)+" 23:59:59";
            StatisticCount week_data = resultDao.findStatisticCount(week_param);

            double es_count_week_ratio = 0.0;
            if(statisticCount!=null && week_data!=null){
                es_count_week_ratio = esRatio(statisticCount.getEs_count_week(),week_data.getEs_count_week());
            }

            //'上周'比较参数
            String size_param1 = DateUtil.getWeekLastDay(statisticCount.getCreate_time(),0);
            String size_param2 = DateUtil.getWeekLastDay(statisticCount.getCreate_time(),-1);
            StatisticCount size_data1 = resultDao.findStatisticCount(size_param1);
            StatisticCount size_data2 = resultDao.findStatisticCount(size_param2);

            double es_size_ratio = 0.0;
            if(size_data1!=null && size_data2!=null){
                es_size_ratio = esRatio(size_data1.getEs_size() , size_data2.getEs_size());
            }


            //返回组装
            result.put("es_count", statisticCount.getEs_count());
            result.put("es_size", DataUtil.divided_2(statisticCount.getEs_size(),(1024*1024)) );
            result.put("es_count_today", statisticCount.getEs_count_today());
            result.put("es_count_day", statisticCount.getEs_count_day());
            result.put("minio_count", statisticCount.getMinio_count());
            result.put("minio_size", statisticCount.getMinio_size());
            result.put("minio_disk_size", DataUtil.divided_2(statisticCount.getMinio_disk_size(),(1024*1024)) );
            result.put("es_count_yesterday", statisticCount.getEs_count_yesterday());
            result.put("es_count_week", statisticCount.getEs_count_week());
            result.put("group_json", JSONObject.parse(statisticCount.getGroup_json()) );
            result.put("category_json", JSONArray.parse(statisticCount.getCategory_json()) );
            result.put("type_json", JSONArray.parse(statisticCount.getType_json()) );
            result.put("create_time", DateUtil.getDateTime_day(statisticCount.getCreate_time(),0));

            result.put("es_count_ratio", es_count_ratio);
            result.put("es_count_day_ratio", es_count_day_ratio);
            result.put("es_count_yesterday_ratio", es_count_yesterday_ratio);
            result.put("es_count_week_ratio", es_count_week_ratio);
            result.put("es_size_ratio", es_size_ratio);

            return result;
        }catch (Exception e){
            e.printStackTrace();
        }

        return new HashMap<>();
    }


    public double esRatio(int value1, int value2 ){
        try {
            if(value1>0 && value2>0 && value1!=value2){
                double value = (value1-value2)/(double)value1;
                return value;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0.0;
    }

    public double esRatio(double value1, double value2 ){
        try {
            if(value1>0 && value2>0 && value1!=value2){
                double value = (value1-value2)/value1;
                return value;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0.0;
    }


    @Override
    public Map<String, Object> page(int from, int size,int category_id,String esIndex) {
        Map<String, Object> result = new HashMap<>();
        String body="";
        String paramEsIndex = "postal";
        try {
            List<ParamES> termParams = new ArrayList<>();
            if( category_id>0 ){
                List<Integer> websiteIds = spiderFlowDao.findWebsiteByCategoryId(category_id);
                for (Integer websiteId : websiteIds) {
                    if(websiteId!=null && websiteId>0){
                        termParams.add( ElasticSearchUtil.toParamES("term","website_id",websiteId) );
                    }
                }
                if(termParams.size()==0){
                    return new HashMap<>();
                }
            }
            if( esIndex!=null && !esIndex.equals("") ){
                paramEsIndex = esIndex;
            }else {
                /*String esindexs = "";
                List<String> esindexList = resultDao.findEsIndexAll();
                for (String str : esindexList ) {
                    if(str!=null){
                        termParams.add( ElasticSearchUtil.toParamES("term","esindex",str) );
                        esindexs += ","+str;
                    }
                }
                if(esindexs.indexOf(",")!=-1){
                    esindexs = esindexs.substring(1);
                }
                esindex = esindexs;*/
            }

            List<List<ParamES>> paramList = new ArrayList<>();
            paramList.add(termParams);

            String params = ElasticSearchUtil.paramArr_map(from,size,paramList);
            String esUrl = es_result_url+ paramEsIndex + "/infor/_search";
            body = ElasticSearchUtil.sendEs(params,esUrl);
            if(body!=null && !"".equals(body)){

                JSONObject json_body = ElasticSearchUtil.body_toArray(body);

                int total = 0;
                List<Map<String,Object>> resultArray = new ArrayList<>();
                if(json_body!=null){

                    if(json_body.getInteger("total")!=null){
                        total = json_body.getInteger("total");
                    }

                    JSONArray jsonArray = json_body.getJSONArray("data");
                    if(jsonArray!=null && jsonArray.size()>0){

                        for (int i = 0; i < jsonArray.size(); i++) {
                            JSONObject json_obj = jsonArray.getJSONObject(i);
                            if(json_obj!=null){
                                JSONObject source = json_obj.getJSONObject("_source");

                                if(source!=null){
                                    String article_public_id = source.getString("article_public_id")!=null?source.getString("article_public_id"):"";
                                    String title = source.getString("title")!=null?source.getString("title"):"";
                                    String content = source.getString("content")!=null?source.getString("content"):"";
                                    String contenthtml = source.getString("content")!=null?source.getString("content"):"";
                                    String source_name = source.getString("source_name")!=null?source.getString("source_name"):"";
                                    String source_url = source.getString("source_url")!=null?source.getString("source_url"):"";
                                    String publish_time = source.getString("publish_time")!=null?source.getString("publish_time"):"";

                                    Integer website_id = source.getInteger("website_id")!=null?source.getInteger("website_id"):null;
                                    Integer seed_id = source.getInteger("seed_id")!=null?source.getInteger("seed_id"):null;
                                    Integer type = source.getInteger("type")!=null?source.getInteger("type"):null;

                                    String sourcewebsitename = source.getString("sourcewebsitename")!=null?source.getString("sourcewebsitename"):"";
                                    String datasource_type = source.getString("datasource_type")!=null?source.getString("datasource_type"):"";
                                    String es_index = source.getString("es_index")!=null?source.getString("es_index"):"";

                                    if(content==null && contenthtml!=null){
                                        try {
                                            Document document = Jsoup.parse(contenthtml);
                                            content = document.text();
                                        }catch (Exception e_content){
                                            e_content.printStackTrace();
                                            content = "";
                                        }
                                    }

                                    Map<String, Object> source_data = new HashMap<>();
                                    source_data.put("article_public_id", article_public_id);
                                    source_data.put("title", title);
                                    source_data.put("content", content);
                                    source_data.put("source_name", source_name);
                                    source_data.put("source_url", source_url);
                                    source_data.put("publish_time", publish_time);

                                    source_data.put("website_id", website_id);
                                    source_data.put("seed_id", seed_id);
                                    source_data.put("type", type);

                                    source_data.put("sourcewebsitename", sourcewebsitename);
                                    source_data.put("datasource_type", datasource_type);
                                    source_data.put("esindex", es_index);
                                    resultArray.add(source_data);
                                }
                            }
                        }

                    }
                }

                result.put("data",resultArray);
                result.put("total",total);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Object pageList(int from, int size, int category_id, String esIndex, String keywordStr) {
        Map<String,Object> result = new HashMap<>();
        result.put("tableNames",new ArrayList<>());
        result.put("data",new ArrayList<>());
        result.put("total",0);

        String body="";
        String paramEsIndex = "postal,stonedt_*,-*log*";
        try {
            List<ParamES> termParams = new ArrayList<>();
            //分类检查
            if( category_id>0 ){
                List<Integer> websiteIds = spiderFlowDao.findWebsiteByCategoryId(category_id);
                for (Integer websiteId : websiteIds) {
                    if(websiteId!=null && websiteId>0){
                        termParams.add( ElasticSearchUtil.toParamES("term","website_id",websiteId) );
                    }
                }
                if(termParams.size()==0){
                    return new HashMap<>();
                }
            }

            //关键词组装
            List<EsKeyword> keywordList = new ArrayList<>();
            if(keywordStr!=null && !keywordStr.equals("")){
                String keyword = keywordStr;
                List<String> likeKeyList = new ArrayList<>();
                likeKeyList.add("title");
                likeKeyList.add("content");
                likeKeyList.add("source_name");
                likeKeyList.add("es_index");

                EsKeyword esKeyword = new EsKeyword();
                esKeyword.setKeyword(keyword);
                esKeyword.setKeys(likeKeyList);
                keywordList.add(esKeyword);
            }


            //索引
            if( esIndex!=null && !esIndex.equals("") ){
                paramEsIndex = esIndex;
            }else {
            }

            List<List<ParamES>> paramList = new ArrayList<>();
            paramList.add(termParams);

            String params = ElasticSearchUtil.paramArr_map( from, size, paramList, keywordList );
            String esUrl = es_result_url+ paramEsIndex + "/infor/_search";
            body = ElasticSearchUtil.sendEs(params,esUrl);
            if(body!=null && !"".equals(body)){

                JSONObject json_body = ElasticSearchUtil.body_toArray(body);

                int total = 0;

                if(json_body!=null){

                    if(json_body.getInteger("total")!=null){
                        total = json_body.getInteger("total");
                    }

                    JSONArray jsonArray = json_body.getJSONArray("data");
                    if(jsonArray!=null && jsonArray.size()>0){

                        JSONArray dataJsonArr = new JSONArray();
                        String tableNameStr = "";
                        String noKey = "{classify},{otherwebsiteid},{websitelogo},{kafka_queue_name},{otherseedid},{customlable},{hbase_table},{similarvolume},{heatvolume},{contenthtml},{extend_string_one},{type_of_article},{es_type},{sourcewebsitename}";
                        for (int i = 0; i < jsonArray.size(); i++) {
                            JSONObject json_obj = jsonArray.getJSONObject(i);
                            if(json_obj!=null){
                                JSONObject source = json_obj.getJSONObject("_source");

                                List<Map<String,Object>> dataList = new ArrayList<>();
                                if(source!=null){
                                    try {
                                        for (String jsonKey : source.keySet()){
                                            if(noKey.indexOf("{"+jsonKey+"}")!=-1 ){
                                                continue;
                                            }
                                            if(tableNameStr.indexOf("#k"+jsonKey+"#k")==-1 && !jsonKey.equals("article_public_id") && !jsonKey.equals("title") ){
                                                tableNameStr += ",#k"+jsonKey+"#k";
                                            }
                                            Map<String,Object> dataMap = new HashMap<>();
                                            dataMap.put("key", jsonKey);
                                            dataMap.put("value", source.get(jsonKey));
                                            dataList.add(dataMap);
                                        }
                                        dataJsonArr.add(dataList);
                                    }catch (Exception e_source){
                                        e_source.printStackTrace();
                                    }
                                }
                            }
                        }
                        String[] tableNames = null;
                        if(tableNameStr.indexOf(",")!=-1){
                            try {
                                tableNameStr = tableNameStr.replace("#k","").trim();
                                if(!tableNameStr.equals("")){
                                    tableNameStr = "article_public_id,title"+tableNameStr;
                                    tableNames = tableNameStr.split(",");
                                    result.put("tableTitles",getTableTitles(tableNameStr.split(",")));
                                    result.put("tableNames",tableNames);
                                    result.put("data",dataJsonArr);
                                    result.put("total",total);
                                }
                            }catch (Exception e_result){
                                e_result.printStackTrace();
                            }
                        }
                        System.out.println("es.dataArr 遍历结束!");
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public String[] getTableTitles(String[] names){
        String[] result = names;

        List<ResultNote> noteList = resultDao.noteList();
        for (int i = 0; i < names.length; i++) {
            for (ResultNote note : noteList ) {
                try {
                    if(note.getEnglish_key().equals(names[i])){
                        result[i] = note.getChina_key();
                        break;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    @Override
    public int getEsCount(int category_id, String esindex, String beginDate, String endDate) {
        try {
            List<ParamES> termParams = new ArrayList<>();
            if( category_id>0 ){
                List<Integer> websiteIds = spiderFlowDao.findWebsiteByCategoryId(category_id);
                for (Integer websiteId : websiteIds) {
                    if(websiteId!=null && websiteId>0){
                        termParams.add( ElasticSearchUtil.toParamES("term","website_id",websiteId) );
                    }
                }
                if(termParams.size()==0){
                    return 0;
                }
            }

            List<List<ParamES>> paramList = new ArrayList<>();
            paramList.add(termParams);
            String params = ElasticSearchUtil.esCountParamArr_map(beginDate,endDate,paramList);
            String esUrl = es_result_url+ esindex + "/infor/_search";
            String body = ElasticSearchUtil.sendEs(params,esUrl);
            if(body!=null && !"".equals(body)){
                JSONObject json_body = ElasticSearchUtil.body_toArray(body);
                if(json_body!=null){
                    if(json_body.getInteger("total")!=null){
                        return json_body.getInteger("total");
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public JSONObject info(String id, String esindex) {

        JSONObject result = new JSONObject();
        String body="";
        try {
            if(id!=null && esindex!=null){
                String params = ElasticSearchUtil.paramDetail(id);
                String esUrl = es_result_url+ esindex + "/infor/_search";
                body = ElasticSearchUtil.sendEs(params,esUrl);
                if(body!=null && !"".equals(body)){

                    JSONObject json_map = ElasticSearchUtil.body_toMap(body);

                    if(json_map!=null){
                        JSONObject source = json_map.getJSONObject("_source");
                        if(source!=null){
                            if(source!=null){
                                String es_index = source.getString("es_index")!=null?source.getString("es_index"):"";
                                source.put("esindex",es_index);
                            }
                            result = source;
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String list( int category_id, String esIndex,  String ids ) {
        String paramEsIndex = "postal,stonedt_*";
        try {
            List<ParamES> termParams = new ArrayList<>();
            //分类参数
            if( category_id>0 ){
                List<Integer> websiteIds = spiderFlowDao.findWebsiteByCategoryId(category_id);
                for (Integer websiteId : websiteIds) {
                    if(websiteId!=null && websiteId>0){
                        termParams.add( ElasticSearchUtil.toParamES("term","website_id",websiteId) );
                    }
                }
                if(termParams.size()==0){
                    return null;
                }
            }

            //索引
            if( esIndex!=null && !esIndex.equals("") ){
                paramEsIndex = esIndex;
            }else {
            }

            //id参数
            if(ids!=null && !ids.equals("")){
                String[] idArr = ids.split(",");
                for (int i = 0; i < idArr.length; i++) {
                    termParams.add( ElasticSearchUtil.toParamES("term","article_public_id",idArr[i]) );
                }
            }
            List<List<ParamES>> paramList = new ArrayList<>();
            paramList.add(termParams);

            String params = ElasticSearchUtil.paramArr_map( -1, -1, paramList, null );

            String esUrl = es_result_url+ paramEsIndex + "/infor/_search";
            String body = ElasticSearchUtil.sendEs(params,esUrl);
            if(body!=null && !"".equals(body)){
                JSONObject json_body = ElasticSearchUtil.body_toArray(body);
                if(json_body!=null){
                    JSONArray jsonArray = json_body.getJSONArray("data");
                    if(jsonArray!=null && jsonArray.size()>0){
                        JSONArray dataJsonArr = new JSONArray();

                        for (int i = 0; i < jsonArray.size(); i++) {
                            JSONObject json_obj = jsonArray.getJSONObject(i);
                            if(json_obj!=null){
                                JSONObject source = json_obj.getJSONObject("_source");
                                if(source!=null){
                                    dataJsonArr.add(source);
                                }
                            }
                        }
                        if(dataJsonArr.size()>0){
                            return JSONObject.toJSONString(dataJsonArr);
                        }

                        System.out.println("es.dataArr 遍历结束!");
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public Map<String, Object> getFileCount() {
        Map<String, Object> result = new HashMap<>();
        result.put("file_gather_count",2081);
        result.put("file_save_count",500);
        result.put("disk_count", "2T");
        return result;
    }

    @Override
    public Map<String, Object> getDataCount() {
        Map<String, Object> result = new HashMap<>();
        result.put("file_count",3939);
        result.put("file_save_count",2705);
        result.put("disk_count",1400);
        result.put("file__count_ratio",-0.018);
        result.put("file_save_count_ratio",0.027);
        result.put("disk_count_ratio",0.068);

        return result;
    }

    @Override
    public List<Map<String, Object>> getResourceCount() {
        List<Map<String, Object>> resultArr = new ArrayList<>();
        Map<String, Object> result = new HashMap<>();
        result.put("name", "有效信源");
        result.put("file_save_count",580);
        resultArr.add(result);

        result = new HashMap<>();
        result.put("name", "失效信源");
        result.put("file_save_count",735);
        resultArr.add(result);

        result = new HashMap<>();
        result.put("namee", "抓取异常");
        result.put("file_save_count",1048);
        resultArr.add(result);

        return resultArr;
    }

    @Override
    public List<Map<String, Object>> getDataType() {
        List<Map<String, Object>> resultArr = new ArrayList<>();
        Map<String, Object> result = new HashMap<>();
        result.put("name", "百科");
        result.put("file_save_count",580);
        resultArr.add(result);

        result = new HashMap<>();
        result.put("name", "新闻");
        result.put("file_save_count",0.7529);
        resultArr.add(result);

        result = new HashMap<>();
        result.put("namee", "电商");
        result.put("file_save_count",1048);
        resultArr.add(result);

        return resultArr;
    }

    @Override
    public List<ResultNote> pageResultNote(String limit, String keyword) {
        return resultDao.pageResultNote(limit,keyword);
    }

    @Override
    public ResultNote infoNote(String english) {
        return resultDao.infoNote(english);
    }

    @Override
    public int saveNote(ResultNote note) {
        return resultDao.saveNote(note);
    }

    @Override
    public int updateNote(ResultNote note) {
        return resultDao.updateNote(note);
    }

    @Override
    public int removeNote(int id) {
        return resultDao.removeNote(id);
    }

    @Override
    public int noteCount(String keyword) {
        return resultDao.noteCount(keyword);
    }
}
