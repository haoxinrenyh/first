package com.stonedt.spider.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.stonedt.spider.param.UserParam;
import com.stonedt.spider.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.stonedt.spider.aop.AuthorizationInterceptor;
import com.stonedt.spider.aop.Token;
import com.stonedt.spider.entity.StonedtUser;
import com.stonedt.spider.service.IStonedtUserService;
import com.stonedt.spider.service.LandingUserLogService;
import com.stonedt.spider.util.MD5Util;
import com.stonedt.spider.util.RedisUtil;
import com.stonedt.spider.util.ResultUtil;

@RestController
@RequestMapping("/")
public class StonedtUserController extends BaseController {
	@Autowired
	private IStonedtUserService stonedtUserService;
	@Autowired
	private LandingUserLogService landingUserLogService;
	
	@Autowired
	private RedisUtil redisUtil;
	

	/**
	 * 登录验证
	 * @return
	 */
	@PostMapping("/login")
	public Object login(@RequestParam(value = "username", required = false, defaultValue = "")String username,
						@RequestParam(value = "password", required = false, defaultValue = "")String password ){
		if(username==null || username.equals("") || password==null || password.equals("") ){
			return error_int(500,"账号或者密码为空!","");
		}

		JSONObject result = new JSONObject();
		
		String md5Password = MD5Util.getMD5(password);
		StonedtUser cheakUser = stonedtUserService.cheakUser(username);
		System.out.println(cheakUser);
		if(cheakUser == null || cheakUser.equals("")) {
			result.put("code", 501);
			result.put("msg", "用户不存在");
		}else {
			if(cheakUser.getPassword().equals(md5Password) && cheakUser.getStatus()==1) {
				String username2 = cheakUser.getUsername();
				System.out.println(username2);
				String token = AuthorizationInterceptor.USER_KEY+MD5Util.MD5(cheakUser.getId().toString()+System.currentTimeMillis());
				redisUtil.set(token, JSONObject.toJSONString(cheakUser));
				if(cheakUser.getUser_power() == 1) {
					result.put("code", 200);
					result.put("msg", "管理员登陆成功");
					result.put("token", token);
					result.put("power",1);
				}else {
					result.put("code", 200);
					result.put("msg", "用户登陆成功");
					result.put("token", token);
					result.put("power",2);
				}
				landingUserLogService.save(cheakUser);
			}else if(cheakUser.getStatus()==2 && cheakUser.getPassword().equals(md5Password) ){
				result.put("code", 502);
				result.put("msg", "该账号已被停用");
			}else {
				result.put("code", 503);
				result.put("msg", "用户名密码错误");
			}
		}
		return result;
	}

	/**
	 * 安全退出
	 * @param response
	 * @param request
	 * @return
	 */
	@Token
	@RequestMapping(value="/exit")
	public Object exit(HttpServletResponse response,HttpServletRequest request){
		
		ResultUtil result = new ResultUtil();
		try {
			String userinfo = request.getAttribute(AuthorizationInterceptor.USER_INFO).toString();
			String token = getToken(request);
			redisUtil.deleteKey(token);
			return success_int(200,"退出成功!","");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return error_int(500,"退出异常!","");
	}

	/**
	 * 	查询所有用户
	 * @param pageNum
	 * @param request
	 * @return
	 */
	@Token
	@GetMapping(value="/user/list")
	public Object getUserAll(@RequestParam(value = "pageNum", defaultValue = "1" ,required = false)Integer pageNum,
							 @RequestParam(value = "pageSize", defaultValue = "10" ,required = false)Integer pageSize,
							 @RequestParam(value = "keyword", defaultValue = "" ,required = false)String  keyword,
							 HttpServletRequest request){
		try {
			PageHelper.startPage(pageNum, pageSize);
			String userinfo = request.getAttribute(AuthorizationInterceptor.USER_INFO).toString();
			if(userinfo!=null && !userinfo.equals("") && userinfo.indexOf("user_power")!=-1 && userinfo.indexOf("id")!=-1){
				JSONObject userJson = JSONObject.parseObject(userinfo);
				List<StonedtUser> userAllList = stonedtUserService.queryUserAll(userJson.getInteger("user_power"),userJson.getInteger("id"),keyword);
				PageInfo<StonedtUser> pageInfo = new PageInfo<StonedtUser>(userAllList);
				return success_int(200,"成功!",pageInfo);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return  error_int(500,"流程异常!","");
	}

	@Token
	@PostMapping("/user/info")
	public Object userInfo(HttpServletRequest request){
		String userinfo = request.getAttribute(AuthorizationInterceptor.USER_INFO).toString();
		if(userinfo!=null){
			JSONObject userJson = JSONObject.parseObject(userinfo);
			if(userJson!=null && userJson.getInteger("id")!=null){
				long time = userJson.getLong("create_time");
				String dateTime = "";
				if(time>0){
					dateTime = DateUtil.getJsonTime(time+"");
					userJson.put("dateTime",dateTime);
				}
				return success_int(200,"查询用户详情!",userJson);
			}
		}
		return error_int(500,"查询失败!","");
	}

	@Token
	@PostMapping("/user/changePassword")
	public Object changePassword(@RequestBody UserParam userParam, HttpServletRequest request){
		String userinfo = request.getAttribute(AuthorizationInterceptor.USER_INFO).toString();
		if(userinfo!=null && userParam!=null && userParam.getPassword()!=null && userParam.getNewPassword()!=null ){
			JSONObject userJson = JSONObject.parseObject(userinfo);
			if(userJson!=null && userJson.getInteger("id")!=null){
				int user_id = userJson.getInteger("id");
				String password = userParam.getPassword();
				String newPassword = userParam.getNewPassword();
				return stonedtUserService.changePassword(password,user_id,newPassword);
			}
		}
		return error_int(500,"修改失败!","");
	}

	/**
	 *   添加用户
	 * @return
	 */
	@Token
	@PostMapping(value="/user/regist")
	public Object addUser(@RequestBody UserParam userParam){
		try {
			if(!userParam.userRegist() ){
				String md5Password = MD5Util.getMD5(userParam.getPassword());
				StonedtUser user = new StonedtUser();
				user.setUsername(userParam.getUsername());
				user.setPassword(md5Password);
				user.setName(userParam.getName());
				user.setUser_power(userParam.getPower());
				user.setStatus(1);
				user.setCompany(userParam.getInstitutionName());
				int insertUser = stonedtUserService.insertUser(user);
				if(insertUser>0){
					return success_int(200,"添加成功!",insertUser);
				}else if(insertUser==-1){
					return success_int(501,"用户名存在!",insertUser);
				}else {
					return success_int(502,"添加失败!",insertUser);
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}

		return error_int(500,"提交的参数有误!",userParam.toString());
	}

	/**
	 * 修改用户状态
	 * @param request
	 * @return
	 */
	@Token
	@PostMapping(value="/user/update")
	public Object update(@RequestBody UserParam userParam,
							   HttpServletRequest request) {
		if(userParam!=null && userParam.getUsername()!=null && !userParam.getUsername().equals("")){
			int type = stonedtUserService.updateUser(userParam);
			if(type==1){
				return success_int(200,"修改成功!",type);
			}
		}
		return error_int(500,"参数格式有误!","");
	}

	/**
	 * 停用/启用
	 * @param request
	 * @return
	 */
	@Token
	@PostMapping(value="/user/state")
	public Object state(@RequestBody UserParam userParam,
							  HttpServletRequest request) {
		if(userParam!=null && userParam.getUsername()!=null && !userParam.getUsername().equals("") && userParam.getStatus()>=1 && userParam.getStatus()<=2){
			int type = stonedtUserService.updateUser(userParam);
			if(type==1){
				return success_int(200,"修改成功!",type);
			}
		}
		return error_int(500,"参数格式有误!","");
	}

//	/**
//	 * 修改用户状态
//	 * @param request
//	 * @return
//	 */
//	@Token
//	@PostMapping(value="/user/status")
//	public Integer modifyState(HttpServletRequest request) {
//		String username = request.getParameter("username");
//		Integer status = Integer.parseInt(request.getParameter("status"));
//		Integer modifyState = null;
//		if(username!=null && !"".equals(username)) {
//			if(status == 1) {
//				//修改用户账号状态
//				Integer statu = 2;
//				modifyState = stonedtUserService.modifyState(username,statu);
//			}else if(status == 2) {
//				//修改用户账号状态
//				Integer statu = 1;
//				modifyState = stonedtUserService.modifyState(username,statu);
//			}
//		}
//		return modifyState;
//	}

//
//	/**
//	 * 删除用户
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping(value="/delStonedtUser")
//	@ResponseBody
//	public Integer delStonedtUser(HttpServletRequest request) {
//		String username = request.getParameter("username");
//		Integer delStonedtUser = null;
//		if(username!=null && !"".equals(username)) {
//			delStonedtUser = stonedtUserService.delStonedtUser(username);
//		}
//		return delStonedtUser;
//	}
//
//	/**
//	 * 重置密码
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping(value="/resetPwd")
//	@ResponseBody
//	public Integer resetPwd(HttpServletRequest request) {
//		String username = request.getParameter("username");
//		Integer delStonedtUser = null;
//		if(username!=null && !"".equals(username)) {
//			String password = MD5Util.getMD5("123456");
//			delStonedtUser = stonedtUserService.resetPwd(username, password);
//		}
//		return delStonedtUser;
//	}
//
//	/**
//	 *   添加用户页面
//	 * @param mv
//	 * @return
//	 */
//	@RequestMapping(value="/register",produces = "text/plain;charset=utf-8")
//	public ModelAndView register(ModelAndView mv){
//		mv.setViewName("register");
//		return mv;
//
//	}
//
//	/**
//	 *   修改密码界面页面
//	 * @param mv
//	 * @return
//	 */
//	@RequestMapping(value="/toupdatePassword",produces = "text/plain;charset=utf-8")
//	public ModelAndView toupdatePassword(ModelAndView mv){
//		mv.setViewName("updatePassword");
//		return mv;
//
//	}
//
//	/**
//	 * @Description:确认密码是否正确
//	 * @return（展示方法参数和返回值）
//	 */
//	@RequestMapping(value="/checkPassword")
//	@ResponseBody
//	public ResultUtil checkPassword(HttpServletRequest request,
//			@RequestParam(value = "password")String password,
//			@RequestParam(value = "user_id")Integer user_id) {
//			ResultUtil resultUtil = stonedtUserService.checkPassword(password,user_id);
//		return resultUtil;
//	}
//
//	/**
//	 * @Description:确认密码是否正确
//	 * @return（展示方法参数和返回值）
//	 */
//	@RequestMapping(value="/repassword")
//	@ResponseBody
//	public ResultUtil repassword(HttpServletRequest request,
//			@RequestParam(value = "password")String password,
//			@RequestParam(value = "user_id")Integer user_id,
//			@RequestParam(value = "repassword")String repassword) {
//			ResultUtil resultUtil = stonedtUserService.repassword(password,user_id,repassword);
//		return resultUtil;
//	}
//
//
//	/**
//	 * @Description:确认用户是否存在
//	 * @param username
//	 * @return（展示方法参数和返回值）
//	 */
//	@RequestMapping(value="/ifUserExist")
//	@ResponseBody
//	public ResultUtil ifUserExist(HttpServletRequest request,
//			@RequestParam(value = "username")String username) {
//			ResultUtil resultUtil = stonedtUserService.ifUserExist(username);
//		return resultUtil;
//	}
//
//
//	/**
//	 *   官网添加用户
//	 * @return
//	 */
//	@RequestMapping(value="/registStonedt", produces = "application/json; charset=utf-8")
//	public @ResponseBody Object addStonedtUser(StonedtUser user,HttpServletRequest request){
//		String callback =request.getParameter("callbacka");
//		user.setUser_password("e10adc3949ba59abbe56e057f20f883e");
//		user.setUser_power(2);
//		user.setUser_status(2);
//		int insertUser = stonedtUserService.insertUser(user);
//		Map<String,Object> map = new HashMap<String,Object>();
//		if(0 != insertUser) {
//			map.put("msg", "1");
//		}else {
//			map.put("msg", "-1");
//		}
//
//		String jsonString = JSON.toJSONString(map);
//
//		return callback+ "(" + jsonString + ")";
//	}
//
//
//	/**
//	 * 登录页面
//	 * @param mv
//	 * @param response
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping(value="/",produces = "text/plain;charset=utf-8")
//	public ModelAndView admin(ModelAndView mv,HttpServletResponse response,HttpServletRequest request){
//
//		mv.setViewName("login");
//		return mv;
//
//	}
	
}
