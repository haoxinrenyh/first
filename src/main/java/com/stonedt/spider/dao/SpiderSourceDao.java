package com.stonedt.spider.dao;

import com.stonedt.spider.entity.Account;
import com.stonedt.spider.entity.AgentPool;
import com.stonedt.spider.entity.Vps;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SpiderSourceDao {

    List<Vps> findVpsPage( @Param("limit")String limit , @Param("keyword")String keyword);

    int findVpsCount( @Param("keyword")String keyword);

    int saveVps( @Param("vps")Vps vps);



    List<Account> findAccountPage( @Param("limit")String limit );

    int findAccountCount();

    int saveAccount(@Param("account")Account account);

    int updateAccount(@Param("account")Account account);

    List<AgentPool> findAgentPoolPage(@Param("limit")String limit );

    int findAgentPoolCount();

    int saveAgentPool(@Param("agent_pool")AgentPool agent_pool);

    int updateAgentPool(@Param("agent_pool")AgentPool agent_pool);


}
