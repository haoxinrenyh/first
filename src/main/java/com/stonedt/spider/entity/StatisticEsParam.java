package com.stonedt.spider.entity;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class StatisticEsParam {

    private int count;
    private double size;
    private String checkEsIndex;
    private Map<String,String> esIndexMap;

}
