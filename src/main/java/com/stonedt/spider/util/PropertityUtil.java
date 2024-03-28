package com.stonedt.spider.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


	/**鎻忚堪锛氳幏鍙栭偖浠堕厤缃枃浠朵腑淇℃伅<br>
	 */
	public class PropertityUtil {
		private static HashMap<String,Properties> map = null;
		
		/**
		 * 鏂规硶鍚嶇О: get<br>
		 * 鎻忚堪锛氶�氳繃key鑾峰彇璧勬簮鏂囦欢涓殑value
		 */
		public static String get(String key,String fileName){
			String propertyPath = fileName;
			if(map == null){
				map = new HashMap<String,Properties>();
			}
			if(map.get(fileName) != null) {
				return map.get(fileName).get(key)==null?null:map.get(fileName).get(key).toString();
			}
			InputStream is = PropertityUtil.class.getClassLoader().getResourceAsStream(propertyPath);
			Properties propertie = new Properties();
			try {
				propertie.load(is);
				map.put(fileName, propertie);
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				if(is != null){
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} 
			}
			return map.get(fileName).get(key)==null?null:map.get(fileName).get(key).toString();
		}

		/**
		 * 鏂规硶鍚嶇О锛歳eload<br>
		 * 鎻忚堪锛氶噸鏂板姞杞介厤缃枃浠�<br>
		 * @param fileNames 濡傛灉涓虹┖锛屽垯reload鍏ㄩ儴閰嶇疆鏂囦欢
		 */
		public static void reload(String... fileNames){
			if(fileNames.length == 0){
				fileNames =  map.keySet().toArray(new String[0]);
			}
			String propertyPath;
			for (String fileName : fileNames) {
				propertyPath = fileName;
				if(map == null){
					map = new HashMap<String,Properties>();
				}
				map.remove(fileName);
				InputStream is = PropertityUtil.class.getClassLoader().getResourceAsStream(propertyPath);
				Properties propertie = new Properties();
				try {
					propertie.load(is);
					map.put(fileName, propertie);
				} catch (IOException e) {
					e.printStackTrace();
				}finally{
					if(is != null){
						try {
							is.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					} 
				}
			}
		}
		

		public static Map<String, String> getMap(String fileName){
			String propertyPath = fileName;
			if(map == null){
				map = new HashMap<String,Properties>();
			}
			if(map.get(fileName) != null) {
				return new HashMap<String, String>((Map) map.get(fileName));
			}
			InputStream is = PropertityUtil.class.getClassLoader().getResourceAsStream(propertyPath);
			Properties propertie = new Properties();
			try {
				propertie.load(is);
				map.put(fileName, propertie);
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				if(is != null){
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} 
			}
			return new HashMap<String, String>((Map) map.get(fileName));
		
		}
	} 