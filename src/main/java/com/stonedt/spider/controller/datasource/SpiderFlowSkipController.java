//package com.stonedt.spider.controller.datasource;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
///**
// * @author 丁祥珂
// * @version V1.0
// * @date 2021/4/15 10:33
// * @Copyright
// *  负责spiderFlow相关跳转页面
// */
//@Controller
//@RequestMapping("/spiderFlowSkip")
//public class SpiderFlowSkipController {
//
//    /**
//     * @author dxk
//     * @date 2021/4/15 10:36
//       加载图片页面
//     * @return
//     * @throws
//     * @since
//    */
//    @RequestMapping(value="/comment",produces = "text/plain;charset=utf-8")
//    public ModelAndView comment(ModelAndView mv, HttpServletResponse response, HttpServletRequest request){
//        mv.setViewName("spiderflow/templates/comment");
//        return mv;
//    }
//
//    @RequestMapping(value="/edge",produces = "text/plain;charset=utf-8")
//    public ModelAndView edge(ModelAndView mv, HttpServletResponse response, HttpServletRequest request){
//        mv.setViewName("spiderflow/templates/edge");
//        return mv;
//    }
//
//    @RequestMapping(value="/forkJoin",produces = "text/plain;charset=utf-8")
//    public ModelAndView forkJoin(ModelAndView mv, HttpServletResponse response, HttpServletRequest request){
//        mv.setViewName("spiderflow/templates/forkJoin");
//        return mv;
//    }
//
//    @RequestMapping(value="/function",produces = "text/plain;charset=utf-8")
//    public ModelAndView function(ModelAndView mv, HttpServletResponse response, HttpServletRequest request){
//        mv.setViewName("spiderflow/templates/function");
//        return mv;
//    }
//
//    @RequestMapping(value="/loop",produces = "text/plain;charset=utf-8")
//    public ModelAndView loop(ModelAndView mv, HttpServletResponse response, HttpServletRequest request){
//        mv.setViewName("spiderflow/templates/loop");
//        return mv;
//    }
//
//    @RequestMapping(value="/output",produces = "text/plain;charset=utf-8")
//    public ModelAndView output(ModelAndView mv, HttpServletResponse response, HttpServletRequest request){
//        mv.setViewName("spiderflow/templates/output");
//        return mv;
//    }
//
//    @RequestMapping(value="/process",produces = "text/plain;charset=utf-8")
//    public ModelAndView process(ModelAndView mv, HttpServletResponse response, HttpServletRequest request){
//        mv.setViewName("spiderflow/templates/process");
//        return mv;
//    }
//
//    @RequestMapping(value="/redisSend",produces = "text/plain;charset=utf-8")
//    public ModelAndView redisSend(ModelAndView mv, HttpServletResponse response, HttpServletRequest request){
//        mv.setViewName("spiderflow/templates/redisSend");
//        return mv;
//    }
//
//    @RequestMapping(value="/request",produces = "text/plain;charset=utf-8")
//    public ModelAndView request(ModelAndView mv, HttpServletResponse response, HttpServletRequest request){
//        mv.setViewName("spiderflow/templates/request");
//        return mv;
//    }
//
//    @RequestMapping(value="/root",produces = "text/plain;charset=utf-8")
//    public ModelAndView root(ModelAndView mv, HttpServletResponse response, HttpServletRequest request){
//        mv.setViewName("spiderflow/templates/root");
//        return mv;
//    }
//
//    @RequestMapping(value="/start",produces = "text/plain;charset=utf-8")
//    public ModelAndView start(ModelAndView mv, HttpServletResponse response, HttpServletRequest request){
//        mv.setViewName("spiderflow/templates/start");
//        return mv;
//    }
//
//    @RequestMapping(value="/variable",produces = "text/plain;charset=utf-8")
//    public ModelAndView variable(ModelAndView mv, HttpServletResponse response, HttpServletRequest request){
//        mv.setViewName("spiderflow/templates/variable");
//        return mv;
//    }
//
//    @RequestMapping(value="/selenium",produces = "text/plain;charset=utf-8")
//    public ModelAndView selenium(ModelAndView mv, HttpServletResponse response, HttpServletRequest request){
//        mv.setViewName("spiderflow/templates/selenium");
//        return mv;
//    }
//
//    @RequestMapping(value="/executeSql",produces = "text/plain;charset=utf-8")
//    public ModelAndView executeSql(ModelAndView mv, HttpServletResponse response, HttpServletRequest request){
//        mv.setViewName("spiderflow/templates/executeSql");
//        return mv;
//    }
//    /**
//     * @author dxk
//     * @date 2021/4/15 10:47
//       打开cron编辑页面
//     * @return
//     * @throws
//     * @since
//    */
//    @RequestMapping(value="/cron",produces = "text/plain;charset=utf-8")
//    public ModelAndView cron(ModelAndView mv){
//        mv.setViewName("spiderflow/editCron");
//        return mv;
//    }
//
//}
