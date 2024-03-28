package com.stonedt.spider.config.wechatAccount.handler;


import com.stonedt.spider.util.TextBuilder;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MsgHandler extends AbstractHandler {
    @Value("${wx.mp.nlp-message}")
    private String nlp_message;
    @Value("${wx.mp.contact-us}")
    private String contact_us;
    @Value("${wx.mp.sentiment-message}")
    private String sentiment_message;
    @Value("${wx.mp.data-message}")
    private String data_message;
    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) {
        //获取消息点击事件
        String eventKey = wxMessage.getEventKey();
        if ("CONTACT_US".equals(eventKey)) {
            //联系我们
            return new TextBuilder().build(contact_us, wxMessage, weixinService);
        } else if ("NLP_MESSAGE".equals(eventKey)) {
            //NLP测试账号
            return new TextBuilder().build(nlp_message, wxMessage, weixinService);
        } else if ("DATA_MESSAGE".equals(eventKey)) {
            //数据采集
            return new TextBuilder().build(data_message, wxMessage, weixinService);
        } else if ("SENTIMENT_MESSAGE".equals(eventKey)) {
            //舆情测试账号
            return new TextBuilder().build(sentiment_message, wxMessage, weixinService);
        }
        //获取用户消息
        String content = wxMessage.getContent();
        if (!StringUtils.isEmpty(content)) {
            if (content.contains("de") || content.contains("mo"))
            return new TextBuilder().build(sentiment_message, wxMessage, weixinService);
        }
        return null;
    }

}
