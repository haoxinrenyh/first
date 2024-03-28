package com.stonedt.spider.service;

import com.stonedt.spider.entity.Account;
import com.stonedt.spider.entity.AgentPool;
import com.stonedt.spider.entity.Vps;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SpiderSourceService {

    Map<String,Object> findVpsPage(String limit , String keyword);

    int saveVps( Vps vps);



    Map<String, Object> findAccountPage(String limit );

    int removeAccount(Account account);

    int saveAccount(Account account);

    int updateAccount(Account account);



    Map<String, Object> findAgentPoolPage(String limit );

    int removeAgentPool(AgentPool agentPool);

    int saveAgentPool(AgentPool agent_pool);

    int updateAgentPool(AgentPool agent_pool);

}
