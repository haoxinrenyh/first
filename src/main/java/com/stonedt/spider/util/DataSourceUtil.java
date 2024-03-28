package com.stonedt.spider.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 *
 * @date  2019年9月10日 下午1:41:03
 */
public class DataSourceUtil {
	
	public static void main(String[] args) {
		String aString = "热门视频 $正在加载...请稍等~ "
				+ "<#et hotVideoTpl data> 啊啊啊啊<#list data.data as dataList> "
				+ "<#if (dataList_index < 3 && dataList.video.video_id)> ${++dataList_index}."
				+ " ${dataList.numFormate} ${dataList.overTitle} <#else ${++dataList_index}."
				+ " ${dataList.numFormate} ${dataList.shortTitle}";
		System.err.println(removeFreemarker(aString));
	}
	
	// 去除 <#..> ${...} 类似标签
	public static String removeFreemarker(String text) {
		String regex1 = "<[^>]*>";
		String regex2 = "\\$\\{.*?\\}.";
		String regex3 = "\\$\\{.*?\\}"; 
		String str = "";
		if (StringUtils.isNotEmpty(text)) {
			str = text.replaceAll(regex1, "").replaceAll(regex2, "").replaceAll(regex3, "");
		}
		return str;
	}

	//数据源
	public static String dataSourceClassification(String key) {
		String result = "";
		switch (key) {
			case "1": result = "微信"; break;
			case "2": result = "微博"; break;
			case "3": result = "政务"; break;
			case "4": result = "论坛"; break;
			case "5": result = "新闻"; break;
			case "6": result = "报刊"; break;
			case "7": result = "客户端"; break;
			case "8": result = "网站"; break;
			case "9": result = "外媒"; break;
			case "10": result = "视频"; break;
			case "11": result = "博客"; break;
			default: break;
		}
		return result;
	}
	
	//数据源
	public static String dataSourceClassification2(String key) {
		String result = "";
		switch (key) {
			case "wechat": result = "微信"; break;
			case "weibo": result = "微博"; break;
			case "gov": result = "政务"; break;
			case "bbs": result = "论坛"; break;
			case "news": result = "新闻"; break;
			case "newspaper": result = "报刊"; break;
			case "app": result = "客户端"; break;
			case "web": result = "网站"; break;
			case "foreign_media": result = "外媒"; break;
			case "video": result = "视频"; break;
			case "blog": result = "博客"; break;
			default: break;
		}
		return result;
	}
	
	//数据源
		public static String dataSourceClassificationEng(String key) {
			String result = "";
			switch (key) {
				case "1": result = "wechat"; break;
				case "2": result = "weibo"; break;
				case "3": result = "gov"; break;
				case "4": result = "bbs"; break;
				case "5": result = "news"; break;
				case "6": result = "newspaper"; break;
				case "7": result = "app"; break;
				case "8": result = "web"; break;
				case "9": result = "foreign_media"; break;
				case "10": result = "video"; break;
				case "11": result = "blog"; break;
				default: break;
			}
			return result;
		}

	//数据源
	public static String dataSourceClassificationNew(String key) {
		String result = "";
		switch (key) {
			case "1": result = "微信,wechat"; break;
			case "2": result = "微博,weibo"; break;
			case "3": result = "政务,gov"; break;
			case "4": result = "论坛,bbs"; break;
			case "5": result = "新闻,news"; break;
			case "6": result = "报刊,newspaper"; break;
			case "7": result = "客户端,app"; break;
			case "8": result = "网站,web"; break;
			case "9": result = "外媒,foreign_media"; break;
			case "10": result = "视频,video"; break;
			case "11": result = "博客,blog"; break;
            case "12": result = "全部,all"; break;
			default: break;
		}
		return result;
	}
	
	//情感
	public static String emotionClassification(int i) {
		String result = "";
		switch (i) {
			case 1: result = "正面"; break;
			case 2: result = "中性"; break;
			case 3: result = "负面"; break;
			default:
				break;
		}
		return result;
	}

    /**
     * 华建成
     * 2019/09/17
     * 返回key
     * @return
     */
	public static List<String> returnKeyList(){
	    List<String> keyAll = new ArrayList<>();
        for (int i = 1; i <13 ; i++) {
            String key = String.valueOf(i);
            keyAll.add(key);
        }
        return keyAll;
    }

	//情感判断
	public static String emotionClassification(String key) {
		String result = "";
		switch (key) {
			case "1": result = "正面,positive"; break;
			case "2": result = "中性,neutrality"; break;
			case "3": result = "负面,negativity"; break;
			case "total": result = "全部,total"; break;
			default: break;
		}
		return result;
	}
	
	//计算占比 a/b
	public static String calculatedRatio(Integer a, Integer b) {
		String result = "";
		if (b == 0 || a == 0) {
			result = "0.00%";
		}else {
			BigDecimal bigDecimala = new BigDecimal(a);
			BigDecimal bigDecimalb = new BigDecimal(b);
			//BigDecimal divide = bigDecimala.divide(bigDecimalb, 4, BigDecimal.ROUND_HALF_EVEN);
		    BigDecimal divide = bigDecimala.divide(bigDecimalb, 4, BigDecimal.ROUND_HALF_UP);
		    NumberFormat numberFormat = NumberFormat.getPercentInstance();
		    numberFormat.setMaximumFractionDigits(2);
		    result = numberFormat.format(divide.doubleValue());
		}
	    return result;
	}
	
	//计算增长率(b-a)/a
	public static String CalculatedGrowthRate(Integer a, Integer b) {
		String result = "";
		if (a == 0) {
			result = "100%";
		}else {
			BigDecimal bigDecimala = new BigDecimal(b-a);
			BigDecimal bigDecimalb = new BigDecimal(a);
		    BigDecimal divide = bigDecimala.divide(bigDecimalb, 4, BigDecimal.ROUND_HALF_UP);
		    NumberFormat numberFormat = NumberFormat.getPercentInstance();
		    numberFormat.setMaximumFractionDigits(2);
		    result = numberFormat.format(divide.doubleValue());
		}
	    return result;
	}
	
	/**
	 * 
	 * @param a 值
	 * @param b 值
	 * @return
	 */
	public static String sentimentRatio(int a, int b) {
		BigDecimal aDecimal = new BigDecimal(a);
		BigDecimal bDecimal = new BigDecimal(b);
		
		BigDecimal divide = aDecimal.divide(bDecimal, 6, BigDecimal.ROUND_HALF_UP);
		BigDecimal multiply = divide.multiply(new BigDecimal(100));
		return multiply.toString();
	}
	
	//double数相减
	public static double subtraction(double a, String b) {
		BigDecimal bigDecimala = new BigDecimal(String.valueOf(a));
		BigDecimal bigDecimalb = new BigDecimal(b);
		BigDecimal subtract = bigDecimala.subtract(bigDecimalb);
		return subtract.doubleValue();
	}

}
