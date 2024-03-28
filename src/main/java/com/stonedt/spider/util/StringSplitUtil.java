package com.stonedt.spider.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class StringSplitUtil {
	
	public static String removerEmpty(String string) {
		string = string.replaceAll("，", ",");
		String[] stringSplit = string.split(",");
		List<String> stringList = new ArrayList<String>();
		for (String split : stringSplit) {
			if(!StringUtils.isEmpty(split.trim())){
				stringList.add(split.trim());
			}
		}
		String str = "";
		for (int i = 0; i < stringList.size(); i++) {
			if(i == stringList.size() -1){
				str += stringList.get(i);
			}else{
				str += stringList.get(i) + ",";
			}
		}
		return str;
	}
	
	public static void main(String[] args) {
		System.out.println(removerEmpty("1"));
	}

}
