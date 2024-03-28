package com.stonedt.spider.serviceImpl;

import com.stonedt.spider.dao.SpiderSourceDao;
import com.stonedt.spider.entity.Account;
import com.stonedt.spider.entity.AgentPool;
import com.stonedt.spider.entity.Vps;
import com.stonedt.spider.service.SpiderSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SpiderSourceServiceImpl implements SpiderSourceService {

    @Autowired
    private SpiderSourceDao spiderSourceDao;

    @Override
    public Map<String, Object> findVpsPage(String limit, String keyword) {
        Map<String, Object> result = new HashMap<>();
        int total = 0;
        List<Vps> list = new ArrayList<>();
        try {
            list = spiderSourceDao.findVpsPage(limit, keyword);
            total = spiderSourceDao.findVpsCount(keyword);
        }catch (Exception e){
            e.printStackTrace();
        }
        result.put("list", list );
        result.put("total", total );
        return result;
    }

    @Override
    public int saveVps(Vps vps) {
        return spiderSourceDao.saveVps(vps);
    }





    @Override
    public Map<String, Object> findAccountPage(String limit) {
        Map<String, Object> result = new HashMap<>();
        int total = 0;
        List<Account> list = new ArrayList<>();
        try {
            list = spiderSourceDao.findAccountPage(limit);
            total = spiderSourceDao.findAccountCount();
        }catch (Exception e){
            e.printStackTrace();
        }
        result.put("list", list );
        result.put("total", total );
        return result;
    }

    @Override
    public int removeAccount(Account account) {
        try {
            int type = spiderSourceDao.updateAccount(account);
            if(type>0){
                return type;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int saveAccount(Account account) {
        try {
            if(account!=null ){
                int type = spiderSourceDao.saveAccount(account);
                if(type>0){
                    return type;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int updateAccount(Account account) {
        try {
            if(account!=null && account.getId()>0){
                int type = spiderSourceDao.updateAccount(account);
                if(type>0){
                    return type;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }




    @Override
    public Map<String, Object> findAgentPoolPage(String limit) {
        Map<String, Object> result = new HashMap<>();
        int total = 0;
        List<AgentPool> list = new ArrayList<>();
        try {
            list = spiderSourceDao.findAgentPoolPage(limit);
            total = spiderSourceDao.findAgentPoolCount();
        }catch (Exception e){
            e.printStackTrace();
        }
        result.put("list", list );
        result.put("total", total );
        return result;
    }

    @Override
    public int removeAgentPool(AgentPool agentPool) {
        try {

            int type = spiderSourceDao.updateAgentPool(agentPool);
            if(type>0){
                return type;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int saveAgentPool(AgentPool agent_pool) {
        try {
            if(agent_pool!=null){
                int type = spiderSourceDao.saveAgentPool(agent_pool);
                if(type>0){
                    return type;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int updateAgentPool(AgentPool agent_pool) {
        try {
            if(agent_pool!=null && agent_pool.getId()>0){
                int type = spiderSourceDao.updateAgentPool(agent_pool);
                if(type>0){
                    return type;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
}
