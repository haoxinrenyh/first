package com.stonedt.spider.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/spiderFlowSkip")
public class SpiderFlowSkipController {

    /**
     * @author dxk
     * @date 2021/4/15 10:36
    加载图片页面
     * @return
     * @throws
     * @since
     */
    @RequestMapping(value="/comment",produces = "text/plain;charset=utf-8")
    public String comment(HttpServletResponse response, HttpServletRequest request){
//        mv.setViewName("spiderflow/templates/comment");
//        return mv;
        return "comment";
    }

    @RequestMapping(value="/edge",produces = "text/plain;charset=utf-8")
    public String edge(HttpServletResponse response, HttpServletRequest request){
        //mv.setViewName("spiderflow/templates/edge");
        //return mv;
        return "edge";
    }

    @RequestMapping(value="/forkJoin",produces = "text/plain;charset=utf-8")
    public String forkJoin(HttpServletResponse response, HttpServletRequest request){
//        mv.setViewName("spiderflow/templates/forkJoin");
//        return mv;
        return "forkJoin";
    }

    @RequestMapping(value="/function",produces = "text/plain;charset=utf-8")
    public String function(HttpServletResponse response, HttpServletRequest request){
//        mv.setViewName("spiderflow/templates/function");
//        return mv;
        return "function";
    }

    @RequestMapping(value="/loop",produces = "text/plain;charset=utf-8")
    public String loop(HttpServletResponse response, HttpServletRequest request){
//        mv.setViewName("spiderflow/templates/loop");
//        return mv;
        return "loop";
    }

    @RequestMapping(value="/output",produces = "text/plain;charset=utf-8")
    public String output(HttpServletResponse response, HttpServletRequest request){
//        mv.setViewName("spiderflow/templates/output");
//        return mv;1
        return "output";
    }

    @RequestMapping(value="/process",produces = "text/plain;charset=utf-8")
    public String process(HttpServletResponse response, HttpServletRequest request){
//        mv.setViewName("spiderflow/templates/process");
//        return mv;1
        return "process";
    }

    @RequestMapping(value="/redisSend",produces = "text/plain;charset=utf-8")
    public String redisSend(HttpServletResponse response, HttpServletRequest request){
//        mv.setViewName("spiderflow/templates/redisSend");
//        return mv;
        return "redisSend";
    }

    @RequestMapping(value="/request",produces = "text/plain;charset=utf-8")
    public String request(HttpServletResponse response, HttpServletRequest request){
        //mv.setViewName("spiderflow/templates/request");
        //return mv;
        return "request";
    }

    @RequestMapping(value="/root",produces = "text/plain;charset=utf-8")
    public String root(HttpServletResponse response, HttpServletRequest request){
//        mv.setViewName("spiderflow/templates/root");
//        return mv;
        return "root";
    }

    @RequestMapping(value="/start",produces = "text/plain;charset=utf-8")
    public String start(HttpServletResponse response, HttpServletRequest request){
        //mv.setViewName("spiderflow/templates/start");
        //return mv;
        return "start";
    }

    @RequestMapping(value="/variable",produces = "text/plain;charset=utf-8")
    public String variable( HttpServletResponse response, HttpServletRequest request){
        //mv.setViewName("spiderflow/templates/variable");
        //return mv;
        return "variable";
    }

    @RequestMapping(value="/selenium",produces = "text/plain;charset=utf-8")
    public String selenium( HttpServletResponse response, HttpServletRequest request){
        //mv.setViewName("spiderflow/templates/selenium");
        //return mv;1
        return "selenium";
    }

    @RequestMapping(value="/executeSql",produces = "text/plain;charset=utf-8")
    public String executeSql( HttpServletResponse response, HttpServletRequest request){
//        mv.setViewName("spiderflow/templates/executeSql");
//        return mv;1
        return "executeSql";
    }
    /**
     * @author dxk
     * @date 2021/4/15 10:47
    打开cron编辑页面
     * @return
     * @throws
     * @since
     */
    @RequestMapping(value="/cron",produces = "text/plain;charset=utf-8")
//    @ResponseBody
    public String cron( HttpServletResponse response, HttpServletRequest request){
        return "editCron";
//        mv.setViewName("spiderflow/editCron");
//        return mv;
    }

}
