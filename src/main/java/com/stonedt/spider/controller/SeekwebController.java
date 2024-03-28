package com.stonedt.spider.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.stonedt.spider.entity.Seekweb;
import com.stonedt.spider.service.ScreenSeedService;
import com.stonedt.spider.service.SeekwebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.stonedt.spider.util.ElasticSearchUtil;

@Controller
@RequestMapping("/seekweb")
public class SeekwebController {
	@Autowired
	private ScreenSeedService screenSeedService; 
	@Resource
	private SeekwebService SeekwebService;
	
	@RequestMapping("toweb")
	public ModelAndView seek(Seekweb Seekweb,ModelAndView mav) {
		String words= screenSeedService.getStringList();
		String param ="";
		try {
			param = "keyword="+URLEncoder.encode(words, "utf-8")+"&times=&tmiee=";
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String sendPost ="";
		
		try {
			sendPost = ElasticSearchUtil.sendPost("yys/qbwebsiteidnum", param);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		JSONObject jsonobject = JSONObject.parseObject(sendPost);
		JSONArray array = jsonobject.getJSONObject("aggregations").getJSONObject("group_by_tags").getJSONArray("buckets");
		List<Seekweb> list=new ArrayList<Seekweb>();
		List<Seekweb> arry=new ArrayList<Seekweb>();
		for(int i=0;i<array.size();i++) {
			Seekweb.setExtend_int_two((String) array.getJSONObject(i).getJSONObject("top_score_hits").getJSONObject("hits").getJSONArray("hits").getJSONObject(0).getJSONObject("_source").get("extend_int_two"));
			list.addAll(SeekwebService.getSeekList(Seekweb));
			
			if("2".equals(list.get(i).getIsOnSite())) {
				System.out.println("-----------------");
			}else if("1".equals(list.get(i).getIsOnSite())) {
				Seekweb instation=SeekwebService.getoneList(Seekweb);
				instation.setDoc_count((Integer) array.getJSONObject(i).get("doc_count"));
				instation.setOtherwebsiteid((String) array.getJSONObject(i).getJSONObject("top_score_hits").getJSONObject("hits").getJSONArray("hits").getJSONObject(0).getJSONObject("_source").get("website_id"));
				 
				 arry.add(instation);
			}
		}
		mav.addObject("arry", arry);
		mav.setViewName("seek_web");
		
		return mav;
	}
}
