package com.stonedt.spider.entity;

import lombok.Data;

import java.util.Date;

@Data
public class MinioFileData {
    private int id;
    private String file_name;
    private double size;
    private Date file_time;
    private Date update_time;
}
