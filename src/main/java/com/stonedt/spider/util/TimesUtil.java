package com.stonedt.spider.util;



import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.stonedt.spider.entity.MessageEntity;

/**
 * @date 2019年9月11日 下午3:13:10
 */
public class TimesUtil {
	
	public static void main(String[] args) {
		List<Map<String,String>> historicalTrend = historicalTrend("2019-12-12 00:00:00","2019-12-19 23:59:59");
		 Collections.reverse(historicalTrend);  
		System.err.println(historicalTrend);
//		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" ); 
//		Date date1 = null;
//		Date date2 = null;
//		try {
//			date1 = sdf.parse("2019-12-12 00:00:00");
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		try {
//			date2 = sdf.parse("2019-12-19 00:00:00");
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		int differentDays = differentDays(date1, date2);
//		System.out.println(differentDays);
	}
	
	public static JSONObject recruitmentMonitorStr(String timeStr) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		String timee = now.format(dateTimeFormatter);
		int days = 1;
		if (StringUtils.isBlank(timeStr)) {
			days = 1;
		}else {
			timeStr = timeStr.trim();
			if ("24小时".equals(timeStr)) { // 24小时
				days = 1;
			}
			if ("3天".equals(timeStr)) { // 3天
				days = 3;
			}
			if ("7天".equals(timeStr)) { // 7天
				days = 7;
			}
		}
		String times = now.minusDays(days).format(dateTimeFormatter);
		JSONObject result = new JSONObject();
		result.put("times", times);
		result.put("timee", timee);
		return result;
	}
	
	public static JSONObject recruitmentMonitor(Integer timeIndex) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		String timee = now.format(dateTimeFormatter);
		int days = 1;
		if (timeIndex == null) {
			days = 1;
		}else {
			if (timeIndex == 0) { // 24小时
				days = 1;
			}
			if (timeIndex == 1) { // 3天
				days = 3;
			}
			if (timeIndex == 2) { // 7天
				days = 7;
			}
		}
		String times = now.minusDays(days).format(dateTimeFormatter);
		JSONObject result = new JSONObject();
		result.put("times", times);
		result.put("timee", timee);
		return result;
	}

	public static Map<String, String> twentyFourHours() {
		DateTimeFormatter ofPattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime localDateTime = LocalDateTime.now();
		String endTime = ofPattern.format(localDateTime);
		String startTime = ofPattern.format(localDateTime.minusHours(24));
		Map<String, String> time = new HashMap<String, String>();
		time.put("startTime", startTime);
		time.put("endTime", endTime);
		return time;
	}

	//历史走势时间处理
	public static List<Map<String, String>> historicalTrend(String startTime, String endTime) {
		List<Map<String, String>> result = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar startCalendar = Calendar.getInstance();
		Calendar endCalendar = Calendar.getInstance();
		Calendar yearCalendar = Calendar.getInstance();
		int days = 0;
		try {
			startCalendar.setTime(sdf.parse(startTime));
			endCalendar.setTime(sdf.parse(endTime));
			int day1 = startCalendar.get(Calendar.DAY_OF_YEAR);
			int day2 = endCalendar.get(Calendar.DAY_OF_YEAR);
			int year1 = startCalendar.get(Calendar.YEAR);
			int year2 = endCalendar.get(Calendar.YEAR);
			if (year1 != year2) {  //不同年  
				int timeDistance = 0;
				for (int i = year1; i < year2; i++) {
					if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) {  //闰年
						timeDistance += 366;
					} else {  //平年
						timeDistance += 365;
					}
				}
				days = timeDistance + (day2 - day1);
			} else { //同一年
				days = day2 - day1;
			}
			//按天算
			for (int i = 0; i <= days; i++) {
				//System.err.println(sdf.format(endCalendar.getTime()));
				String nowDate = sdf.format(endCalendar.getTime());
				yearCalendar.setTime(endCalendar.getTime());
				yearCalendar.add(Calendar.YEAR, -1);
				//System.out.println(sdf.format(yearCalendar.getTime()));
				String yearDate = sdf.format(yearCalendar.getTime());
				endCalendar.add(Calendar.DATE, -1);
				Map<String, String> map = new HashMap<String, String>();
				map.put("nowDate", nowDate);
				map.put("yearDate", yearDate);
				result.add(map);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}

	// 舆情监测
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
			calendar.add(Calendar.DATE, -1);
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
		}
		return times + "&" + timee;
	}

	// 相似文章列表使用
	public static String similaritySimpleDateFormat(Integer timeType) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年MM月dd日");
		Calendar calendar = Calendar.getInstance();
		String times = "";
		String timee = "";
		if (timeType == 0) {
			timee = sdf.format(calendar.getTime());
			times = timee.substring(0, 11) + " 00:00:00";
		} else if (timeType == 1) {
			calendar.add(Calendar.DATE, -1);
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
		}
		return times.substring(0, 11) + "&" + timee.substring(0, 11);
	}


	// 全文搜索
	public static String fullTextYYYYMMDDHHMMSS(Integer timeType) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		String times = "";
		String timee = "";
		if (timeType == 0) {
			timee = sdf.format(calendar.getTime());
			times = timee.substring(0, 10) + " 00:00:00";
		} else if (timeType == 1) {
			timee = sdf.format(calendar.getTime());
			calendar.add(Calendar.DATE, -1);
			times = sdf.format(calendar.getTime());
		} else if (timeType == 2) {
			timee = sdf.format(calendar.getTime());
			calendar.add(Calendar.DATE, -2);
			times = sdf.format(calendar.getTime());
		} else if (timeType == 3) {
			timee = sdf.format(calendar.getTime());
			calendar.add(Calendar.DATE, -3);
			times = sdf.format(calendar.getTime());
		} else if (timeType == 4) {
			timee = sdf.format(calendar.getTime());
			calendar.add(Calendar.DATE, -7);
			times = sdf.format(calendar.getTime());
		}
		return times + "&" + timee;
	}

	//截止时间往前推两分钟
	public static String PushForwardTwoMinute(String timee) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date parse = sdf.parse(timee);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(parse);
			calendar.add(Calendar.MINUTE, -2);
			timee = sdf.format(calendar.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return timee;
	}


	/**
	 * 华建成
	 * 2019/09/17
	 * 计算时间差
	 */

	public static int calculateTimeDiff(String startTime, String endTime) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startTimeDate = null;
		Date endTimeDate = null;
		int days = 0;
		try {
			startTimeDate = format.parse(startTime);
			endTimeDate = format.parse(endTime);
			days = (int) ((endTimeDate.getTime() - startTimeDate.getTime()) / (1000 * 3600 * 24));
			System.out.println(days);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return days;
	}

	/**
	 * 开始时间每次增加两个小时
	 * 华建成
	 * 2019/09/17
	 *
	 * @param startTime
	 * @param endTime
	 */
	public static List<Date> addTime(String startTime, String endTime) {
		int days = calculateTimeDiff(startTime, endTime);
		int count = days * 12;
		List<Date> timeList = new ArrayList<>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startTimeDate = null;
		//Date endTimeDate = null;
		String startTimeStr = "";
		try {
			startTimeDate = format.parse(startTime);
			Calendar c = Calendar.getInstance();
			c.setTime(startTimeDate);
			for (int i = 0; i < count; i++) {
				c.add(Calendar.HOUR_OF_DAY, 2);
				Date newDate = c.getTime();
				startTimeStr = format.format(newDate);
//                timeList.add(startTimeStr);
				timeList.add(newDate);
				if (startTimeStr.equals(endTime)) {
					System.out.println("时间增加结束");
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return timeList;
	}


	public static String addEndTime(String startTime, Integer reportNav) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startTimeDate = null;
		//Date endTimeDate = null;
		String endTimeStr = "";
		Random random = new Random();


		int step = 0;
		if (reportNav == 1) {
			step = 1 * 24;
		} else if (reportNav == 2) {
			step = 7 * 24;
		} else if (reportNav == 3) {
			step = 30 * 24;
		} else if (reportNav == 4) {
			step = random.nextInt(10) * 24;
		}

		try {
			startTimeDate = format.parse(startTime);
			Calendar c = Calendar.getInstance();
			c.setTime(startTimeDate);
			c.add(Calendar.HOUR_OF_DAY, step);
			Date newDate = c.getTime();
			endTimeStr = format.format(newDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return endTimeStr;
	}

	//获取当前时间
	public static String nowYYYYMMDDHHMMSS() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		return sdf.format(calendar.getTime());
	}

	//同比周期
	public static Map<String, String> YearOnYearCycle(String startTime, String endTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(sdf.parse(startTime));
			calendar.add(Calendar.YEAR, -1);
			startTime = sdf.format(calendar.getTime());
			calendar.setTime(sdf.parse(endTime));
			calendar.add(Calendar.YEAR, -1);
			endTime = sdf.format(calendar.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		return map;
	}

	//环比周期
	public static Map<String, String> RingRatioCycle(String startTime, String endTime) {
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar startCalendar = Calendar.getInstance();
		Calendar endCalendar = Calendar.getInstance();
		int days = 0;
		try {
			startCalendar.setTime(sdf1.parse(startTime));
			endCalendar.setTime(sdf1.parse(endTime));
			int day1 = startCalendar.get(Calendar.DAY_OF_YEAR);
			int day2 = endCalendar.get(Calendar.DAY_OF_YEAR);
			int year1 = startCalendar.get(Calendar.YEAR);
			int year2 = endCalendar.get(Calendar.YEAR);
			if (year1 != year2) {  //不同年  
				int timeDistance = 0;
				for (int i = year1; i < year2; i++) {
					if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) {  //闰年
						timeDistance += 366;
					} else {  //平年
						timeDistance += 365;
					}
				}
				days = timeDistance + (day2 - day1);
			} else { //同一年
				days = day2 - day1;
			}
			startCalendar.add(Calendar.DATE, -days);
			endCalendar.add(Calendar.DATE, -days);
			startTime = sdf1.format(startCalendar.getTime());
			endTime = sdf1.format(endCalendar.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		return map;
	}


	/**
	 * 根据天数推算日期
	 * 华建成
	 * 2019/10/31
	 *
	 * @param args
	 */
	public static String getDateTime(int days) {
		SimpleDateFormat format1 = new SimpleDateFormat("HH:mm:ss");
		String timeHour = format1.format(Calendar.getInstance().getTime());
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		// 考虑2月份
		if (month==2){
//			if ()
		}
		return "";
	}
	
//	// 請求es
//	public static JSONObject getCode(String name,Integer pageNo,String tmiee,String times) {
//	 
//		JSONObject returnObj = new JSONObject();
//		List<MessageEntity> webnamelist=new ArrayList<MessageEntity>();
//		JSONObject conjson=new JSONObject();
//		String url =  "/media/qbsearchcontent";
//		
//		String content;
//		try {
//			content = "?times="+tmiee+"&tmiee="+times+"&page="+pageNo+"&searchType=1&author&source_name="+URLEncoder.encode(name, "utf-8");
//			String post;
//			try {
//				post = ElasticSearchUtil.sendPost(url,content);
//				
//				conjson=JSONObject.parseObject(post);
//				JSONArray dataarray=conjson.getJSONArray("data");
//				String page_count=conjson.getString("page_count");
//				String page=conjson.getString("page");
//				String count=conjson.getString("count");
//				returnObj.put("pageCount", page_count);
//				returnObj.put("page", page);
//				returnObj.put("totalCount", count);
//				for(int n=0;n<dataarray.size();n++) {
//					String title=dataarray.getJSONObject(n).getJSONObject("_source").get("title").toString();
//					String source_url=dataarray.getJSONObject(n).getJSONObject("_source").get("source_url").toString();
//					String publish_time=dataarray.getJSONObject(n).getJSONObject("_source").get("publish_time").toString();
//					String sourcewebsitename=dataarray.getJSONObject(n).getJSONObject("_source").get("sourcewebsitename").toString();
//					String article_public_id=dataarray.getJSONObject(n).getJSONObject("_source").get("article_public_id").toString();
//					String author=dataarray.getJSONObject(n).getJSONObject("_source").get("author").toString();
//					if(author.isEmpty()) {
//						author="空";
//					}
//					
//					MessageEntity me=new MessageEntity();
//					me.setArticle_public_id(article_public_id);
//					me.setPublish_time(publish_time);
//					me.setSource_url(source_url);
//					me.setSourcewebsitename(sourcewebsitename);
//					me.setTitle(title);
//					me.setAuthor(author);
//					webnamelist.add(me);
//					returnObj.put("webnamelist", webnamelist);
//				}
//				
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			
//		} catch (UnsupportedEncodingException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//
//		return returnObj;
//		
//	}
	
	//两个时间段的天数
	 public static int differentDays(Date date1,Date date2)
	    {
	        Calendar cal1 = Calendar.getInstance();
	        cal1.setTime(date1);
	        
	        Calendar cal2 = Calendar.getInstance();
	        cal2.setTime(date2);
	        int day1= cal1.get(Calendar.DAY_OF_YEAR);
	        int day2 = cal2.get(Calendar.DAY_OF_YEAR);
	        
	        int year1 = cal1.get(Calendar.YEAR);
	        int year2 = cal2.get(Calendar.YEAR);
	        if(year1 != year2)   //不同一年
	        {
	            int timeDistance = 0 ;
	            for(int i = year1 ; i < year2 ; i ++)
	            {
	                if(i%4==0 && i%100!=0 || i%400==0)    //闰年            
	                {
	                    timeDistance += 366;
	                }
	                else    //不是闰年
	                {
	                    timeDistance += 365;
	                }
	            }
	            
	            return timeDistance + (day2-day1) ;
	        }
	        else    //同一年
	        {
	            System.out.println("判断day2 - day1 : " + (day2-day1));
	            return day2-day1;
	        }
	    }


	/**
	 * 取出相隔三天的日期日期
	 * hjc
	 *
	 * @param
	 * @return
	 */
	public static JSONObject getDifferDayTimes() {
		JSONObject returnTimes = new JSONObject();
		SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
		Date endDate = new Date();
		String endTime = simple.format(endDate); // 结束时间
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -3);
		Date date = cal.getTime();
		String startTime = simple.format(date);//开始时间
        returnTimes.put("TIMEBEGIN", startTime);
        returnTimes.put("TIMEEND", endTime);
//		returnTimes.put("TIMEBEGIN", "2019-12-23");
//		returnTimes.put("TIMEEND", "2019-12-23");
		return returnTimes;
	}
}
