package com.stonedt.spider.controller.datasource;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.stonedt.spider.aop.AuthorizationInterceptor;
import com.stonedt.spider.aop.Token;
import com.stonedt.spider.controller.BaseController;
import com.stonedt.spider.entity.DataSource;
import com.stonedt.spider.entity.SourceSite;
import com.stonedt.spider.entity.SpiderTestData;
import com.stonedt.spider.service.SourceSiteService;
import com.stonedt.spider.service.WebsiteshieService;
import com.stonedt.spider.util.ResultUtil;
import com.stonedt.spider.util.URLUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/datasourcesite")
public class SourceSiteController extends BaseController {


    @Autowired
    SourceSiteService sourceSiteService;

    @Autowired
    private WebsiteshieService websiteshieService;

    /***
     * 详情页面
     * @param
     * @return
     */
    @RequestMapping(value = "/detail/{website_id}")
    @ResponseBody
    public ResultUtil detail(@PathVariable Integer website_id, ModelAndView mv, HttpServletRequest request) {
    	String string = request.getAttribute(AuthorizationInterceptor.USER_INFO).toString();
    	//String parameter = request.getParameter();
    	//System.out.println(string);
    	ResultUtil resultUtil = new ResultUtil();
        SourceSite sourceSite = sourceSiteService.searchSiteById(website_id);
        resultUtil.setStatus(200);
        resultUtil.setData(sourceSite);
        return resultUtil;
    }


    /***
     * 修改状态
     * @param
     * @return
     */
    @RequestMapping(value = "/state/{state}/{id}")
    @ResponseBody
    public ResultUtil state(@PathVariable Integer state,@PathVariable Integer id) throws Exception {
        ResultUtil resultUtil=new ResultUtil();
          sourceSiteService.state(state,id);
          resultUtil.setStatus(200);
          return resultUtil;
    }
    /***
     * 清除临时spider_test_data表数据改变状态state为1
     * @param
     * @return
     */
    @RequestMapping(value = "/eliminate/{id}")
    @ResponseBody
    public ResultUtil eliminate(@PathVariable Integer id) throws Exception {
        ResultUtil resultUtil=new ResultUtil();
          sourceSiteService.eliminate(id);
          resultUtil.setStatus(200);
          return resultUtil;
    }
    /***
     * 查看当前站点抓取的栏目列表
     * @param
     * @return
     */
    @RequestMapping(value = "/see_section/{id}/{page_num}/{page_size}")
    @ResponseBody
    public ResultUtil seeSection(@PathVariable Integer id,@PathVariable Integer page_num,@PathVariable Integer page_size) throws Exception {
        ResultUtil resultUtil = new ResultUtil();
        PageHelper.startPage(page_num, page_size);
        ArrayList<SpiderTestData> list = sourceSiteService.seeSection(id);
        PageInfo<SpiderTestData> pageInfo = new PageInfo<SpiderTestData>(list);
        resultUtil.setStatus(200);
        resultUtil.setData(pageInfo);
        return resultUtil;
    }



    /**
     * @return
     * @throws
     * @author dxk
     * @date 2021/7/26 14:58
     * 跳转新增站点页面
     * @since
     */
    @RequestMapping("/addwebsite")
    public ModelAndView addwebsite(ModelAndView mv) {
        mv.setViewName("website/add_website");
        return mv;
    }

    /**
     * 保存站点
     * @since
     */
    @RequestMapping("/saveWeb")
    public Object spiderSave(@RequestBody DataSource dataSource,HttpServletRequest request) {
    	
    	String userinfo = request.getAttribute(AuthorizationInterceptor.USER_INFO).toString();
    	JSONObject parseObject = JSONObject.parseObject(userinfo);
        try {
            String websiteUrl = dataSource.getWebsiteUrl();
            String one_domain = URLUtil.getDomain(websiteUrl, 1);
            String two_domain = URLUtil.getDomain(websiteUrl, 2);
            String domainName = URLUtil.getDomainName(websiteUrl);
            dataSource.setOneDomain(one_domain);
            dataSource.setTwoDomain(two_domain);
            dataSource.setSitedomain(domainName);

            dataSource.setCreateUserId(parseObject.getInteger("id"));
            dataSource.setCreate_user(parseObject.getString("username"));
            DataSource check = websiteshieService.seleteWebsiteByTwoDomain(two_domain);
            if(check!=null){
                return error_int(502,"该域名存在，请检查!","");
            }else {
                int saveType = websiteshieService.insertWebSite(dataSource);
                if(saveType>0){
                    return success_int(200,"站点新增成功!",dataSource);
                }else {
                    return error_int(501,"新增失败,请检查参数是否有误!","");
                }
            }
//            if (dataSource2 != null) {
//                //更新内容
//                dataSource.setId(dataSource2.getId());
//                websiteshieService.updateWebSite(dataSource);
//            } else {
//                //新增内容
//
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return error_int(501,"新增失败,请检查参数是否有误!","");
    }

    /**
     * 更新站点
     * @since
     */
    @RequestMapping("/updateWeb")
    public Object updateWeb(@RequestBody DataSource dataSource,HttpServletRequest request) {

        String userinfo = request.getAttribute(AuthorizationInterceptor.USER_INFO).toString();
        JSONObject parseObject = JSONObject.parseObject(userinfo);
        try {
            int id = dataSource.getId();
            if(id>0){
                String websiteUrl = dataSource.getWebsiteUrl();
                String one_domain = URLUtil.getDomain(websiteUrl, 1);
                String two_domain = URLUtil.getDomain(websiteUrl, 2);
                String domainName = URLUtil.getDomainName(websiteUrl);
                dataSource.setOneDomain(one_domain);
                dataSource.setTwoDomain(two_domain);
                dataSource.setSitedomain(domainName);

                dataSource.setCreateUserId(parseObject.getInteger("id"));
                dataSource.setCreate_user(parseObject.getString("username"));
                DataSource check = websiteshieService.findCheckUpdateWeb(two_domain,id);
                if(check!=null){
                    return error_int(502,"该域名存在，请检查!","");
                }else {
                    int updateType = websiteshieService.updateWebSite(dataSource);
                    if(updateType>0){
                        return success_int(200,"站点修改成功!",dataSource);
                    }else {
                        return error_int(501,"修改失败,请检查参数是否有误!","");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return error_int(501,"修改失败,请检查参数是否有误!","");
    }

    /**
     * @author dxk
     * @date 2021/7/26 17:46
       通过url获取一二级域名
     * @return
     * @throws
     * @since
    */
    @GetMapping("/getDomain")
    public Object spiderSave(@RequestParam(value = "url",required = false) String url) {
        if(url!=null && url.indexOf("http")!=-1){
            Map map = new HashMap();
            String one_domain = URLUtil.getDomain(url, 1);
            String two_domain = URLUtil.getDomain(url, 2);
            map.put("one_domain",one_domain);
            map.put("two_domain",two_domain);
            return success_int(200,"域名解析成功!",map);
        }
    	return error_int(500,"域名解析失败, 请检查参数是否有误!","");
    }

}
