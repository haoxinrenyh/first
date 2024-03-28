
package com.stonedt.spider.controller.hotlist;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.stonedt.spider.util.DateUtil;
import com.stonedt.spider.util.ElasticSearchUtil;
import com.stonedt.spider.util.UtilConfig;

/**
* <p> 热榜数据</p>
* <p>Title: HotListToday</p>  
* <p>Description: </p>  
* @author Mapeng 
* @date May 15, 2020  
*/
@Controller
@RequestMapping("/hotday")
public class HotListTodayController {

	@RequestMapping("")
	public ModelAndView hotListToday(ModelAndView mav) {
		mav.addObject("nowArray", DateUtil.getNowDate());
		mav.setViewName("informationData/hotListToday");
		return mav;
	}
	
	/**
	 * 列表
	 * @param page
	 * @param classification
	 * @param keyword
	 * @return
	 */
	@RequestMapping(value="/data",method = RequestMethod.POST)
	public@ResponseBody JSONObject listData(@RequestParam(value = "page", defaultValue = "1" ,required = false)Integer page,
			@RequestParam(value = "classification", defaultValue = "1" ,required = false)String classification,
			@RequestParam(value = "keyword", defaultValue = "" ,required = false)String keyword) {
		String response = "";
		JSONObject json=new JSONObject();
		String params = "{\"timefield\":\"publish_time\",\"index\":\"hotnews\",\"times\":\"\",\"timee\":\"\",\"page\":"+page+",\"size\":\"10\"";
		if(StringUtils.isNotBlank(keyword)) {
			params += ",\"or\":[{\"field\":\"title,content\",\"keyword\":\""+keyword+"\"}]";
		}
		if(!StringUtils.equals(classification, "全部")) {
			params += ",\"and\":[{\"field\":\"classification\",\"keyword\":\""+classification+"\"}]";
		}
		params += "}";
		System.err.println(params);
		try {
			response = ElasticSearchUtil.doPostJson(UtilConfig.getURL() + "commonsearch/superdatalist", params);
			System.err.println("查询热榜结束");
			json = JSONObject.parseObject(response);
			Integer count =0;
			Integer page_count =0;
			Integer size =0;
			JSONArray news = json.getJSONArray("data");
			count = json.getIntValue("count");
			page_count = json.getIntValue("page_count");
			size = json.getIntValue("size");
			json.put("news",news);
			json.put("count",count);
			json.put("page_count",page_count);
			json.put("page",page);
			json.put("size",size);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	@RequestMapping("/details")
    public ModelAndView ZiXunDetails(HttpServletRequest req,ModelAndView mav,String article_public_id,String type) {
    	req.setAttribute("article_public_id", article_public_id);
    	req.setAttribute("LEFT", type);
    	mav.setViewName("informationData/hotListToday_details");
    	return mav;
    }
	
	/***
     * 获取详情数据
     * @param mav
     * @return
     */
    @RequestMapping("/informationDetails-data")
    @ResponseBody
    public String ZiXunDetailsData(String article_public_id) {
    		String url="/yqsearch/getArticlenewdetail";
    		String response="";
    		String param="?&article_public_id="+article_public_id;
    		/*JSONObject json=new JSONObject();
    		json.put("index", "postal");
    		json.put("type", "infor");
    		json.put("fieldname", "article_public_id");
    		json.put("fielddata", article_public_id);*/
    		JSONObject _sourceObj=new JSONObject();
    		try {
				response=sendPost(url, param);
				_sourceObj=JSONObject.parseObject(response);
				if(_sourceObj.get("title").toString().contains("_http")) {
            		String[] ti=_sourceObj.get("title").toString().split("_h");
            		_sourceObj.put("title", ti[0]);
            		_sourceObj.put("source_url", ti[1]);
            	}
				String obj_text=_sourceObj.get("text").toString();
				//去掉class正则
				Pattern p_class=Pattern.compile("class[\\s]*=[\\s]*['|\"](.*?)['|\"]");
				//去掉style正则
				Pattern p_style=Pattern.compile("style[\\s]*=[\\s]*['|\"](.*?)['|\"]");
				//去掉id正则
				Pattern p_id=Pattern.compile("id[\\s]*=[\\s]*['|\"](.*?)['|\"]");
				
				//把img标签中的data-src 替换为src
				Pattern img_src=Pattern.compile("data-src[\\s]*");
				
				String class_String=obj_text.replaceAll(p_class.toString(), "");
				String style_String=class_String.replaceAll(p_style.toString(), "");
				String id_String=style_String.replaceAll(p_id.toString(), "");
				String text=id_String.replaceAll(img_src.toString(), "src");
				_sourceObj.remove("text");
				_sourceObj.put("text", text);
			} catch (Exception e) {
				e.printStackTrace();
			}
    	return _sourceObj.toJSONString();
    }
    
    
    public static String sendPost(String method, String param) throws Exception {
        String url = UtilConfig.getURL().substring(0,UtilConfig.getURL().length()-1) + method;
//		String url = "http://els.stonedt.com:6399/bryes/"+method;
        PrintWriter out = null;
        BufferedReader in = null;
//        String result = "";
        System.out.println(url + param);
        URL realUrl = new URL(url);
        URLConnection conn = realUrl.openConnection();
        // 打开和URL之间的连接
        //URLConnection conn = realUrl.openConnection();
        // 设置通用的请求属性
//			conn.setRequestProperty("content-type","application/json; charset=UTF-8");

        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        // 发送POST请求必须设置如下两行
        conn.setDoOutput(true);
        conn.setDoInput(true);
        // 获取URLConnection对象对应的输出流
        out = new PrintWriter(conn.getOutputStream());
        // 发送请求参数
        out.print(param);
        // flush输出流的缓冲
        out.flush();
        // 定义BufferedReader输入流来读取URL的响应
        in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));//转成utf-8格式
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            response.append(line);
        }
        // 使用finally块来关闭输出流、输入流
        try {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return response.toString();
    }
}
