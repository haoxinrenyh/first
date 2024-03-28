package com.stonedt.spider.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.stonedt.spider.service.InviteService;
import com.stonedt.spider.util.CkickHouseLink;
import com.stonedt.spider.util.ElasticSearchUtil;
import com.stonedt.spider.util.UtilConfig;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @param
 * @purpose:
 * @time: 2020/1/13 14:17
 * @author:
 */
@Service
public class InviteServiceImpl implements InviteService {
//    public static final String jobslistApi = "/jobs/jobslist";// 列表数据
//    public static final String jobssourcesApi = "/jobs/jobssources";// 渠道占比
//    public static final String citylistApi = "/jobs/citylist";// 城市占比
//    public static final String getjob = "/jobs/jobsnumlist";// 岗位以及人数
//    public static final String getjobsdetail = "/jobs/getjobsdetail";

    /**
     * 获取列表数据
     *
     * @param inviteSource 招聘来源
     * @param searchText   搜索内容
     * @param searchType   搜索类型
     * @return
     */
    @Override
    public JSONObject getInviteList(Map<String, String> map) {
        String response = send(map, ElasticSearchUtil.jobslistApi);
        JSONObject responseObj = JSONObject.parseObject(response);
        Integer count = responseObj.getInteger("count");
        Integer page_count = responseObj.getInteger("page_count");
        Integer page = responseObj.getInteger("page");
        String code = responseObj.getString("code");
        JSONArray dataArray = responseObj.getJSONArray("data");
        JSONObject returnObj = new JSONObject();
        JSONArray list = new JSONArray();
        for (int i = 0; i < dataArray.size(); i++) {
            JSONObject dataObj = (JSONObject) dataArray.get(i);
            JSONObject _sourceObj = dataObj.getJSONObject("_source");
            list.add(_sourceObj);
        }
        JSONArray returnArray = getSourceList();
        returnObj.put("source", returnArray);
        returnObj.put("inviteList", list);
        System.out.println(list);
        returnObj.put("pageCount", page_count);
        returnObj.put("pageNum", page);
        returnObj.put("totalCount", count);
        return returnObj;
    }

    public JSONArray getSourceList() {
        Map<String, String> map = new HashMap<>();
        String response = send(map, ElasticSearchUtil.jobssourcesApi);
        JSONObject responseObj = JSONObject.parseObject(response);
        JSONObject aggregationsObj = responseObj.getJSONObject("aggregations");
        JSONObject group_by_tagsObj = aggregationsObj.getJSONObject("group_by_tags");
        JSONArray bucketsArray = group_by_tagsObj.getJSONArray("buckets");
        JSONArray returnArray = new JSONArray();
        for (int i = 0; i < bucketsArray.size(); i++) {
            JSONObject jsonObject = (JSONObject) bucketsArray.get(i);
            String source = jsonObject.getString("key");
            returnArray.add(source);
        }
        return returnArray;
    }

    /**
     * 招聘请求数据
     *
     * @param param
     * @param api
     * @return
     */
    public static String send(Map<String, String> param, String api) {
        String sendPost = "";
        try {
            String size = ""; // 步长
            String page = "1"; // 当前页数
            String jobsorigin = "";
            String searchkeywrord = "";
            String searchtype = "";
            String province = "";
            String city = "";
            if (param.containsKey("searchkeywrord")) {
                searchkeywrord = param.get("searchkeywrord");
            }
            if (param.containsKey("searchtype")) {
                searchtype = param.get("searchtype");
            }

            if (param.containsKey("size")) {
                size = param.get("size");
            }
            if (param.containsKey("page")) {
                page = param.get("page");
            }

            if (param.containsKey("province")) {
                province = URLEncoder.encode(param.get("province"), "utf-8");
            }

            if (param.containsKey("city")) {
                city = URLEncoder.encode(param.get("city"), "utf-8");
            }

            if (param.containsKey("jobsorigin")) {
                jobsorigin = param.get("jobsorigin");
                if (jobsorigin.equals("全部")) {
                    jobsorigin = "";
                }
            }

            String url = UtilConfig.getURL() + api;
            String params = null;
            try {
            	if(searchtype.equals("2")) {
            		params = "&size=" + size + "&invite_province=" + province + "&invite_city=" + city + "&page=" + page + "&jobsorigin=" + URLEncoder.encode(jobsorigin, "utf-8")
            			+ "&searchkeywrord=" + searchkeywrord;
            	}else if(searchtype.equals("3")) {
            		params = "&size=" + size + "&invite_province=" + province + "&invite_city=" + city + "&page=" + page + "&jobsorigin=" + URLEncoder.encode(jobsorigin, "utf-8")
            			+ "&company_name=" + searchkeywrord;
            	}else {
	                params = "&size=" + size + "&invite_province=" + province + "&invite_city=" + city + "&page=" + page + "&jobsorigin=" + URLEncoder.encode(jobsorigin, "utf-8")
	                        + "&searchkeywrord=" + searchkeywrord + "&searchtype=" + searchtype;
            	}
            } catch (UnsupportedEncodingException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            System.err.println(url + "?" + params);

            try {
                sendPost = ElasticSearchUtil.sendPost1(url, params);
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sendPost;
    }

    /**
     * 获取招聘详情信息
     */
    @Override
    public JSONObject getInviteDetail(Map<String, Object> map) {
        JSONObject responseDataJson = null;
        try {
            // TODO Auto-generated method
            String record_id = String.valueOf(map.get("record_id"));
            String api = ElasticSearchUtil.getjobsdetail;
            String url = UtilConfig.getURL() + api;
            String params = "?&record_id=" + record_id;
            String responseData = ElasticSearchUtil.sendPost1(url, params);
            System.out.println(responseData);
            responseDataJson = JSON.parseObject(responseData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseDataJson;
    }

    @Override
    public JSONObject getProviceAsyn(Map<String, String> map) {
        JSONObject proviceJson = new JSONObject();
        try {
            String api = ElasticSearchUtil.provincelist;
            String esResponse = send(map, api);
            JSONObject esResponseJson = JSON.parseObject(esResponse);
            JSONObject aggregationsJson = esResponseJson.getJSONObject("aggregations");
            JSONObject group_by_tagsJson = aggregationsJson.getJSONObject("group_by_tags");
            JSONArray bucketsArray = group_by_tagsJson.getJSONArray("buckets");
            proviceJson.put("code", "200");
            proviceJson.put("msg", "省份信息获取成功！");
            proviceJson.put("data", bucketsArray);
        } catch (Exception e) {
            e.printStackTrace();
            JSONArray dataArray = new JSONArray();
            proviceJson.put("code", "500");
            proviceJson.put("msg", "省份信息获取异常！");
            proviceJson.put("data", dataArray);
        }
        return proviceJson;
    }

    @Override
    public JSONObject getCityAsyn(Map<String, String> map) {
        JSONObject cityJson = new JSONObject();
        try {
            String api = ElasticSearchUtil.citylist;
            String esResponse = send(map, api);
            JSONObject esResponseJson = JSON.parseObject(esResponse);
            JSONObject aggregationsJson = esResponseJson.getJSONObject("aggregations");
            JSONObject group_by_tagsJson = aggregationsJson.getJSONObject("group_by_tags");
            JSONArray bucketsArray = group_by_tagsJson.getJSONArray("buckets");
            cityJson.put("code", "200");
            cityJson.put("msg", "城市信息获取成功！");
            cityJson.put("data", bucketsArray);
        } catch (Exception e) {
            e.printStackTrace();
            JSONArray dataArray = new JSONArray();
            cityJson.put("code", "500");
            cityJson.put("msg", "城市信息获取异常！");
            cityJson.put("data", dataArray);
        }
        return cityJson;
    }

	@Override
	public JSONObject getListedcompany(Integer pageNum,Integer pageSize) {
		PageHelper.startPage(pageNum, 10);
		// pageSize 每页显示的条数
		// pageNum 当前页码
		//先查詢出总数据量
		String countSql="select count(*) from ent_map.firm_data";
		List<Map<String, Object>> Countlist=CkickHouseLink.search(countSql);
		String CountStr=JSON.toJSONString(Countlist);
		JSONArray CountObj=JSONArray.parseArray(CountStr);
		Integer count=Integer.valueOf(CountObj.getJSONObject(0).getString("count()"));
		//当前页码 pageNum
		//总页码 等于当前数据量除以每页显示条数
		Integer pageCount=(count/pageSize)+1;
		
		JSONObject EndJson=new JSONObject();
		List<Map<String, Object>> list=null;
		String sql="SELECT id, firm_id, name, abbr, legal_representative, status, establish_time, registered_capital_str, reality_capital_str, registered_capital, reality_capital, approval_date, abstract as companyInfo, uniform_social_credit_code, organization_code, registration_number, taxpayer_identification_number, industry_involved, enterprise_type, business_term, register_organization, address, register_adress, business_scope, website, province, city, `type`, is_completed FROM ent_map.firm_data limit "+(pageNum-1)*10+","+pageSize;
		EndJson.put("msg", "查询失败！");
		EndJson.put("code", 500);
		try {
			list=CkickHouseLink.search(sql);
	        //返回
			EndJson.put("msg", "查询成功！");
			EndJson.put("code", 200);
			EndJson.put("total", count);
			EndJson.put("pageNum", pageNum);
			EndJson.put("pageCount", pageCount);
			EndJson.put("data", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EndJson;
	}
}
