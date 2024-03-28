package com.stonedt.spider.entity;

import lombok.Data;

import java.util.Date;

@Data
public class EsRecord {
    private int id;
    private int website_id;
    private int seed_id;
    private String es_index;
    private Date publish_time;
    private Date spider_time;
    private int type;
    private String article_public_id;
    private Date create_time;
    private Date update_time;
}
