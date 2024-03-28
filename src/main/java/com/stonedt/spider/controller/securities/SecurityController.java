package com.stonedt.spider.controller.securities;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.stonedt.spider.util.DateUtil;
import com.stonedt.spider.util.ElasticSearchUtil;

@Controller
@RequestMapping("/security")
public class SecurityController {
	/***
	 * 上交所 财经资讯 深交所 证监会
	 * @param mav
	 * @param request
	 * @param type
	 * @return
	 * Colin
	 */
	@RequestMapping("/shanghaiStockExchange")
	public ModelAndView ShanghaiStockExchange(ModelAndView mav,HttpServletRequest request,
			@RequestParam(value = "type", defaultValue = "" ,required = false)String type) {
		mav.setViewName("securitiesCompany/financialinformation_list");
		if(type.equals("shanghaiStockExchange")) {
			mav.addObject("webName", "上交所");
		}else if(type.equals("financialinformation")) {
			mav.addObject("webName", "财经资讯");
		}else if(type.equals("shenZhenStockExchange")) {
			mav.addObject("webName", "深交所");
		}else if(type.equals("csrc")) {
			mav.addObject("webName", "证监会");
		}
		mav.addObject("nowArray", DateUtil.getNowDate());
		request.setAttribute("LEFT", type);
		return mav;
	}

	
	@RequestMapping(value="/getShanghaiStockExchange",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getShanghaiStockExchange(
			@RequestParam(value = "page", defaultValue = "1" ,required = false)Integer page,
			@RequestParam(value = "typeName", defaultValue = "" ,required = false)String typeName) {
		JSONObject json=new JSONObject();
		try {
			String response="";
			String param="";
			if(typeName.equals("shanghaiStockExchange")) {
				//上交所
				param="?&page="+page+"&size=10&otherseedid=23101&searchType=1";	
			}else if(typeName.equals("shenZhenStockExchange")) {
				//深交所
				param="?&page="+page+"&size=10&otherseedid=23104&searchType=1";	
			}else if(typeName.equals("financialinformation")) {
				//财经资讯
				param="?&page="+page+"&size=10&otherseedid=13,2467,2468,2406&searchType=1";	
			}else if(typeName.equals("csrc")) {
				//证监会
				param="?&page="+page+"&size=10&otherseedid=23102,23103&searchType=1";	
			}
			Integer count =0;
			Integer page_count =0;
			Integer size =0;
				response = ElasticSearchUtil.sendPost("yys/qbsearchcontent", param);
				json = JSONObject.parseObject(response);
				JSONArray news=json.getJSONArray("data");
				count=Integer.valueOf(json.getString("count"));
				page_count=Integer.valueOf(json.getString("page_count"));
				size=Integer.valueOf(json.getString("size"));
			json.put("news",news);
			json.put("count",count);
			json.put("page_count",page_count);
			json.put("page",page);
			json.put("size",size);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
}
