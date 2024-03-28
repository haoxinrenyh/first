package com.stonedt.spider.entity;

import lombok.Data;

import java.util.List;

@Data
public class EsKeyword {
    private String keyword;
    private List<String> keys;
}
