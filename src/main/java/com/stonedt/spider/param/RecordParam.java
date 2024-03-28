package com.stonedt.spider.param;

import com.stonedt.spider.entity.EsRecord;
import lombok.Data;

import java.util.List;

@Data
public class RecordParam {
    private List<EsRecord> recordList;

}
