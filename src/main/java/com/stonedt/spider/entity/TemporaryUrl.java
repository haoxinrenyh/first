package com.stonedt.spider.entity;

import java.util.Date;

/**
 * @author 丁祥珂
 * @version V1.0
 * @date 2021/5/6 14:36
 * @Copyright
 * 临时url表
 */
public class TemporaryUrl {

    private Integer id;

    private Integer spider_flow_id;

    private Integer website_id;

    private String url;

    private String json;

    private Date create_time;

    private Integer count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSpider_flow_id() {
        return spider_flow_id;
    }

    public void setSpider_flow_id(Integer spider_flow_id) {
        this.spider_flow_id = spider_flow_id;
    }

    public Integer getWebsite_id() {
        return website_id;
    }

    public void setWebsite_id(Integer website_id) {
        this.website_id = website_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }
}
