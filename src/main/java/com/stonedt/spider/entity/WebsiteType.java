package com.stonedt.spider.entity;

import lombok.Data;

import java.util.Date;

@Data
public class WebsiteType {
    private Integer id;
    private String typename;
    private String estype;
    private String esindex;
    private String kafka_queue_name;
    private String bloomname;
    private Date updatetime;
    private Integer userid;
    private String username;
    private Integer is_del;
}
