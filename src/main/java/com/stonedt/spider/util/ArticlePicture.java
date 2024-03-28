package com.stonedt.spider.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.http.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.stonedt.spider.entity.InformationEntity;


public class ArticlePicture {
	public static void main(String[] args) {
	}
	
	/**
	 * 判断是否存在图片，如果不存在就随机替换。布尔参数默认为true，如果是微信请设置为false
	 * @param contenthtml
	 * @param flag
	 * @return
	 */
	public static InformationEntity getPicture(String contenthtml,boolean flag,String seed_id) {
		InformationEntity info = new InformationEntity();
		try {
			Document parse = Jsoup.parse(contenthtml);
			 Elements elements = parse.getElementsByTag("img");
			 List<String> list = new ArrayList<String>();
		        if (elements != null && elements.size() > 0) {
		            Element loopElement = null;
		            for (int i = 0; i < elements.size(); i++) {
		                loopElement = elements.get(i);
//		                System.out.println("图片地址：" + loopElement.attr("src"));
		                String url1 = loopElement.attr("src");
		                url1 = url1.replace(" ", "").trim();
		                String regex = "^([hH][tT]{2}[pP]:/*|[hH][tT]{2}[pP][sS]:/*|[fF][tT][pP]:/*)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+(\\?{0,1}(([A-Za-z0-9-~]+\\={0,1})([A-Za-z0-9-~]*)\\&{0,1})*)$";
		                Pattern pattern = Pattern.compile(regex);
		                if (pattern.matcher(url1).matches()) {
//		                System.out.println("是正确的网址");
		                if(flag)
		                {
		                	list.add(url1);
		                }
		                break;
		                } 
		            }
		        }
			if(list.size()>0 && list != null) {
				String picUrl = list.get(0);
				String picC ="2";
				info.setPicC(picC);
				info.setPicUrl(picUrl);
			}else {
				String picUrl = "";
				if("317".equals(seed_id)) {
					int k = (int) Math.floor(Math.random()*(54)+1);
					picUrl = "img/premier/"+k+".jpg";
				}else {
					int  j = (int) Math.floor(Math.random()*(202)+1);
					picUrl = "img/infomation/"+j+".jpg";
				}
				
				String picC = "1";
				info.setPicC(picC);
				info.setPicUrl(picUrl);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return info;
	}
	
	//去除广告
	public static String  removeAdvertisement(String contenthtml) {
		if(null!=contenthtml&&!"null".equals(contenthtml)) {
			contenthtml = contenthtml.replaceAll("</?iframe[^><]*>", "");
		}
		return contenthtml;
	}
	
	
	/***
	 * 
	 * @param text 传入需要格式化的html内容 去掉class style id
	 * @return 返回格式化后的字符串
	 */
	public static String getFormatting(String text) {
		//去掉class正则
		Pattern p_class=Pattern.compile("class[\\s]*=[\\s]*['|\"](.*?)['|\"]");
		//去掉id正则
		Pattern p_id=Pattern.compile("id[\\s]*=[\\s]*['|\"](.*?)['|\"]");
		String class_String=text.replaceAll(p_class.toString(), "");
		String id_String=class_String.replaceAll(p_id.toString(), "");
		String Formattext=id_String.replaceAll(" ", "");
		return Formattext;
	}
	
	
	
	

}
