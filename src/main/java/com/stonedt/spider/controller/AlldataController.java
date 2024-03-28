package com.stonedt.spider.controller;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.stonedt.spider.util.EsUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;

/**
 * @author 作者 Lanry
 * @version 创建时间：2020年1月13日 下午1:38:06
 * @explain alldata
 */

@Controller
@RequestMapping("/main")
public class AlldataController {
	// 服务器列表

	@RequestMapping(value = "/toalldata", produces = "text/plain;charset=utf-8")
	public@ResponseBody String toserverlist() throws IOException {
		// http://192.168.71.63:6302/yqt/sourcewebsitesearch?times=2019-12-17
		// 21:05:56&timee=2019-12-18 21:05:56&classify=7
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		String endtime = format.format(new Date());
		String startime = endtime.substring(0, 10) + " 00:00:00";
		//全部
		Map<String, String> mapall = new HashMap<String, String>();
		mapall.put("times", "");
		mapall.put("timee", "");
		mapall.put("classify", "");
		JSONObject dataoverviewall = EsUtils.dataoverview(mapall);
		JSONObject jsonObjectall = dataoverviewall.getJSONObject("hits");
		Integer dataoverview = jsonObjectall.getInteger("total");
		//今天
		Map<String, String> maptoday = new HashMap<String, String>();
		maptoday.put("times", startime);
		maptoday.put("timee", endtime);
		maptoday.put("classify", "");
		JSONObject dataoverviewday = EsUtils.dataoverview(maptoday);
		JSONObject jsonObjectday = dataoverviewday.getJSONObject("hits");
		Integer dataoverviewtoday = jsonObjectday.getInteger("total");
		//一周

		String weektime = dataMonitorYYYYMMDDHHMMSS(2);
		String starweek = weektime.substring(0, 19);
		String endweek = weektime.substring(20);
		Map<String, String> mapweek = new HashMap<String, String>();
		mapweek.put("times", starweek);
		mapweek.put("timee", endweek);
		mapweek.put("classify", "");
		JSONObject dataoverviewweek = EsUtils.dataoverview(mapweek);
		JSONObject jsonObjectweek = dataoverviewweek.getJSONObject("hits");
		Integer dataoverviewtoweek = jsonObjectweek.getInteger("total");

// 一个月

		String monthtime = dataMonitorYYYYMMDDHHMMSS(3);
		String starmonth = monthtime.substring(0, 19);
		String endmonth = monthtime.substring(20);
		Map<String, String> mapmonth = new HashMap<String, String>();
		mapmonth.put("times", starmonth);
		mapmonth.put("timee", endmonth);
		mapmonth.put("classify", "");
		JSONObject dataoverviewmonth = EsUtils.dataoverview(mapmonth);
		JSONObject jsonObjectmonth = dataoverviewmonth.getJSONObject("hits");
		Integer dataoverviewtomonth = jsonObjectmonth.getInteger("total");


//  搜索引擎抓取总量
		Map<String, String> ssmapall = new HashMap<String, String>();
		ssmapall.put("times", "");
		ssmapall.put("timee", "");
		ssmapall.put("classify", "5");
		JSONObject ssdtaoverviewall = EsUtils.dataoverview(ssmapall);
		JSONObject jsonObjectssall = ssdtaoverviewall.getJSONObject("hits");
		Integer ssdtaoverviewtoall = jsonObjectssall.getInteger("total");

//  搜素引擎今日抓取
		String sstime = dataMonitorYYYYMMDDHHMMSS(1);
		String ssstime = sstime.substring(0, 19);
		String ssendtime = sstime.substring(20);
		Map<String, String> ssmapday = new HashMap<String, String>();
		ssmapday.put("times", ssstime);
		ssmapday.put("timee", ssendtime);
		ssmapday.put("classify", "5");
		JSONObject ssdtaoverviewday = EsUtils.dataoverview(ssmapday);
		JSONObject jsonObjectssday = ssdtaoverviewday.getJSONObject("hits");
		Integer ssdtaoverviewtoday = jsonObjectssday.getInteger("total");

//  搜索引擎一周抓取
		String ssweek = dataMonitorYYYYMMDDHHMMSS(2);
		String sssweek = ssweek.substring(0, 19);
		String ssendweek = ssweek.substring(20);
		Map<String, String> ssmapweek = new HashMap<String, String>();
		ssmapweek.put("times", sssweek);
		ssmapweek.put("timee", ssendweek);
		ssmapweek.put("classify", "5");
		JSONObject ssdtaoverviewweek = EsUtils.dataoverview(ssmapweek);
		JSONObject jsonObjectssweek = ssdtaoverviewweek.getJSONObject("hits");
		Integer ssdtaoverviewtoweek = jsonObjectssweek.getInteger("total");

//  搜索引擎一个月抓取
		String ssmonth = dataMonitorYYYYMMDDHHMMSS(3);
		String sssmonth = ssmonth.substring(0, 19);
		String ssendmonth = ssmonth.substring(20);
		Map<String, String> ssmapmonth = new HashMap<String, String>();
		ssmapmonth.put("times", sssmonth);
		ssmapmonth.put("timee", ssendmonth);
		ssmapmonth.put("classify", "5");
		JSONObject ssdtaoverviewmonth = EsUtils.dataoverview(ssmapmonth);
		JSONObject jsonObjectssmonth = ssdtaoverviewmonth.getJSONObject("hits");
		Integer ssdtaoverviewtomonth = jsonObjectssmonth.getInteger("total");

		
		// 客户端抓取总量
		Map<String, String> khdmapall = new HashMap<String, String>();
		khdmapall.put("times", "");
		khdmapall.put("timee", "");
		khdmapall.put("classify", "7");
		JSONObject khddtaoverviewall = EsUtils.dataoverview(khdmapall);
		JSONObject jsonObjectkhdall = khddtaoverviewall.getJSONObject("hits");
		Integer  khddtaoverviewtoall = jsonObjectkhdall.getInteger("total");

		//  客户端今日抓取
		Map<String, String> khdmapday = new HashMap<String, String>();
		khdmapday.put("times", ssstime);
		khdmapday.put("timee", ssendtime);
		khdmapday.put("classify", "7");
		JSONObject khddtaoverviewday = EsUtils.dataoverview(khdmapday);
		JSONObject jsonObjectkhdday = khddtaoverviewday.getJSONObject("hits");
		Integer khddtaoverviewtoday = jsonObjectkhdday.getInteger("total");

		//  客户端一周抓取
		Map<String, String> khdmapweek = new HashMap<String, String>();
		khdmapweek.put("times", sssweek);
		khdmapweek.put("timee", ssendweek);
		khdmapweek.put("classify", "7");
		JSONObject khddtaoverviewweek = EsUtils.dataoverview(khdmapweek);
		JSONObject khdjsonObjectweek = khddtaoverviewweek.getJSONObject("hits");
		Integer khddtaovervietowweek = khdjsonObjectweek.getInteger("total");

		//  客户端一个月抓取
		Map<String, String> khdmapmonth = new HashMap<String, String>();
		khdmapmonth.put("times", sssmonth);
		khdmapmonth.put("timee", ssendmonth);
		khdmapmonth.put("classify", "7");
		JSONObject khdaoverviewmonth = EsUtils.dataoverview(khdmapmonth);
		JSONObject khdjsonObjectssmonth = khdaoverviewmonth.getJSONObject("hits");
		Integer khddtaoverviewtomonth = khdjsonObjectssmonth.getInteger("total");

		/**
		 * 招聘 dataOverviewInvite
		 */
		
		// 招聘抓取总量
		Map<String, String> zpmapall = new HashMap<String, String>();
		zpmapall.put("times", "");
		zpmapall.put("timee", "");
		JSONObject zpdtaoverviewall = EsUtils.dataOverviewInvite(zpmapall);
		Integer  zpDtaoverviewtoall = zpdtaoverviewall.getInteger("count");

		//  招聘今日抓取
		Map<String, String> zpMapday = new HashMap<String, String>();
		zpMapday.put("times", ssstime);
		zpMapday.put("timee", ssendtime);
		JSONObject zpDtaoverviewday = EsUtils.dataOverviewInvite(zpMapday);
		Integer zpDtaoverviewtoday = zpDtaoverviewday.getInteger("count");
		//  招聘一周抓取
		Map<String, String> zpMapWeek = new HashMap<String, String>();
		zpMapWeek.put("times", sssweek);
		zpMapWeek.put("timee", ssendweek);
		JSONObject zpDtaoverviewweek = EsUtils.dataOverviewInvite(zpMapWeek);
		Integer zpDtaovervietowweek = zpDtaoverviewweek.getInteger("count");
		//  招聘一个月抓取
		Map<String, String> zpMapMonth = new HashMap<String, String>();
		zpMapMonth.put("times", sssmonth);
		zpMapMonth.put("timee", ssendmonth);
		JSONObject zpDaoverviewmonth = EsUtils.dataOverviewInvite(zpMapMonth);
		Integer zpDtaoverviewtomonth = zpDaoverviewmonth.getInteger("count");
		
		
		JSONArray resArray = new JSONArray();
		JSONObject res = new JSONObject();
		res.put("alldata", dataoverview);
		res.put("today", dataoverviewtoday);
		res.put("week", dataoverviewtoweek);
		res.put("month", dataoverviewtomonth);
		res.put("name", "全部抓取总量");
		
		JSONObject res2 = new JSONObject();
		res2.put("alldata", ssdtaoverviewtoall);
		res2.put("today", ssdtaoverviewtoday);
		res2.put("week", ssdtaoverviewtoweek);
		res2.put("month", ssdtaoverviewtomonth);
		res2.put("name", "搜索引擎抓取总量");
		
		JSONObject res3 = new JSONObject();
		res3.put("alldata", khddtaoverviewtoall);
		res3.put("today", khddtaoverviewtoday);
		res3.put("week", khddtaovervietowweek);
		res3.put("month", khddtaoverviewtomonth);
		res3.put("name", "客户端抓取总量");
		
		JSONObject res4 = new JSONObject();
		res4.put("alldata", zpDtaoverviewtoall);
		res4.put("today", zpDtaoverviewtoday);
		res4.put("week", zpDtaovervietowweek);
		res4.put("month", zpDtaoverviewtomonth);
		res4.put("name", "招聘抓取总量");
		
		resArray.add(res);
		resArray.add(res2);
		resArray.add(res3);
		resArray.add(res4);
		
		JSONObject rest = new JSONObject();
		rest.put("data", resArray);
		System.out.println(JSON.toJSONString(rest));
		return JSON.toJSONString(rest);

	}

	
	
	
	// 取时间区段
	public static String dataMonitorYYYYMMDDHHMMSS(Integer timeType) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		String times = "";
		String timee = "";
		if (timeType == 0) {
			timee = sdf.format(calendar.getTime());
			times = timee.substring(0, 10) + " 00:00:00";
		} else if (timeType == 1) {
			calendar.add(Calendar.DATE,0);
			timee = sdf2.format(calendar.getTime()) + " 23:59:59";
			times = sdf2.format(calendar.getTime()) + " 00:00:00";
		} else if (timeType == 2) {
			timee = sdf.format(calendar.getTime());
			calendar.add(Calendar.DATE, -7);
			times = sdf.format(calendar.getTime());
		} else if (timeType == 3) {
			timee = sdf.format(calendar.getTime());
			calendar.add(Calendar.DATE, -30);
			times = sdf.format(calendar.getTime());
		} else if (timeType == 5) {
			timee = sdf.format(calendar.getTime());
			calendar.add(Calendar.DATE, -1);
			times = sdf.format(calendar.getTime());
		}
		return times + "&" + timee;
	}
}
