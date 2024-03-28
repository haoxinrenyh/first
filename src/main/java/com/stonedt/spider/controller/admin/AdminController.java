package com.stonedt.spider.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.stonedt.spider.aop.AuthorizationInterceptor;
import com.stonedt.spider.aop.Token;
import com.stonedt.spider.controller.BaseController;
import com.stonedt.spider.entity.NewEntity;
import com.stonedt.spider.entity.WebsiteCategory;
import com.stonedt.spider.param.WebParam;
import com.stonedt.spider.service.IServerResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;



@RestController
@RequestMapping("/admin")
public class AdminController extends BaseController {
	@Autowired
	private IServerResourceService serverResourceService;
	
//	//数据源
//	@RequestMapping(value="/datasource",produces = "text/plain;charset=utf-8")
//	public ModelAndView datasource(ModelAndView mv,HttpServletResponse response,HttpServletRequest request){
//		List<ServerResource> list = serverResourceService.queryServerResourceAll();
//		mv.addObject("list",list);
//		mv.setViewName("datasource");
//		return mv;
//	}
//
//	//站点管理
//	@RequestMapping(value="/website_admin",produces = "text/plain;charset=utf-8")
//	public ModelAndView website_admin(ModelAndView mv, @RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum){
////		PageHelper.startPage(pageNum, 30);
////		List<Map<String, Object>> listHotWords = serverResourceService.listGov(3);
////		for (Map<String, Object> map : listHotWords) {
////			Object object = map.get("create_date");
////			map.put("create_date", object.toString().substring(0, 10));
////		}
////		PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String,Object>>(listHotWords);
////		mv.addObject("list", listHotWords);
////		mv.addObject("pageInfo", pageInfo);
//		mv.setViewName("datasource/website_admin");
//		return mv;
//	}

	@Token
	@PostMapping("/list")
	public Object list(@RequestBody WebParam params){
		JSONObject result =new JSONObject();
		try{
			if(params.getWebsite_type()!=null) {
				String[] split = params.getWebsite_type().split(",");
				List<Integer> typeList=new ArrayList<Integer>();
				for (String string : split) {
					typeList.add(Integer.valueOf(string));
				}
				params.setTypeList(typeList);
			}
			if(params.getPageNum()==null && params.getPageSize()==null){
				params.setPageNum(1);
			}
			if(params.getPageSize()==null){
				params.setPageSize(10);
			}
			PageHelper.startPage(params.getPageNum(), params.getPageSize());
			List<NewEntity> listHotWords = serverResourceService.list(params);
			for (NewEntity map : listHotWords) {
				Date create_date = map.getCreate_date();
				map.setDate(new SimpleDateFormat("yyyy-MM-dd").format(create_date));
			}
			PageInfo<NewEntity> pageInfo = new PageInfo<NewEntity>(listHotWords);
			result.put("list", listHotWords);
			result.put("pageInfo", pageInfo);
			return success_int(200,"查询成功!",result);
		}catch (Exception e){
			e.printStackTrace();
		}

		return error_int(501,"查询失败,请检查参数是否有误！","");
	}

	@Token
	@GetMapping("/webCategory")
	public Object webCategory(){
		try{
			return success_int(200,"查询成功!",serverResourceService.webCategory());
		}catch (Exception e){
			e.printStackTrace();
		}
		return error_int(501,"查询失败,请检查参数是否有误！","");
	}

	@Token
	@GetMapping(value = "/removeWeb")
	public Object removeWeb(@RequestParam(value = "id",required = false,defaultValue = "0")Integer id){
		if(id!=null&&id>0){
			int type = serverResourceService.removeWeb(id);
			if(type>0){
				return success_int(200,"删除成功!",type);
			}
		}
		return error_int(500,"删除失败!","");
	}


	@Token
	@GetMapping("/webInfo")
	public Object webInfo(@RequestParam(value = "pageNum",required = false,defaultValue = "0")int pageNum,
						  @RequestParam(value = "pageSize",required = false,defaultValue = "10")int pageSize,
						  @RequestParam(value = "webId",required = false,defaultValue = "0")int webId,
						  @RequestParam(value = "status",required = false,defaultValue = "-1")Integer status,
						  @RequestParam(value = "level",required = false,defaultValue = "0")Integer level,
						  @RequestParam(value = "type",required = false,defaultValue = "-1")Integer type,
						  @RequestParam(value = "keyword",required = false,defaultValue = "")String keyword){
		Map<String,Object> result = new HashMap<>();
		if(webId>=0 ){
			int beginIndex = (pageNum-1)*pageSize;
			String limit = "limit "+beginIndex+","+pageSize;

			result = serverResourceService.findWebInfo(limit,webId,status,level,type,keyword);

			return success_int(200,"网站详情查询成功！",result);
		}

		return error_int(500,"查询失败,请检查参数是否有误!","");
	}
	
//	@RequestMapping(value = "/loadSponsorNature", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
//	@ResponseBody
//	public Set<String> loadSponsorNature(){
//		Set<String> listSponsorNature=serverResourceService.loadSponsorNature();
//		listSponsorNature.remove("无");
//		listSponsorNature.remove(null);
//		return listSponsorNature;
//	}
//
//	@RequestMapping(value = "/updateWebType", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
//	@ResponseBody
//	public String updateWebType(Integer id, Integer webType) {
//		serverResourceService.updateWebType(id, webType);
//		return "OK";
//	}




	@Token
	@GetMapping("/category/list")
	public Object categoryList(){
		Map<String,Object> result = new HashMap<>();
		try {
				result = serverResourceService.websiteCategoryPage(null,null);
				return success_int(200,"网站分类查询成功！",result);

		}catch (Exception e){
			e.printStackTrace();
		}
		return error_int(500,"查询失败!","");
	}

	@Token
	@GetMapping("/category/page")
	public Object categoryPage(@RequestParam(value = "pageNum",required = false,defaultValue = "0")Integer pageNum,
							   @RequestParam(value = "pageSize",required = false,defaultValue = "10")Integer pageSize,
							   @RequestParam(value = "keyword",required = false)String  keyword){
		Map<String,Object> result = new HashMap<>();
		try {
			if(pageNum!=null && pageSize!=null){
				int beginIndex = (pageNum-1)*pageSize;
				String limit = "limit "+beginIndex+","+pageSize;
				result = serverResourceService.websiteCategoryPage(limit,keyword);
				return success_int(200,"网站分类查询成功！",result);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return error_int(500,"查询失败,请检查参数是否有误!","");
	}

	@Token
	@PostMapping("/category/save")
	public Object categorySave(@RequestBody WebsiteCategory category,HttpServletRequest request){
		int result = 0;
		if(category!=null &&category.getCategory_name()!=null){
			WebsiteCategory selectCategory = serverResourceService.findOneWebsiteCategory(category.getCategory_name());
			if(selectCategory==null){
				String userinfo = request.getAttribute(AuthorizationInterceptor.USER_INFO).toString();
				JSONObject userJson = JSONObject.parseObject(userinfo);
				if(userJson!=null && userJson.getInteger("id")!=null){
					result = serverResourceService.categorySave(category.getCategory_name(),userJson.getInteger("id"));
					return success_int(200,"新增成功!",result);
				}
			}else {
				return success_int(502,"该分类存在，请重新添加!","");
			}

		}
		return error_int(501,"新增失败，请检查参数是否有误!",result);
	}

	@Token
	@PostMapping("/category/update")
	public Object categoryUpdate(@RequestBody WebsiteCategory category,HttpServletRequest request){
		int result = 0;
		if(category!=null && category.getCategory_name()!=null && category.getId()>0 ){
			WebsiteCategory selectCategory = serverResourceService.findOneUpdateWebsiteCategory(category.getCategory_name(),category.getId());
			if(selectCategory==null){
				String userinfo = request.getAttribute(AuthorizationInterceptor.USER_INFO).toString();
				JSONObject userJson = JSONObject.parseObject(userinfo);
				if(userJson!=null && userJson.getInteger("id")!=null){
					result = serverResourceService.UpdateWebsiteCategory(category.getCategory_name(),userJson.getInteger("id"),category.getId());
					return success_int(200,"修改成功!",result);
				}
			}else {
				return success_int(502,"该分类存在，请重新添加!","");
			}
		}
		return error_int(501,"修改失败，请检查参数是否有误!",result);
	}

	@Token
	@GetMapping("/category/remove")
	public Object categoryRemove(@RequestParam(value = "id",required = false,defaultValue = "0")Integer id ){
		int result = 0;
		if(id!=null && id>0){
			result = serverResourceService.removeWebsiteCategory(id);
			if(result>0){
				return success_int(200,"删除成功!",result);
			}
		}
		return error_int(501,"删除失败，请检查参数是否有误!",result);
	}


//	@RequestMapping(value="/gov",produces = "text/plain;charset=utf-8")
//	public ModelAndView gov(ModelAndView mv,@RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum) {
//		PageHelper.startPage(pageNum, 30);
//		List<Map<String, Object>> listHotWords = serverResourceService.listGov(3);
//		for (Map<String, Object> map : listHotWords) {
//			Object object = map.get("create_date");
//			map.put("create_date", object.toString().substring(0, 10));
//		}
//		PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String,Object>>(listHotWords);
//		mv.addObject("list", listHotWords);
//		mv.addObject("pageInfo", pageInfo);
//		mv.setViewName("datasource/gov");
//		return mv;
//	}
//
//	@RequestMapping(value="/news",produces = "text/plain;charset=utf-8")
//	public ModelAndView news(ModelAndView mv,@RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum) {
//		PageHelper.startPage(pageNum, 30);
//		List<Map<String, Object>> listHotWords = serverResourceService.listNews(5);
//		for (Map<String, Object> map : listHotWords) {
//			Object object = map.get("create_date");
//			map.put("create_date", object.toString().substring(0, 10));
//		}
//		PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String,Object>>(listHotWords);
//		mv.addObject("list", listHotWords);
//		mv.addObject("pageInfo", pageInfo);
//		mv.setViewName("datasource/news");
//		return mv;
//	}
//
//	@RequestMapping(value="/forum",produces = "text/plain;charset=utf-8")
//	public ModelAndView forum(ModelAndView mv,@RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum) {
//		PageHelper.startPage(pageNum, 30);
//		List<Map<String, Object>> listHotWords = serverResourceService.listForum(4);
//		for (Map<String, Object> map : listHotWords) {
//			Object object = map.get("create_date");
//			map.put("create_date", object.toString().substring(0, 10));
//		}
//		PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String,Object>>(listHotWords);
//		mv.addObject("list", listHotWords);
//		mv.addObject("pageInfo", pageInfo);
//		mv.setViewName("datasource/forum");
//		return mv;
//	}
//
//	@RequestMapping(value="/newspaper",produces = "text/plain;charset=utf-8")
//	public ModelAndView newspaper(ModelAndView mv,@RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum) {
//		PageHelper.startPage(pageNum, 30);
//		List<Map<String, Object>> listHotWords = serverResourceService.listNewspaper(6);
//		for (Map<String, Object> map : listHotWords) {
//			Object object = map.get("create_date");
//			map.put("create_date", object.toString().substring(0, 10));
//		}
//		PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String,Object>>(listHotWords);
//		mv.addObject("list", listHotWords);
//		mv.addObject("pageInfo", pageInfo);
//		mv.setViewName("datasource/newspaper");
//		return mv;
//	}
//
//	@RequestMapping(value="/blog",produces = "text/plain;charset=utf-8")
//	public ModelAndView blog(ModelAndView mv,@RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum) {
//		PageHelper.startPage(pageNum, 30);
//		List<Map<String, Object>> listHotWords = serverResourceService.listBlog(11);
//		for (Map<String, Object> map : listHotWords) {
//			Object object = map.get("create_date");
//			map.put("create_date", object.toString().substring(0, 10));
//		}
//		PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String,Object>>(listHotWords);
//		mv.addObject("list", listHotWords);
//		mv.addObject("pageInfo", pageInfo);
//		mv.setViewName("datasource/blog");
//		return mv;
//	}
//
//	@RequestMapping(value="/website",produces = "text/plain;charset=utf-8")
//	public ModelAndView website(ModelAndView mv,@RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum) {
//		PageHelper.startPage(pageNum, 30);
//		List<Map<String, Object>> listHotWords = serverResourceService.listWebsite(8);
//		for (Map<String, Object> map : listHotWords) {
//			Object object = map.get("create_date");
//			map.put("create_date", object.toString().substring(0, 10));
//		}
//		PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String,Object>>(listHotWords);
//		mv.addObject("list", listHotWords);
//		mv.addObject("pageInfo", pageInfo);
//		mv.setViewName("datasource/website");
//		return mv;
//	}
//
//	//服务器列表
//	@RequestMapping(value="/toserverlist",produces = "text/plain;charset=utf-8")
//	public ModelAndView toserverlist(ModelAndView mv,HttpServletResponse response,HttpServletRequest request){
//		mv.setViewName("server_list");
//		return mv;
//	}
//
//	//系统概览
//	@RequestMapping(value="/tosystem_overview",produces = "text/plain;charset=utf-8")
//	public ModelAndView tosystem_overview(ModelAndView mv,HttpServletResponse response,HttpServletRequest request){
//		List<ServerResource> list = serverResourceService.queryServerResourceAll();
//		mv.addObject("list",list);
//		mv.setViewName("dashboard");
//		return mv;
//	}
//
//	//系统概览
//		@RequestMapping(value="/tosystem_overview_factory",produces = "text/plain;charset=utf-8")
//		public ModelAndView tosystem_overview_factory(ModelAndView mv,HttpServletResponse response,HttpServletRequest request){
//			List<ServerResource> list = serverResourceService.queryServerResourceAll();
//			mv.addObject("list",list);
//			mv.setViewName("system_overview-factory");
//			return mv;
//		}
//
//	@RequestMapping(value="/fail",produces = "text/plain;charset=utf-8")
//	public ModelAndView fail(ModelAndView mv,HttpServletResponse response,HttpServletRequest request){
//
//		mv.setViewName("fail");
//		return mv;
//	}
//	@RequestMapping(value="/success",produces = "text/plain;charset=utf-8")
//	public ModelAndView success(ModelAndView mv,HttpServletResponse response,HttpServletRequest request){
//
//		mv.setViewName("success");
//		return mv;
//	}
//
//	@RequestMapping(value="/left",produces = "text/plain;charset=utf-8")
//	public ModelAndView left(ModelAndView mv,HttpServletResponse response,HttpServletRequest request){
//
//		mv.setViewName("left");
//		return mv;
//	}
//
//	@RequestMapping(value="/top",produces = "text/plain;charset=utf-8")
//	public ModelAndView top(ModelAndView mv,HttpServletResponse response,HttpServletRequest request){
//
//		mv.setViewName("components/top");
//		return mv;
//	}
//
//
//	@RequestMapping(value="/footer",produces = "text/plain;charset=utf-8")
//	public ModelAndView footer(ModelAndView mv,HttpServletResponse response,HttpServletRequest request){
//
//		mv.setViewName("components/footer");
//		return mv;
//	}
//
//
//
//	@RequestMapping(value="/disabled_column",produces = "text/plain;charset=utf-8")
//	public ModelAndView disabled_column(ModelAndView mv){
//		mv.setViewName("datasource/disabled_column");
//		return mv;
//	}
}
