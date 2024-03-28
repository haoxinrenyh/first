package com.stonedt.spider.controller.wechat;

import com.stonedt.spider.entity.ResponseResult;
import com.stonedt.spider.service.WechatAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController

@RequestMapping(value = "/wechatLogin")
public class UserController {
    @Autowired
    private WechatAccountService wechatAccountService;

    // 生成带参数的二维码，扫描关注微信公众号，自动登录网站
    @GetMapping(value = "/loginQRCode")
    public ResponseResult wechatLoginQRCode() throws Exception {
        return wechatAccountService.getWechatLoginQRCode();
    }

    /**
     * 检测登录
     * @param code 二维码场景值
     * @return 是否登录
     */
    @GetMapping("/checkLogin")
    public HashMap wechatMpCheckLogin(@RequestParam Integer code) throws Exception {
        return wechatAccountService.checkLogin(code);
    }

}
