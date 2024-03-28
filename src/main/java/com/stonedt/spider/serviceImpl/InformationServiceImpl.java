package com.stonedt.spider.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.stonedt.spider.util.ElasticSearchUtil;
import com.stonedt.spider.util.UtilConfig;
import org.springframework.stereotype.Service;

@Service
public class InformationServiceImpl {
	
    /**
     * 	获取资讯列表数据
     */
    public JSONObject getInformationList(JSONObject json) {
        JSONObject returnObj = new JSONObject();
        try {
        	 Integer classify = json.getInteger("classify");
             Integer size = json.getInteger("size");
             Integer page = json.getInteger("page");
             Integer matchingmode = json.getInteger("matchingmode");
             String keyword = json.getString("keyword");

             String params ="";
             
             if (classify==1) {
             	//门户网站 新浪572 光明网769 人民网573 网易571 腾讯财经60 新华780
             	params="?&page="+page+"&size="+size+"&otherwebsiteid=572,769,573,571,60,780&searchType=1&matchingmode="+matchingmode+"&keyword="+keyword;
 			}else if (classify==2) {
 				//经济日报799 中国财经报807it之家538体育总局520中国会计视野517国泰君安证券502教育部425
             	params="?&page="+page+"&size="+size+"&otherwebsiteid=807,538,520,517,502,425&searchType=1&matchingmode="+matchingmode+"&keyword="+keyword;
 			}else if (classify==6) {
 				//报刊媒体
             	params="?&page="+page+"&size="+size+"&otherwebsiteid=573,574,575,576,577,578,579,580,581,582,583,584,585,586,587,588,589,590,591,592,593,594,595,596,597,598,599,600,601,602,603,604,605,606,607,608,609,610,611,612,613,614,615,616,617,618,619,620,621,622,623,624,625,626,627,628,629,630,631,632,633,634,635,636,637,638,639,640,641,642,643,644,645,646,647,648,649,650,651,652,653,654,655,656,657,658,659,660,661,662,663,664,665,666,667,668,669,670,671,672,673,674,675,676,677,678,679,680,681,682,683,684,685,686,687,688,689,690,691,692,693,694,695,696,697,698,699,700,701,702,703,704,705,706,707,708,709,710,711,712,713,714,715,716,717,718,719,720,721,722,723,724,725,726,727,728,729,730,731,732,733,734,735,736,737,738,739,740,741,742,743,744,745,746,747,748,749,750,751,752,753,754,755,756,757,758,759,760,761,762,763,764,765,766,767,768,769,770,771,772,773,774,775,776,777,778,779,780,781,782,783,784,785,786,787,788,789,790,791,792,793,794,795,796,797,798,799,800,801,802,803,804,805,806,807,808,809,810,811,812,813,814,815,816,817,818,819,820,821,822,823,824,825,826,827,828,829,830,831,832,833,834,835,836,837,838,839,840,841,842,843,844,845,846,847,848,849,850,851,852,853,854,855,856,857,858,859,860,861,862,863,864,865,866,867,868,869,870,871,872,873,874,875,876,877,878,879,880,881,882,883,884,885,886,887,888,889,890,891,892,893,894,895,896,897,898,899,900,901,902,903,904,905,906,907,908,909,910,911,912,913,914,915,916,917,918,919,920,921,922,923,924,925,926,927,928,929,930,931,932,933,934,935,936,937,938,939,940,941,942,943,944,945&searchType=1&matchingmode="+matchingmode+"&keyword="+keyword;
 			}else if(classify==101) {
 				//微信公众号的，为了不跟门户数据冲突
 				classify=1;
 				params= "?&classify=" + classify + "&size=" + size + "&page=" + page+"&searchType=1&matchingmode="+matchingmode+"&keyword="+keyword;
 			}else {
 				params= "?&classify=" + classify + "&size=" + size + "&page=" + page+"&searchType=1&matchingmode="+matchingmode+"&keyword="+keyword;
 			}
            String api = ElasticSearchUtil.yysQbsearchcontent;
            String url = UtilConfig.getURL() + api;
            System.out.println(url + params);
            String responseData = ElasticSearchUtil.sendPost1(url, params);
            JSONObject responseObj = JSON.parseObject(responseData);
            JSONArray dataArray = responseObj.getJSONArray("data");
            JSONArray responseArray = new JSONArray();
            for (int i = 0; i < dataArray.size(); i++) {
                JSONObject dataObj = (JSONObject) dataArray.get(i);
                JSONObject _sourceObj = dataObj.getJSONObject("_source");
                if(classify==1) {
                	//去掉微信公众号的title后面链接
                	if(_sourceObj.get("title").toString().contains("_h")) {
                		String[] ti=_sourceObj.get("title").toString().split("_h");
                		_sourceObj.put("title", ti[0]);
                	}
                }
               
                responseArray.add(_sourceObj);
            }
            Integer page_count = responseObj.getInteger("page_count");
            Integer pageNum = responseObj.getInteger("page");
            Integer totalData = responseObj.getInteger("count");
            
            returnObj.put("list", responseArray);
            returnObj.put("totalPage", page_count);
            returnObj.put("pageNum", pageNum);
            returnObj.put("totalData", totalData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnObj;
    }
    
    
    /***
     * 	获取门户网站数据
     */
    public JSONObject getWebPortal(JSONObject json) {
        JSONObject returnObj = new JSONObject();
        try {
//            Integer classify = json.getInteger("classify");
            Integer size = json.getInteger("size");
            Integer page = json.getInteger("page");
            String params="";
            //新浪572
            params="?&page="+page+"&size="+size+"&websiteid=572&searchType=1";

            String api = ElasticSearchUtil.yysQbsearchcontent;
            String url = UtilConfig.getURL() + api;
            System.out.println(url + params);
            String responseData = ElasticSearchUtil.sendPost1(url, params);
            System.out.println(responseData);
            JSONObject responseObj = JSON.parseObject(responseData);
            JSONArray dataArray = responseObj.getJSONArray("data");
            JSONArray responseArray = new JSONArray();
            for (int i = 0; i < dataArray.size(); i++) {
                JSONObject dataObj = (JSONObject) dataArray.get(i);
                JSONObject _sourceObj = dataObj.getJSONObject("_source");
                responseArray.add(_sourceObj);
            }
            Integer page_count = responseObj.getInteger("page_count");
            Integer pageNum = responseObj.getInteger("page");
            Integer totalData = responseObj.getInteger("count");
            returnObj.put("list", responseArray);
            returnObj.put("totalPage", page_count);
            returnObj.put("pageNum", pageNum);
            returnObj.put("totalData", totalData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnObj;
    }
}
