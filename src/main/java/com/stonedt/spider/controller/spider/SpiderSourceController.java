package com.stonedt.spider.controller.spider;

import com.stonedt.spider.aop.AuthorizationInterceptor;
import com.stonedt.spider.aop.Token;
import com.stonedt.spider.controller.BaseController;
import com.stonedt.spider.entity.Account;
import com.stonedt.spider.entity.AgentPool;
import com.stonedt.spider.entity.Vps;
import com.stonedt.spider.service.SpiderSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/spiderSource")
public class SpiderSourceController extends BaseController {

    @Autowired
    private SpiderSourceService spiderSourceService;

    @Token
    @GetMapping("/vps/page")
    public Object vps_page(@RequestParam(value = "pageNum",required = false,defaultValue = "1") int pageNum,
                           @RequestParam(value = "pageSize",required = false,defaultValue = "10") int pageSize,
                           @RequestParam(value = "keyword",required = false) String keyword){
        try{
            if(pageNum>0 ){
                int beginIndex = (pageNum-1)*pageSize;
                String limit = "limit "+beginIndex+","+pageSize;
                return success_int(200,"查询成功!",spiderSourceService.findVpsPage(limit,keyword));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return error_int(501,"查询失败, 请检查参数是否有误!","");
    }

    @Token
    @PostMapping("/vps/save")
    public Object vps_page(@RequestBody Vps vps,HttpServletRequest request){
        try{
            int userId = getUserId(request);
            if(vps!=null && vps.getIp()!=null && vps.getPort()!=null && vps.getPassword()!=null && userId>0){
                vps.setCreate_user_id(userId);
                vps.setUpdate_user_id(userId);
                int type = spiderSourceService.saveVps(vps);
                if(type>0){
                    return success_int(200,"保存成功!",type);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return error_int(501,"保存失败, 请检查参数是否有误!","");
    }


    @Token
    @GetMapping("/account/page")
    public Object account_page(@RequestParam(value = "pageNum",required = false,defaultValue = "1") int pageNum,
                               @RequestParam(value = "pageSize",required = false,defaultValue = "10") int pageSize){
        try{
            if(pageNum>0 ){
                int beginIndex = (pageNum-1)*pageSize;
                String limit = "limit "+beginIndex+","+pageSize;
                return success_int(200,"查询成功!",spiderSourceService.findAccountPage(limit));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return error_int(501,"查询失败, 请检查参数是否有误!","");
    }

    @Token
    @PostMapping("/account/save")
    public Object account_save(@RequestBody Account account,
                               HttpServletRequest request){
        try{
            String userinfo = request.getAttribute(AuthorizationInterceptor.USER_INFO).toString();
            int userId = getUserId(request);
            if(account!=null && account.getUsername()!=null && account.getPassword()!=null && account.getSite()!=null && userId>0 ){
                account.setCreate_user_id(userId);
                account.setUpdate_user_id(userId);
                int type = spiderSourceService.saveAccount(account);
                if(type>0){
                    return success_int(200,"保存成功!",type);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return error_int(501,"保存失败, 请检查参数是否有误!","");
    }

    @Token
    @PostMapping("/account/update")
    public Object account_update(@RequestBody Account account,
                                 HttpServletRequest request){
        try{
            int userId = getUserId(request);
            if(account!=null&& account.getId()>0 && userId>0 ){
                account.setUpdate_user_id(userId);
                int type = spiderSourceService.updateAccount(account);
                if(type>0){
                    return success_int(200,"更新成功!",type);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return error_int(501,"更新失败, 请检查参数是否有误!","");
    }

    @Token
    @GetMapping("/account/remove")
    public Object account_remove(@RequestParam(value = "id",required = false,defaultValue = "0") int id,
                                 HttpServletRequest request){
        try{
            int userId = getUserId(request);
            if(id>0 && userId>0){
                Account account = new Account();
                account.setId(id);
                account.setIs_del(1);
                account.setUpdate_user_id(userId);
                int type = spiderSourceService.removeAccount(account);
                if(type>0){
                    return success_int(200,"删除成功!",type);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return error_int(501,"删除失败, 请检查参数是否有误!","");
    }


    @Token
    @GetMapping("/agentPool/page")
    public Object agentPool_page(@RequestParam(value = "pageNum",required = false,defaultValue = "1") int pageNum,
                               @RequestParam(value = "pageSize",required = false,defaultValue = "10") int pageSize){
        try{
            if(pageNum>0 ){
                int beginIndex = (pageNum-1)*pageSize;
                String limit = "limit "+beginIndex+","+pageSize;
                return success_int(200,"查询成功!",spiderSourceService.findAgentPoolPage(limit));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return error_int(501,"查询失败, 请检查参数是否有误!","");
    }

    @Token
    @PostMapping("/agentPool/save")
    public Object agentPool_save(@RequestBody AgentPool agentPool,
                                 HttpServletRequest request){
        try{
            int userId = getUserId(request);
            if(agentPool!=null && agentPool.getService_name()!=null && agentPool.getTerm()!=null && agentPool.getUrl()!=null && userId>0 ){
                agentPool.setCreate_user_id(userId);
                agentPool.setUpdate_user_id(userId);
                int type = spiderSourceService.saveAgentPool(agentPool);
                if(type>0){
                    return success_int(200,"保存成功!",type);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return error_int(501,"保存失败, 请检查参数是否有误!","");
    }

    @Token
    @PostMapping("/agentPool/update")
    public Object agentPool_update(@RequestBody AgentPool agentPool,
                                   HttpServletRequest request){
        try{
            int userId = getUserId(request);
            if(agentPool!=null && agentPool.getId()>0 && userId>0 ){
                agentPool.setUpdate_user_id(userId);
                int type = spiderSourceService.updateAgentPool(agentPool);
                if(type>0){
                    return success_int(200,"更新成功!",type);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return error_int(501,"更新失败, 请检查参数是否有误!","");
    }

    @Token
    @GetMapping("/agentPool/remove")
    public Object agentPool_remove(@RequestParam(value = "id",required = false,defaultValue = "0") int id,
                                   HttpServletRequest request){
        try{
            int userId = getUserId(request);
            if(id>0 && userId>0){
                AgentPool agentPool = new AgentPool();
                agentPool.setId(id);
                agentPool.setIs_del(1);
                agentPool.setUpdate_user_id(userId);
                int type = spiderSourceService.removeAgentPool(agentPool);
                if(type>0){
                    return success_int(200,"删除成功!",type);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return error_int(501,"删除失败, 请检查参数是否有误!","");
    }






}
