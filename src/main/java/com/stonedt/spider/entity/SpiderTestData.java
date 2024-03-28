package com.stonedt.spider.entity;

import java.util.Date;

/**
  *  @author Lucifer
  *  @date 2021/7/30  
  */
public class SpiderTestData {
    private Integer id;

    /**
    * 站点名称
    */
    private String webName;

    /**
    * 提取出的名称
    */
    private String name;

    /**
    * 原链接
    */
    private String url;

    /**
    * 分类  1 正文 2 栏目 3 其他网站 4为添加站点3的成功，5为添加站点异常，6为在data_source有重复的站点   7 代码判断不是栏目,不是详情,不是新站点的
    */
    private Integer kind;

    /**
    * 提取出来的域名路径
    */
    private String webLink;

    /**
    * 站点id
    */
    private Integer webId;

    /**
    *  状态  1 未处理,2处理 默认1
    */
    private Integer status;

    /**
    * 深度 默认1
    */
    private Integer level;

    /**
    * 创建时间
    */
    private Date createTime;

    /**
    * 1 未插入xml表   2 已插入xml表
    */
    private Integer sign;

    /**
    * 1为清除
    */
    private Integer state;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWebName() {
        return webName;
    }

    public void setWebName(String webName) {
        this.webName = webName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getKind() {
        return kind;
    }

    public void setKind(Integer kind) {
        this.kind = kind;
    }

    public String getWebLink() {
        return webLink;
    }

    public void setWebLink(String webLink) {
        this.webLink = webLink;
    }

    public Integer getWebId() {
        return webId;
    }

    public void setWebId(Integer webId) {
        this.webId = webId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getSign() {
        return sign;
    }

    public void setSign(Integer sign) {
        this.sign = sign;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}