package com.stonedt.spider.controller;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.stonedt.spider.aop.AuthorizationInterceptor;
import com.stonedt.spider.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class BaseController {

    public Map<String,Object> success_int(int code, String msg, Object result){
        Map<String,Object> successMap = new HashMap<>();
        successMap.put("code", code);
        successMap.put("msg", msg);
        successMap.put("result", result);
        return successMap;
    }

    public Map<String,Object> error_int(int code, String msg, Object result){
        Map<String,Object> errorMap = new HashMap<>();
        errorMap.put("code", code);
        errorMap.put("msg", msg);
        errorMap.put("result", result);
        return errorMap;
    }

    public String getToken(HttpServletRequest request){
        String token = null;
        try {
            token = request.getAttribute("token").toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return token;
    }

    public int getUserId(HttpServletRequest request){
        int userId=0;
        try {
            String userinfo = request.getAttribute(AuthorizationInterceptor.USER_INFO).toString();
            if(userinfo!=null) {
                JSONObject userJson = JSONObject.parseObject(userinfo);
                if (userJson != null && userJson.getInteger("id") != null) {
                    userId=userJson.getInteger("id");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return userId;
    }


    public int getUserId(String userInfo){
        int userId = 0;
        try {
            if(userInfo!=null) {
                JSONObject userJson = JSONObject.parseObject(userInfo);
                if (userJson != null && userJson.getInteger("id") != null) {
                    userId=userJson.getInteger("id");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return userId;
    }


    public String getUserName(String userInfo){
        String userName = null;
        try {
            if(userInfo!=null) {
                JSONObject userJson = JSONObject.parseObject(userInfo);
                if (userJson != null && userJson.getString("username") != null  && !userJson.getString("username").equals("") ) {
                    userName=userJson.getString("username");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return userName;
    }




}
