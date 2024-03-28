package com.stonedt.spider.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.stonedt.spider.entity.CustomerEntity;

@Mapper
public interface CustomerDao {

	List<CustomerEntity> getList();

}
