package com.stonedt.spider.entity;

import lombok.Data;

import java.util.Date;

@Data
public class StatisticCount {
    private int es_count;
    private int es_count_today;
    private int es_count_day;
    private double es_size;
    private int minio_count;
    private int minio_size;
    private double minio_disk_size;
    private int es_count_yesterday;
    private int es_count_week;
    private String group_json;
    private String category_json;
    private String type_json;
    private Date create_time;
    private Date create_date;
}
