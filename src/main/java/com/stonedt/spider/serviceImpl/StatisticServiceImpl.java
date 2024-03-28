package com.stonedt.spider.serviceImpl;

import cn.hutool.json.JSONArray;
import com.alibaba.fastjson.JSON;
import com.stonedt.spider.dao.IServerResourceDao;
import com.stonedt.spider.dao.ResultDao;
import com.stonedt.spider.dao.WebSiteTypeDao;
import com.stonedt.spider.entity.*;
import com.stonedt.spider.service.MinioService;
import com.stonedt.spider.service.ResultService;
import com.stonedt.spider.service.StatisticService;
import com.stonedt.spider.util.DataUtil;
import com.stonedt.spider.util.DateUtil;
import com.stonedt.spider.util.ElasticSearchUtil;
import com.stonedt.spider.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StatisticServiceImpl  implements StatisticService {


    @Autowired
    private WebSiteTypeDao webSiteTypeDao;

    @Value("${es.result.url}")
    private String es_result_url;

    @Autowired
    private ResultService resultService;

    @Autowired
    private ResultDao resultDao;

    @Autowired
    private MinioService minioService;

    @Autowired
    private IServerResourceDao resourceDao;

    @Override
    public boolean findEsCount() {
        try {
            Date create_time = new Date();

            /* 类型总值、 类型列表、 count、 size、 checkEsIndex  */
            double esWebsiteTypeCount = 0.0;
            StatisticEsParam statisticEsParam = getCheckEsIndex();
            List<EsIndexRatio> esIndexRatioList = getEsIndexRatioList(statisticEsParam);

            /* 分类总值、 分类列表 */
            double esWebsiteCategoryCount = 0.0;
            List<EsWebsiteCategoryRatio> esWebsiteCategoryList =  webSiteTypeDao.findWebsiteCategoryAll();

            //计算分类、类型的json
            for (EsIndexRatio esIndexRatio : esIndexRatioList) {
                try {
                    if(esIndexRatio.getEsIndex()!=null){
                        /* 统计类型数量 */
                        int esIndexCount = resultService.getEsCount(0,esIndexRatio.getEsIndex(),null,null);
                        esIndexRatio.setValue( esIndexRatio.getValue() + esIndexCount );
                        esWebsiteTypeCount += esIndexCount;

                        /* 统计分类数量 */
                        for (EsWebsiteCategoryRatio esWebsiteCategoryRatio : esWebsiteCategoryList ) {
                            try {
                                if(esWebsiteCategoryRatio!=null && esWebsiteCategoryRatio.getId()>0){
                                    int wcCount = resultService.getEsCount(esWebsiteCategoryRatio.getId(),esIndexRatio.getEsIndex(),null,null);
                                    esWebsiteCategoryRatio.setValue( esWebsiteCategoryRatio.getValue() + wcCount );
                                    esWebsiteCategoryCount += wcCount;
                                }
                            }catch (Exception e_wc){
                                e_wc.printStackTrace();
                            }
                        }
                    }
                }catch (Exception e_type){
                    e_type.printStackTrace();
                }
            }
            esWebsiteCategoryList = handleEsWebsiteCategoryRatioList(esWebsiteCategoryList,esWebsiteCategoryCount);
            esIndexRatioList = handleEsIndexRatioList(esIndexRatioList,esWebsiteTypeCount);

            /* minio相关值统计 */
//            int  minio_size = minioService.minioFileCount();
            int minio_size = resourceDao.findWebInfoCount(0,-1,0,-1,null);
            minioService.minioFileRecord();
            MinioFileCount minioFileCount = resultDao.findMinioFileCount();
            int  minio_count = minioFileCount.getCount();
            double minio_disk_size = minioFileCount.getSize();

            /* 组装保存参数 */
            StatisticCount result = new StatisticCount();
            result.setMinio_count(minio_count);
            result.setMinio_size(minio_size);
            result.setMinio_disk_size( minio_disk_size );

            result.setEs_count( statisticEsParam.getCount() );
            result.setEs_size( statisticEsParam.getSize() );
            result.setEs_count_today( getEsCount_today(create_time,statisticEsParam.getCheckEsIndex()) );
            result.setEs_count_day( getEsCount_day(create_time,statisticEsParam.getCheckEsIndex()) );
            result.setEs_count_yesterday( getEsCount_yesterday(create_time,statisticEsParam.getCheckEsIndex()) );
            result.setEs_count_week( getEsCount_week(create_time,statisticEsParam.getCheckEsIndex()) );
            result.setGroup_json( JSON.toJSONString(getEsCountGroupByHour(create_time,statisticEsParam.getCheckEsIndex())) );
            result.setCategory_json( JSON.toJSONString(esWebsiteCategoryList) );
            result.setType_json( JSON.toJSONString(esIndexRatioList) );
            result.setCreate_time( create_time );
            result.setCreate_date( create_time );

            int saveType = resultDao.saveStatisticCount(result);
            if(saveType>0){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 近一周的es数据总量
     */
    public int getEsCount_week(Date dateTime,String esIndex){
        try {
            String endTime = DateUtil.getDateTime_day(dateTime,0);
            String beginTime = DateUtil.getDateTime_day(dateTime,-7);
            int count = resultService.getEsCount(0,esIndex,beginTime,endTime);
            return count;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 昨天的es数据总量
     */
    public int getEsCount_yesterday(Date dateTime,String esIndex){
        try {
            String endTime = DateUtil.getDate_day(dateTime,-1)+" 23:59:59";
            String beginTime = DateUtil.getDate_day(dateTime,-1)+" 00:00:00";
            int count = resultService.getEsCount(0,esIndex,beginTime,endTime);
            return count;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 最近24小时的es数据总量
     */
    public int getEsCount_day(Date dateTime,String esIndex){
        try {
            String endTime = DateUtil.getDateTime_hour(dateTime,0)+":00:00";
            String beginTime = DateUtil.getDateTime_hour(dateTime,-24)+":00:01";
            //int count = resultService.getEsCount(0,esIndex,beginTime,endTime);//es查询
            int count = resultDao.findEsRecordCount(beginTime,endTime);
            return count;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 今天的es数据
     */
    public int getEsCount_today(Date dateTime,String esIndex){
        try {
            String endTime = DateUtil.getDate_day(dateTime,0)+"23:59:59";
            String beginTime = DateUtil.getDate_day(dateTime,0)+":00:00";
            int count = resultService.getEsCount(0,esIndex,beginTime,endTime);
            return count;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 最近24小时的es数据，按时间分组数量
     */
    public Map<String,Object> getEsCountGroupByHour(Date dateTime, String esIndex){
        try {
            Map<String,Object> result = new HashMap<>();
            List<String> titleList = new ArrayList<>();
            List<Integer> valueList = new ArrayList<>();
            for (int i = 11; i >= 0; i--) {
                String key = DateUtil.getHour(dateTime,(i*-2))+":00:00";
                String endTime = DateUtil.getDateTime_hour(dateTime,(i*-2))+":00:00";
                String beginTime = DateUtil.getDateTime_hour(dateTime,(i*-2)-2)+":00:01";

                //int count = resultService.getEsCount(0,esIndex,beginTime,endTime);//查询es数据
                int count = resultDao.findEsRecordCount(beginTime,endTime);

                titleList.add(key);
                valueList.add(count);
            }
            if(titleList.size()>0 && titleList.size()==valueList.size()){
                result.put("title", titleList);
                result.put("value", valueList);
                return result;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 索引(类型)json计算
     */
    public List<EsIndexRatio> handleEsIndexRatioList(List<EsIndexRatio> list,double count){
        List<EsIndexRatio> result = new ArrayList<>();
        try {
            if(list!=null){
                for (EsIndexRatio index : list) {
                    if(index!=null && index.getValue()>0){
                        index.setValue(DataUtil.divided(index.getValue(), count) );
                        result.add(index);
                    }
                }
                return result;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * 分类json计算
     */
    public List<EsWebsiteCategoryRatio> handleEsWebsiteCategoryRatioList(List<EsWebsiteCategoryRatio> list,double count){
        List<EsWebsiteCategoryRatio> result = new ArrayList<>();
        try {
            if(list!=null){
                for (EsWebsiteCategoryRatio category : list) {
                    if(category!=null && category.getValue()>0){
                        category.setValue(DataUtil.divided(category.getValue(), count) );
                        result.add(category);
                    }
                }
                return result;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * 索引list组装
     */
    public List<EsIndexRatio> getEsIndexRatioList(StatisticEsParam statisticEsParam){
        try {
            if(statisticEsParam!=null && statisticEsParam.getCheckEsIndex()!=null && statisticEsParam.getEsIndexMap()!=null){
                Map<String,String> typeMap = statisticEsParam.getEsIndexMap();

                List<EsIndexRatio> resultList = new ArrayList<>();
                String[] esIndexArr = statisticEsParam.getCheckEsIndex().split(",");
                for (int i = 0; i < esIndexArr.length; i++) {
                    EsIndexRatio esIndexRatio = new EsIndexRatio();
                    esIndexRatio.setEsIndex(esIndexArr[i]);
                    esIndexRatio.setName( typeMap.get(esIndexArr[i]) );
                    resultList.add(esIndexRatio);
                }
                return resultList;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * es总量、es占用 、索引串组装
     */
    public StatisticEsParam getCheckEsIndex(){
        StatisticEsParam result = new StatisticEsParam();

        int count = 0;
        double size = 0;
        String checkEsIndex = "";
        Map<String,String> esIndexMap = new HashMap<>();
        try {
            String esIndexAll = "";
            List<EsIndexRatio> websiteTypes =  webSiteTypeDao.findWebsiteTypeAll();
            for (EsIndexRatio type : websiteTypes) {
                if(type!=null && type.getEsIndex()!=null && type.getName()!=null ){
                    esIndexAll += ",{"+type.getEsIndex()+"}";
                    esIndexMap.put(type.getEsIndex(),type.getName() );
                }
            }
            if(esIndexAll.indexOf(",")!=-1){
                esIndexAll = esIndexAll.substring(1);
            }

            String esUrl = es_result_url+ "_cat/indices?v";
            String body = HttpUtil.get(esUrl);
            String[] rows  =  body.split("\n");
            if(rows!=null && rows.length>1){
                for (int i = 1; i < rows.length; i++) {
                    String row = rows[i];
                    if(row!=null){
                        row = row.replaceAll("\\s{1,}", " ");
                        String[] points = row.split(" ");
                        if(points!=null && points.length==10){
                            if(esIndexAll.indexOf(points[2])!=-1){
                                checkEsIndex += ","+points[2];
                                count += countConvert(points[6]);
                                size += sizeConvert(points[9]);
                            }
                        }
                    }
                }
            }
            if(checkEsIndex.indexOf(",")!=-1){
                checkEsIndex = checkEsIndex.substring(1);
            }

            result.setCheckEsIndex(checkEsIndex);
            result.setCount(count);
            result.setSize(size);
            result.setEsIndexMap(esIndexMap);
            return result;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * es总量计算
     */
    public int countConvert(String countStr){
        try {
            if(countStr!=null && !countStr.equals("") && DataUtil.isNumeric_int(countStr)){
                return Integer.valueOf(countStr);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * es占用计算
     */
    public double sizeConvert(String sizeStr){
        try {
            if(sizeStr!=null && !sizeStr.equals("")){
                int unit = 1;
                double size = 0.0;

                String number = null;
                if( DataUtil.checkStr("gb",sizeStr) ){
                    unit = 1024 * 1024;
                    number = DataUtil.replace_unit(sizeStr,"gb","");
                }else if( DataUtil.checkStr("mb",sizeStr) ){
                    unit = 1024 ;
                    number = DataUtil.replace_unit(sizeStr,"mb","");
                }else if( DataUtil.checkStr("kb",sizeStr) ){
                    unit = 1 ;
                    number = DataUtil.replace_unit(sizeStr,"kb","");
                }

                if(number!=null && DataUtil.isNumeric(number)){
                    size = Double.valueOf(number);
                    return size*unit;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0.0;
    }

}
