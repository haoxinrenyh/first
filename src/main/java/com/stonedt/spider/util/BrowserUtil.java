package com.stonedt.spider.util;

import com.stonedt.spider.entity.SpiderPlan;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class BrowserUtil {

    public static String doXml(SpiderPlan spiderPlan, String templateXml){
        try {
            String xml = null;

            if( !BrowserUtil.checkData(spiderPlan) ){
                System.out.println(" error: 详情有误");
                return null;
            }

            Map<String,String> infoMap = spiderPlan.getInfoSelect();
            String titleselect = infoMap.get("title");
            String contentselect = infoMap.get("content");
            String dateselect = infoMap.get("date");
            String authorselect = infoMap.get("author");
            if( !DataUtil.CheckString(authorselect)){
                authorselect = "";
            }
            String sourceselect = infoMap.get("source");
            if( !DataUtil.CheckString(sourceselect)){
                sourceselect = "";
            }
            String listselect = spiderPlan.getListSelect();
            String weburl = spiderPlan.getUrl();

            if( DataUtil.CheckString(weburl) && DataUtil.CheckString(listselect) && DataUtil.CheckString(titleselect) && DataUtil.CheckString(contentselect) && DataUtil.CheckString(dateselect)){
                templateXml = templateXml.replace("#{weburl}#", StringEscapeUtils.escapeJava(weburl).replace("&","&amp;") );
                templateXml = templateXml.replace("#{listselect}#", StringEscapeUtils.escapeJava(listselect) );

                templateXml = templateXml.replace("#{titleselect}#", StringEscapeUtils.escapeJava(titleselect) );
                templateXml = templateXml.replace("#{contentselect}#", StringEscapeUtils.escapeJava(contentselect) );
                templateXml = templateXml.replace("#{dateselect}#", StringEscapeUtils.escapeJava(dateselect) );
                templateXml = templateXml.replace("#{authorselect}#", StringEscapeUtils.escapeJava(authorselect) );
                templateXml = templateXml.replace("#{sourceselect}#", StringEscapeUtils.escapeJava(sourceselect) );
                xml = templateXml;
            }

            return xml;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static boolean checkData(SpiderPlan spiderPlan){
        try {
            if(spiderPlan==null || !DataUtil.CheckString(spiderPlan.getListSelect())  ){
                return false;
            }

            if(spiderPlan.getInfoSelect()!=null){
                Map<String,String> infoMap = spiderPlan.getInfoSelect();
                if( infoMap.get("title")==null || !DataUtil.CheckString( infoMap.get("title")) ){
                    return false;
                }
                if( infoMap.get("content")==null || !DataUtil.CheckString( infoMap.get("content")) ){
                    return false;
                }
                if( infoMap.get("date")==null || !DataUtil.CheckString( infoMap.get("date")) ){
                    return false;
                }
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  false;
    }

}
