package com.stonedt.spider.util;


import com.alibaba.fastjson.JSONArray;
import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 时间的帮助类
 * @author 10743
 *
 */
public class DateUtil {

	
	
	public static JSONArray getNowDate() {
		//当前时间戳
        long nowTime=new Date().getTime();
        //每天毫秒数
        Integer msec=86400000;
        //14天毫秒数
        Integer long_time=1209600000;
        SimpleDateFormat sim=new SimpleDateFormat("MM-dd");
        JSONArray end_array=new JSONArray();
        //先减去14天
    	long time=nowTime-long_time;
    	System.out.println(time);
        for(int i=0;i<14;i++) {
        	String end="";
        	//拿十四天前的日期每次加一天
        	if (i==0) {
        		end=sim.format(time);
			}else {
				time=time+msec;
	        	end=sim.format(time);
			}
        	//先随机生成一个数字
        	double random=new Random().nextDouble()*30;
        	DecimalFormat df = new DecimalFormat( "0" );
            String str=df.format(random);
        	JSONArray allTime=new JSONArray();
        	allTime.add(end);
        	allTime.add(str);
        	end_array.add(allTime);
        }
        return end_array;
	}
	/**
	 * 获取当前时间前一小时的时间
	 * @return (String)年月日时分秒
	 */
	public static List<String> beforeOneHourToNowDate() {
		Calendar calendar = Calendar.getInstance();
		List<String> listTime = new ArrayList<>();
		/* HOUR_OF_DAY 指示一天中的小时 */
		calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 1);
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:05:00");
		
		listTime.add(df.format(calendar.getTime()));
		listTime.add(df.format(new Date()));
//		System.out.println("一个小时前的时间：" + df.format(calendar.getTime()));

//		System.out.println("当前的时间：" + df.format(new Date()));
		return listTime;
}
	public static List<String> beforeFiveHourToNowDate() {
		Calendar calendar = Calendar.getInstance();
		List<String> listTime = new ArrayList<>();
		/* HOUR_OF_DAY 指示一天中的小时 */
		calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 72);
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:05:00");
		
		listTime.add(df.format(calendar.getTime()));
		listTime.add(df.format(new Date()));
//		System.out.println("一个小时前的时间：" + df.format(calendar.getTime()));

//		System.out.println("当前的时间：" + df.format(new Date()));
		return listTime;
}
	
	/**
	 * 获取当前年月日时分秒
	 * @return (String)年月日时分秒
	 */
	public static String getDate()
	{
		//获取当前时间
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        
        //当前时间starttimeDate
        calendar.setTime(new Date());
        Date starttimeDate = calendar.getTime();
        
		return format.format(starttimeDate);
	}
	
	/**
	 * 获取当前年月日
	 * @return (String)年月日
	 */
	public static String getDateday()
	{
		//获取当前时间
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        
        //当前时间starttimeDate
        calendar.setTime(new Date());
        Date starttimeDate = calendar.getTime();
        
		return format.format(starttimeDate);
	}
	
	/**
	 * 获取当前年
	 * @return (String)年
	 */
	public static String getDateyear()
	{
		//获取当前时间
		SimpleDateFormat format = new SimpleDateFormat("yyyy");
        Calendar calendar = Calendar.getInstance();
        
        //当前时间starttimeDate
        calendar.setTime(new Date());
        Date starttimeDate = calendar.getTime();
        
		return format.format(starttimeDate);
	}
	
	/**
	 * 根据传入的参数，推算当前时间-参数分钟后的时间
	 * @param num
	 * @return (String)年月日时分秒
	 */
	public static String minusMinute(int num)
	{
		//获取当前时间
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        //当前时间starttimeDate
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE,-num);
        Date starttimeDate = calendar.getTime();
        
		return format.format(starttimeDate);
	}
	
	/**
	 * 根据传入的参数，推算当前时间-参数小时后的时间
	 * @param num
	 * @return (String)年月日时分秒
	 */
	public static String minusHour(int num)
	{
		//获取当前时间
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        //当前时间starttimeDate
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR,-num);
        Date starttimeDate = calendar.getTime();
        
		return format.format(starttimeDate);
	}

	/**
	 * 根据传入的参数，推算当前时间
	 * @param num  小时
	 */
	public static String getDateTime_hour(Date dateTime,int num) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateTime);//赋值
		calendar.add(Calendar.HOUR,num);//计算
		Date resultDateTime = calendar.getTime();//结果
		return format.format(resultDateTime);
	}

	/**
	 * 根据传入的参数，推算当前时间
	 * @param num  小时
	 */
	public static String getHour(Date dateTime,int num) {
		SimpleDateFormat format = new SimpleDateFormat("HH");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateTime);//赋值
		calendar.add(Calendar.HOUR,num);//计算
		Date resultDateTime = calendar.getTime();//结果
		return format.format(resultDateTime);
	}

	
	/**
	 * 根据传入的参数，推算当前时间-参数天数后的时间
	 * @param num
	 * @return (String)年月日时分秒
	 */
	public static String minusDay(int num)
	{
		//获取当前时间
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        //当前时间starttimeDate
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE,-num);
        Date starttimeDate = calendar.getTime();
        
		return format.format(starttimeDate);
	}

	/**
	 * 根据传入的参数，推算当前时间
	 * @param num 天
	 */
	public static String getDateTime_day(Date dateTime,int num) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateTime);
		calendar.add(Calendar.DATE,num);
		Date result = calendar.getTime();
		return format.format(result);
	}

	/**
	 * 根据传入的参数，推算当前时间
	 * @param num 天
	 */
	public static String getDate_day(Date dateTime,int num) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateTime);
		calendar.add(Calendar.DATE,num);
		Date result = calendar.getTime();
		return format.format(result);
	}


	/**
	 * 获取上周未的时间
	 */
	public static String getWeekLastDay(Date dateTime,int num){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateTime);
		calendar.set(Calendar.DAY_OF_WEEK,1);
		calendar.add(Calendar.WEEK_OF_YEAR,num);
		Date result = calendar.getTime();
		return format.format(result);
	}
	
	/**
	 * 获取数字
	 * @param str
	 * @return (String)字符串中的数字
	 */
	public static String getNumber(String str)
	{
		String regEx="[^0-9]"; 
		Pattern pattern = Pattern.compile(regEx);
		return pattern.matcher(str).replaceAll("").trim();
	}
	
	/**
	 * 判断字符串是否包含中文
	 * @param str
	 * @return boolean
	 */
	public static boolean isContainChinese(String str)
	{
	    Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
	    Matcher m = p.matcher(str);
	    if (m.find()) {
	      return true;
	    }
	    return false;
	}
	
	
	/**
	 * 判断输入时间是否超过当前时间，如果超过返回当前时间
	 * @param strSuccess
	 * @return
	 */
	public static String exceedToDay(String strSuccess)
	{
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			Date nowDate = df.parse(getDate());
			Date inputDate = df.parse(strSuccess);
			
			if(inputDate.getTime()>nowDate.getTime())//比较时间大小,如果输入时间大于当前时间
			{
				return getDate();
			}
			return strSuccess;
		} catch (Exception e) {
			return getDate();
		}
	}
	
	/**
	 * JSON格式时间戳日期转换
	 * @return
	 */
	public static String getJsonTime(String times){
		long seconds = Long.parseLong(times);
		Date time = new Date(seconds);
		DateFormat dateFormat = DateFormat.getDateInstance();
		//自己指定格式
	    dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(time);
	}


	//按照格式将date转换成字符串
	public static String format(Date date,String fo){
		if(fo == null || StringUtils.isBlank(fo)){
			fo = "yyyy-MM-dd HH:mm:ss";
		}
		DateFormat df = new SimpleDateFormat(fo);
		return df.format(date);
	}


	public static Date parse(String date,String fo){
		if(fo == null || StringUtils.isBlank(fo)){
			fo = "yyyy-MM-dd HH:mm:ss";
		}
		DateFormat df = new SimpleDateFormat(fo);
		try {
			return df.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static long getTime_zero(){
		try {
			SimpleDateFormat dayFormat = new SimpleDateFormat("yyyyMMdd");
			long time = dayFormat.parse(dayFormat.format(new Date())).getTime();
			return time;
		}catch (Exception e){
			e.printStackTrace();
		}
		return 0;
	}

	public static void main(String[] args) {
		System.out.println(getTime_zero());
	}
}
