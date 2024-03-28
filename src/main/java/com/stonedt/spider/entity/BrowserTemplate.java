package com.stonedt.spider.entity;

import lombok.Data;

import java.util.Date;

@Data
public class BrowserTemplate {
    private Long id;
    private String temp_name;
    private String temp_xml;
    private Date create_time;
    private Date update_time;
}
