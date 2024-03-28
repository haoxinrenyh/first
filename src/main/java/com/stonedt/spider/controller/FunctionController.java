package com.stonedt.spider.controller;

import java.util.ArrayList;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.stonedt.spider.aop.Token;
import com.stonedt.spider.entity.Function;
import com.stonedt.spider.script.ScriptManager;
import com.stonedt.spider.service.FunctionService;
import com.stonedt.spider.util.MD5Util;
import com.stonedt.spider.util.ResultUtil;


@RestController
@RequestMapping("/function")
public class FunctionController {

	@Autowired
	private FunctionService functionService;
	//private static Logger logger = LoggerFactory.getLogger(FunctionService.class);

	@Token
	@RequestMapping("/list")
	public ResultUtil list( Integer page_num,Integer page_size) {
		ResultUtil resultUtil = new ResultUtil();
		try {
			PageHelper.startPage(page_num, page_size);
			ArrayList<Function> list = functionService.list();
			PageInfo<Function> pageInfo = new PageInfo<Function>(list);
			resultUtil.setStatus(200);
			resultUtil.setData(pageInfo);
		} catch (Exception e) {
			//logger.error("自定义函数列表出错", e);
			resultUtil.setStatus(500);
			resultUtil.setMsg(ExceptionUtils.getStackTrace(e));
		}
		return resultUtil;
	}

	@Token
	@RequestMapping("/save")
	public ResultUtil save(Function function) {
		//执行校验
		ResultUtil resultUtil = new ResultUtil();
		try {
			ScriptManager.validScript(function.getName(),function.getParameter(),function.getScript());
		} catch (Exception e1) {
			resultUtil.setStatus(500);
			resultUtil.setMsg(ExceptionUtils.getStackTrace(e1));
			return resultUtil;
		}
		try {
			if ("".equals(function.getId()) || null == function.getId()) {
				function.setId(MD5Util.MD5(function.getName()));
		        //格式化时间
				functionService.save(function);
			} else {
				functionService.update(function);
			}

		} catch (Exception e) {
			//logger.error("保存自定义函数出错", e);
			resultUtil.setStatus(500);
			resultUtil.setMsg(ExceptionUtils.getStackTrace(e));
			return resultUtil;
		}
		resultUtil.setStatus(200);
		return resultUtil;

	}
	@Token
	@RequestMapping("/get")
	public ResultUtil get(String id) {
		ResultUtil resultUtil = new ResultUtil();
		resultUtil.setStatus(200);
		resultUtil.setData(functionService.getById(id));
		return resultUtil;
	}

	/**
	 * 根据id删除函数
	 * 
	 * @param id
	 */
	@Token
	@RequestMapping("/remove")
	public ResultUtil remove(String id) {
		ResultUtil resultUtil = new ResultUtil();
		try {
			functionService.removeById(id);
			resultUtil.setStatus(200);
		} catch (Exception e) {
			//logger.error("删除自定义函数出错", e);
			resultUtil.setStatus(500);
			resultUtil.setMsg(ExceptionUtils.getStackTrace(e));
		}
		return resultUtil;
	}
}
