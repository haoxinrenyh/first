package com.stonedt.spider.controller.sendEmail;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.stonedt.spider.controller.overseas.OverseasInfo;
import com.stonedt.spider.util.ParamUtil;

@Component
public class sendEmail {

	@Scheduled(cron = "0 0 18 * * ?")
	public void start() {
		JSONObject resObj=getData();
		if(!resObj.getString("count").equals("0")) {
			String emailContent=contentHtml(resObj);
			try {
				SendMailFox.Send("jianlong.wang@dmrjkj.cn", new SimpleDateFormat("yyyy-MM-dd").format(new Date())+" 招标投标(残疾,辅具)详细数据", emailContent);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public JSONObject getData() {
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("timefield", "publish_time");
		paramMap.put("index", "stonedt_biao");
		paramMap.put("page", "1");
		paramMap.put("size", "10");
		paramMap.put("type", "infor");
		
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timee=sdf.format(date);
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, -1);
		String times=sdf.format(calendar.getTime());
		
		paramMap.put("times", times);
		paramMap.put("timee", timee);
		List<ParamUtil> listParams=new ArrayList<ParamUtil>();
		listParams.add(new ParamUtil("content_text", "残疾"));
		listParams.add(new ParamUtil("content_text", "辅具"));
		listParams.add(new ParamUtil("title", "残疾"));
		listParams.add(new ParamUtil("title", "辅具"));
		paramMap.put("or", listParams);
		
		String api="http://dx2.stonedt.com:7120/commonsearch/superdatalist";
		String response=OverseasInfo.sendPostRaw(new JSONObject(paramMap).toJSONString(), api, "utf-8");
		
		return JSON.parseObject(response);
	}
	
	public String contentHtml(JSONObject resObj) {
		JSONArray resArr=JSON.parseArray(resObj.getString("data"));
		String trList="";
		for (Object o : resArr) {
			JSONObject obj1=JSON.parseObject(o.toString());
			String _source=obj1.getString("_source");
			JSONObject _sourceObj=JSON.parseObject(_source);
			trList+="<tr>\r\n" + 
					"    <td>"+_sourceObj.getString("title")+"</td>\r\n" + 
					"    <td>"+_sourceObj.getString("province")+"</td>\r\n" + 
					"    <td>"+_sourceObj.getString("publish_time").substring(0, 10)+"</td>\r\n" + 
					"    <td><a href=\""+_sourceObj.getString("originalurl")+"\" target=\"_blank\">"+_sourceObj.getString("datasource")+"</a></td>\r\n" + 
					"</tr>";
		}
		String html="<!DOCTYPE html>\r\n" + 
				"<html lang=\"en\">\r\n" + 
				"<head>\r\n" + 
				"  <meta charset=\"UTF-8\">\r\n" + 
				"  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n" + 
				"  <title>每日推送</title>\r\n" + 
				"</head>\r\n" + 
				"<body>\r\n" + 
				"  <h2 style=\"text-align: center;margin: 2em 2em;\">"+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+" 招标投标(残疾,辅具)详细数据</h2>\r\n" + 
				"  <table style=\"text-align: center;border-color: rgb(222,226,230);\" border=\"1\" cellspacing=\"0\" cellpadding=\"10\">\r\n" + 
				"    <thead>\r\n" + 
				"      <tr>\r\n" + 
				"        <th style=\"width: 40%;\">标题</th>\r\n" + 
				"        <th style=\"width: 20%;\">省份</th>\r\n" + 
				"        <th style=\"width: 20%;\">发布日期</th>\r\n" + 
				"        <th style=\"width: 20%;\">数据来源</th>\r\n" + 
				"      </tr>\r\n" + 
				"    </thead>\r\n" + 
				"    <tbody>\r\n" + 
				      	trList+
				"    </tbody>\r\n" + 
				"  </table>\r\n" + 
				"</body>\r\n" + 
				"</html>";
		return html;
	}
	
}
