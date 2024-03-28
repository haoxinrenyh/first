package com.stonedt.spider.serviceImpl;

import com.stonedt.spider.dao.TemplateDao;
import com.stonedt.spider.entity.SpiderFlowTemplate;
import com.stonedt.spider.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TemplateServiceImpl implements TemplateService {

    @Autowired
    private TemplateDao templateDao;

    @Override
    public Map<String, Object> templatePage(String limit, Integer environment, Integer status) {
        List<SpiderFlowTemplate> templateList = new ArrayList<>();
        int total = 0;
        try {
            templateList = templateDao.templatePage(limit,environment,status);
            total = templateDao.templateCount(environment,status);
        }catch (Exception e){
            e.printStackTrace();
        }
        Map<String, Object> result = new HashMap<>();
        result.put("list",templateList);
        result.put("total",total);
        return result;
    }

    @Override
    public int templateCount(Integer environment, Integer status) {
        return templateDao.templateCount(environment,status);
    }

    @Override
    public List<Map<String, Object>> templates() {
        return templateDao.templates();
    }

    @Override
    public SpiderFlowTemplate templateInfo(int id) {
        SpiderFlowTemplate template = new SpiderFlowTemplate();
        try {
            if(id>0){
                template = templateDao.templateInfo(id);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return template;
    }

    @Override
    public int saveTemplate(SpiderFlowTemplate template) {
        return templateDao.saveTemplate(template);
    }

    @Override
    public int updateTemplate(SpiderFlowTemplate template) {
        return templateDao.updateTemplate(template);
    }

    @Override
    public int openTemplate(int id, Integer status,int userId , String userName) {
        int result = 0;
        try {
            if(id>0 && status!=null && (status==0 || status==1) ){
                SpiderFlowTemplate template = new SpiderFlowTemplate();
                template.setId(id);
                template.setStatus(status);
                template.setUpdate_user_id(userId);
                template.setUpdate_user_name(userName);
                result = templateDao.updateTemplate(template);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }


}
