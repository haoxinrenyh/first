
package com.stonedt.spider.controller.hotlist;

import java.net.URLEncoder;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.stonedt.spider.util.DateUtil;
import com.stonedt.spider.util.ElasticSearchUtil;
/**
* <p></p>
* <p>Title: HotListController</p>  
* <p>Description: </p>  
* @author Mapeng 
* @date May 27, 2020  
*/
@Controller
@RequestMapping("/hotlist")
public class HotListController {

	@RequestMapping("information")
	public ModelAndView information(ModelAndView mav,Integer classify) {
		mav.addObject("classify", classify);
		mav.addObject("nowArray", DateUtil.getNowDate());
		mav.setViewName("hotlist/information");
		return mav;
	}
	
	@RequestMapping("video")
	public ModelAndView video(ModelAndView mav,Integer classify) {
		mav.addObject("classify", classify);
		mav.addObject("nowArray", DateUtil.getNowDate());
		mav.setViewName("hotlist/video");
		return mav;
	}
	
	@RequestMapping("onlineRetailers")
	public ModelAndView onlineRetailers(ModelAndView mav,Integer classify) {
		mav.addObject("classify", classify);
		mav.addObject("nowArray", DateUtil.getNowDate());
		mav.setViewName("hotlist/onlineRetailers");
		return mav;
	}
	
	@RequestMapping("topic")
	public ModelAndView topic(ModelAndView mav,Integer classify) {
		mav.addObject("classify", classify);
		mav.addObject("nowArray", DateUtil.getNowDate());
		mav.setViewName("hotlist/topic");
		return mav;
	}
	
	@RequestMapping(value = "listData")
	public@ResponseBody String listData(Integer page,String searchparam,String source_name,Integer classify,String topic) throws Exception {
		Integer searchType = 0;
		String times = null; 
		String timee = null; 
		String timetype = null; 
		timetype = "spider_time";
		List<String> listTime = DateUtil.beforeFiveHourToNowDate();
		times = listTime.get(0);
		timee = listTime.get(1);
		searchparam = "spider_time";
//		System.out.println("开始时间="+times);
//		System.out.println("结束时间="+timee);
//		System.out.println("来源="+source_name);
//		System.out.println(classify);
//		System.out.println("搜索框="+topic);
		
		String params = "page="+page+ "&topic="+URLEncoder.encode(topic, "utf-8")+ 
				"&searchparam="+searchparam+"&searchType="+searchType+
				"&source_name="+URLEncoder.encode(source_name, "utf-8")+
				"&classify=" + classify + "&size=30"+
				"&times="+times+
				"&timee="+timee+
				"&timetype="+timetype;
		String host= "hotnews/hotuniquelist";
//		System.out.println("params="+params);
		String sendPost = ElasticSearchUtil.sendPost(host, params);
		System.out.println(sendPost);
		return sendPost;
	}
	
}
