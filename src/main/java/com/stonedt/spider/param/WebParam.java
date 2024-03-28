package com.stonedt.spider.param;

import lombok.Data;

import java.util.List;

@Data
public class WebParam {
    private Integer pageNum;
    private Integer pageSize;
    private String keyword;
    private String category_name;
    private String website_type;
    private List<Integer> typeList;
    private Integer sort_field;
    private Integer sort_type;
    private Integer log_error;
}
