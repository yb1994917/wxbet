/*
 * Copyright 2015-2020 msun.com All right reserved.
 */
package com.msun.wxbet.support.wechat;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lamfire.json.JSON;

/**
 * @author zxc Sep 19, 2016 2:49:50 PM
 */
@Service
public class WechatJsApiTicket {

    private String      ticket;
    private long        invalidTime;

    @Autowired
    private WechatToken wechatToken;

    /**
     * 获取微信JSAPI Ticket
     * 
     * @return
     * @throws IOException
     */
    public synchronized String getTicket() throws IOException, WechatException {
        long now = System.currentTimeMillis();
        if (now > invalidTime) {
            String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="
                         + wechatToken.getAccessToken() + "&type=jsapi";
            JSON json = JSON.fromJSONString(HttpUtil.get(url, "UTF-8"));
            ticket = json.getString("ticket");
            invalidTime = now + json.getIntValue("expires_in") * 1000 - 60000;
        }
        return ticket;
    }
}
