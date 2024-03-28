package com.stonedt.spider.controller.locallife;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

/**
 * 本地生活
 * @author xp006
 *
 */
@Controller
@RequestMapping("/localLife")
public class LocalLifeController {
	// 居/村委会
	@RequestMapping(value = "/villageCommittee", method = RequestMethod.GET)
	public ModelAndView villageCommittee(ModelAndView mv) {
		mv.setViewName("localLife/villageCommittee");
		return mv;
	}
	
	// 居/村委会列表
	@RequestMapping(value = "/villageCommitteeList", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public String villageCommitteeList(String pageNum) {
		String response = "";
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, String>> list = new ArrayList<>();
		try {
			String param = "{\"timefield\":\"spider_time\",\"times\":\"\",\"size\":\"25\",\"and\":[{\"field\":\"org_type\", \"keyword\":\"居/村委会\"}],\"index\":\"stonedt_local_life\",\"page\":"+pageNum+",\"type\":\"infor\",\"timee\":\"\"}";
	    	JSONObject parseObject = JSONObject.parseObject(param);
	    	System.out.println(parseObject);
	    	RestTemplate client=new RestTemplate();
	    	ResponseEntity<String> responseEntity = client.postForEntity(
	    			"http://dx2.stonedt.com:7120/commonsearch/superdatalist", 
	    			parseObject, 
	    			String.class);
	    	response=responseEntity.getBody();
//			    	response = getJsonHtml("http://dx2.stonedt.com:7120/commonsearch/superdatalist",parseObject);
			System.out.println(response);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("list", list);
			result.put("totalData", 0);
			result.put("totalPage", 1);
		}
		if (StringUtils.isNotBlank(response)) {
			JSONObject parseObject = JSON.parseObject(response);
			String totalData = parseObject.getString("count");
			String totalPage = parseObject.getString("page_count");
			JSONArray jsonArray = parseObject.getJSONArray("data");
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				JSONObject jsonObject2 = jsonObject.getJSONObject("_source");
				Map<String, String> params = JSONObject.parseObject(jsonObject2.toJSONString(), new TypeReference<Map<String, String>>(){});
				list.add(params);
			}
			int totalCount=Integer.parseInt(totalData);
			if (totalCount >= 5000 && totalCount < 7500) {
				totalPage = "200";
			}else if(totalCount >= 7500 && totalCount < 10000) {
				totalPage = "300";
			}else if (totalCount >= 10000) {
				totalPage = "400";
			}
			if (StringUtils.isBlank(totalPage)) {
				totalPage = "1";
			}else if ("0".equals(totalPage)) {
				totalPage = "1";
			}
			result.put("list", list);
			result.put("totalData", totalData);
			result.put("totalPage", totalPage);
		}else {
			result.put("list", list);
			result.put("totalData", 0);
			result.put("totalPage", 1);
		}
		return JSON.toJSONString(result);
	}
	
	// 银行
	@RequestMapping(value = "/bank", method = RequestMethod.GET)
	public ModelAndView bank(ModelAndView mv) {
		mv.setViewName("localLife/bank");
		return mv;
	}
	
	// 银行列表
	@RequestMapping(value = "/bankList", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public String bankList(String pageNum) {
		String response = "";
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, String>> list = new ArrayList<>();
		try {
			String param = "{\"timefield\":\"spider_time\",\"times\":\"\",\"size\":\"25\",\"and\":[{\"field\":\"org_type\", \"keyword\":\"银行\"}],\"index\":\"stonedt_local_life\",\"page\":"+pageNum+",\"type\":\"infor\",\"timee\":\"\"}";
	    	JSONObject parseObject = JSONObject.parseObject(param);
	    	System.out.println(parseObject);
	    	RestTemplate client=new RestTemplate();
	    	ResponseEntity<String> responseEntity = client.postForEntity(
	    			"http://dx2.stonedt.com:7120/commonsearch/superdatalist", 
	    			parseObject, 
	    			String.class);
	    	response=responseEntity.getBody();
//				    	response = getJsonHtml("http://dx2.stonedt.com:7120/commonsearch/superdatalist",parseObject);
			System.out.println(response);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("list", list);
			result.put("totalData", 0);
			result.put("totalPage", 1);
		}
		if (StringUtils.isNotBlank(response)) {
			JSONObject parseObject = JSON.parseObject(response);
			String totalData = parseObject.getString("count");
			String totalPage = parseObject.getString("page_count");
			JSONArray jsonArray = parseObject.getJSONArray("data");
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				JSONObject jsonObject2 = jsonObject.getJSONObject("_source");
				Map<String, String> params = JSONObject.parseObject(jsonObject2.toJSONString(), new TypeReference<Map<String, String>>(){});
				list.add(params);
			}
			int totalCount=Integer.parseInt(totalData);
			if (totalCount >= 5000 && totalCount < 7500) {
				totalPage = "200";
			}else if(totalCount >= 7500 && totalCount < 10000) {
				totalPage = "300";
			}else if (totalCount >= 10000) {
				totalPage = "400";
			}
			if (StringUtils.isBlank(totalPage)) {
				totalPage = "1";
			}else if ("0".equals(totalPage)) {
				totalPage = "1";
			}
			result.put("list", list);
			result.put("totalData", totalData);
			result.put("totalPage", totalPage);
		}else {
			result.put("list", list);
			result.put("totalData", 0);
			result.put("totalPage", 1);
		}
		return JSON.toJSONString(result);
	}

	// 街道办/乡镇政府
	@RequestMapping(value = "/townshipGovernment", method = RequestMethod.GET)
	public ModelAndView townshipGovernment(ModelAndView mv) {
		mv.setViewName("localLife/townshipGovernment");
		return mv;
	}
	
	// 街道办/乡镇政府列表
	@RequestMapping(value = "/townshipGovernmentList", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public String townshipGovernmentList(String pageNum) {
		String response = "";
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, String>> list = new ArrayList<>();
		try {
			String param = "{\"timefield\":\"spider_time\",\"times\":\"\",\"size\":\"25\",\"and\":[{\"field\":\"org_type\", \"keyword\":\"街道办/乡镇政府\"}],\"index\":\"stonedt_local_life\",\"page\":"+pageNum+",\"type\":\"infor\",\"timee\":\"\"}";
	    	JSONObject parseObject = JSONObject.parseObject(param);
	    	System.out.println(parseObject);
	    	RestTemplate client=new RestTemplate();
	    	ResponseEntity<String> responseEntity = client.postForEntity(
	    			"http://dx2.stonedt.com:7120/commonsearch/superdatalist", 
	    			parseObject, 
	    			String.class);
	    	response=responseEntity.getBody();
//				    	response = getJsonHtml("http://dx2.stonedt.com:7120/commonsearch/superdatalist",parseObject);
			System.out.println(response);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("list", list);
			result.put("totalData", 0);
			result.put("totalPage", 1);
		}
		if (StringUtils.isNotBlank(response)) {
			JSONObject parseObject = JSON.parseObject(response);
			String totalData = parseObject.getString("count");
			String totalPage = parseObject.getString("page_count");
			JSONArray jsonArray = parseObject.getJSONArray("data");
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				JSONObject jsonObject2 = jsonObject.getJSONObject("_source");
				Map<String, String> params = JSONObject.parseObject(jsonObject2.toJSONString(), new TypeReference<Map<String, String>>(){});
				list.add(params);
			}
			int totalCount=Integer.parseInt(totalData);
			if (totalCount >= 5000 && totalCount < 7500) {
				totalPage = "200";
			}else if(totalCount >= 7500 && totalCount < 10000) {
				totalPage = "300";
			}else if (totalCount >= 10000) {
				totalPage = "400";
			}
			if (StringUtils.isBlank(totalPage)) {
				totalPage = "1";
			}else if ("0".equals(totalPage)) {
				totalPage = "1";
			}
			result.put("list", list);
			result.put("totalData", totalData);
			result.put("totalPage", totalPage);
		}else {
			result.put("list", list);
			result.put("totalData", 0);
			result.put("totalPage", 1);
		}
		return JSON.toJSONString(result);
	}

	// 卫生所
	@RequestMapping(value = "/clinic", method = RequestMethod.GET)
	public ModelAndView clinic(ModelAndView mv) {
		mv.setViewName("localLife/clinic");
		return mv;
	}
	
	// 卫生所列表
	@RequestMapping(value = "/clinicList", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public String clinicList(String pageNum) {
		String response = "";
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, String>> list = new ArrayList<>();
		try {
			String param = "{\"timefield\":\"spider_time\",\"times\":\"\",\"size\":\"25\",\"and\":[{\"field\":\"org_type\", \"keyword\":\"卫生所\"}],\"index\":\"stonedt_local_life\",\"page\":"+pageNum+",\"type\":\"infor\",\"timee\":\"\"}";
	    	JSONObject parseObject = JSONObject.parseObject(param);
	    	System.out.println(parseObject);
	    	RestTemplate client=new RestTemplate();
	    	ResponseEntity<String> responseEntity = client.postForEntity(
	    			"http://dx2.stonedt.com:7120/commonsearch/superdatalist", 
	    			parseObject, 
	    			String.class);
	    	response=responseEntity.getBody();
//				    	response = getJsonHtml("http://dx2.stonedt.com:7120/commonsearch/superdatalist",parseObject);
			System.out.println(response);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("list", list);
			result.put("totalData", 0);
			result.put("totalPage", 1);
		}
		if (StringUtils.isNotBlank(response)) {
			JSONObject parseObject = JSON.parseObject(response);
			String totalData = parseObject.getString("count");
			String totalPage = parseObject.getString("page_count");
			JSONArray jsonArray = parseObject.getJSONArray("data");
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				JSONObject jsonObject2 = jsonObject.getJSONObject("_source");
				Map<String, String> params = JSONObject.parseObject(jsonObject2.toJSONString(), new TypeReference<Map<String, String>>(){});
				list.add(params);
			}
			int totalCount=Integer.parseInt(totalData);
			if (totalCount >= 5000 && totalCount < 7500) {
				totalPage = "200";
			}else if(totalCount >= 7500 && totalCount < 10000) {
				totalPage = "300";
			}else if (totalCount >= 10000) {
				totalPage = "400";
			}
			if (StringUtils.isBlank(totalPage)) {
				totalPage = "1";
			}else if ("0".equals(totalPage)) {
				totalPage = "1";
			}
			result.put("list", list);
			result.put("totalData", totalData);
			result.put("totalPage", totalPage);
		}else {
			result.put("list", list);
			result.put("totalData", 0);
			result.put("totalPage", 1);
		}
		return JSON.toJSONString(result);
	}
	
	// 派出所
	@RequestMapping(value = "/policeStation", method = RequestMethod.GET)
	public ModelAndView policeStation(ModelAndView mv) {
		mv.setViewName("localLife/policeStation");
		return mv;
	}
	
	// 派出所列表
	@RequestMapping(value = "/policeStationList", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public String policeStationList(String pageNum) {
		String response = "";
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, String>> list = new ArrayList<>();
		try {
			String param = "{\"timefield\":\"spider_time\",\"times\":\"\",\"size\":\"25\",\"and\":[{\"field\":\"org_type\", \"keyword\":\"派出所\"}],\"index\":\"stonedt_local_life\",\"page\":"+pageNum+",\"type\":\"infor\",\"timee\":\"\"}";
	    	JSONObject parseObject = JSONObject.parseObject(param);
	    	System.out.println(parseObject);
	    	RestTemplate client=new RestTemplate();
	    	ResponseEntity<String> responseEntity = client.postForEntity(
	    			"http://dx2.stonedt.com:7120/commonsearch/superdatalist", 
	    			parseObject, 
	    			String.class);
	    	response=responseEntity.getBody();
//				    	response = getJsonHtml("http://dx2.stonedt.com:7120/commonsearch/superdatalist",parseObject);
			System.out.println(response);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("list", list);
			result.put("totalData", 0);
			result.put("totalPage", 1);
		}
		if (StringUtils.isNotBlank(response)) {
			JSONObject parseObject = JSON.parseObject(response);
			String totalData = parseObject.getString("count");
			String totalPage = parseObject.getString("page_count");
			JSONArray jsonArray = parseObject.getJSONArray("data");
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				JSONObject jsonObject2 = jsonObject.getJSONObject("_source");
				Map<String, String> params = JSONObject.parseObject(jsonObject2.toJSONString(), new TypeReference<Map<String, String>>(){});
				list.add(params);
			}
			int totalCount=Integer.parseInt(totalData);
			if (totalCount >= 5000 && totalCount < 7500) {
				totalPage = "200";
			}else if(totalCount >= 7500 && totalCount < 10000) {
				totalPage = "300";
			}else if (totalCount >= 10000) {
				totalPage = "400";
			}
			if (StringUtils.isBlank(totalPage)) {
				totalPage = "1";
			}else if ("0".equals(totalPage)) {
				totalPage = "1";
			}
			result.put("list", list);
			result.put("totalData", totalData);
			result.put("totalPage", totalPage);
		}else {
			result.put("list", list);
			result.put("totalData", 0);
			result.put("totalPage", 1);
		}
		return JSON.toJSONString(result);
	}
	
	// 交管网点
	@RequestMapping(value = "/trafficControl", method = RequestMethod.GET)
	public ModelAndView trafficControl(ModelAndView mv) {
		mv.setViewName("localLife/trafficControl");
		return mv;
	}
	
	// 交管网点列表
	@RequestMapping(value = "/trafficControlList", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public String trafficControlList(String pageNum) {
		String response = "";
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, String>> list = new ArrayList<>();
		try {
			String param = "{\"timefield\":\"spider_time\",\"times\":\"\",\"size\":\"25\",\"and\":[{\"field\":\"org_type\", \"keyword\":\"交管网点\"}],\"index\":\"stonedt_local_life\",\"page\":"+pageNum+",\"type\":\"infor\",\"timee\":\"\"}";
	    	JSONObject parseObject = JSONObject.parseObject(param);
	    	System.out.println(parseObject);
	    	RestTemplate client=new RestTemplate();
	    	ResponseEntity<String> responseEntity = client.postForEntity(
	    			"http://dx2.stonedt.com:7120/commonsearch/superdatalist", 
	    			parseObject, 
	    			String.class);
	    	response=responseEntity.getBody();
//				    	response = getJsonHtml("http://dx2.stonedt.com:7120/commonsearch/superdatalist",parseObject);
			System.out.println(response);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("list", list);
			result.put("totalData", 0);
			result.put("totalPage", 1);
		}
		if (StringUtils.isNotBlank(response)) {
			JSONObject parseObject = JSON.parseObject(response);
			String totalData = parseObject.getString("count");
			String totalPage = parseObject.getString("page_count");
			JSONArray jsonArray = parseObject.getJSONArray("data");
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				JSONObject jsonObject2 = jsonObject.getJSONObject("_source");
				Map<String, String> params = JSONObject.parseObject(jsonObject2.toJSONString(), new TypeReference<Map<String, String>>(){});
				list.add(params);
			}
			int totalCount=Integer.parseInt(totalData);
			if (totalCount >= 5000 && totalCount < 7500) {
				totalPage = "200";
			}else if(totalCount >= 7500 && totalCount < 10000) {
				totalPage = "300";
			}else if (totalCount >= 10000) {
				totalPage = "400";
			}
			if (StringUtils.isBlank(totalPage)) {
				totalPage = "1";
			}else if ("0".equals(totalPage)) {
				totalPage = "1";
			}
			result.put("list", list);
			result.put("totalData", totalData);
			result.put("totalPage", totalPage);
		}else {
			result.put("list", list);
			result.put("totalData", 0);
			result.put("totalPage", 1);
		}
		return JSON.toJSONString(result);
	}
	
	// 足疗按摩
	@RequestMapping(value = "/pedicure", method = RequestMethod.GET)
	public ModelAndView pedicure(ModelAndView mv) {
		mv.setViewName("localLife/pedicure");
		return mv;
	}
	
	// 足疗按摩列表
	@RequestMapping(value = "/pedicureList", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public String pedicureList(String pageNum) {
		String response = "";
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, String>> list = new ArrayList<>();
		try {
			String param = "{\"timefield\":\"spider_time\",\"times\":\"\",\"size\":\"25\",\"and\":[{\"field\":\"org_type\", \"keyword\":\"足疗按摩\"}],\"index\":\"stonedt_local_life\",\"page\":"+pageNum+",\"type\":\"infor\",\"timee\":\"\"}";
	    	JSONObject parseObject = JSONObject.parseObject(param);
	    	System.out.println(parseObject);
	    	RestTemplate client=new RestTemplate();
	    	ResponseEntity<String> responseEntity = client.postForEntity(
	    			"http://dx2.stonedt.com:7120/commonsearch/superdatalist", 
	    			parseObject, 
	    			String.class);
	    	response=responseEntity.getBody();
//				    	response = getJsonHtml("http://dx2.stonedt.com:7120/commonsearch/superdatalist",parseObject);
			System.out.println(response);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("list", list);
			result.put("totalData", 0);
			result.put("totalPage", 1);
		}
		if (StringUtils.isNotBlank(response)) {
			JSONObject parseObject = JSON.parseObject(response);
			String totalData = parseObject.getString("count");
			String totalPage = parseObject.getString("page_count");
			JSONArray jsonArray = parseObject.getJSONArray("data");
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				JSONObject jsonObject2 = jsonObject.getJSONObject("_source");
				Map<String, String> params = JSONObject.parseObject(jsonObject2.toJSONString(), new TypeReference<Map<String, String>>(){});
				list.add(params);
			}
			int totalCount=Integer.parseInt(totalData);
			if (totalCount >= 5000 && totalCount < 7500) {
				totalPage = "200";
			}else if(totalCount >= 7500 && totalCount < 10000) {
				totalPage = "300";
			}else if (totalCount >= 10000) {
				totalPage = "400";
			}
			if (StringUtils.isBlank(totalPage)) {
				totalPage = "1";
			}else if ("0".equals(totalPage)) {
				totalPage = "1";
			}
			result.put("list", list);
			result.put("totalData", totalData);
			result.put("totalPage", totalPage);
		}else {
			result.put("list", list);
			result.put("totalData", 0);
			result.put("totalPage", 1);
		}
		return JSON.toJSONString(result);
	}
	
	// 司法所
	@RequestMapping(value = "/officeOfJustice", method = RequestMethod.GET)
	public ModelAndView officeOfJustice(ModelAndView mv) {
		mv.setViewName("localLife/officeOfJustice");
		return mv;
	}
	
	// 司法所列表
	@RequestMapping(value = "/officeOfJusticeList", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public String officeOfJusticeList(String pageNum) {
		String response = "";
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, String>> list = new ArrayList<>();
		try {
			String param = "{\"timefield\":\"spider_time\",\"times\":\"\",\"size\":\"25\",\"and\":[{\"field\":\"org_type\", \"keyword\":\"司法所\"}],\"index\":\"stonedt_local_life\",\"page\":"+pageNum+",\"type\":\"infor\",\"timee\":\"\"}";
	    	JSONObject parseObject = JSONObject.parseObject(param);
	    	System.out.println(parseObject);
	    	RestTemplate client=new RestTemplate();
	    	ResponseEntity<String> responseEntity = client.postForEntity(
	    			"http://dx2.stonedt.com:7120/commonsearch/superdatalist", 
	    			parseObject, 
	    			String.class);
	    	response=responseEntity.getBody();
//				    	response = getJsonHtml("http://dx2.stonedt.com:7120/commonsearch/superdatalist",parseObject);
			System.out.println(response);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("list", list);
			result.put("totalData", 0);
			result.put("totalPage", 1);
		}
		if (StringUtils.isNotBlank(response)) {
			JSONObject parseObject = JSON.parseObject(response);
			String totalData = parseObject.getString("count");
			String totalPage = parseObject.getString("page_count");
			JSONArray jsonArray = parseObject.getJSONArray("data");
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				JSONObject jsonObject2 = jsonObject.getJSONObject("_source");
				Map<String, String> params = JSONObject.parseObject(jsonObject2.toJSONString(), new TypeReference<Map<String, String>>(){});
				list.add(params);
			}
			int totalCount=Integer.parseInt(totalData);
			if (totalCount >= 5000 && totalCount < 7500) {
				totalPage = "200";
			}else if(totalCount >= 7500 && totalCount < 10000) {
				totalPage = "300";
			}else if (totalCount >= 10000) {
				totalPage = "400";
			}
			if (StringUtils.isBlank(totalPage)) {
				totalPage = "1";
			}else if ("0".equals(totalPage)) {
				totalPage = "1";
			}
			result.put("list", list);
			result.put("totalData", totalData);
			result.put("totalPage", totalPage);
		}else {
			result.put("list", list);
			result.put("totalData", 0);
			result.put("totalPage", 1);
		}
		return JSON.toJSONString(result);
	}
	
	// 为民服务中心
	@RequestMapping(value = "/serviceCenter", method = RequestMethod.GET)
	public ModelAndView serviceCenter(ModelAndView mv) {
		mv.setViewName("localLife/serviceCenter");
		return mv;
	}
	
	// 为民服务中心列表
	@RequestMapping(value = "/serviceCenterList", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public String serviceCenterList(String pageNum) {
		String response = "";
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, String>> list = new ArrayList<>();
		try {
			String param = "{\"timefield\":\"spider_time\",\"times\":\"\",\"size\":\"25\",\"and\":[{\"field\":\"org_type\", \"keyword\":\"为民服务中心\"}],\"index\":\"stonedt_local_life\",\"page\":"+pageNum+",\"type\":\"infor\",\"timee\":\"\"}";
	    	JSONObject parseObject = JSONObject.parseObject(param);
	    	System.out.println(parseObject);
	    	RestTemplate client=new RestTemplate();
	    	ResponseEntity<String> responseEntity = client.postForEntity(
	    			"http://dx2.stonedt.com:7120/commonsearch/superdatalist", 
	    			parseObject, 
	    			String.class);
	    	response=responseEntity.getBody();
//				    	response = getJsonHtml("http://dx2.stonedt.com:7120/commonsearch/superdatalist",parseObject);
			System.out.println(response);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("list", list);
			result.put("totalData", 0);
			result.put("totalPage", 1);
		}
		if (StringUtils.isNotBlank(response)) {
			JSONObject parseObject = JSON.parseObject(response);
			String totalData = parseObject.getString("count");
			String totalPage = parseObject.getString("page_count");
			JSONArray jsonArray = parseObject.getJSONArray("data");
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				JSONObject jsonObject2 = jsonObject.getJSONObject("_source");
				Map<String, String> params = JSONObject.parseObject(jsonObject2.toJSONString(), new TypeReference<Map<String, String>>(){});
				list.add(params);
			}
			int totalCount=Integer.parseInt(totalData);
			if (totalCount >= 5000 && totalCount < 7500) {
				totalPage = "200";
			}else if(totalCount >= 7500 && totalCount < 10000) {
				totalPage = "300";
			}else if (totalCount >= 10000) {
				totalPage = "400";
			}
			if (StringUtils.isBlank(totalPage)) {
				totalPage = "1";
			}else if ("0".equals(totalPage)) {
				totalPage = "1";
			}
			result.put("list", list);
			result.put("totalData", totalData);
			result.put("totalPage", totalPage);
		}else {
			result.put("list", list);
			result.put("totalData", 0);
			result.put("totalPage", 1);
		}
		return JSON.toJSONString(result);
	}
	
	// 家具家居
	@RequestMapping(value = "/furniture", method = RequestMethod.GET)
	public ModelAndView furniture(ModelAndView mv) {
		mv.setViewName("localLife/furniture");
		return mv;
	}
	
	// 家具家居列表
	@RequestMapping(value = "/furnitureList", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public String furnitureList(String pageNum) {
		String response = "";
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, String>> list = new ArrayList<>();
		try {
			String param = "{\"timefield\":\"spider_time\",\"times\":\"\",\"size\":\"25\",\"and\":[{\"field\":\"org_type\", \"keyword\":\"家具家居\"}],\"index\":\"stonedt_local_life\",\"page\":"+pageNum+",\"type\":\"infor\",\"timee\":\"\"}";
	    	JSONObject parseObject = JSONObject.parseObject(param);
	    	System.out.println(parseObject);
	    	RestTemplate client=new RestTemplate();
	    	ResponseEntity<String> responseEntity = client.postForEntity(
	    			"http://dx2.stonedt.com:7120/commonsearch/superdatalist", 
	    			parseObject, 
	    			String.class);
	    	response=responseEntity.getBody();
//				    	response = getJsonHtml("http://dx2.stonedt.com:7120/commonsearch/superdatalist",parseObject);
			System.out.println(response);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("list", list);
			result.put("totalData", 0);
			result.put("totalPage", 1);
		}
		if (StringUtils.isNotBlank(response)) {
			JSONObject parseObject = JSON.parseObject(response);
			String totalData = parseObject.getString("count");
			String totalPage = parseObject.getString("page_count");
			JSONArray jsonArray = parseObject.getJSONArray("data");
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				JSONObject jsonObject2 = jsonObject.getJSONObject("_source");
				Map<String, String> params = JSONObject.parseObject(jsonObject2.toJSONString(), new TypeReference<Map<String, String>>(){});
				list.add(params);
			}
			int totalCount=Integer.parseInt(totalData);
			if (totalCount >= 5000 && totalCount < 7500) {
				totalPage = "200";
			}else if(totalCount >= 7500 && totalCount < 10000) {
				totalPage = "300";
			}else if (totalCount >= 10000) {
				totalPage = "400";
			}
			if (StringUtils.isBlank(totalPage)) {
				totalPage = "1";
			}else if ("0".equals(totalPage)) {
				totalPage = "1";
			}
			result.put("list", list);
			result.put("totalData", totalData);
			result.put("totalPage", totalPage);
		}else {
			result.put("list", list);
			result.put("totalData", 0);
			result.put("totalPage", 1);
		}
		return JSON.toJSONString(result);
	}
	
	// 茶馆
	@RequestMapping(value = "/teahouse", method = RequestMethod.GET)
	public ModelAndView teahouse(ModelAndView mv) {
		mv.setViewName("localLife/teahouse");
		return mv;
	}
	
	// 茶馆列表
	@RequestMapping(value = "/teahouseList", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public String teahouseList(String pageNum) {
		String response = "";
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, String>> list = new ArrayList<>();
		try {
			String param = "{\"timefield\":\"spider_time\",\"times\":\"\",\"size\":\"25\",\"and\":[{\"field\":\"org_type\", \"keyword\":\"茶馆\"}],\"index\":\"stonedt_local_life\",\"page\":"+pageNum+",\"type\":\"infor\",\"timee\":\"\"}";
	    	JSONObject parseObject = JSONObject.parseObject(param);
	    	System.out.println(parseObject);
	    	RestTemplate client=new RestTemplate();
	    	ResponseEntity<String> responseEntity = client.postForEntity(
	    			"http://dx2.stonedt.com:7120/commonsearch/superdatalist", 
	    			parseObject, 
	    			String.class);
	    	response=responseEntity.getBody();
//				    	response = getJsonHtml("http://dx2.stonedt.com:7120/commonsearch/superdatalist",parseObject);
			System.out.println(response);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("list", list);
			result.put("totalData", 0);
			result.put("totalPage", 1);
		}
		if (StringUtils.isNotBlank(response)) {
			JSONObject parseObject = JSON.parseObject(response);
			String totalData = parseObject.getString("count");
			String totalPage = parseObject.getString("page_count");
			JSONArray jsonArray = parseObject.getJSONArray("data");
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				JSONObject jsonObject2 = jsonObject.getJSONObject("_source");
				Map<String, String> params = JSONObject.parseObject(jsonObject2.toJSONString(), new TypeReference<Map<String, String>>(){});
				list.add(params);
			}
			int totalCount=Integer.parseInt(totalData);
			if (totalCount >= 5000 && totalCount < 7500) {
				totalPage = "200";
			}else if(totalCount >= 7500 && totalCount < 10000) {
				totalPage = "300";
			}else if (totalCount >= 10000) {
				totalPage = "400";
			}
			if (StringUtils.isBlank(totalPage)) {
				totalPage = "1";
			}else if ("0".equals(totalPage)) {
				totalPage = "1";
			}
			result.put("list", list);
			result.put("totalData", totalData);
			result.put("totalPage", totalPage);
		}else {
			result.put("list", list);
			result.put("totalData", 0);
			result.put("totalPage", 1);
		}
		return JSON.toJSONString(result);
	}
	
	// 街道办
	@RequestMapping(value = "/subdistrictOffice", method = RequestMethod.GET)
	public ModelAndView subdistrictOffice(ModelAndView mv) {
		mv.setViewName("localLife/subdistrictOffice");
		return mv;
	}
	
	// 街道办列表
	@RequestMapping(value = "/subdistrictOfficeList", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public String subdistrictOfficeList(String pageNum) {
		String response = "";
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, String>> list = new ArrayList<>();
		try {
			String param = "{\"timefield\":\"spider_time\",\"times\":\"\",\"size\":\"25\",\"and\":[{\"field\":\"org_type.keyword\", \"keyword\":\"街道办\"}],\"index\":\"stonedt_local_life\",\"page\":"+pageNum+",\"type\":\"infor\",\"timee\":\"\"}";
	    	JSONObject parseObject = JSONObject.parseObject(param);
	    	System.out.println(parseObject);
	    	RestTemplate client=new RestTemplate();
	    	ResponseEntity<String> responseEntity = client.postForEntity(
	    			"http://dx2.stonedt.com:7120/commonsearch/superdatalist", 
	    			parseObject, 
	    			String.class);
	    	response=responseEntity.getBody();
//				    	response = getJsonHtml("http://dx2.stonedt.com:7120/commonsearch/superdatalist",parseObject);
			System.out.println(response);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("list", list);
			result.put("totalData", 0);
			result.put("totalPage", 1);
		}
		if (StringUtils.isNotBlank(response)) {
			JSONObject parseObject = JSON.parseObject(response);
			String totalData = parseObject.getString("count");
			String totalPage = parseObject.getString("page_count");
			JSONArray jsonArray = parseObject.getJSONArray("data");
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				JSONObject jsonObject2 = jsonObject.getJSONObject("_source");
				Map<String, String> params = JSONObject.parseObject(jsonObject2.toJSONString(), new TypeReference<Map<String, String>>(){});
				list.add(params);
			}
			int totalCount=Integer.parseInt(totalData);
			if (totalCount >= 5000 && totalCount < 7500) {
				totalPage = "200";
			}else if(totalCount >= 7500 && totalCount < 10000) {
				totalPage = "300";
			}else if (totalCount >= 10000) {
				totalPage = "400";
			}
			if (StringUtils.isBlank(totalPage)) {
				totalPage = "1";
			}else if ("0".equals(totalPage)) {
				totalPage = "1";
			}
			result.put("list", list);
			result.put("totalData", totalData);
			result.put("totalPage", totalPage);
		}else {
			result.put("list", list);
			result.put("totalData", 0);
			result.put("totalPage", 1);
		}
		return JSON.toJSONString(result);
	}
	
	// 移动通信
	@RequestMapping(value = "/mobileCommunication", method = RequestMethod.GET)
	public ModelAndView mobileCommunication(ModelAndView mv) {
		mv.setViewName("localLife/mobileCommunication");
		return mv;
	}
	
	// 移动通信列表
	@RequestMapping(value = "/mobileCommunicationList", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public String mobileCommunicationList(String pageNum) {
		String response = "";
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, String>> list = new ArrayList<>();
		try {
			String param = "{\"timefield\":\"spider_time\",\"times\":\"\",\"size\":\"25\",\"and\":[{\"field\":\"org_type\", \"keyword\":\"移动通信\"}],\"index\":\"stonedt_local_life\",\"page\":"+pageNum+",\"type\":\"infor\",\"timee\":\"\"}";
	    	JSONObject parseObject = JSONObject.parseObject(param);
	    	System.out.println(parseObject);
	    	RestTemplate client=new RestTemplate();
	    	ResponseEntity<String> responseEntity = client.postForEntity(
	    			"http://dx2.stonedt.com:7120/commonsearch/superdatalist", 
	    			parseObject, 
	    			String.class);
	    	response=responseEntity.getBody();
//				    	response = getJsonHtml("http://dx2.stonedt.com:7120/commonsearch/superdatalist",parseObject);
			System.out.println(response);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("list", list);
			result.put("totalData", 0);
			result.put("totalPage", 1);
		}
		if (StringUtils.isNotBlank(response)) {
			JSONObject parseObject = JSON.parseObject(response);
			String totalData = parseObject.getString("count");
			String totalPage = parseObject.getString("page_count");
			JSONArray jsonArray = parseObject.getJSONArray("data");
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				JSONObject jsonObject2 = jsonObject.getJSONObject("_source");
				Map<String, String> params = JSONObject.parseObject(jsonObject2.toJSONString(), new TypeReference<Map<String, String>>(){});
				list.add(params);
			}
			int totalCount=Integer.parseInt(totalData);
			if (totalCount >= 5000 && totalCount < 7500) {
				totalPage = "200";
			}else if(totalCount >= 7500 && totalCount < 10000) {
				totalPage = "300";
			}else if (totalCount >= 10000) {
				totalPage = "400";
			}
			if (StringUtils.isBlank(totalPage)) {
				totalPage = "1";
			}else if ("0".equals(totalPage)) {
				totalPage = "1";
			}
			result.put("list", list);
			result.put("totalData", totalData);
			result.put("totalPage", totalPage);
		}else {
			result.put("list", list);
			result.put("totalData", 0);
			result.put("totalPage", 1);
		}
		return JSON.toJSONString(result);
	}
	
	// KTV
	@RequestMapping(value = "/ktv", method = RequestMethod.GET)
	public ModelAndView ktv(ModelAndView mv) {
		mv.setViewName("localLife/ktv");
		return mv;
	}
	
	// KTV列表
	@RequestMapping(value = "/ktvList", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public String ktvList(String pageNum) {
		String response = "";
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, String>> list = new ArrayList<>();
		try {
			String param = "{\"timefield\":\"spider_time\",\"times\":\"\",\"size\":\"25\",\"and\":[{\"field\":\"org_type\", \"keyword\":\"KTV\"}],\"index\":\"stonedt_local_life\",\"page\":"+pageNum+",\"type\":\"infor\",\"timee\":\"\"}";
	    	JSONObject parseObject = JSONObject.parseObject(param);
	    	System.out.println(parseObject);
	    	RestTemplate client=new RestTemplate();
	    	ResponseEntity<String> responseEntity = client.postForEntity(
	    			"http://dx2.stonedt.com:7120/commonsearch/superdatalist", 
	    			parseObject, 
	    			String.class);
	    	response=responseEntity.getBody();
//				    	response = getJsonHtml("http://dx2.stonedt.com:7120/commonsearch/superdatalist",parseObject);
			System.out.println(response);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("list", list);
			result.put("totalData", 0);
			result.put("totalPage", 1);
		}
		if (StringUtils.isNotBlank(response)) {
			JSONObject parseObject = JSON.parseObject(response);
			String totalData = parseObject.getString("count");
			String totalPage = parseObject.getString("page_count");
			JSONArray jsonArray = parseObject.getJSONArray("data");
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				JSONObject jsonObject2 = jsonObject.getJSONObject("_source");
				Map<String, String> params = JSONObject.parseObject(jsonObject2.toJSONString(), new TypeReference<Map<String, String>>(){});
				list.add(params);
			}
			int totalCount=Integer.parseInt(totalData);
			if (totalCount >= 5000 && totalCount < 7500) {
				totalPage = "200";
			}else if(totalCount >= 7500 && totalCount < 10000) {
				totalPage = "300";
			}else if (totalCount >= 10000) {
				totalPage = "400";
			}
			if (StringUtils.isBlank(totalPage)) {
				totalPage = "1";
			}else if ("0".equals(totalPage)) {
				totalPage = "1";
			}
			result.put("list", list);
			result.put("totalData", totalData);
			result.put("totalPage", totalPage);
		}else {
			result.put("list", list);
			result.put("totalData", 0);
			result.put("totalPage", 1);
		}
		return JSON.toJSONString(result);
	}
	
	// 文化艺术
	@RequestMapping(value = "/cultureArt", method = RequestMethod.GET)
	public ModelAndView cultureArt(ModelAndView mv) {
		mv.setViewName("localLife/cultureArt");
		return mv;
	}
	
	// 文化艺术列表
	@RequestMapping(value = "/cultureArtList", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public String cultureArtList(String pageNum) {
		String response = "";
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, String>> list = new ArrayList<>();
		try {
			String param = "{\"timefield\":\"spider_time\",\"times\":\"\",\"size\":\"25\",\"and\":[{\"field\":\"org_type\", \"keyword\":\"文化艺术\"}],\"index\":\"stonedt_local_life\",\"page\":"+pageNum+",\"type\":\"infor\",\"timee\":\"\"}";
	    	JSONObject parseObject = JSONObject.parseObject(param);
	    	System.out.println(parseObject);
	    	RestTemplate client=new RestTemplate();
	    	ResponseEntity<String> responseEntity = client.postForEntity(
	    			"http://dx2.stonedt.com:7120/commonsearch/superdatalist", 
	    			parseObject, 
	    			String.class);
	    	response=responseEntity.getBody();
//				    	response = getJsonHtml("http://dx2.stonedt.com:7120/commonsearch/superdatalist",parseObject);
			System.out.println(response);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("list", list);
			result.put("totalData", 0);
			result.put("totalPage", 1);
		}
		if (StringUtils.isNotBlank(response)) {
			JSONObject parseObject = JSON.parseObject(response);
			String totalData = parseObject.getString("count");
			String totalPage = parseObject.getString("page_count");
			JSONArray jsonArray = parseObject.getJSONArray("data");
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				JSONObject jsonObject2 = jsonObject.getJSONObject("_source");
				Map<String, String> params = JSONObject.parseObject(jsonObject2.toJSONString(), new TypeReference<Map<String, String>>(){});
				list.add(params);
			}
			int totalCount=Integer.parseInt(totalData);
			if (totalCount >= 5000 && totalCount < 7500) {
				totalPage = "200";
			}else if(totalCount >= 7500 && totalCount < 10000) {
				totalPage = "300";
			}else if (totalCount >= 10000) {
				totalPage = "400";
			}
			if (StringUtils.isBlank(totalPage)) {
				totalPage = "1";
			}else if ("0".equals(totalPage)) {
				totalPage = "1";
			}
			result.put("list", list);
			result.put("totalData", totalData);
			result.put("totalPage", totalPage);
		}else {
			result.put("list", list);
			result.put("totalData", 0);
			result.put("totalPage", 1);
		}
		return JSON.toJSONString(result);
	}
	
	// 酒吧
	@RequestMapping(value = "/bar", method = RequestMethod.GET)
	public ModelAndView bar(ModelAndView mv) {
		mv.setViewName("localLife/bar");
		return mv;
	}
	
	// 酒吧列表
	@RequestMapping(value = "/barList", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public String barList(String pageNum) {
		String response = "";
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, String>> list = new ArrayList<>();
		try {
			String param = "{\"timefield\":\"spider_time\",\"times\":\"\",\"size\":\"25\",\"and\":[{\"field\":\"org_type\", \"keyword\":\"酒吧\"}],\"index\":\"stonedt_local_life\",\"page\":"+pageNum+",\"type\":\"infor\",\"timee\":\"\"}";
	    	JSONObject parseObject = JSONObject.parseObject(param);
	    	System.out.println(parseObject);
	    	RestTemplate client=new RestTemplate();
	    	ResponseEntity<String> responseEntity = client.postForEntity(
	    			"http://dx2.stonedt.com:7120/commonsearch/superdatalist", 
	    			parseObject, 
	    			String.class);
	    	response=responseEntity.getBody();
//				    	response = getJsonHtml("http://dx2.stonedt.com:7120/commonsearch/superdatalist",parseObject);
			System.out.println(response);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("list", list);
			result.put("totalData", 0);
			result.put("totalPage", 1);
		}
		if (StringUtils.isNotBlank(response)) {
			JSONObject parseObject = JSON.parseObject(response);
			String totalData = parseObject.getString("count");
			String totalPage = parseObject.getString("page_count");
			JSONArray jsonArray = parseObject.getJSONArray("data");
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				JSONObject jsonObject2 = jsonObject.getJSONObject("_source");
				Map<String, String> params = JSONObject.parseObject(jsonObject2.toJSONString(), new TypeReference<Map<String, String>>(){});
				list.add(params);
			}
			int totalCount=Integer.parseInt(totalData);
			if (totalCount >= 5000 && totalCount < 7500) {
				totalPage = "200";
			}else if(totalCount >= 7500 && totalCount < 10000) {
				totalPage = "300";
			}else if (totalCount >= 10000) {
				totalPage = "400";
			}
			if (StringUtils.isBlank(totalPage)) {
				totalPage = "1";
			}else if ("0".equals(totalPage)) {
				totalPage = "1";
			}
			result.put("list", list);
			result.put("totalData", totalData);
			result.put("totalPage", totalPage);
		}else {
			result.put("list", list);
			result.put("totalData", 0);
			result.put("totalPage", 1);
		}
		return JSON.toJSONString(result);
	}
}
