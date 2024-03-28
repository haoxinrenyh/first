package com.stonedt.spider.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Statistic {
    private int id;
    private String file_json;
    private String data_json;
    private String trend_json;
    private String status_json ;
    private String type_json;
    private Date update_tme;
}
