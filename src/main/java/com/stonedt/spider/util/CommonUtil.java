package com.stonedt.spider.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {
    /**
     * 判断一个字符串中是否含有数字
     */
    public static boolean isIncludeNumber(String str) {
        boolean result = false;
        try {
            Pattern p = Pattern.compile(".*\\d+.*");
            Matcher m = p.matcher(str);
            if (m.matches()) {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 判断一个字符串是否都为数字
     *
     * @param strNum
     * @return
     */
    public static boolean isDigit(String strNum) {
        Pattern pattern = Pattern.compile("[0-9]{1,}");
        Matcher matcher = pattern.matcher((CharSequence) strNum);
        return matcher.matches();
    }


    /**
     * 去除中文
     *
     * @param args
     */
    public static String wipeEnglish(String charsets) {
        String str = charsets.replaceAll("[a-zA-Z]", "");
        return str;
    }

    /**
     * 去除英文
     *
     * @param args
     */
    public static String wipeChinese(String charsets) {
        String REGEX_CHINESE = "[\u4e00-\u9fa5]";// 中文正则
        Pattern pat = Pattern.compile(REGEX_CHINESE);
        Matcher mat = pat.matcher(charsets);
        String str = mat.replaceAll("");
        return str;
    }

    /**
     * 去除特殊字符
     *
     * @param args
     */
    public static String wipeSpecialCharset(String charsets) {
        String REGEX_CHINESE = "[\n`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。， 、？]";
        Pattern pat = Pattern.compile(REGEX_CHINESE);
        Matcher mat = pat.matcher(charsets);
        String str = mat.replaceAll("");
        return str;
    }

    /**
     * 去除引号
     */
    public static String wideSpecialChart2(String charsets) {
        String string = charsets.replaceAll("\"([^\"]*)\"", " ");
        return string;
    }

    /**
     * 去除数字
     * hjc
     */
    public static String wideNumber(String charsets) {
        String result = Pattern.compile("[\\d]").matcher(charsets).replaceAll("");
        return result;
    }

    /**
     * 读取properties文件
     *
     * @param args
     */
    public static String readProperties(String key) {
        String keywords = "";
        try {
            Properties properties = new Properties();
            properties.load(new InputStreamReader(new FileInputStream("config/config.properties"), "UTF-8"));
            keywords = properties.getProperty(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return keywords;
    }



    public static boolean checkname(String name) {
        int n = 0;
        for (int i = 0; i < name.length(); i++) {
            n = (int) name.charAt(i);
            if (!(19968 <= n && n < 40869)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
//        readProperties("keywords");
    }
}
