package com.stonedt.spider.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.stonedt.spider.service.WechatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.stonedt.spider.entity.SpiderArticle;
import com.stonedt.spider.entity.Wechat;
import com.stonedt.spider.service.InformationListService;
import com.stonedt.spider.util.ResultUtil;

@Controller
@RequestMapping("/social")
public class WechatController {
	@Autowired
	private WechatService wechatService;
	@Autowired
	private InformationListService informationListService;
	//寰俊鍒楄〃
	@RequestMapping(value="/wechat_list",produces = "text/plain;charset=utf-8")
	public ModelAndView wechat_list(ModelAndView mv,
			@RequestParam(value = "pageNum", defaultValue = "1" ,required = false)Integer pageNum,HttpServletResponse response,HttpServletRequest request){
		PageHelper.startPage(pageNum,25);
		//String typeNum = request.getParameter("type");
		int type = 1;
		Wechat wechat = new Wechat();
		wechat.setType(type);
		
		List<Wechat> listWechat = wechatService.listWechat(wechat);
		PageInfo<Wechat> page = new PageInfo<Wechat>(listWechat);
		mv.addObject("list", page);
		mv.setViewName("wechat_list");
		return mv;
	}
	
	//寰崥鍒楄〃
	@RequestMapping(value="/sina_list",produces = "text/plain;charset=utf-8")
	public ModelAndView sina_list(ModelAndView mv,
			@RequestParam(value = "pageNum", defaultValue = "1" ,required = false)Integer pageNum,HttpServletResponse response,HttpServletRequest request){
		PageHelper.startPage(pageNum,10);
		int type = 2;
		Wechat wechat = new Wechat();
		wechat.setType(type);
		List<Wechat> listWechat = wechatService.listWechat(wechat);
		PageInfo<Wechat> page = new PageInfo<Wechat>(listWechat);
		mv.addObject("list", page);
		mv.setViewName("sina_list");
		return mv;
	}
	
	//娣诲姞璧勬簮
	@RequestMapping(value="/toadd_social",produces = "text/plain;charset=utf-8")
	public ModelAndView toadd_social(HttpServletResponse response,HttpServletRequest request){
		ModelAndView mView = new ModelAndView();
		mView.addObject("flag",request.getParameter("flag"));
		mView.setViewName("add_social");
		return mView;
	}
	
	@RequestMapping("add_social")
	@ResponseBody
	public ResultUtil add_social(ModelAndView mv,HttpServletResponse response,HttpServletRequest request){
		Wechat weChat = new Wechat();
		String name = request.getParameter("name");//寰俊鏄电О寰崥鍚嶇О
		String alias ="";
		alias = request.getParameter("alias");//寰俊鍙�/寰崥瀹樼綉
		
		String origin = request.getParameter("origin");//寰俊婧�/寰崥璧勮鏉ユ簮
		
		String avatar_url ="";
		avatar_url= request.getParameter("avatar_url");//寰俊澶村儚URL/寰崥澶村儚url
		String qrcode_url ="";
		qrcode_url= request.getParameter("qrcode_url");//寰俊浜岀淮鐮乁RL
		String introduction ="";
		introduction = request.getParameter("introduction");//寰俊绠�浠�
		String fans_num_estimate = "";
		fans_num_estimate = request.getParameter("fans_num_estimate");//绮変笣閲�
		String typeNum = request.getParameter("type");//绫诲瀷锛岄粯璁や负0锛�1涓哄井淇″叕浼楀彿锛�2涓哄井鍗�
			int type=Integer.parseInt(typeNum);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
		String create_date=sdf.format(new Date());//璧勬簮鍒涘缓鏃堕棿
			weChat.setAlias(alias);
			weChat.setAvatar_url(avatar_url);
			weChat.setCreate_date(create_date);
			weChat.setFans_num_estimate(fans_num_estimate);
			weChat.setIntroduction(introduction);
			weChat.setName(name);
			weChat.setType(type);
			weChat.setQrcode_url(qrcode_url);
			weChat.setOrigin(origin);
		int count = wechatService.saveWechat(weChat);
		if(count != 0) {
			return ResultUtil.build(200, "鏁版嵁鎻掑叆鎴愬姛");
		}else{
			return ResultUtil.build(500, "鏁版嵁鎻掑叆澶辫触");
		}
		
	}
	
	//绀句氦寰俊
	@RequestMapping(value="/social_wechat",produces = "text/plain;charset=utf-8")
	public ModelAndView social_wechat(ModelAndView mv,
			@RequestParam(value = "pageNum", defaultValue = "1" ,required = false)Integer pageNum,HttpServletResponse response,HttpServletRequest request){
		PageHelper.startPage(pageNum,10);
		int article_type=9;
		List<SpiderArticle> listArticle = wechatService.listArticle(article_type);
		PageInfo<SpiderArticle> page = new PageInfo<SpiderArticle>(listArticle);
		mv.addObject("list", page);
		mv.setViewName("social_wechat");
		return mv;
	}
	
	//绀句氦寰崥
	@RequestMapping(value="/social_sina",produces = "text/plain;charset=utf-8")
	public ModelAndView social_sina(ModelAndView mv,
			@RequestParam(value = "pageNum", defaultValue = "1" ,required = false)Integer pageNum,HttpServletResponse response,HttpServletRequest request){
		String kind = request.getParameter("type");
		
		if(kind.equals("1")) {//澶鍒楄〃
			PageHelper.startPage(pageNum,10);
			int type = 2;
			Wechat wechat = new Wechat();
			wechat.setType(type);
			List<Wechat> listWechat = wechatService.listWechat(wechat);
			PageInfo<Wechat> page = new PageInfo<Wechat>(listWechat);
			mv.addObject("list", page);
			mv.setViewName("sina_social_list");
		}else {//璧勮
			PageHelper.startPage(pageNum,10);
			int article_type=8;
			List<SpiderArticle> listArticle = wechatService.listArticle(article_type);
			PageInfo<SpiderArticle> page = new PageInfo<SpiderArticle>(listArticle);
			mv.addObject("list", page);
			mv.setViewName("social_sina");
		}
		return mv;
	}
	
	/**
	 * 鑾峰彇寰崥鐨勫垪琛�
	 * @param mv
	 * @param pagenum
	 * @param request
	 * @return
	 */
	@RequestMapping("/listbysina")
	public ModelAndView getInformationByWebo(ModelAndView mv,@RequestParam(value = "pageNum", defaultValue = "1" ,required = false)Integer pagenum,HttpServletRequest request) {
		int id = Integer.parseInt(request.getParameter("id").toString());
		PageHelper.startPage(pagenum, 15);
		List<SpiderArticle> informationList =  wechatService.getWeboInformationList(id);//鑾峰彇鍒楄〃淇℃伅
		PageInfo<SpiderArticle> pageInfo = new PageInfo<SpiderArticle>(informationList);
		Wechat wechat = informationListService.getWechatName(id);//鑾峰彇寰崥鍚�
		String titleName = "寰崥";
		mv.addObject("seedName",wechat.getName());
		mv.addObject("list", pageInfo);
		mv.addObject("titleName", titleName);
		mv.addObject("id", id);
		mv.setViewName("informationByType_sinaList");
		return mv;
	}
	
	
	
	
	
	
}
