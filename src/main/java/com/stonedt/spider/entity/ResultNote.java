package com.stonedt.spider.entity;

import lombok.Data;

import java.util.Date;

@Data
public class ResultNote {
    private int id;
    private String english_key;
    private String china_key;
    private Date create_time;
    private Date update_time;
}
