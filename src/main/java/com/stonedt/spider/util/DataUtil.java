package com.stonedt.spider.util;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class DataUtil {

    /**
     * 检测字符串不为 null,存在非空格的字符
     */
    public static boolean CheckString(String str){
        try {
            if(str!=null){
                str = str.replaceAll("\\s*|\r|\n|\t","");//去除：空格(\s)、换行(\n)、回车(\r)、制表(\t)
                if( !"".equals(str) ){
                    return true;
                }
            }
        }catch (Exception e){}
        return false;
    }

    private static final Pattern NUMBER_PATTERN = Pattern.compile("-?\\d+(\\.\\d+)?");
    public static boolean isNumeric(String str) {
        try {
            if(str!=null){
                boolean pn = NUMBER_PATTERN.matcher(str).matches();
                return pn;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isNumeric_int(String str){
        try {
            Pattern pattern = Pattern.compile("[0-9]*");
            Matcher isNum = pattern.matcher(str);
            if( isNum.matches() ){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 比较s1是否在s2中 ， 不区分大小写
     * @param s1
     * @param s2
     * @return
     */
    public static boolean checkStr(String s1 ,String s2){
        try {
            boolean pn = Pattern.compile(Pattern.quote(s1),    Pattern.CASE_INSENSITIVE).matcher(s2).find();
            return pn;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }


    public static String replace_unit(String input, String regex, String replacement) {
        try {
            Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(input);
            String result = m.replaceAll(replacement);
            return result;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static double divided(double d1, double d2){
        try {
            double value = d1/d2;
            value = (double) Math.round(value * 10000) / 10000;
            return value;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0.0;
    }

    public static double divided_2(double d1, double d2){
        try {
            double value = d1/d2;
            value = (double) Math.round(value * 100) / 100;
            return value;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0.0;
    }

    public static Map<String,Object> toMap_object(String key , Object value){
        Map<String,Object> newMap = new HashMap<>();
        newMap.put("key",key);
        newMap.put("value",value);
        return newMap;
    }

}
