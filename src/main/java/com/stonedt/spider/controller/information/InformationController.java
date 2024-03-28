package com.stonedt.spider.controller.information;


import com.alibaba.fastjson.JSONArray;

import com.alibaba.fastjson.JSONObject;
import com.stonedt.spider.serviceImpl.InformationServiceImpl;
import com.stonedt.spider.util.DateUtil;
import com.stonedt.spider.util.ElasticSearchUtil;
import com.stonedt.spider.util.UtilConfig;

//import sun.text.resources.cldr.en.FormatData_en_IE;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

/**
 * 	资源控制器
 */
@Controller
@RequestMapping("/information")
public class InformationController {
	
    @Autowired
    InformationServiceImpl informationService;

    /**
     * 	跳转列表页面
     */
    @RequestMapping("/toInformationPage")
    public ModelAndView toInformationPage(ModelAndView mv, @RequestParam("classify") String classify, HttpServletRequest request) {
        mv.setViewName("informationData/informationList");
        mv.addObject("classify", classify);
        if(classify.equals("1")) {
        	mv.addObject("webName", "门户网站");
        }else if(classify.equals("2")) {
        	mv.addObject("webName", "行业网站");
        }else if(classify.equals("6")) {
        	mv.addObject("webName", "报刊媒体");
        }else if(classify.equals("7")) {
        	mv.addObject("webName", "APP客户端");
        }else if(classify.equals("9")) {
        	mv.addObject("webName", "境外媒体");
        }
        mv.addObject("nowArray", DateUtil.getNowDate());
        request.setAttribute("LEFT", classify);
        return mv;
    }
    
    @RequestMapping("/informationDetails")
    public ModelAndView ZiXunDetails(HttpServletRequest req,ModelAndView mav,String article_public_id,String type) {
    	req.setAttribute("article_public_id", article_public_id);
    	req.setAttribute("LEFT", type);
    	mav.setViewName("informationData/information_details");
    	return mav;
    }
    /***
     * 获取详情数据
     * @return
     */
    @RequestMapping("/informationDetails-data")
    @ResponseBody
    public String ZiXunDetailsData(String article_public_id) {
    		String url="/yqsearch/getArticlenewdetail";
    		String response="";
    		String param="?&article_public_id="+article_public_id;
    		System.out.println(param);
    		/*JSONObject json=new JSONObject();
    		json.put("index", "postal");
    		json.put("type", "infor");
    		json.put("fieldname", "article_public_id");
    		json.put("fielddata", article_public_id);*/
    		JSONObject _sourceObj=new JSONObject();
    		try {
				response=sendPost(url, param);
				_sourceObj=JSONObject.parseObject(response);
				if(_sourceObj.get("title").toString().contains("_http")) {
            		String[] ti=_sourceObj.get("title").toString().split("_h");
            		_sourceObj.put("title", ti[0]);
            	}
				String obj_text=_sourceObj.get("text").toString();
				try {
					Document parse = Jsoup.parse(obj_text);
					parse.select("script,noscript,style,iframe").remove();
					obj_text = parse.toString();
				} catch (Exception e) {
					e.printStackTrace();
				}
				//去掉class正则
				Pattern p_class=Pattern.compile("class[\\s]*=[\\s]*['|\"](.*?)['|\"]");
				//去掉style正则
				Pattern p_style=Pattern.compile("style[\\s]*=[\\s]*['|\"](.*?)['|\"]");
				//去掉id正则
				Pattern p_id=Pattern.compile("id[\\s]*=[\\s]*['|\"](.*?)['|\"]");
				
				//把img标签中的data-src 替换为src
				Pattern img_src=Pattern.compile("data-src[\\s]*");
				
				String class_String=obj_text.replaceAll(p_class.toString(), "");
				String style_String=class_String.replaceAll(p_style.toString(), "");
				String id_String=style_String.replaceAll(p_id.toString(), "");
				String text=id_String.replaceAll(img_src.toString(), "src");
				_sourceObj.remove("text");
				_sourceObj.put("text", text);
			} catch (Exception e) {
				e.printStackTrace();
			}
    	return _sourceObj.toJSONString();
    }


    /**
     * 	获取列表页数据
     */
    @RequestMapping("/getInformationList")
    @ResponseBody
    public JSONObject getInformationList(@RequestParam("matchingmode") String matchingmode,
    									 @RequestParam("searchWord") String searchWord,
    									 @RequestParam("classify") String classify,
                                         @RequestParam("page") Integer page) {
    	JSONObject responseJson = new JSONObject();
        try {
        	int mode=0;
            JSONObject json = new JSONObject();
            if (page>500){
                page = 500;
            }
            if(matchingmode.equals("全部")) {
            	mode=0;
            }else if(matchingmode.equals("正文")) {
            	mode=2;
            }else if(matchingmode.equals("标题")) {
            	mode=1;
            }
            json.put("matchingmode", mode);
            json.put("keyword", searchWord);
            json.put("classify", classify);
            json.put("page", page);
            json.put("size", 10);
            System.out.println(json);
            responseJson = informationService.getInformationList(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseJson;
    }
    /*
     * 以下为静态页面
     */
    
    
    /*
     * 企业静态页面
     */
    @RequestMapping("/toBusiness")
    public ModelAndView toBusiness(ModelAndView mv,@RequestParam("classify") String classify, HttpServletRequest request) {
    	 mv.addObject("classify", classify);
         if(classify.equals("q")) {
         	mv.addObject("webName", "企业数据");
         	mv.setViewName("businessIn");
         }else if(classify.equals("zb")) {
         	mv.addObject("webName", "招标投标");
         	mv.setViewName("ZhaoBiaoIn");
         }else if(classify.equals("zp")) {
         	mv.addObject("webName", "招聘数据");
         	mv.setViewName("ZhaoPinIn");
         }else if(classify.equals("f")) {
         	mv.addObject("webName", "法律文书");
         	mv.setViewName("FaIn");
         }else if(classify.equals("pj")) {
          	mv.addObject("webName", "判决书");
          	mv.setViewName("PanJueIn");
          }
         
         request.setAttribute("LEFT2", classify);
    	 return mv;
    }
    /*
     * 判决书详情
     */
    @RequestMapping("/PanJueDetail")
   	public ModelAndView PanJueDe(ModelAndView mav,HttpServletRequest request) {
   		mav.setViewName("article2");
   		mav.addObject("webName", "判决书");
   		mav.addObject("classify", "pj");
   		request.setAttribute("LEFT2", "pj");
   		return mav;
   	}
    @RequestMapping("/PanJueDetail2")
   	public ModelAndView PanJueDe2(ModelAndView mav,HttpServletRequest request) {
   		mav.setViewName("article22");
   		mav.addObject("webName", "判决书");
   		mav.addObject("classify", "pj");
   		request.setAttribute("LEFT2", "pj");
   		return mav;
   	}
    /*
     * 企业数据招标投标详情
     * 
     */
    @RequestMapping("/ZhaoBiaoDetail")
	public ModelAndView BlackCat(ModelAndView mav,HttpServletRequest request) {
		mav.setViewName("article1");
		mav.addObject("webName", "招标");
   		mav.addObject("classify", "zb");
   		request.setAttribute("LEFT2", "zb");
		return mav;
	}
    
    /*
     * 企业数据工商详情
     * 
     */
    @RequestMapping("/BusinessDetail")
	public ModelAndView BusinessDetail(ModelAndView mv,HttpServletRequest request) {
    	mv.addObject("webName", "企业数据");
    	 mv.addObject("classify", "q");
    	 request.setAttribute("LEFT2", "q");
		mv.setViewName("article3");
		return mv;
	}
    
    /*
     * 招聘数据工商详情
     * 
     */
    @RequestMapping("/ZhaoPinDetail")
	public ModelAndView ZhaoPinDetail(ModelAndView mv,HttpServletRequest request) {
    	mv.addObject("webName", "招聘数据");
    	 mv.addObject("classify", "zp");
    	 request.setAttribute("LEFT2", "zp");
		mv.setViewName("article5");
		return mv;
	}
    
    /*
     * 专利数据工商详情
     * 
     */
    @RequestMapping("/KnowledgeDetail")
	public ModelAndView KnowledgeDetail(ModelAndView mv,HttpServletRequest request) {
    	mv.addObject("webName", "专利");
   	 mv.addObject("classify", "zs");
   	request.setAttribute("LEFT2", "zs");
		mv.setViewName("article4");
		return mv;
	}
    /*
     * 资讯数据数据-自媒体静态页面
     */
    @RequestMapping("/toZi")
    public ModelAndView toZi(ModelAndView mv,@RequestParam("classify") String classify, HttpServletRequest request) {
    	 mv.setViewName("ZiIn");
    	 mv.addObject("classify", classify);
         if(classify.equals("zi")) {
         	mv.addObject("webName", "自媒体");
         	 mv.setViewName("ZiIn");
         }else if(classify.equals("sj")) {
         	mv.addObject("webName", "社交媒体");
         	mv.setViewName("SheJiaoIn");
         }else if(classify.equals("wl")) {
         	mv.addObject("webName", "网络媒体");
         	mv.setViewName("WangLuoIn");
         }else if(classify.equals("jw")) {
         	mv.addObject("webName", "境外数据");
         	mv.setViewName("JingWaiIn");
         }else if(classify.equals("duan")) {
          	mv.addObject("webName", "短视频");
          	mv.setViewName("DuanIn");
          }
         mv.addObject("nowArray", DateUtil.getNowDate());
         request.setAttribute("LEFT2", classify);
    	 return mv;
    }
    /*
     * 生活服务 静态页面
     */
    @RequestMapping("/toSheng")
    public ModelAndView toSheng(ModelAndView mv,@RequestParam("classify") String classify, HttpServletRequest request) {
    	 mv.addObject("classify", classify);
         if(classify.equals("tq")) {
         	mv.addObject("webName", "天气数据");
         	mv.setViewName("ShengIn");
         }else if(classify.equals("ds")) {
         	mv.addObject("webName", "电商");
         	mv.setViewName("DianShangIn");
         }else if(classify.equals("ts")) {
          	mv.addObject("webName", "投诉");
          	mv.setViewName("TouSuIn");
          }else if(classify.equals("bk")) {
        	mv.addObject("webName", "百科");
          	mv.setViewName("BaiKeIn");
          }
         mv.addObject("nowArray", DateUtil.getNowDate());
         request.setAttribute("LEFT2", classify);
    	 return mv;
    }
    
    
    /*
     * 行业数据 静态页面
     */
    @RequestMapping("/toHang")
    public ModelAndView toHang(ModelAndView mv,@RequestParam("classify") String classify, HttpServletRequest request) {
    	 mv.addObject("classify", classify);
         if(classify.equals("zq")) {
         	mv.addObject("webName", "证券数据");
         	mv.setViewName("HangIn");
         }else if(classify.equals("qc")) {
         	mv.addObject("webName", "汽车行业");
         	mv.setViewName("QiCheIn");
         }else if(classify.equals("yl")) {
          	mv.addObject("webName", "医疗行业");
          	mv.setViewName("YiLiaoIn");
         }else if(classify.equals("ly")) {
    		mv.addObject("webName", "旅游行业");
          	mv.setViewName("LvYouIn");
         }else if(classify.equals("fc")) {
         	mv.addObject("webName", "房产行业");
          	mv.setViewName("FangChanIn");
         }else if(classify.equals("cm")) {
         	mv.addObject("webName", "传媒行业");
          	mv.setViewName("ChuanMeiIn");
         }
         mv.addObject("nowArray", DateUtil.getNowDate());
         request.setAttribute("LEFT2", classify);
    	 return mv;
    }
    
    /*
     * 专业数据 静态页面
     */
    @RequestMapping("/toZhuan")
    public ModelAndView toZhuan(ModelAndView mv,@RequestParam("classify") String classify, HttpServletRequest request) {
    	 mv.addObject("classify", classify);
         if(classify.equals("zj")) {
         	mv.addObject("webName", "专家人才");
         	 mv.setViewName("ZhuanIn");
         }else if(classify.equals("qg")) {
         	mv.addObject("webName", "器官芯片");
         	 mv.setViewName("QiGuanIn");
         }else if(classify.equals("zs")) {
          	mv.addObject("webName", "知识产权");
        	 mv.setViewName("ZhiShiIn");
        }
         mv.addObject("nowArray", DateUtil.getNowDate());
         request.setAttribute("LEFT2", classify);
    	 return mv;
    }
    
    
    /*
     * 政府部门 静态页面
     */
    @RequestMapping("/toZheng")
    public ModelAndView toZheng(ModelAndView mv,@RequestParam("classify") String classify, HttpServletRequest request) {
    	 mv.addObject("classify", classify);
         if(classify.equals("zf")) {
         	mv.addObject("webName", "政府动态");
         	mv.setViewName("ZhengIn");
         }else if(classify.equals("gj")) {
         	mv.addObject("webName", "国家机关");
     	 	mv.setViewName("GuoIn");
         }else if(classify.equals("zc")) {
          	mv.addObject("webName", "政府政策");
      	 	mv.setViewName("ZhengCeIn");
          }
         mv.addObject("nowArray", DateUtil.getNowDate());
         request.setAttribute("LEFT2", classify);
    	 return mv;
    }

    /*
     * 系统设置 静态页面
     */
    @RequestMapping("/toXi")
    public ModelAndView toXi(ModelAndView mv,@RequestParam("classify") String classify, HttpServletRequest request) {
    	 mv.setViewName("ZhengIn");
    	 mv.addObject("classify", classify);
         if(classify.equals("zf")) {
         	mv.addObject("webName", "政府动态");
         }else if(classify.equals("gj")) {
         	mv.addObject("webName", "国家机关");
         }
         mv.addObject("nowArray", DateUtil.getNowDate());
         request.setAttribute("LEFT2", classify);
    	 return mv;
    }
    
    
    public static String getJsonHtml(String url, JSONObject jsonObject) throws Exception {
		System.err.println(url+jsonObject.toString());
		String result ="";
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		StringEntity postingString = new StringEntity(jsonObject.toString());// json传递
		post.setEntity(postingString);
		post.setHeader("Referer", "http://hotel.qunar.com/cn/shanghai_city/?fromDate=2020-02-13&toDate=2020-02-14&cityName=%E4%B8%8A%E6%B5%B7");
		post.setHeader("Content-type", "application/json");
		HttpResponse response = httpClient.execute(post);
		String content = EntityUtils.toString(response.getEntity());
		result = content;
		return result;
	}
    
    public static String sendPost(String method, String param) throws Exception {
        String url = UtilConfig.getURL().substring(0,UtilConfig.getURL().length()-1) + method;
//		String url = "http://els.stonedt.com:6399/bryes/"+method;
        PrintWriter out = null;
        BufferedReader in = null;
//        String result = "";
        System.out.println(url + param);
        URL realUrl = new URL(url);
        URLConnection conn = realUrl.openConnection();
        // 打开和URL之间的连接
        //URLConnection conn = realUrl.openConnection();
        // 设置通用的请求属性
//			conn.setRequestProperty("content-type","application/json; charset=UTF-8");

        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        // 发送POST请求必须设置如下两行
        conn.setDoOutput(true);
        conn.setDoInput(true);
        // 获取URLConnection对象对应的输出流
        out = new PrintWriter(conn.getOutputStream());
        // 发送请求参数
        out.print(param);
        // flush输出流的缓冲
        out.flush();
        // 定义BufferedReader输入流来读取URL的响应
        in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));//转成utf-8格式
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            response.append(line);
        }
        // 使用finally块来关闭输出流、输入流
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
