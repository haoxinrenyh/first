package com.stonedt.spider.entity;

import lombok.Data;

import java.util.Map;

@Data
public class SpiderPlan {
    private String url;
    private String listSelect;
    private Map<String,String> infoSelect;
}
