package com.stonedt.spider.controller.datasource;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.stonedt.spider.aop.AuthorizationInterceptor;
import com.stonedt.spider.aop.Token;
import com.stonedt.spider.config.IpConfiguration;
import com.stonedt.spider.controller.BaseController;
import com.stonedt.spider.entity.*;
import com.stonedt.spider.param.RecordParam;
import com.stonedt.spider.scheduled.SpiderJob;
import com.stonedt.spider.scheduled.SpiderJobManager;
import com.stonedt.spider.service.*;
import com.stonedt.spider.util.DateUtil;
import com.stonedt.spider.util.MongodbTestSpiderflow;
import com.stonedt.spider.util.ResultUtil;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/spiderflow")
public class SpiderFlowController extends BaseController {

    private static Logger logger = Logger.getLogger(SpiderFlowController.class);

    @Autowired
    SpiderFlowService spiderFlowService;

    @Autowired
    private WebSiteTypeService webSiteTypeService;

    @Autowired
    private SpiderFlowDataSourceService dataSourceService;
    
    
    @Autowired
    SourceSiteService ssService;
    
    
    @Autowired
    private SpiderService spiderService;
    
    
    @Autowired
    private SpiderJobManager spiderJobManager;
    
    @Autowired
    private CustomerService customerService;
    
    @Autowired
	private IpConfiguration ip;
    

    /***
     * 详情页面
     * @param
     * @return
     */
    @RequestMapping(value = "/spiderlist/{website_id}/{pageNo}/{pageSize}")
    @ResponseBody
    @Token
    public ResultUtil spiderlist(@PathVariable Integer website_id, @PathVariable Integer pageNo, @PathVariable Integer pageSize) {
        ResultUtil resultUtil = new ResultUtil();
        PageHelper.startPage(pageNo, pageSize);
        List<SpiderFlow> spiderFlows = spiderFlowService.searchSpiderFlows(website_id,pageNo,pageSize);
        if(spiderFlows!=null) {
        	PageInfo<SpiderFlow> pageInfo = new PageInfo<SpiderFlow>(spiderFlows);
            Map map = new HashMap();
            map.put("website_id",website_id.toString());
            Integer count = 0;
    		try {
    			count = MongodbTestSpiderflow.searchCount(map, null);
    		} catch (UnknownHostException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
            pageInfo.setTotal(count);
            pageInfo.setPageSize(pageSize);
            int pages = count % pageSize == 0 ? count/pageSize : count/pageSize +1;
            pageInfo.setPages(pages);
            resultUtil.setStatus(200);
            resultUtil.setData(pageInfo);
        }else {
        	
        	resultUtil.setStatus(200);
        	JSONObject jsonObject = new JSONObject();
        	jsonObject.put("total", 0);
        	jsonObject.put("list", new JSONArray());
        	resultUtil.setData(jsonObject);
        }
        return resultUtil;
    }


    /***
     * 详情页面
     * @param
     * @return
     */
    @RequestMapping(value = "/detail/{id}")
    @ResponseBody
    public ModelAndView detail(@PathVariable Integer id, ModelAndView mv) {
        //
        mv.setViewName("spiderflow/editor");
        mv.addObject("url", fileRead());
        mv.addObject("id", id.toString());
        mv.addObject("websiteId", "");
        return mv;

    }

    @RequestMapping(value = "/saveSkip")
    @ResponseBody
    public ModelAndView saveSkip(@RequestParam Integer websiteId, ModelAndView mv) {
        //
        mv.setViewName("spiderflow/editor");
        mv.addObject("url", fileRead());
        mv.addObject("id", "");
        mv.addObject("websiteId", websiteId.toString());
        return mv;

    }
    
    
    /**
     * 返回客户标签
     * @return
     */
    @RequestMapping(value = "/customerlist")
    @ResponseBody
    @Token
    public ResultUtil customerlist() {
    	List<CustomerEntity> list = customerService.getList();
    	return new ResultUtil(list);
    }
    
    
    

    /**
     * 获取爬虫xml
     *
     * @param id
     * @return
     */
    @Token
    @GetMapping("/xml")
    public Object getXml(@RequestParam int id) {
        try {
            SpiderFlow flow = spiderFlowService.searchSpiderFlow(id);
            if (flow != null) {
                JSONObject param = new JSONObject();
                param.put("source_name", flow.getWebsite_name());
                param.put("sourcewebsitename", flow.getWebsite_name());
                param.put("extend_string_five", flow.getSeed_name());
                param.put("bloomName", flow.getBloomname());
//            param.put("bloomName", "BLOOM_INFOS_TEST123");
                param.put("websitelogo", flow.getWebsite_ico());
                param.put("otherwebsiteid", flow.getWebsite_id());
                param.put("website_id", flow.getWebsite_id());
                param.put("seed_id", flow.getId());
                param.put("es_type", flow.getEstype());
                param.put("es_index", flow.getEsindex());
                param.put("hbase_table", flow.getHbase_table());
                param.put("kafka_queue_name", flow.getKafka_queue_name());
                param.put("customlable", flow.getCustomlable());
                param.put("key_sources_flag", flow.getKey_sources_flag());
                param.put("spider_type", flow.getSpider_type());

                param.put("sendDate", DateUtil.getDate());
                //获取服务器ip
                param.put("sendIp", SpiderJob.getLocalIP());
                //param.put("sendPort", SpiderJob.getLocalPort());

                param.put("sendPort", ip.getPort());


//                param.put("key_sources_flag", flow.getKey_sources_flag());
//                param.put("spider_type", flow.getSpider_type());
                param.put("type", flow.getType());
                param.put("temp_id", flow.getTemp_id());


                Integer new_website_type = flow.getNew_website_type();
                if (new_website_type == null) {
                    new_website_type = 8;
                }
                param.put("classify", new_website_type);
                param.put("similarvolume", 1);
                double a = Math.random() * 100;
                DecimalFormat df = new DecimalFormat("0.00");
                param.put("heatvolume", df.format(a));

                Map map = new HashMap();
                map.put("xml", flow.getXml());
                //转成json字符串
                String json = param.toJSONString();
                //加密
                map.put("json", SpiderJob.compress(json));
                map.put("spiderFlow", flow);
                return success_int(200,"查询成功!",map);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return error_int(501,"查询详情失败,请检查参数是否有误! ","");
    }

    @Token
    @GetMapping("/removeSpiderFlow")
    public Object removeSpiderFlow(@RequestParam(value = "id",required = false,defaultValue = "0")Integer id,
                                   @RequestParam(value = "is_del",required = false,defaultValue = "0")Integer is_del){
        if(id!=null&&id>0 && is_del==1){
            int type = spiderFlowService.removeSpiderFlow(id,is_del);
            if(type>0){
                return success_int(200,"查询成功",type);
            }
        }
        return error_int(501,"删除失败，请检查参数是否有误!","");
    }


    public static String fileRead() {

        // 获取系统环境变量
        String getenv = System.getenv("PROPERTIES_SPIDER");
        // 如果开发时没有系统环境变量 使用自己自定义一个路径
        String path = "";
        if (null == getenv) {
//            path = "D:\\env";
            path = "/opt/properties-spider";
//        	  path = "/Users/wangyi/Desktop/properties-spider";
        } else {
            path = getenv.replace('\\', '/');
        }
        try {
            BufferedReader systemBr = new BufferedReader(
                    new InputStreamReader(new FileInputStream(new File(path + "/config/spider.properties")), "utf-8"));
            String SystemLine = null;
            String readline = "";
            while ((SystemLine = systemBr.readLine()) != null) {
                if (!SystemLine.trim().startsWith("#")) {
                    String[] split = SystemLine.split("=");

                    for (int i = 1; i < split.length; i++) {
                        if (i < split.length - 1) {
                            readline += split[i] + "=";
                        } else {
                            readline += split[i];
                        }
                    }
                    if (split[0].equals("spiderFlow")) {
                        systemBr.close();

//                        readline = "http://192.168.71.83:8383/";
                        logger.info("读取成功,url=" + readline);
                        return readline;
                    }
                    readline = "";
                }
            }
            systemBr.close();

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("spiderFlow路径读取失败" + e.getMessage());
        }
        logger.error("spiderFlow路径读取失败,没有找的对应配置");
        return "";
    }


    /**
     * @return
     * @throws
     * @author dxk
     * @date 2021/4/15 9:55
     * 保存爬虫xml
     * @since
     */
    @Token
    @PostMapping("/save")
    public Object spiderSave(@RequestBody SpiderFlow spiderFlow,HttpServletRequest request) {
        try {
            if(spiderFlow!=null && spiderFlow.getSole_sign()!=null){
                int soleSignCount = spiderFlowService.checkSoleSign(spiderFlow.getId(),spiderFlow.getSole_sign());
                if(soleSignCount>0){
                    return error_int(502,"种子url重复，请重新输入!","");
                }

                String user_info = request.getAttribute(AuthorizationInterceptor.USER_INFO).toString();
                JSONObject parseObject = JSONObject.parseObject(user_info);

                String update_user = parseObject.getString("username");
                Integer update_user_id = Integer.parseInt(parseObject.getString("id"));
                //根据唯一标识查询
                if (spiderFlow.getId() != null) {

                    spiderFlow.setUpdate_user(update_user);
                    spiderFlow.setUpdate_user_id(update_user_id);
                    spiderFlow.setSeed_url(spiderFlow.getSole_sign());

                    DataSource ds =new DataSource();
                    ds.setUpdate_user(update_user);
                    ds.setUpdate_user_id(update_user_id);
                    ds.setId(spiderFlow.getWebsite_id());

                    ssService.updateUserByID(ds);

                    spiderFlowService.updateSpiderFlow(spiderFlow);
                    if(null != spiderFlow.getSeed_status() && 1== spiderFlow.getSeed_status()) {
                        spiderService.checkAddSpiderByCron(spiderFlow.getCron());
                    }

                } else {

                    DataSource ds =new DataSource();
                    ds.setUpdate_user(update_user);
                    ds.setUpdate_user_id(update_user_id);
                    ds.setId(spiderFlow.getWebsite_id());

                    ssService.updateUserByID(ds);

                    spiderFlow.setSeed_url(spiderFlow.getSole_sign());
                    spiderFlow.setCreate_user(update_user);
                    spiderFlow.setCreate_user_id(update_user_id);
                    spiderFlowService.saveSpiderFlow(spiderFlow);
                    Integer seed_status = spiderFlow.getSeed_status();
                    if(null != seed_status && 1== seed_status){
                        spiderService.checkAddSpiderByCron(spiderFlow.getCron());
                    }
                }
                return success_int(200,"保存成功!",spiderFlow.getId());
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return error_int(500,"保存失败,请检查参数是否有误!","");
    }


    /**
     * 浏览器插件-生成采集流程
     */
    @PostMapping("/savePlan")
    public Object savePlan(@RequestBody SpiderPlan spiderPlan){
        try {

            int saveType = spiderFlowService.saveBrowserPlan(spiderPlan);
            if(saveType>0){
                return success_int(200,"保存成功",saveType);
            }

//            if(null != seed_status && 1== seed_status){
//                spiderService.checkAddSpiderByCron(spiderFlow.getCron());
//            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return error_int(500,"参数有误","");
    }



    /**
     * @return
     * @throws
     * @author dxk
     * @date 2021/4/15 18:02
     * 获取表达式最近五次运行时间
     * @since
     */
    @RequestMapping("/recent5TriggerTime")
    @ResponseBody
    @Token
    public List<String> getRecent5TriggerTime(@RequestParam String cron) {
        return spiderFlowService.getRecentTriggerTime(cron, 5);
    }

    /**
     * @return
     * @throws
     * @author dxk
     * @date 2021/4/15 11:24
     * 查询所有的数据类型    id  typename 字段
     * @since
     */
    @RequestMapping("/websitetype")
    @ResponseBody
    @Token
    public ResultUtil websitetype() {
        List<Map<String, Object>> list = webSiteTypeService.selectList();
        return new ResultUtil(list);
    }

    /**
     * @return
     * @throws
     * @author dxk
     * @date 2021/4/19 10:19
     * 修改爬虫状态,同时操作定时任务的内存
     * @since
     */
    @Token
    @RequestMapping("/updateSpiderStatus")
    public Object updateSpiderStatus(@RequestBody SpiderFlow spiderFlow) {

//        int flag = spiderFlowService.updateSpiderFlowById(spiderFlow);
        //根据状态修改定时任务
//        spiderFlowService.statusSpiderFlowJob(spiderFlow);

        if(spiderFlow!=null && spiderFlow.getId()!=null && spiderFlow.getId()>0  && spiderFlow.getSeed_status()!=null ){
            SpiderFlow sf = new SpiderFlow();
            sf.setId(spiderFlow.getId());
            sf.setSeed_status(spiderFlow.getSeed_status());
            int type = spiderFlowService.updateSpiderFlow(sf);
            if(type>0){
                SpiderFlow info = spiderFlowService.searchSpiderFlow(spiderFlow.getId());
                if(info!=null && info.getSeed_status()!=null && info.getSeed_status()==1 ) {
                    spiderService.checkAddSpiderByCron(info.getCron());
                }
                return success_int(200,"修改成功!",type);
            }
        }
        return error_int(501,"修改失败,请检查参数是否有误!","");
    }


    /**
     * @return
     * @throws
     * @author dxk
     * @date 2021/5/6 14:01
     * 查询spiderflow配置的数据源
     * @since
     */
    @RequestMapping("/all")
    @ResponseBody
    public List<SpiderFlowDataSource> all() {

        return dataSourceService.selectListAll();
    }

    /**
     * @return
     * @throws
     * @author dxk
     * @date 2021/5/18 15:51
     * 根据数据类型获取测试需要的json
     * @since
     */
    @RequestMapping("/getTypeJson")
    @ResponseBody
    public ResultUtil getTypeJson(@RequestParam("typeid") int typeid, @RequestParam("websiteId") int websiteId,
                                  @RequestParam("seed_name") String seed_name, @RequestParam("id") Integer id) {
        Map<String, Object> map = spiderFlowService.getTypeJson(typeid, websiteId);
        JSONObject param = new JSONObject();
        param.put("source_name", map.get("website_name"));
        param.put("sourcewebsitename", map.get("website_name"));
        param.put("extend_string_five", seed_name);
        param.put("bloomName", map.get("bloomname"));
        param.put("websitelogo", map.get("website_ico"));
        param.put("otherwebsiteid", websiteId);
        param.put("website_id", websiteId);
        if (id != null) {
            param.put("seed_id", id);
        } else {
            param.put("seed_id", "test");
        }

        param.put("es_type", map.get("estype"));
        param.put("es_index", map.get("esindex"));
        param.put("hbase_table", map.get("hbase_table"));
        param.put("kafka_queue_name", map.get("kafka_queue_name"));
        param.put("sendDate", DateUtil.getDate());
        //获取服务器ip
        param.put("sendIp", SpiderJob.getLocalIP());
       // param.put("sendPort", SpiderJob.getLocalPort());
        param.put("sendPort",ip.getPort());
        Object new_website_type = map.get("new_website_type");
        if (new_website_type == null) {
            new_website_type = 8;
        }
        param.put("classify", new_website_type);
        param.put("similarvolume", 1);
        double a = Math.random() * 100;
        DecimalFormat df = new DecimalFormat("0.00");
        param.put("heatvolume", df.format(a));

        String compress = SpiderJob.compress(param.toJSONString());
        ResultUtil resultUtil = new ResultUtil();
        resultUtil.setData(compress);
        resultUtil.setStatus(200);
        return resultUtil;
    }

    @Token
    @GetMapping("/websitetypes")
    public Object websitetypes(@RequestParam(value = "categoryId",required = false,defaultValue = "0")int categoryId){
        try {
            if(categoryId>0){
                return success_int(200,"查询成功!",webSiteTypeService.websitetypes(categoryId));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return error_int(501,"查询失败，请检查参数是否有误！","");
    }


    /**
     * 分页查询
     */
    @Token
    @GetMapping("/websitetype/page")
    public Object page(@RequestParam(value = "pageNum",required = false,defaultValue = "1")int pageNum,
                       @RequestParam(value = "pageSize",required = false,defaultValue = "10")int pageSize){
        try {
            int beginIndex = (pageNum-1)*pageSize;
            String limit = "limit "+beginIndex+","+pageSize;
            List<WebsiteType> list =  webSiteTypeService.page(limit);
            int total = webSiteTypeService.pageCount();
            Map<String,Object> result = new HashMap<>();
            result.put("list", list);
            result.put("total", total);
            return success_int(200,"查询成功!", result);
        }catch (Exception e){
            e.printStackTrace();
        }
        return error_int(501,"查询失败, 请检查参数是否有误!","");
    }

    @Token
    @GetMapping("/websitetype/info")
    public Object websitetypes_info(@RequestParam(value = "id",required = false,defaultValue = "0")int id){
        try {
            if(id>0){
                WebsiteType param = new WebsiteType();
                param.setId(id);
                WebsiteType websiteType = webSiteTypeService.findOne(param);
                if(websiteType!=null){
                    return success_int(200,"查询成功!",websiteType);
                }else {
                    return error_int(501,"查询失败, 该数据不存在,请检查参数!","");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return error_int(501,"查询失败，请检查参数是否有误！","");
    }

    @Token
    @PostMapping("/websitetype/save")
    public Object websitetype_save(@RequestBody WebsiteType websitetype,HttpServletRequest request){
        try {
            int userid = getUserId(request);
            if(userid<1){
                return error_int(502,"用户验证有误!","");
            }
            websitetype.setUserid(userid);
            if(websitetype!=null && websitetype.getTypename()!=null && websitetype.getEsindex()!=null && websitetype.getKafka_queue_name()!=null && websitetype.getBloomname()!=null ){
                if(websitetype.getId()!=null){
                    WebsiteType info = webSiteTypeService.findOne(websitetype);
                    if(info!=null){
                        int updateType = webSiteTypeService.updateWebsiteType(websitetype);
                        if(updateType>0){
                           return success_int(200,"保存成功!",updateType);
                        }
                    }
                }else {
                    if(websitetype.getEstype()==null){
                        websitetype.setEstype("infor");
                    }
                    int insertType = webSiteTypeService.insertWebsiteType(websitetype);
                    if(insertType>0){
                        return success_int(200,"保存成功!",insertType);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return error_int(501,"保存失败, 请检查参数是否有误!","");
    }

    @Token
    @GetMapping("/websitetype/remove")
    public Object websitetype_remove(@RequestParam("id")int id){
        try {
            WebsiteType remove = new WebsiteType();
            remove.setId(id);
            WebsiteType info = webSiteTypeService.findOne(remove);
            if(info!=null){
                remove.setIs_del(1);
                int removeType = webSiteTypeService.updateWebsiteType(remove);
                if(removeType>0){
                    return success_int(200,"删除成功!","");
                }
            }else {
                return error_int(501,"删除失败, 该数据不存在,请检查参数!","");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return error_int(501,"删除失败, 请检查参数是否有误!","");
    }

    @PostMapping("/updateSeedCount")
    public void updateSeedCount(@RequestBody RecordParam recordParam ){
        try {
            if(recordParam!=null && recordParam.getRecordList()!=null && recordParam.getRecordList().size()>0){
                spiderFlowService.updateSeedCount(recordParam.getRecordList());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
