package com.stonedt.spider.entity;

import lombok.Data;

import java.util.Date;

@Data
public class WebsiteCategory {
    private int id;
    private String category_name;
    private int create_user_id;
    private Date create_time;
    private String username;
}
