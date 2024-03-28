
package com.stonedt.spider.controller.dict;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.stonedt.spider.serviceImpl.DictServiceImpl;
import com.stonedt.spider.util.CheckIkWord;

/**
* <p></p>
* <p>Title: DictController</p>  
* <p>Description: </p>  
* @author Mapeng 
* @date Apr 30, 2020  
*/
@Controller
@RequestMapping("/dict")
public class DictController {
	

	private static final Logger logger = Logger.getLogger(DictController.class);
	
	@Autowired
	private DictServiceImpl dictService;

	@RequestMapping("/hot")
	public ModelAndView hot(ModelAndView mv,@RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum) {
		PageHelper.startPage(pageNum, 30);
		List<Map<String, Object>> listHotWords = dictService.listHotWords();
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String,Object>>(listHotWords);
		mv.addObject("list", listHotWords);
		mv.addObject("pageInfo", pageInfo);
		mv.setViewName("hot_dict");
		return mv;
	}
	
	@RequestMapping("/stop")
	public ModelAndView stop(ModelAndView mv,@RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum) {
		PageHelper.startPage(pageNum, 30);
		List<Map<String, Object>> listStopWords = dictService.listStopWords();
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String,Object>>(listStopWords);
		mv.addObject("list", listStopWords);
		mv.addObject("pageInfo", pageInfo);
		mv.setViewName("stop_dict");
		return mv;
	}
	
	@RequestMapping("/saveHotWords")
	public@ResponseBody String saveHotWords(String word) {
		Map<String, Object> map = new HashMap<String, Object>();
		String[] split = word.split(",");
		List<String> dataList = new ArrayList<String>();
		for (int i = 0; i < split.length; i++) {
			try {
				if(!CheckIkWord.checkIkTokens(split[i])) {
					Integer saveHotWords = dictService.saveHotWords(split[i]);
				}else {
					dataList.add(split[i]);
				}
			} catch (Exception e) {
				dataList.add(split[i]);
				logger.error("保存异常：" + split[i] + " 已存在");
			}
		}
		map.put("code", 200);
		map.put("msg", "成功");
		map.put("data", dataList);
		return JSON.toJSONString(map);
	}
	
	@RequestMapping("/saveStopWords")
	public@ResponseBody String saveStopWords(String word) {
		Map<String, Object> map = new HashMap<String, Object>();
		String[] split = word.split(",");
		List<String> dataList = new ArrayList<String>();
		for (int i = 0; i < split.length; i++) {
			try {
				if(!CheckIkWord.checkIkTokens(split[i])) {
					Integer saveStopWords = dictService.saveStopWords(split[i]);
				}else {
					dataList.add(split[i]);
				}
			} catch (Exception e) {
				dataList.add(split[i]);
				logger.error("保存异常：" + split[i] + " 已存在");
			}
		}
		map.put("code", 200);
		map.put("msg", "成功");
		map.put("data", dataList);
		return JSON.toJSONString(map);
	}
	
	@RequestMapping("/delHotWords")
	public@ResponseBody String delHotWords(String word) {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer delHotWords = dictService.delHotWords(word);
		if(delHotWords != 0) {
			map.put("code", 200);
			map.put("msg", "成功");
		}else {
			map.put("code", 500);
			map.put("msg", "失败");
		}
		return JSON.toJSONString(map);
	}
	
	@RequestMapping("/delStopWords")
	public@ResponseBody String delStopWords(String word) {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer delStopWords = dictService.delStopWords(word);
		if(delStopWords != 0) {
			map.put("code", 200);
			map.put("msg", "成功");
		}else {
			map.put("code", 500);
			map.put("msg", "失败");
		}
		return JSON.toJSONString(map);
	}
	
	
}
