package com.stonedt.spider.controller.autoIndustry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.stonedt.spider.controller.overseas.OverseasInfo;
import com.stonedt.spider.util.DateUtil;
import com.stonedt.spider.util.ElasticSearchUtil;
import com.stonedt.spider.util.EsUtils;

@Controller
@RequestMapping("/autoindustry")
public class AutoshowController {
	
	/***
	 * 车展活动
	 * @param mav
	 * @return
	 */
	@RequestMapping(value="/activity",method = RequestMethod.GET)
	public ModelAndView message(ModelAndView mav) {
			mav.setViewName("autoIndustry/activity_list");
		return mav;
	}

	
	@RequestMapping(value="/activity-data",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject message2(
			@RequestParam(value = "page", defaultValue = "1" ,required = false)Integer page,
			@RequestParam Integer searchType,
			@RequestParam String searchkeyword) {
		JSONObject json=new JSONObject();
		try {
			String response="";
			
//			String param="";
//				param="?&page="+page+"&size=10&otherseedid=22953,23013,23018,23019&searchType=1";
//				response = ElasticSearchUtil.sendPost("yys/qbsearchcontent", param);
				
			JSONObject end_json=new JSONObject();
            JSONArray array=new JSONArray();
            JSONArray orArr=new JSONArray();
			String otherseedid = "22953,23013,23018,23019";
        	String[] all_id=otherseedid.split(",");
        	for (int i = 0; i < all_id.length; i++) {
        		JSONObject json1=new JSONObject();
				json1.put("field", "otherseedid");
				json1.put("keyword", all_id[i]);
	            array.add(json1);
			}
        	if(searchType==0) {
        		if(!searchkeyword.equals("")) {
            		JSONObject json2=new JSONObject();
                	json2.put("field", "title");
    	            json2.put("keyword", searchkeyword);
    	            orArr.add(json2);
    	            JSONObject json3=new JSONObject();
                	json3.put("field", "content");
    	            json3.put("keyword", searchkeyword);
    	            orArr.add(json3);
                    end_json.put("and", orArr);
            	}
        	}else if(searchType==1){
        		if(!searchkeyword.equals("")) {
            		JSONObject json2=new JSONObject();
                	json2.put("field", "title");
    	            json2.put("keyword", searchkeyword);
    	            orArr.add(json2);
                    end_json.put("and", orArr);
            	}
        	}else if(searchType==2){
        		if(!searchkeyword.equals("")) {
            		JSONObject json2=new JSONObject();
                	json2.put("field", "content");
    	            json2.put("keyword", searchkeyword);
    	            orArr.add(json2);
                    end_json.put("and", orArr);
            	}
        	}
        	
            
            end_json.put("timefield", "publish_time");
            end_json.put("times", "");
            end_json.put("timee", "");
            end_json.put("index", "postal");
            end_json.put("type", "infor");
            end_json.put("page", page);
            end_json.put("size", "10");
            end_json.put("or", array);
            //System.err.println(end_json.toString());
            String url =  "http://dx2.stonedt.com:7120/commonsearch/superdatabankbiaolist";
            response = OverseasInfo.sendPostRaw(end_json.toJSONString(),url,"utf-8");
				
			Integer count =0;
			Integer page_count =0;
			Integer size =0;
			//System.err.println(response.toString());
			json = JSONObject.parseObject(response);
			JSONArray news=json.getJSONArray("data");
			count=Integer.valueOf(json.getString("count"));
			page_count=Integer.valueOf(json.getString("page_count"));
			size=Integer.valueOf(json.getString("size"));
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
	
	
	
	/***
	 * 新车上市
	 * @param mav
	 * @return
	 */
	@RequestMapping(value="/carlaunch",method = RequestMethod.GET)
	public ModelAndView carlaunch(ModelAndView mav) {
			mav.addObject("nowArray", DateUtil.getNowDate());
			mav.setViewName("autoIndustry/carlaunch");
		return mav;
	}

	
	@RequestMapping(value="/carlaunch-data",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject carlaunch_data(
			@RequestParam(value = "page", defaultValue = "1" ,required = false)Integer page,
			@RequestParam Integer searchType,
			@RequestParam String searchkeyword) {
		JSONObject json=new JSONObject();
		try {
			String response="";
//			String param="";
//				param="?&page="+page+"&size=10&otherseedid=23022&searchType=1";
//				response = ElasticSearchUtil.sendPost("yys/qbsearchcontent", param);
//			System.err.println(response.toString());
			
			JSONObject end_json=new JSONObject();
            JSONArray array=new JSONArray();
            JSONArray orArr=new JSONArray();
        	
        	JSONObject json1=new JSONObject();
			json1.put("field", "otherseedid");
			json1.put("keyword", "23022");
	        array.add(json1); 
        	if(searchType==0) {
        		if(!searchkeyword.equals("")) {
            		JSONObject json2=new JSONObject();
                	json2.put("field", "title");
    	            json2.put("keyword", searchkeyword);
    	            orArr.add(json2);
    	            JSONObject json3=new JSONObject();
                	json3.put("field", "content");
    	            json3.put("keyword", searchkeyword);
    	            orArr.add(json3);
                    end_json.put("or", orArr);
            	}
        	}else if(searchType==1){
        		if(!searchkeyword.equals("")) {
            		JSONObject json2=new JSONObject();
                	json2.put("field", "title");
    	            json2.put("keyword", searchkeyword);
    	            array.add(json2);
            	}
        	}else if(searchType==2){
        		if(!searchkeyword.equals("")) {
            		JSONObject json2=new JSONObject();
                	json2.put("field", "content");
    	            json2.put("keyword", searchkeyword);
    	            array.add(json2);
            	}
        	}
        	
            
            end_json.put("timefield", "publish_time");
            end_json.put("times", "");
            end_json.put("timee", "");
            end_json.put("index", "postal");
            end_json.put("type", "infor");
            end_json.put("page", page);
            end_json.put("size", "10");
            end_json.put("and", array);
            System.err.println(end_json.toString());
            String url =  "http://dx2.stonedt.com:7120/commonsearch/superdatalist";
            response = OverseasInfo.sendPostRaw(end_json.toJSONString(),url,"utf-8");
				
			Integer count =0;
			Integer page_count =0;
			Integer size =0;
			json = JSONObject.parseObject(response);
			JSONArray news=json.getJSONArray("data");
			count=Integer.valueOf(json.getString("count"));
			page_count=Integer.valueOf(json.getString("page_count"));
			size=Integer.valueOf(json.getString("size"));
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
	
	
	/***
	 * 资讯数据
	 * @param mav
	 * @param page 分页参数
	 * @return
	 */
	@RequestMapping(value="/carinformation",method = RequestMethod.GET)
	public ModelAndView carinformation(ModelAndView mav,
			@RequestParam(value = "page", defaultValue = "1" ,required = false)Integer page) {
			mav.addObject("nowArray", DateUtil.getNowDate());
			mav.setViewName("autoIndustry/carinformation_list");
		return mav;
	}

	
	@RequestMapping(value="/carinformation-data",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject carinformation2(
			@RequestParam(value = "page", defaultValue = "1" ,required = false)Integer page,
			@RequestParam Integer searchType,
			@RequestParam String searchkeyword) {
		JSONObject json=new JSONObject();
		try {
			String response="";
			
//			String param="";
//			param="?&page="+page+"&size=10&otherseedid=22951,22957,22962,23004,23007&searchType=1";
//			response = ElasticSearchUtil.sendPost("yys/qbsearchcontent", param);
//			System.err.println(response.toString());
			JSONObject end_json=new JSONObject();
            JSONArray array=new JSONArray();
            JSONArray orArr=new JSONArray();
			String otherseedid = "22951,22957,22962,23004,23007";
        	String[] all_id=otherseedid.split(",");
        	for (int i = 0; i < all_id.length; i++) {
        		JSONObject json1=new JSONObject();
				json1.put("field", "otherseedid");
				json1.put("keyword", all_id[i]);
	            array.add(json1);
			}
        	if(searchType==0) {
        		if(!searchkeyword.equals("")) {
            		JSONObject json2=new JSONObject();
                	json2.put("field", "title");
    	            json2.put("keyword", searchkeyword);
    	            orArr.add(json2);
    	            JSONObject json3=new JSONObject();
                	json3.put("field", "content");
    	            json3.put("keyword", searchkeyword);
    	            orArr.add(json3);
                    end_json.put("and", orArr);
            	}
        	}else if(searchType==1){
        		if(!searchkeyword.equals("")) {
            		JSONObject json2=new JSONObject();
                	json2.put("field", "title");
    	            json2.put("keyword", searchkeyword);
    	            orArr.add(json2);
                    end_json.put("and", orArr);
            	}
        	}else if(searchType==2){
        		if(!searchkeyword.equals("")) {
            		JSONObject json2=new JSONObject();
                	json2.put("field", "content");
    	            json2.put("keyword", searchkeyword);
    	            orArr.add(json2);
                    end_json.put("and", orArr);
            	}
        	}
        	
            
            end_json.put("timefield", "publish_time");
            end_json.put("times", "");
            end_json.put("timee", "");
            end_json.put("index", "postal");
            end_json.put("type", "infor");
            end_json.put("page", page);
            end_json.put("size", "10");
            end_json.put("or", array);
            System.err.println(end_json.toString());
            String url =  "http://dx2.stonedt.com:7120/commonsearch/superdatabankbiaolist";
            response = OverseasInfo.sendPostRaw(end_json.toJSONString(),url,"utf-8");
			
			
			
			Integer count =0;
			Integer page_count =0;
			Integer size =0;
			json = JSONObject.parseObject(response);
			JSONArray news=json.getJSONArray("data");
			count=Integer.valueOf(json.getString("count"));
			page_count=Integer.valueOf(json.getString("page_count"));
			size=Integer.valueOf(json.getString("size"));
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
	
}
