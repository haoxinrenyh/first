package com.stonedt.spider.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/Common")
public class CommonModuleController {
	//通用组件
	@RequestMapping(value="/CommonModule",produces = "text/plain;charset=utf-8")
	public ModelAndView CommonModule(ModelAndView mv,HttpServletResponse response,HttpServletRequest request){
		mv.setViewName("/components/CommonModule");
		return mv;
	}
	//通用组件详情页面
	@RequestMapping(value="/CommonModuleDetail",produces = "text/plain;charset=utf-8")
	public ModelAndView CommonModuleDetail(ModelAndView mv,HttpServletResponse response,HttpServletRequest request){
		mv.setViewName("/components/CommonModuleDetail");
		return mv;
	}
}
