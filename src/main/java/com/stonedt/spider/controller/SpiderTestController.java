package com.stonedt.spider.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

//import api.yqt.com.test.SpiderTest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSONObject;




@Controller
@RequestMapping("/spiderconfig")
public class SpiderTestController {

	@RequestMapping("/test")
	public ModelAndView testspider(ModelAndView mv,HttpServletRequest request) {
		int spiderid = Integer.parseInt(request.getParameter("spiderId"));
		JSONObject json  = new JSONObject();
		int vpnflag=0;
		int selenium_flag = Integer.parseInt(request.getParameter("killOrder2"));
		if(request.getParameter("killOrder3")!=null) {
			vpnflag = Integer.parseInt(request.getParameter("killOrder3"));
		}
		if(selenium_flag==1) {
			//使用selenium
		}else {
			json.put("seed_type", Integer.parseInt(request.getParameter("seedtype")));//任务类型
			 String seedurlconfig = request.getParameter("seedurlconfig");
			 seedurlconfig = seedurlconfig.replace(" ", "");
			 json.put("seed_url_config", seedurlconfig);//url配置
			 json.put("seed_thread_config", 1);//线程数
			 json.put("seed_list_url", request.getParameter("seedurllist"));//URL列表
			 json.put("seed_interval_config", 1000);//间隔时间
			 json.put("seed_origin_config", request.getParameter("seedoriginconfig"));//正文来源/	
			 json.put("seed_linkurl_config", request.getParameter("seedlinkurlconfig"));//url拼接
			 json.put("seed_origin_url_config", request.getParameter("seedoriginurlconfig"));//正文来源url配置
			 json.put("seed_author_config", request.getParameter("seedauthorconfig"));//作者配置
			 json.put("seed_author_url_config", request.getParameter("seedauthorurlconfig"));//作者url配置
			 json.put("seed_author_avatar_config", request.getParameter("seedauthoravatarconfig"));//作者头像配置
			 json.put("seed_spider_type", spiderid);
			 json.put("seed_page_rule_config", request.getParameter("seedpageruleconfig"));//配置分页规则
			 json.put("seed_list_request_param", request.getParameter("seedlistrequestparam"));//列表页面请求解析规则
			 json.put("seed_detail_request_param", request.getParameter("seeddetailrequestparam"));//详情页面请求解析规则
			if (spiderid==2) {
				 json.put("seed_title_config", request.getParameter("seedtitleconfig"));//标题
				 json.put("seed_text_config", request.getParameter("seedtextconfig"));//正文内容配置
				 json.put("seed_date_config", request.getParameter("seeddateconfig"));//正文日期提取
			}
		}
		List<Map<String, Object>> list=null;
		json.put("vpnflag",vpnflag);
		System.out.println(json);
		//list = SpiderTest.spiderhtml(json);
		mv.addObject("list", null);
		mv.setViewName("seed_test");
		return mv;

	}
	
	
//	@RequestMapping("/test1")
//	public ModelAndView testspider(ModelAndView mv,HttpServletRequest request) {
//		int spiderid = Integer.parseInt(request.getParameter("spiderId"));
//		JSONObject json  = new JSONObject();
//		int vpnflag=0;
//		int selenium_flag = Integer.parseInt(request.getParameter("killOrder2"));
//		if(request.getParameter("killOrder3")!=null) {
//			vpnflag = Integer.parseInt(request.getParameter("killOrder3"));
//		}
//		if(selenium_flag==1) {
//			//使用selenium
//		}else {
//			json.put("seed_type", Integer.parseInt(request.getParameter("seedtype")));//任务类型
//			 String seedurlconfig = request.getParameter("seedurlconfig");
//			 seedurlconfig = seedurlconfig.replace(" ", "");
//			 json.put("seed_url_config", seedurlconfig);//url配置
//			 json.put("seed_thread_config", 1);//线程数
//			 json.put("seed_list_url", request.getParameter("seedurllist"));//URL列表
//			 json.put("seed_interval_config", 1000);//间隔时间
//			 json.put("seed_origin_config", request.getParameter("seedoriginconfig"));//正文来源/	
//			 json.put("seed_linkurl_config", request.getParameter("seedlinkurlconfig"));//url拼接
//			 json.put("seed_origin_url_config", request.getParameter("seedoriginurlconfig"));//正文来源url配置
//			 json.put("seed_author_config", request.getParameter("seedauthorconfig"));//作者配置
//			 json.put("seed_author_url_config", request.getParameter("seedauthorurlconfig"));//作者url配置
//			 json.put("seed_author_avatar_config", request.getParameter("seedauthoravatarconfig"));//作者头像配置
//			 json.put("seed_spider_type", spiderid);
//			 json.put("seed_page_rule_config", request.getParameter("seedpageruleconfig"));//配置分页规则
//			 json.put("seed_list_request_param", request.getParameter("seedlistrequestparam"));//列表页面请求解析规则
//			 json.put("seed_detail_request_param", request.getParameter("seeddetailrequestparam"));//详情页面请求解析规则
//			if (spiderid==2) {
//				 json.put("seed_title_config", request.getParameter("seedtitleconfig"));//标题
//				 json.put("seed_text_config", request.getParameter("seedtextconfig"));//正文内容配置
//				 json.put("seed_date_config", request.getParameter("seeddateconfig"));//正文日期提取
//			}
//		}
//		List<Map<String, Object>> list=null;
//		json.put("vpnflag",vpnflag);
//		System.out.println(json);
//		list = SpiderTest.spiderhtml(json);
//		mv.addObject("list", list);
//		mv.setViewName("seed_test");
//		return mv;
//
//	}


}
