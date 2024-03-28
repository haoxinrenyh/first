package com.stonedt.spider.util;

import java.io.File;
import java.util.Map;
import com.stonedt.spider.util.PropertityUtil;

public class UtilConfig {

	private static Map<String,String> UtilInfo=PropertityUtil.getMap("utilconfig"+File.separator+"utilconfig.properties");
	
	/**
	 *
	 * @return
	 */
	public static String getARTICLE_URL(){
		return UtilInfo.get("ARTICLE_URL");
	}
	
	/**
	 *
	 * @return
	 */
	public static String getURL(){
		return UtilInfo.get("URL");
	}
	
	/**
	 *
	 * @return
	 */
	public static String getURL2(){
		return UtilInfo.get("URL2");
	}

	public static String getKeywords(){
		return UtilInfo.get("keywords");
	}
	
	/**
	 * 
	 * @return
	 */
	public static String getPAGE_SIZE(){
		return UtilInfo.get("PAGE_SIZE");
	}
	
	
	public static String getParam(){
		return UtilInfo.get("Param");
	}
	
	
	public static String getSEARCH_CLASSIFY(){
		return UtilInfo.get("SEARCH_CLASSIFY");
	}
	
	public static String getNOTSEARCH_CLASSIFY(){
		return UtilInfo.get("NOTSEARCH_CLASSIFY");
	}
	public static String getBLOCK_WORD(){
		return UtilInfo.get("BLOCK_WORD");
	}
	
}
