package com.stonedt.spider.serviceImpl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stonedt.spider.dao.CustomerDao;
import com.stonedt.spider.entity.CustomerEntity;
import com.stonedt.spider.service.CustomerService;
@Service
public class CustomerServiceImpl implements CustomerService{
	
	@Autowired
	private CustomerDao customerDao;
	

	@Override
	public List<CustomerEntity> getList() {
		// TODO Auto-generated method stub
		return customerDao.getList();
	}


}
