//package com.stonedt.spider.util;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.http.ParseException;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.stonedt.spider.entity.InformationEntity;
//
///**
// * 获取动态的数据
// * @author wangyi
// *
// */
//
//
//public class DongcaiUtil {
//	public static void main(String[] args) {
//	 	//String news = getNews("http://app.jg.eastmoney.com/NewsData/GetNewsBySearch.do?securityCodes=600120.SH&title=&pageIndex=1&limit=50&sort=&order=desc");
//	    //System.out.println(news);
//	 	//String news = getNews("http://app.jg.eastmoney.com/NewsData/GetNewsBySearch.do?securityCodes=600120.SH&title=&pageIndex=1&limit=50&sort=&order=desc");
//	    //String info = getCurrentPrice("http://app2.jg.eastmoney.com/stock/f9/GetNoticeBySearch?securityCodes=600000.SH&pageIndex=1&limit=50&sort=datetime&order=desc&endDate=&startDate=&keyWords=&keyType=title&types=");
//	   // String info = getCurrentPrice("http://app2.jg.eastmoney.com/stock/f9/GetNoticeBySearch?securityCodes=600000.SH&pageIndex=1&limit=50&sort=datetime&order=desc&endDate=&startDate=&keyWords=&keyType=title&types=");
//	    //System.out.println(news);
//	 	//String news = getNews("http://app.jg.eastmoney.com/NewsData/GetNewsBySearch.do?securityCodes=600120.SH&title=&pageIndex=1&limit=50&sort=&order=desc");
//	   // String info = getCurrentPrice("http://app2.jg.eastmoney.com/stock/f9/GetNoticeBySearch?securityCodes=600000.SH&pageIndex=1&limit=50&sort=datetime&order=desc&endDate=&startDate=&keyWords=&keyType=title&types=");
//		//System.out.println(info);
//		//String infomation = getInformation("http://emweb.securities.eastmoney.com/CompanySurvey/CompanySurveyAjax?code=SZ002477");
//		//System.out.println("信息"+infomation);
//		//String getnews = getstockinfo("600614");
//		//System.out.println(getnews);
//
//
//		//String jsonMessage = "{\"语文\":\"88\",\"数学\":\"78\",\"计算机\":\"99\"}";
//		//JSONObject  myJson = JSONObject.parseObject(jsonMessage);
//		//Map<String, String> params = JSONObject.parseObject(myJson.toJSONString(), new TypeReference<Map<String, String>>(){});
//		//System.out.println(params);
//		//String notice = getNotice("http://app2.jg.eastmoney.com/stock/f9/GetNoticeBySearch?securityCodes=600000.SH&pageIndex=1&limit=50&sort=datetime&order=desc&endDate=&startDate=&keyWords=&keyT");
//
//
//		//System.out.println(notice);
//
//
//		//List getstocknewsbycode = getstocknewsbycode("60000.SH");
//
//
//
//		//System.out.println(getstocknewsbycode);
//
//		String allmoney = getAllmoney("sz300352");
//		System.out.println(allmoney);
//
//	}
//
//	/**
//	 * 根据url抓取上市公司概况信息
//	 * @param url
//	 * @return
//	 */
//
//	public static String getInformation(String url) {
//
//		url = "http://emweb.securities.eastmoney.com/CompanySurvey/CompanySurveyAjax?code="+url;
//		// TODO Auto-generated method stub
//		String html = "";
//		JSONObject json = new JSONObject();
//		try {
//			html = HttpUtil.get(url);
//			JSONObject parseObject = JSONObject.parseObject(html);
//			html = parseObject.get("fxxg").toString();
//			JSONObject jsonObject = JSONObject.parseObject(html);
//			String builddate = jsonObject.get("clrq").toString();
//			String listdate = jsonObject.get("ssrq").toString();
//			String allmoney = jsonObject.get("fxzsz").toString();
//			json.put("builddate", builddate);
//			json.put("listdate", listdate);
//			json.put("allmoney", allmoney);
//			System.out.println(json);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return json.toJSONString();
//	}
//
//
//	/**
//	 * 根据url抓取上市公司新闻
//	 * @param url
//	 * @return
//	 */
//	public static String getNews(String code) {
//
//
//		String url = "http://app.jg.eastmoney.com/NewsData/GetNewsBySearch.do?securityCodes="+code+"&title=&pageIndex=1&limit=30&sort=&order=desc";
//
//		String html = null;
//		try {
//			html = HttpUtil.get(url);
//			JSONObject parseObject = JSONObject.parseObject(html);
//			html = parseObject.get("records").toString();
//			//System.out.println(string);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		return html;
//
//	}
//	/**
//	 * 抓取公告
//	 * @param url
//	 * @return
//	 */
//	public static List<Map<String,Object>> getNotice1(String code) {
//		String url = "http://app2.jg.eastmoney.com/stock/f9/GetNoticeBySearch?securityCodes="+code+"&pageIndex=1&limit=50&sort=datetime&order=desc&endDate=&startDate=&keyWords=&keyT";
//		String html = null;
//		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
//		try {
//			html = HttpUtil.get(url);
//			System.out.println(html);
//			html = html.substring(1, html.length()-1);
//			html = html.replaceAll("\\\\", "");
//			JSONObject parseObject = JSONObject.parseObject(html);
//			html = parseObject.get("records").toString();
//			JSONArray array = JSONArray.parseArray(html);
//			for (int i = 0; i < array.size(); i++) {
//				Map<String,Object> map =new HashMap<String,Object>();
//				JSONObject parseObject2 = JSONObject.parseObject(array.get(i).toString());
//				String date = parseObject2.get("date").toString();//日期
//				map.put("date", date);
//				String title = parseObject2.get("title").toString();//标题
//				map.put("title", title);
//				JSONObject json = (JSONObject)JSONArray.parseArray(parseObject2.get("attach").toString()).get(0);
//				String noticeurl = json.get("url").toString();//公告的url
//				map.put("noticeurl", noticeurl);
//				list.add(map);
//			}
//		} catch (ParseException | IOException e) {
//			e.printStackTrace();
//		}
//
//		return list;
//
//	}
//
//
//	/**
//	 * 获取当前的股价
//	 * @return
//	 */
//	public static String getCurrentPrice(String url) {
//
//		url = "http://hq.sinajs.cn/list=s_"+url;
//		String html = null;
//	   JSONObject json =  new JSONObject();
//		try {
//			html = HttpUtil.get(url);
//			html = html.substring(html.indexOf("\"")+1, html.length()-3);
//			String[] split = html.split(",");
//			double openingpricetoday = Double.parseDouble(split[3].toString());//涨幅
//			double upsAndDowns = Double.parseDouble(split[2].toString());//涨跌额
//			double currentpricetoday = Double.parseDouble(split[1].toString());//当前价格
//			int flag = 0;
//			if(openingpricetoday>currentpricetoday) {
//				flag = 1;//上升
//			}
//			json.put("currentpricetoday", String.valueOf(currentpricetoday));
//			json.put("openingpricetoday", String.valueOf(openingpricetoday));
//			json.put("upsAndDowns", String.valueOf(upsAndDowns));
//			json.put("flag", String.valueOf(flag));
//			html = json.toJSONString();
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return html;
//
//	}
//	/**
//	 * 根据code抓取返回数据
//	 * @param code
//	 * @return
//	 */
//	public static String getstockinfo(String code) {
//		String result = "";
//		try {
//		 	String string = HttpUtil.get("http://app2.jg.eastmoney.com/stock/f9/GetCompanyIntroInfo?securityCode="+code+"&exchangeRate=1&exchangeRateLabel=CNY&unitType=1");
//		    System.out.println(string);
//		    string = string.replaceAll("\\\\", "");
//		    string = string.substring(1, string.length()-1);
//		    string = JSONArray.parseArray(string).get(0).toString();
//		    result = string;
//		} catch (ParseException | IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return result;
//	}
//	/**
//	 * 根据股票代码获取股票资讯
//	 * @param code
//	 * @return
//	 */
//	public static List<Map<String,Object>> getstocknewsbycode(String code) {
////		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
////		try {
////			String string = HttpUtil.get("http://app2.jg.eastmoney.com/stock/f9/GetNewsBySearch?securityCodes="+code+"&pageIndex=1&limit=10&sort=datetime&order=desc");
////			 string = string.replaceAll("\\\\", "");
////			    string = string.substring(1, string.length()-1);
////			    JSONObject parseObject = JSONObject.parseObject(string);
////			    JSONArray parseArray = JSONArray.parseArray(parseObject.get("records").toString());
////
////			    for (int i = 0; i < parseArray.size(); i++) {
////			    	JSONObject json = (JSONObject)parseArray.get(i);
////			    	String medianame = json.get("medianame").toString();
////			    	if(!"中国化工报".equals(medianame)) {
////			    		try {
////			    		Map<String,Object> map = new HashMap<String,Object>();
////			    		map.put("Title", json.get("Title").toString());
////			    		map.put("Date", json.get("Date").toString());
////			    		map.put("medianame", json.get("medianame").toString());
////			    		map.put("url", json.get("url").toString());
////			    		JSONObject srtileInfo = WebCollectorUtil.getSrtileInfo(json.get("url").toString());
////			    		map.put("content", srtileInfo.get("content").toString());
////
////			    		//String conHtml = DongcaiUtil.getAllmoneyByUrl(json.get("url").toString());//获取html
////		    			InformationEntity picture = ArticlePicture.getPicture(srtileInfo.get("contenthtml").toString(),true,"");//获取图片
////			    		map.put("picC", picture.getPicC());
////			    		map.put("picUrl", picture.getPicUrl());
////
////			    		list.add(map);
////			    		} catch (Exception e) {
////			    			e.printStackTrace();
////						}
////			    	}
////				}
////
////
////
////		} catch (ParseException | IOException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
////
////
////
////		return list;
////
////	}
//
//	public static String getAllmoney(String code) {
//		String url = "http://quote.eastmoney.com/"+code+".html";
//		String html = "";
//		try {
//			html = HttpUtil.getDongcai(url);
//			Document parse = Jsoup.parse(html);
//			html = parse.getElementById("gt7_2").text();
//		} catch (ParseException | IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		return html;
//	}
//
//	public static String getAllmoneyByUrl(String url) {
//		String html = "";
//		try {
//			html = HttpUtil.getDongcai(url);
//			Document parse = Jsoup.parse(html);
//			html = parse.getElementById("gt7_2").text();
//		} catch (ParseException | IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		return html;
//	}
//
//
//
//}
