package com.stonedt.spider.controller.organChip;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.stonedt.spider.controller.legal.ExecutionPersonController;
import com.stonedt.spider.controller.overseas.OverseasInfo;
import com.stonedt.spider.util.ArticlePicture;
import com.stonedt.spider.util.DateUtil;
import com.stonedt.spider.util.ElasticSearchUtil;
import com.stonedt.spider.util.UtilConfig;

/*
 * 器官芯片数据
 */
@Controller
@RequestMapping("/organChip")
public class OrganChipController {
	//private static String medlisturl = UtilConfig.getURL2().substring(0,UtilConfig.getURL2().length()-1)+"/dbs/supernihdatalist";
	@Value("${URL2}")
    private  String medlisturl;
	
	//private static String meddetailurl = UtilConfig.getURL2().substring(0,UtilConfig.getURL2().length()-1)+"/dbs/getnihdatadetail";
	
	@Value("${URL2}")
    private  String meddetailurl;

	// 药品列表
	@RequestMapping(value = "/medicine", method = RequestMethod.GET)
	public ModelAndView medicine(ModelAndView mv, HttpServletRequest request) {
		mv.addObject("webName", "药品");
		request.setAttribute("LEFT", "medicine");
		mv.setViewName("organChip/medicine-info");
		return mv;
	}
	
	
	//文献列表
		@RequestMapping(value = "/literature")
		public ModelAndView literature (ModelAndView mv, HttpServletRequest request) {
			mv.addObject("webName", "文献");
			mv.addObject("nowArray", DateUtil.getNowDate());
			request.setAttribute("LEFT", "literature");
			mv.setViewName("organChip/literature");
			return mv;
		}
		
		// 获取文献列表数据
		@RequestMapping(value = "/literature-data", method = RequestMethod.POST)
		@ResponseBody
		public String getliteraturedata(int pageNum) {
			String url=ElasticSearchUtil.superdatalist;
			JSONObject json=getData(pageNum, "dongda", "create_time", url);
			return json.toJSONString();
		}
		
		//文献详情页数据处理
		@RequestMapping(value = "/literaturedetail")
		public ModelAndView literaturedetail (ModelAndView mav, HttpServletRequest request,String id) {
			mav.addObject("webName", "文献");
			request.setAttribute("LEFT", "literature");
			try {
				JSONObject end_json=new JSONObject();
			        end_json.put("fieldname", "id");
			        end_json.put("fielddata", id);
			        end_json.put("index", "dongda");
			        end_json.put("type", "infor");
			        String url = UtilConfig.getURL2().substring(0,UtilConfig.getURL2().length()-1)+ElasticSearchUtil.getcommondatadetail;
		            String responseData = ExecutionPersonController.getJsonHtml(url,"",end_json);
		            JSONObject old_news = JSONObject.parseObject(responseData);
		            JSONArray author=JSONArray.parseArray(old_news.getString("author"));
		            /*//作者信息处理
		            String de = old_news.getJSONObject("authorInfor").toJSONString();
		            old_news.put("authordetail", de.replace("{", "").replace("}", ""));*/
		            //msh条款
		            JSONObject msh = JSONObject.parseObject(old_news.getString("publishType"));
		            JSONArray Substances=msh.getJSONArray("Substances");
		            JSONArray MeSH =msh.getJSONArray("MeSH terms");
		    mav.addObject("Substances", Substances);
		    mav.addObject("MeSH", MeSH);
		    mav.addObject("author", author);
		    mav.addObject("news", old_news);
			} catch (Exception e) {
				e.printStackTrace();
			}
			mav.setViewName("organChip/literature-detail");
			return mav;
		}
		
		
		//GSA列表
		@RequestMapping(value = "/GSA")
		public ModelAndView GSA (ModelAndView mv, HttpServletRequest request) {
		mv.addObject("webName", "GSA");
		mv.addObject("nowArray", DateUtil.getNowDate());
		request.setAttribute("LEFT", "GSA");
		mv.setViewName("organChip/gsa");
		return mv;
		}
				
		// 获取GSA列表数据
		@RequestMapping(value = "/GSA-data", method = RequestMethod.POST)
		@ResponseBody
		public String GSAdata(int pageNum) {
		String url=ElasticSearchUtil.superdatalist;
		JSONObject json=getData(pageNum, "stonedt_geo", "create_time", url);
		System.out.println(json.toJSONString());
		return json.toJSONString();
		}
		
		
		//GSA详情页数据处理
				@RequestMapping(value = "/GSAdetail")
				public ModelAndView GSAdetail (ModelAndView mav, HttpServletRequest request,String article_public_id) {
					mav.addObject("webName", "GSA");
					request.setAttribute("LEFT", "GSA");
					try {
						JSONObject end_json=new JSONObject();
					        end_json.put("fieldname", "article_public_id");
					        end_json.put("fielddata", article_public_id);
					        end_json.put("index", "stonedt_geo");
					        end_json.put("type", "infor");
					        String url = UtilConfig.getURL2().substring(0,UtilConfig.getURL2().length()-1)+ElasticSearchUtil.getcommondatadetail;
				            String responseData = ExecutionPersonController.getJsonHtml(url,"",end_json);
				            JSONObject old_news = JSONObject.parseObject(responseData);
				            JSONArray json_organism=old_news.getJSONArray("organism");
				            old_news.put("json_organism", json_organism);
				            //contributor
				            JSONArray json_contributor=old_news.getJSONArray("contributor");
				            old_news.put("json_contributor", json_contributor);
				            //citation
				            JSONArray json_citation=old_news.getJSONArray("citation");
				            old_news.put("json_citation", json_citation);
				            //邮件
				            JSONArray e_mail=old_news.getJSONArray("e_mail");
				            old_news.put("e_mail", e_mail);
				            //平台platforms
				            JSONArray platforms=old_news.getJSONArray("platforms");
				            old_news.put("platforms", platforms);
				            //样品samples
				            JSONArray samples=old_news.getJSONArray("samples");
				            if(samples.size()>4) {
				            	JSONArray samples_two=new JSONArray();
				            	for(int i=0;i<3;i++) {
				            		samples_two.add(samples.getJSONObject(i));
				            	}
				            	old_news.put("samples", samples_two);
				            }else {
				            	old_news.put("samples", samples);
				            }
				            //relations
				            JSONArray relations=old_news.getJSONArray("relations");
				            old_news.put("relations", relations);
				            //download_family
				            JSONArray download_family=old_news.getJSONArray("download_family");
				            old_news.put("download_family", download_family);
				            //relations
				            JSONArray file_form=old_news.getJSONArray("file_form");
				            old_news.put("file_form", file_form);
				            System.out.println(old_news.toJSONString());
				    mav.addObject("news", old_news);
					} catch (Exception e) {
						e.printStackTrace();
					}
					mav.setViewName("organChip/gsa-detail");
					return mav;
				}

	// 获取列表数据
	@RequestMapping(value = "/medicinelist", method = RequestMethod.POST)
	@ResponseBody
	public String getMedicineList(HttpServletRequest request, int pageNum) {
		JSONObject json = new JSONObject();
		String url = medlisturl+"/dbs/supernihdatalist";
		json.put("timefield", "spider_time");
		json.put("times", "");
		json.put("size", 10);
		json.put("index", "dongda_drug_bank");
		json.put("page", pageNum);
		json.put("type", "infor");
		json.put("timee", "");
		String data = "";
		try {
			System.out.println(json.toString());//修改输出
			data = OverseasInfo.getJsonHtml(url, json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject jsondata = JSONObject.parseObject(data);
		JSONObject list = new JSONObject();
		list.put("totalData", jsondata.get("count"));
		list.put("totalPage", jsondata.get("page_count"));
		list.put("list", jsondata.get("data"));
		System.out.println(list.toString());//修改输出
		return list.toJSONString();
	}

	// 跳转药品详情
	@RequestMapping(value = "/tomedicineDetail")
	public ModelAndView toMedicineDetail(ModelAndView mv, HttpServletRequest request, String pmid) {
		request.setAttribute("pmid", pmid);
		mv.setViewName("organChip/medicine-detail");
		request.setAttribute("webName", "器官芯片");
		request.setAttribute("LEFT", "medicine");
		return mv;
	}

	// 获取详情数据
	@RequestMapping(value = "/getmedicineDetail", method = RequestMethod.POST)
	@ResponseBody
	public String getmedicineDetail(String pmid, HttpServletRequest request) {
		JSONObject json = new JSONObject();
		String url = meddetailurl+"/dbs/getnihdatadetail";
		json.put("fieldname", "pmid");
		json.put("fielddata",pmid);
		json.put("index", "dongda_drug_bank");
		json.put("type", "infor");
		String data = "";
		try {
			data = OverseasInfo.getJsonHtml(url, json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
	
	
	
	/***
	 * 
	 * @param page 页码
	 * @param index 
	 * @param timefield
	 * @param port 接口名
	 * @return 返回JSONObject 通用
	 */
	public JSONObject getData(
			@RequestParam(value = "page", defaultValue = "1" ,required = false)Integer page,
			@RequestParam(value = "index", defaultValue = "" ,required = true)String index,
			@RequestParam(value = "timefield", defaultValue = "" ,required = true)String timefield,
			@RequestParam(value = "port", defaultValue = "" ,required = true)String port) {
		JSONObject json=new JSONObject();
		try {
			JSONObject end_json=new JSONObject();
			Integer count =0;
			Integer page_count =0;
			Integer size =0;
		        end_json.put("timefield", timefield);
		        end_json.put("times", "");
		        end_json.put("timee", "");
		        end_json.put("index", index);
		        end_json.put("type", "infor");
		        end_json.put("page", page);
		        end_json.put("size", "10");
		        String url = UtilConfig.getURL2().substring(0,UtilConfig.getURL2().length()-1)+port;
	            String responseData = ExecutionPersonController.getJsonHtml(url,"",end_json);
	            json = JSONObject.parseObject(responseData);
	        JSONArray news=json.getJSONArray("data");
            count=Integer.valueOf(json.get("count").toString());
			page_count=Integer.valueOf(json.get("page_count").toString());
			size=Integer.valueOf(json.get("size").toString());
		json.put("news",news);
		json.put("count",count);
		json.put("page_count",page_count);
		json.put("page",page);
		json.put("size",size);
            System.err.println(responseData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
}
