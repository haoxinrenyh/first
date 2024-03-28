package com.stonedt.spider.serviceImpl;

import cn.hutool.json.JSONUtil;
import com.stonedt.spider.entity.QrcodeData;
import com.stonedt.spider.entity.ResponseResult;
import com.stonedt.spider.entity.StonedtUser;
import com.stonedt.spider.service.WechatAccountService;
import com.stonedt.spider.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 微信服务实现类
 */
@Service
@Slf4j
public class WechatAccountServiceImpl implements WechatAccountService {
    private static Set<Integer> generatedNumbers = new HashSet<>();
    @Value("${wx.mp.login-expire-time}")
    private String expireTime;
    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private RedisUtil redisUtil;
    @Override
    public ResponseResult getWechatLoginQRCode() throws WxErrorException {
        //生成随机不重复的登录码
        Integer code = generateRandomNumber();
        //请求微信接口，获取登录码地址
        //二维码失效时间
        Duration EXPIRE_TIME = Duration.ofMinutes(Integer.parseInt(expireTime));
        WxMpQrCodeTicket wxMpQrCodeTicket = wxMpService.getQrcodeService().qrCodeCreateTmpTicket(code, (int) EXPIRE_TIME.getSeconds());
        String ticket = wxMpQrCodeTicket.getTicket();
        QrcodeData qrcodeData = new QrcodeData();
        qrcodeData.setQrcodeUrl("https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + ticket);
        qrcodeData.setCode(code);
        return ResponseResult.success(qrcodeData);
    }
    public static synchronized int generateRandomNumber() {
        int randomNumber;
        do {
            randomNumber = ThreadLocalRandom.current().nextInt(100000, 1000000);
        } while (!generatedNumbers.add(randomNumber)); // 重复则重新生成

        return randomNumber;
    }

    @Override
    public HashMap checkLogin(Integer code) {
        HashMap<String, Object> result = new HashMap<>();
        if (redisUtil.existsKey(code.toString())) {
            String token = redisUtil.getKey(code.toString());
            result.put("token", token);
            result.put("msg","用户登录成功");
            result.put("code",200);
            return result;
        }
        result.put("token", null);
        result.put("msg","登录验证失败!");
        result.put("code",501);
        return result;
    }
}
