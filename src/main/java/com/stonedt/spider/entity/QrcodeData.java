package com.stonedt.spider.entity;

import lombok.Data;

/**
 * 二维码数据
 */
@Data
public class QrcodeData {

    /**
     * 二维码图片地址
     */
    private String qrcodeUrl;

    /**
     * 二维码场景值
     */
    private Integer code;
}
