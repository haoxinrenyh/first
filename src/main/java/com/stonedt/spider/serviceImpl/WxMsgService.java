package com.stonedt.spider.serviceImpl;

import cn.hutool.json.JSONUtil;
import com.stonedt.spider.dao.UserDao;
import com.stonedt.spider.entity.StonedtUser;
import com.stonedt.spider.util.MD5Util;
import com.stonedt.spider.util.RedisUtil;
import com.stonedt.spider.util.TextBuilder;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.apache.logging.log4j.util.Base64Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Description: 处理与微信api的交互逻辑
 * Author: <a href="https://github.com/zongzibinbin">abin</a>
 * Date: 2023-03-19
 */
@Service
@Slf4j
public class WxMsgService {
    /**
     * 用户的openId和前端登录场景code的映射关系
     */
    private static final String URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
    @Value("${wx.mp.callback}")
    private String callback;
    @Autowired
    private UserDao userDao;
    @Autowired
    private RedisUtil redisUtil;
    private final ConcurrentHashMap<String,Integer> codeMap = new ConcurrentHashMap<>();

    public WxMpXmlOutMessage scan(WxMpService wxMpService, WxMpXmlMessage wxMpXmlMessage) {
        System.out.println(">>>>>扫码行为验证");
        String openid = wxMpXmlMessage.getFromUser();
        //判断是登录扫码还是扫码关注公众号
        if ("".equals(wxMpXmlMessage.getEventKey())){
            log.info("扫码关注公众号 非登录");
            return null;
        }
        Integer loginCode = Integer.parseInt(getEventKey(wxMpXmlMessage));
        StonedtUser user = userDao.selectUserByOpenId(openid);
        codeMap.put(openid,loginCode);
        //如果已经注册,直接登录成功
        if (Objects.nonNull(user)) {
            if (user.getStatus()==0){
                user.setStatus(1);
            }
            log.info("微信用户{}已注册，返回校验信息".format(openid));
            //生成Token
            String md5 = MD5Util.MD5(user.getUsername()+ UUID.randomUUID());
            //储存redis
            redisUtil.set(md5,JSONUtil.toJsonStr(user));
            redisUtil.set(loginCode.toString(), md5);
            return new TextBuilder().build("登录成功！", wxMpXmlMessage, wxMpService);
        } else {
            //未关注过 注册用户
            String skipUrl = String.format(URL, wxMpService.getWxMpConfigStorage().getAppId(), URLEncoder.encode(callback));
            WxMpXmlOutMessage.TEXT().build();
            // 获取当前时间
            LocalDateTime now = LocalDateTime.now();
            // 定义日期时间格式化模式
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            // 格式化当前时间为字符串
            String formattedDateTime = now.format(formatter);
            return new TextBuilder().build("请点击授权：<a href=\"" + skipUrl + "\">点击此处登录</a> \n当前时间：" + formattedDateTime, wxMpXmlMessage, wxMpService);
        }
    }

    private String getEventKey(WxMpXmlMessage wxMpXmlMessage) {
        //扫码关注的渠道事件有前缀，需要去除
        return wxMpXmlMessage.getEventKey().replace("qrscene_", "");
    }

    /**
     * 用户授权后添加注册用户
     *
     * @param userInfo
     */
    public void authorize(WxOAuth2UserInfo userInfo) {
        String openid = userInfo.getOpenid();
        //获取code
        Integer loginCode = codeMap.get(openid);
        //生成Token
        String nickname = userInfo.getNickname();
        String md5 = MD5Util.MD5(nickname+ UUID.randomUUID());
        //去除重复点击登录
        StonedtUser byOpenId = userDao.getByOpenId(openid);
        if (byOpenId!=null){
            log.info("微信重复点击登录事件");
            return;
        }
        StonedtUser user = userDao.selectUserByOpenId(openid);
        //如果此用户之前取消订阅重新订阅
        //更新白名单status=1
        //如果user不为空，说明之前注册过  更新接口用户id
        if (user !=null){
            user.setStatus(1);
            userDao.updateUserStatus(user);//更新用户状态
            //储存redis
            redisUtil.set(md5,JSONUtil.toJsonStr(user));
            redisUtil.set(loginCode.toString(), md5);
        } else {
            StonedtUser userEntity = new StonedtUser();
            userEntity.setUsername(openid + System.currentTimeMillis());
            userEntity.setName(nickname);
            userEntity.setOpen_id(openid);
            userEntity.setPassword(MD5Util.getMD5("123456"));
            userEntity.setStatus(1);//账号状态，1正常
            userEntity.setUser_power(2);//用户权限：1管理员，2用户
            userDao.insertUser(userEntity);//保存用户信息
            //储存redis
            redisUtil.set(md5,JSONUtil.toJsonStr(userEntity));
            redisUtil.set(loginCode.toString(), md5);
        }
    }

}
