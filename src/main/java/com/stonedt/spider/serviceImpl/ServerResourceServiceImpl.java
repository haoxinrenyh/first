package com.stonedt.spider.serviceImpl;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.stonedt.spider.dao.IServerResourceDao;
import com.stonedt.spider.entity.NewEntity;
import com.stonedt.spider.entity.ServerResource;
import com.stonedt.spider.entity.SpiderFlow;
import com.stonedt.spider.entity.WebsiteCategory;
import com.stonedt.spider.param.WebParam;
import com.stonedt.spider.service.IServerResourceService;
import com.stonedt.spider.service.SpiderFlowService;
import com.stonedt.spider.util.DateUtil;
import com.stonedt.spider.util.MongodbTestSpiderflow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("serverResourceService")
public class ServerResourceServiceImpl implements IServerResourceService {
    @Autowired
    private IServerResourceDao serverResourceDao;

    @Autowired
    private SpiderFlowService spiderFlowService;

    /**
     * 查询全部服务器资源清单与使用状态
     */
    @Override
    public List<ServerResource> queryServerResourceAll() {
        // TODO Auto-generated method stub
        return serverResourceDao.queryServerResourceAll();
    }

    @Override
    public List<Map<String, Object>> listGov(Integer type) {
        return serverResourceDao.listDataSource(type);
    }

    @Override
    public List<Map<String, Object>> listNews(Integer type) {
        return serverResourceDao.listDataSource(type);
    }

    @Override
    public List<Map<String, Object>> listForum(Integer type) {
        return serverResourceDao.listDataSource(type);
    }

    @Override
    public List<Map<String, Object>> listNewspaper(Integer type) {
        return serverResourceDao.listDataSource(type);
    }

    @Override
    public List<Map<String, Object>> listBlog(Integer type) {
        return serverResourceDao.listDataSource(type);
    }

    @Override
    public List<Map<String, Object>> listWebsite(Integer type) {
        return serverResourceDao.listDataSource(type);
    }

    @Override
    public int removeWeb(Integer id) {
        return serverResourceDao.removeWeb(id);
    }

    @Override
    public Map<String,Object> websiteCategoryPage(String limit,String keyword) {
        Map<String,Object> result = new HashMap<>();
        int count = 0;
        List<WebsiteCategory> list = new ArrayList<>();
        try {
            list = serverResourceDao.websiteCategoryPage(limit,keyword);
            count = serverResourceDao.WebsiteCategoryCount(keyword);
            if(limit!=null){
                result.put("total",count);
            }
            result.put("list",list);
        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public List<NewEntity> list(WebParam params) {
        List<NewEntity> list = serverResourceDao.list(params);
        return list;
    }

    @Override
    public List<Map<String, Object>> webCategory() {
        return serverResourceDao.webCategory();
    }

    @Override
    public Map<String,Object> findWebInfo(String limit, int webId, Integer status, Integer level,Integer type, String keyword) {

        List<SpiderFlow> list = serverResourceDao.findWebInfoPage(limit,webId,status,level,type,keyword);
        int count = serverResourceDao.findWebInfoCount(webId,status,level,type,keyword);
        NewEntity webInfo = serverResourceDao.webInfo(webId);

        Map<String,Object> result = new HashMap<>();
        result.put("list",list);
        result.put("total",count);
        if(webInfo!=null){
            result.put("webInfo",webInfo);
        }
        return result;
    }

    @Override
    public void updateWebType(Integer id, Integer webType) {
        Map<String, Integer> params = new HashMap<String, Integer>();
        params.put("id", id);
        params.put("webType", webType);
        serverResourceDao.updateWebType(params);

        //同步站点的修改到mongo
        //先根据站点id查询出所有的爬虫id
        List<Integer> spiderIds = spiderFlowService.selectIdByWebId(id);
        Map mongomap = new HashMap();
        mongomap.put("new_website_type", webType.toString());
        mongomap.put("website_id", id.toString());
        mongomap.put("update_time", DateUtil.format(new Date(),null));
        //链接mongodb
        DBCollection collection = null;
        MongoClient mongoClient = null;
        try {
                mongoClient = new MongoClient(MongodbTestSpiderflow.MONGO_HOST, MongodbTestSpiderflow.MONGO_PORT);
                DB db = mongoClient.getDB(MongodbTestSpiderflow.MONGO_DB_NAME);
                collection = db.getCollection(MongodbTestSpiderflow.MONGO_COLLECTION_NAME);
                System.out.println("mongoDB连接成功");

            for (Integer spiderId : spiderIds) {

                //同步到mongo
                mongomap.put("_id", spiderId.toString());
                try {
                    //调用mongo工具类
                    MongodbTestSpiderflow.update(mongomap, collection);
                }catch (Exception E){
                    E.printStackTrace();
                    System.out.println("出错id__"+spiderId.toString());
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            mongoClient.close();
            System.out.println("mongoDB连接已关闭");
        }



    }

    @Override
    public Set<String> loadSponsorNature() {
        Set<String> listSponsorNature = serverResourceDao.loadSponsorNature();
        return listSponsorNature;
    }

    @Override
    public int categorySave(String category_name,int user_id) {
        int result = 0;
        if(category_name!=null && user_id>0){
            try {
                result = serverResourceDao.categorySave(category_name,user_id);
            }catch (Exception e){
                result = 0;
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public WebsiteCategory findOneWebsiteCategory(String category_name) {
        return serverResourceDao.findOneWebsiteCategory(category_name);
    }

    @Override
    public WebsiteCategory findOneUpdateWebsiteCategory(String category_name ,int id) {
        return serverResourceDao.findOneUpdateWebsiteCategory(category_name, id);
    }

    @Override
    public int UpdateWebsiteCategory(String category_name, int user_id, int id) {
        return serverResourceDao.UpdateWebsiteCategory(category_name,user_id,id);
    }

    @Override
    public int removeWebsiteCategory(int id) {
        return serverResourceDao.removeWebsiteCategory(id);
    }

}
