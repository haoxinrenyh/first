package com.stonedt.spider.controller.media;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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

@Controller
@RequestMapping("/mediaData")
public class MediadataController {//传媒数据controller
	/***
	 * 娱乐资讯
	 * @param mav
	 * @param page
	 * @return
	 * Colin
	 * recreation 娱乐资讯
	 */
	@RequestMapping("/recreation")
	public ModelAndView eleinformation(ModelAndView mav,HttpServletRequest request,
			@RequestParam(value = "type", defaultValue = "" ,required = false)String type) {
		mav.setViewName("mediaData/recreation_list");
		if(type.equals("recreation")) {
			mav.addObject("webName", "娱乐资讯");
		}
		mav.addObject("nowArray", DateUtil.getNowDate());
		request.setAttribute("LEFT", type);
		return mav;
	}

	
	@RequestMapping(value="/getRecreation",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject eleinformation2(
			@RequestParam(value = "page", defaultValue = "1" ,required = false)Integer page) {
		JSONObject json=new JSONObject();
		try {
			String response="";
			String param="";//腾讯娱乐种子id23283,23284,23285,23286,23287   网易娱乐种子id23289,23290,23291  搜狐娱乐种子id23288,23292,23293,23294,23295
			param="?&page="+page+"&size=10&otherseedid=23099,23283,23284,23285,23286,23287,23289,23290,23291,23288,23292,23293,23294,23295&searchType=1";	
			Integer count =0;
			Integer page_count =0;
			Integer size =0;
				response = ElasticSearchUtil.sendPost("yys/qbsearchcontent", param);
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
