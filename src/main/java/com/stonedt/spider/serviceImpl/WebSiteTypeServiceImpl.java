package com.stonedt.spider.serviceImpl;

import com.stonedt.spider.dao.WebSiteTypeDao;
import com.stonedt.spider.entity.WebsiteType;
import com.stonedt.spider.service.WebSiteTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 丁祥珂
 * @version V1.0
 * @date 2021/4/15 11:28
 * @Copyright
 * 数据类型
 */
@Service
public class WebSiteTypeServiceImpl implements WebSiteTypeService {

    @Autowired
    private WebSiteTypeDao webSiteTypeDao;

    /**
     * @author dxk
     * @date 2021/4/15 13:32
    查询id  typename 字段
     * @return
     * @throws
     * @since
    */
    @Override
    public List<Map<String, Object>> selectList() {
        return webSiteTypeDao.selectList();
    }

    @Override
    public List<Map<String, Object>> websitetypes(int categoryId) {
        return webSiteTypeDao.websitetypes(categoryId);
    }

    @Override
    public List<WebsiteType> page(String limit) {
        return webSiteTypeDao.page(limit);
    }

    @Override
    public int pageCount() {
        return webSiteTypeDao.pageCount();
    }

    @Override
    public int insertWebsiteType(WebsiteType websitetype) {
        return webSiteTypeDao.insertWebsiteType(websitetype);
    }

    @Override
    public int updateWebsiteType(WebsiteType websitetype) {
        return webSiteTypeDao.updateWebsiteType(websitetype);
    }

    @Override
    public WebsiteType findOne(WebsiteType websitetype) {
        return webSiteTypeDao.findOne(websitetype);
    }
}
