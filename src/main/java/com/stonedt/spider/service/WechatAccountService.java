package com.stonedt.spider.service;

import com.stonedt.spider.entity.ResponseResult;
import me.chanjar.weixin.common.error.WxErrorException;

import java.util.HashMap;

/**
 * 微信服务接口
 */
public interface WechatAccountService {

    ResponseResult getWechatLoginQRCode() throws WxErrorException;

    HashMap checkLogin(Integer code);
}
