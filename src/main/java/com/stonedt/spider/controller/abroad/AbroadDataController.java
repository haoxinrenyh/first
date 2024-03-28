package com.stonedt.spider.controller.abroad;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/abroad")
public class AbroadDataController {
	 /**
     * 	跳转列表页面
     */
    @RequestMapping("toAbroadPage")
    public ModelAndView toInformationPage(ModelAndView mv, @RequestParam("classify") String classify, HttpServletRequest request) {
        mv.addObject("siteName","境外数据");
    	mv.setViewName("abroadList");
        if(classify.equals("hostile")) {
        	mv.addObject("webName", "敌对网站");
        	 mv.addObject("classify", 2);
        }else if(classify.equals("mainstream")) {
        	mv.addObject("webName", "主流网站");
        	 mv.addObject("classify", 6);
        }else if(classify.equals("HongKongTaiwan")) {
        	mv.addObject("webName", "港台媒体");
        	 mv.addObject("classify", 7);
        }else if(classify.equals("socialcontact")) {
        	mv.addObject("webName", "APP客户端");
        	 mv.addObject("classify", 2);
        }
        request.setAttribute("LEFT", classify);
        return mv;
    }
}
