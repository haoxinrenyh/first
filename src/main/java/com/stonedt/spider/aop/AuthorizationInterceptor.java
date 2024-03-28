package com.stonedt.spider.aop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import com.alibaba.fastjson.JSONObject;
import com.stonedt.spider.util.RedisUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

	@Autowired
	RedisUtil redisUtil;

	public static final String USER_KEY = "USER_ID";
	public static final String USER_INFO = "USER_INFO";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 从header中获取token
		String token = request.getHeader("token");
		if (token == null) {
			response.setContentType("application/json; charset=UTF-8");
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("code", 1201);
			jsonObject.put("msg", "缺少token，拒绝访问");
			response.getWriter().print(jsonObject.toJSONString());
			return false;
		}

		// 查询token信息
		String user = redisUtil.getKey(token);
		if (user == null) {
			response.setContentType("application/json; charset=UTF-8");
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("code", 1201);
			jsonObject.put("msg", "token不正确，拒绝访问");
			response.getWriter().print(jsonObject.toJSONString());
			return false;
		}

		// token校验通过，将用户信息放在request中，供需要用user信息的接口里从token取数据
		request.setAttribute(USER_INFO, user);
		request.setAttribute("token", token);
		return true;
	}
}