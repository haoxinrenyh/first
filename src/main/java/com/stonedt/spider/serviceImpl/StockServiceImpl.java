package com.stonedt.spider.serviceImpl;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.DecimalFormat;


import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.stonedt.spider.dao.StockDao;
import com.stonedt.spider.entity.Patent;
import com.stonedt.spider.entity.Stock;
import com.stonedt.spider.entity.StockFinancial;
import com.stonedt.spider.entity.StockInfo;
import com.stonedt.spider.service.StockService;
import com.stonedt.spider.util.CkickHouseLink;
import com.stonedt.spider.util.ResultUtil;

@Service("StockService")
public class StockServiceImpl implements StockService {
	@Autowired
	private StockDao stockDao;

	private final String es_local_url = "http://dx2.stonedt.com:7120";
		
	@Value("${URL2}")
    private  String es_url;
	private final String es_copyrightlist="/workcopyright/copyrightlist";
	
	private final String es_pledgelist="/pledge/pledgelist";
	
	private final String es_abnormallist="/abnormal/abnormallist";
	
	private final String es_punishmentlist="/punishment/punishmentlist";
	
	private final String es_executionpersonlist="/executionperson/executionpersonlist";
	
	@Override
	public List<Stock> stockList(Integer stocktype) {
		// TODO Auto-generated method stub
		return stockDao.stockList(stocktype);
	}

	@Override
	public Stock getstockbaseinfo(String code) {
		// TODO Auto-generated method stub
		return stockDao.getstockbaseinfo(code);
	}

	@Override
	public StockInfo getStockInfoByCode(String code) {
		// TODO Auto-generated method stub
		StockInfo stockInfoByCode = stockDao.getStockInfoByCode(code);
		stockInfoByCode.setTotalEquity(txfloat(Double.parseDouble(stockInfoByCode.getTotalEquity()),100000000)); 
		stockInfoByCode.setMarketValue(txfloat(Double.parseDouble(stockInfoByCode.getMarketValue()),100000000));
		stockInfoByCode.setNegEquity(txfloat(Double.parseDouble(stockInfoByCode.getNegEquity()),100000000));
		stockInfoByCode.setNegMarketValue(txfloat(Double.parseDouble(stockInfoByCode.getNegMarketValue()),100000000));
		String upAndDown = stockInfoByCode.getData_three();
		if(upAndDown.startsWith("-")){
			stockInfoByCode.setData_three("-"+txfloat(Double.parseDouble(upAndDown.substring(1,upAndDown.length())),0.01));
		}else{
			stockInfoByCode.setData_three(txfloat(Double.parseDouble(upAndDown),0.01));
		}
		return stockInfoByCode;
	}
	
	/**
	 * TODO 除法运算，保留小数
	 * @author 袁忠明
	 * @date 2018-4-17下午2:24:48
	 * @param a 被除数
	 * @param b 除数
	 * @return 商
	 */
	public static String txfloat(double a,double b) {
	    // TODO 自动生成的方法存根
	 
	    DecimalFormat df=new DecimalFormat("0.00");//设置保留位数
 
	    return df.format((float)a/b);
	   
	}

	@Override
	public StockFinancial getFinancial(String code) {
		// TODO Auto-generated method stub
		StockFinancial json = stockDao.getFinancial(code);
		
		return json;
	}

	@Override
	public String getStockJson(String code) {
		String json = stockDao.getStockJson(code);
		
		return json;
	}

	@Override
	public String getStockRevenue(String code) {
		// TODO Auto-generated method stub
		return stockDao.getStockRevenue(code);
	}

	@Override
	public String getStockEarning(String code) {
		// TODO Auto-generated method stub
		return stockDao.getStockEarning(code);
	}

	@Override
	public String getStockNetProfit(String code) {
		// TODO Auto-generated method stub
		return stockDao.getStockNetProfit(code);
	}

	@Override
	public String getStockResearch(String code) {
		// TODO Auto-generated method stub
		return stockDao.getStockResearch(code);
	}

	@Override
	public ResultUtil getPatent(String code) {
		// TODO Auto-generated method stub
		Patent patent = stockDao.getPatent(code);
		return new ResultUtil(200,"",patent);
	}

	@Override
	public JSONObject getShiDa(String company_id) {
		String sql="select * from shares where firm_id ='"+company_id+"' limit 0,10";
		JSONObject EndJson=new JSONObject();
		EndJson.put("code", "500");
		EndJson.put("msg", "获取失败！");
		try {
			List<Map<String, Object>> list=CkickHouseLink.search(sql);
			EndJson.put("code", "200");
			EndJson.put("msg", "获取成功！");
			EndJson.put("data", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EndJson;
	}

	@Override
	public JSONObject getpersons(String id) {
		String sql="select * from persons_data where persons_id like '%"+id+"%' limit 0,1";
		JSONObject EndJson=new JSONObject();
		EndJson.put("code", "500");
		EndJson.put("msg", "获取失败！");
		try {
			List<Map<String, Object>> list=CkickHouseLink.search(sql);
			EndJson.put("code", "200");
			EndJson.put("msg", "获取成功！");
			EndJson.put("data", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EndJson;
	}

	@Override
	public JSONObject companydetail(String company_id) {
		String sql="select id, firm_id, name, abbr, legal_representative, status, establish_time, registered_capital_str, reality_capital_str, registered_capital, reality_capital, approval_date, abstract as companyInfo, uniform_social_credit_code, organization_code, registration_number, taxpayer_identification_number, industry_involved, enterprise_type, business_term, register_organization, address, register_adress, business_scope, website, province, city, `type`, is_completed from `firm_data`  where `firm_id`  like '%"+company_id+"%'";
		JSONObject EndJson=new JSONObject();
		EndJson.put("code", "500");
		EndJson.put("msg", "获取失败！");
		try {
			List<Map<String, Object>> list=CkickHouseLink.search(sql);
			EndJson.put("code", "200");
			EndJson.put("msg", "获取成功！");
			EndJson.put("data", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EndJson;
	}

	@Override
	public JSONObject getProperty(String company_id) {
		JSONObject EndJson=new JSONObject();
		String url = null;
		String params = "firm_id=" + company_id;
		//System.err.println(url + "?&" + params);
		//作品著作权
		JSONObject production=new JSONObject();
		String sendPost=null;
		try {
			url=es_local_url + es_copyrightlist;
			sendPost = sendPost(url, params);
			production = JSON.parseObject(sendPost);
			JSONArray penalizeArray = production.getJSONArray("data");
			production.clear();
			production.put("data", penalizeArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//作品著作权
		EndJson.put("production", production);
		return EndJson;
	}

	@Override
	public JSONObject getLaw(String company_id,String iname) {
		JSONObject EndJson=new JSONObject();
		String url = null;
		String params = "firm_id=" + company_id;
		String pledgeUrl = "pledgee_id=" + company_id;
		//System.err.println(url + "?&" + params);
		//股权出质 pledgee_id是公司id 名字不一
		JSONObject abnormalObject = new JSONObject();
		JSONObject penalizeObject = new JSONObject();
		JSONObject pledgeObject = new JSONObject();
		JSONObject executionpersonObject = new JSONObject();
		String sendPost = null;
		try {
			url=es_local_url + es_abnormallist;
			sendPost = sendPost(url, params);
			abnormalObject = JSON.parseObject(sendPost);
			JSONArray abnArray = abnormalObject.getJSONArray("data");
			Iterator<Object> o = abnArray.iterator();
	        while (o.hasNext()) {
	            JSONObject jo = (JSONObject) o.next();
	            if(!jo.getJSONObject("_source").containsKey("leaveReason")) {
	                o.remove();
	            }
	        }
			abnormalObject.clear();
			abnormalObject.put("data", abnArray);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			url=es_local_url + es_punishmentlist;
			sendPost = sendPost(url, params);
			penalizeObject = JSON.parseObject(sendPost);
			JSONArray penalizeArray = penalizeObject.getJSONArray("data");
	        penalizeObject.clear();
	        penalizeObject.put("data", penalizeArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			url=es_local_url + es_pledgelist;
			sendPost = sendPost(url, pledgeUrl);
			pledgeObject = JSON.parseObject(sendPost);
			JSONArray pledgeArray = pledgeObject.getJSONArray("data");
			pledgeObject.clear();
			pledgeObject.put("data", pledgeArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			url=es_local_url + es_executionpersonlist;
			sendPost = sendPost(url, "iname="+URLEncoder.encode(iname, "utf-8"));
			executionpersonObject = JSON.parseObject(sendPost);
			JSONArray person = executionpersonObject.getJSONArray("data");
			executionpersonObject.clear();
			executionpersonObject.put("data", person);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//经营异常
		EndJson.put("abnormal", abnormalObject);
		//行政处罚
		EndJson.put("penalize", penalizeObject);
		//股权出质
		EndJson.put("pledge", pledgeObject);
		//失信被执行人
		EndJson.put("executionperson", executionpersonObject);
		
//		System.out.println(EndJson.toJSONString());
		return EndJson;
	}

	
	public static String sendPost(String url, String params) throws IOException {
		PrintWriter out = null;
		BufferedReader in = null;
		URL realUrl = new URL(url);
	    URLConnection conn = realUrl.openConnection();  
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		conn.setDoOutput(true);
		conn.setDoInput(true);
		out = new PrintWriter(conn.getOutputStream());
		out.print(params);
		out.flush();
		in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
		StringBuilder response = new StringBuilder();
		String line;
		while((line = in.readLine()) != null){
			response.append(line);
		}
		try {
			if (out != null) {
				out.close();
			}
			if (in != null) {
				in.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return response.toString();
	}
}
