package com.stonedt.spider.config.wechatAccount.handler;

import cn.hutool.json.JSONUtil;
import com.stonedt.spider.dao.UserDao;
import com.stonedt.spider.entity.StonedtUser;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class LogHandler extends AbstractHandler {
    @Autowired
    private UserDao userDao;
//    @Autowired
//    private UserApiDao userApiDao;
    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService wxMpService,
                                    WxSessionManager sessionManager) {
        //如果事件是取消订阅 则删除账户
        String event = wxMessage.getEvent();
        //获取用户id
        String openId = wxMessage.getFromUser();
        if ("unsubscribe".equals(event)) {
            StonedtUser stonedtUser = userDao.getByOpenId(openId);
            stonedtUser.setStatus(2);
            userDao.updateUserStatus(stonedtUser);
            log.info("用户取消订阅 停用账户");
//            UserEntity byOpenId = userDao.getByOpenId(openId);
//            log.info("用户取消订阅 屏蔽账户");
//            byOpenId.setStatus(0);
//            userDao.updateUserStatus(byOpenId);
        }
        log.info("\n接收到请求消息，内容：{}", JSONUtil.toJsonStr(wxMessage));
        return null;
    }

}
