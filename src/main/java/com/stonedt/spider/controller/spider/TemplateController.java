package com.stonedt.spider.controller.spider;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.stonedt.spider.aop.AuthorizationInterceptor;
import com.stonedt.spider.aop.Token;
import com.stonedt.spider.controller.BaseController;
import com.stonedt.spider.entity.SpiderFlowTemplate;
import com.stonedt.spider.util.HttpApiUtil;
import com.stonedt.spider.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/template")
public class TemplateController extends BaseController {
//
//    @Value("${api.admin.templates}")
//    private String adminTemplatesUrl;
//
//    public String adminToken = "admin-token";
//
//    @Value("${api.admin.username}")
//    private String adminUsername;
//
//    @Value("${api.admin.password}")
//    private String adminPassword;
//
//    @Value("${api.admin.login}")
//    private String adminLoginUrl;
//
//    @Autowired
//    private RedisUtil redisUtil;
//
//    @GetMapping("/page")
//    public Object template_page(@RequestParam(value = "pageNum",required = false,defaultValue = "1")int pageNum,
//                                @RequestParam(value = "pageSize",required = false,defaultValue = "10")int pageSize,
//                                @RequestParam(value = "environment",required = false)Integer environment,
//                                @RequestParam(value = "status",required = false)Integer status){
//        try {
//            String admin_token = getAdminToken();
//            if( admin_token!=null && !"".equals(admin_token) ){
//                Map<String,String> headers = new HashMap<>();
//                headers.put(adminToken, admin_token);
//
//                Map<String,Object> param = new HashMap<>();
//                param.put("pageNum", pageNum);
//                param.put("pageSize", pageSize);
//                if(environment!=null){
//                    param.put("environment", environment);
//                }
//                if(status!=null){
//                    param.put("status", status);
//                }
//
//
//                String body = HttpApiUtil.get(adminTemplatesUrl+"/page",param,headers);
//                return JSONObject.parseObject(body);
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return error_int(500,"查询失败, 请检查参数是否有误！","");
//    }
//
//
//    @GetMapping("/list")
//    public Object template_list(){
//        try {
//            String admin_token = getAdminToken();
//            if( admin_token!=null && !"".equals(admin_token) ){
//                Map<String,String> headers = new HashMap<>();
//                headers.put(adminToken, admin_token);
//
//                Map<String,Object> param = new HashMap<>();
//
//                String body = HttpApiUtil.get(adminTemplatesUrl+"/list",param,headers);
//                return JSONObject.parseObject(body);
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return error_int(500,"查询失败, 请检查参数是否有误！","");
//    }
//
//
//    @GetMapping("/info")
//    public Object template_info(@RequestParam(value = "id",required = false,defaultValue = "0")int id){
//        try {
//            if(id>0 ){
//                String admin_token = getAdminToken();
//                if( admin_token!=null && !"".equals(admin_token) ){
//                    Map<String,String> headers = new HashMap<>();
//                    headers.put(adminToken, admin_token);
//
//                    Map<String,Object> param = new HashMap<>();
//                    param.put("id",id);
//
//                    String body = HttpApiUtil.get(adminTemplatesUrl+"/info",param,headers);
//                    return JSONObject.parseObject(body);
//                }
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return error_int(500,"查询失败, 请检查参数是否有误！","");
//    }
//
//
//    //@GetMapping("/open")
//    public Object template_open(@RequestParam(value = "id",required = false,defaultValue = "0")int id,
//                                @RequestParam(value = "status",required = false)Integer status,
//                                HttpServletRequest request){
//        try {
//            String userInfo = request.getAttribute(AuthorizationInterceptor.USER_INFO).toString();
//            int userId = getUserId(userInfo);
//            String userName = getUserName(userInfo);
//            if(id>0 && status!=null && userId>0 && userName!=null){
//                //int type = templateService.openTemplate(id,status,userId,userName);
//                //if(type>0){
//                //    return success_int(200,"更新成功!",type);
//                //}
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return error_int(500,"更新失败, 请检查参数是否有误！","");
//    }
//
//
//    //@PostMapping("/save")
//    public Object template_save(@RequestBody SpiderFlowTemplate template,
//                                HttpServletRequest request){
//        try {
//            String userInfo = request.getAttribute(AuthorizationInterceptor.USER_INFO).toString();
//            int userId = getUserId(userInfo);
//            String userName = getUserName(userInfo);
//            if(template!=null  && userId>0 && userName!=null ){
//                template.setUpdate_user_id(userId);
//                template.setUpdate_user_name(userName);
//                int type = 0;
//                if(template.getId()==0 && template.getType_id()!=null){
//                    if( template.getTemplate_name()!=null){
//                        template.setCreate_user_id(userId);
//                        template.setCreate_user_name(userName);
//                        //type = templateService.saveTemplate(template);
//                        type=0;
//                    }
//                }else {
//                    //type = templateService.updateTemplate(template);
//                    type=0;
//                }
//                if(type>0 ) {
//                    return success_int(200,"保存成功!",type);
//                }
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return error_int(500,"保存失败, 请检查参数是否有误！","");
//    }
//
//    public String getAdminToken(){
//        String admin_token = null;
//
//        try {
//            String login_body = "";
//            if(redisUtil.getKey("admin_api_token")!=null){
//                login_body = redisUtil.getKey("admin_api_token");
//                login_body = login_body.replace("\\","");
//            }
//            if(login_body.equals("")  ){
//                Map<String,Object> loginParam = new HashMap<>();
//                loginParam.put("username", adminUsername);
//                loginParam.put("password", adminPassword);
//                login_body = HttpUtil.post(adminLoginUrl,loginParam);
//            }
//
//            if(!login_body.equals("")){
//                JSONObject login_body_json = JSONObject.parseObject(login_body);
//                if(login_body_json!=null && login_body_json.getInteger("code")!=null && login_body_json.getInteger("code")==200){
//                    admin_token = login_body_json.getString(adminToken);
//                    redisUtil.set("admin_api_token", login_body, 3);
//                }
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//            return null;
//        }
//        return admin_token;
//    }

}
