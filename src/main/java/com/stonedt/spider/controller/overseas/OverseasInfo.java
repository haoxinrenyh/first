package com.stonedt.spider.controller.overseas;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.stonedt.spider.service.OverseasInfoService;
import com.stonedt.spider.util.DateUtil;
import com.stonedt.spider.util.ElasticSearchUtil;
import com.stonedt.spider.util.UtilConfig;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/overseas")
public class OverseasInfo {
    @Autowired
    OverseasInfoService overseasInfoService;

    //	境外数据
    @RequestMapping(value = "/overseasinfo", method = RequestMethod.GET)
    public ModelAndView toInformationPage(ModelAndView mv, @RequestParam("classify") String classify,
                                          HttpServletRequest request) {
        mv.addObject("classify", classify);
        if (classify.equals("1")) {
            mv.addObject("webName", "敌对网站");
        } else if (classify.equals("2")) {
            mv.addObject("webName", "主流网站");
        } else if (classify.equals("3")) {
            mv.addObject("webName", "港台网站");
        } else if (classify.equals("4")) {
            mv.addObject("webName", "社交媒体");
        }
        mv.addObject("nowArray", DateUtil.getNowDate());
        request.setAttribute("LEFT", "overseas" + classify);
        mv.setViewName("overseasData/overseasinfo");
        return mv;
    }
    
    
    //跳转境外数据详情
    @RequestMapping(value = "/toOverSeasDetail", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView toOverSeasDetail(HttpServletRequest request,ModelAndView mv,String article_public_id,String classify) {
    	request.setAttribute("LEFT", "overseas" + classify);
    	request.setAttribute("article_public_id", article_public_id);
    	mv.setViewName("overseasData/overseasDetail");
    	return mv;
    }
    //获取境外详情
    @RequestMapping(value = "/toGetOverSeasDetail", method = RequestMethod.POST)
    @ResponseBody
    public String toGetOverSeasDetail(HttpServletRequest request,ModelAndView mv,String article_public_id) {
    	JSONObject json=new JSONObject();
    	String url=UtilConfig.getURL2().substring(0,UtilConfig.getURL2().length()-1)+"/commonsearch/getcommondatadetail";
    	json.put("index", "foreignmedia");
    	json.put("type", "infor");
    	json.put("fieldname", "article_public_id");
    	json.put("fielddata", article_public_id);
    	String data="";
		try {
			data = getJsonHtml(url,json);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	JSONObject jsondata=JSONObject.parseObject(data);
    	return jsondata.toJSONString();
    }
    /**
     * 获取境外数据的资讯
     * 2020/03/20、hjc
     */
    @RequestMapping(value = "/getOverseasMsg", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getOverseasMsg(HttpServletRequest request, @RequestBody JSONObject data) {
        JSONObject responseJson = new JSONObject();
        try {
            String classify = data.getString("classify");
            String webSiteId = "";
            if (classify.equals("1")) {
                webSiteId = "1028,1040,1080,1029,1081,1017,1070,1033,1034,1057,1072,1071,1058,1082,1035,1075,1059,1074,1075,1018,1084,1060,1085,1086,1036,1061,1076,1078,1087,1071,1090,1091,1092,1037,1038,1093,1039,1040,1063,1064,1094,1095,1065,1019,1097,1079,1098,1042,1099,1043,1066";
            } else if (classify.equals("2")) {
                webSiteId = "1044,1020,1047,1100,1048,1101,1049,1062,1021,1102,1101,1103,1067,1068,1053,1054,1055,1104,1056,1022,1023,1047,1105,1073,1024,1106,1069";
            } else if (classify.equals("3")) {
                webSiteId = "1025,1026,1109,1110,1111,1112,1113,1114,1115,1116,1117,1119,1120,1121,1122,1123";
            }

            Map<String, Object> map = JSONObject.parseObject(data.toJSONString(), Map.class);
            map.put("size", 10);
            map.put("otherwebsiteid", webSiteId);
            responseJson = overseasInfoService.getOverseasList(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseJson;
    }


    /**
     * 跳转境外媒体数据-推特
     * 2020/03/20
     * hjc
     */
    @RequestMapping(value = "/twitter", method = RequestMethod.GET)
    public ModelAndView toTwitterPage(ModelAndView mv, HttpServletRequest request) {
        request.setAttribute("LEFT", "twitter");
        mv.addObject("nowArray", DateUtil.getNowDate());
        mv.setViewName("overseasData/twitter");
        return mv;
    }

    /**
     * 获取推特数据
     * 2020/03/20
     * hjc
     */
    @RequestMapping(value = "/getTwitterAsyn", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getTwitter(HttpServletRequest request, @RequestBody JSONObject data) {
        JSONObject responseJson = new JSONObject();
        try {
            Map<String, Object> map = JSONObject.parseObject(data.toJSONString(), Map.class);
            map.put("size", 10);
            responseJson = overseasInfoService.getTwitterList(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseJson;
    }
    
    public static String getJsonHtml(String url, JSONObject jsonObject) throws Exception {
		System.err.println(url+jsonObject.toString());
		String result ="";
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		StringEntity postingString = new StringEntity(jsonObject.toString());// json传递
		post.setEntity(postingString);
		post.setHeader("Referer", "http://hotel.qunar.com/cn/shanghai_city/?fromDate=2020-02-13&toDate=2020-02-14&cityName=%E4%B8%8A%E6%B5%B7");
		post.setHeader("Content-type", "application/json");
		HttpResponse response = httpClient.execute(post);
		String content = EntityUtils.toString(response.getEntity());
		result = content;
		System.out.println(result.toString());
		return result;
	}
    
    /**
     * post 以raw的方式发送请求
     */
    public static String sendPostRaw(String params, String url, String encoding) {
        String body = "";
        try {
            //创建httpclient对象
            CloseableHttpClient client = HttpClients.createDefault();
            //创建post方式请求对象
            HttpPost httpPost = new HttpPost(url);

            //装填参数
            StringEntity s = new StringEntity(params, encoding);
            s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json;charset=UTF-8"));
            //设置参数到请求对象中
            httpPost.setEntity(s);
//            logger.info("请求地址：" + url);

            //设置header信息
            httpPost.setHeader("Content-type", "application/json;charset=UTF-8");
            httpPost.setHeader("User-Agent",getRandomAgent());

            CloseableHttpResponse response = client.execute(httpPost);  //执行请求操作，并拿到结果（同步阻塞）
            HttpEntity entity = response.getEntity();//获取结果实体
            if (entity != null) {
                body = EntityUtils.toString(entity, encoding);  //按指定编码转换结果实体为String类型
            }
            EntityUtils.consume(entity);
            response.close();  //释放链接
        } catch (Exception e) {
            e.printStackTrace();
        }
        return body;
    }
    
    
    
    /**
     * 随机一个Agent
     * @return
     */
    public static String getRandomAgent()
    {
     List<String> RandomAgent = new ArrayList<String>();
     RandomAgent.add("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:34.0) Gecko/20100101 Firefox/34.0");
     RandomAgent.add("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");
     RandomAgent.add("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36");
     RandomAgent.add("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.64 Safari/537.11");
     RandomAgent.add("Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US) AppleWebKit/534.16 (KHTML, like Gecko) Chrome/10.0.648.133 Safari/534.16");
     int sign = (int)(0+Math.random()*(5));
     return RandomAgent.get(sign);
    }
    
    public static void main(String[] args) throws UnsupportedEncodingException {
//    	String param = "{\"timefield\":\"qualifitime\",\"times\":\"\",\"size\":\"15\",\"and\":[{\"field\":\"name\",\"keyword\":\""+URLEncoder.encode("曹东红","utf-8")+"\"}],\"index\":\"stonedt_law\",\"page\":\"1\",\"type\":\"infor\",\"timee\":\"\"}";
    	String param = "{\"timefield\":\"spider_time\",\"times\":\"\",\"size\":\"15\",\"and\":[{\"field\":\"website_id\",\"keyword\":\""+1584+"\"}],\"index\":\"stonedt_tourism_entertainment\",\"page\":\"2\",\"type\":\"infor\",\"timee\":\"\"}";
    	JSONObject parseObject = JSONObject.parseObject(param);
    	System.out.println(parseObject);
		try {
			String jsonHtml = getJsonHtml("http://dx2.stonedt.com:7120/commonsearch/superdatalist",parseObject);
			System.out.println(jsonHtml);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}	
}