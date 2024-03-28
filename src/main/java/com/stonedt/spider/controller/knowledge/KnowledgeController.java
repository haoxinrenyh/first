package com.stonedt.spider.controller.knowledge;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.stonedt.spider.serviceImpl.KnowledgeServiceImpl;
import com.stonedt.spider.util.UtilConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/knowledge")
public class KnowledgeController {
    @Autowired
    KnowledgeServiceImpl knowledgeService;

    /**
     * 跳转页面
     */
    @RequestMapping("/toKnowledgePage")
    public ModelAndView toKnowledgePage(ModelAndView mv) {
        mv.setViewName("knowledge/knowledgeList");
        return mv;
    }

    /**
     * 获取列表数据
     */
    @RequestMapping("/getKnowledgeList")
    @ResponseBody
    public String getKnowledgeList(@RequestParam(value = "typeName") String typeName, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        String response = "";
        try {
            JSONObject json = new JSONObject();
            json.put("size", 10);
            json.put("page", pageNum);
            json.put("type", typeName);
            response = knowledgeService.getKnowledgeListData(json);
            System.out.println("列表数据");
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 获取类型数据
     */
    @RequestMapping("/getKnowledgeTypeList")
    @ResponseBody
    public String getKnowledgeTypeList() {
        String respose = "";
        try {
            JSONObject json = new JSONObject();
            respose = knowledgeService.getTypeData(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return respose;
    }


    /**
     * 跳转专利详情页面
     */
    @RequestMapping("/toKnowledgeDetailPage")
    public ModelAndView toKnowledgeDetailPage(ModelAndView mv, @RequestParam(value = "article_public_id") String article_public_id) {
        mv.setViewName("knowledge/patentDetail");
        JSONObject response = knowledgeService.getPatentDetail(article_public_id);
        System.out.println(response);
        mv.addObject("patentDetailJson", response);
        return mv;
    }
    
    
    /***
	 * 软件著作权
	 * @param mav
	 * @return
	 * Colin
	 */
	@RequestMapping("/softwarecopyright")
	public ModelAndView Softwarecopyright(ModelAndView mav) {
		mav.setViewName("knowledge/software");
		return mav;
	}
	
    /***
	 * 商标
	 * @param mav
	 * @return
	 * Colin
	 */
	@RequestMapping("/brand")
	public ModelAndView brand(ModelAndView mav) {
		mav.setViewName("knowledge/brand");
		return mav;
	}
	@RequestMapping(value="/softwarecopyright-data",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject SoftwarecopyrightData(
			@RequestParam(value = "page", defaultValue = "1" ,required = false)Integer page) {
		JSONObject json=new JSONObject();
		try {
			String response="";
			String param="";
			param="?times=&timee=&page="+page+"&size=10";
			Integer count =0;
			Integer page_count =0;
			Integer size =0;
				response = sendPost(UtilConfig.getURL2().substring(0,UtilConfig.getURL2().length()-1)+"/softwareworks/softwareworkslist", param);
				json = JSONObject.parseObject(response);
				JSONArray news=json.getJSONArray("data");
				count=Integer.valueOf(json.get("count").toString());
				page_count=Integer.valueOf(json.get("page_count").toString());
				size=Integer.valueOf(json.get("size").toString());

	        System.out.println(news.toString());
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
