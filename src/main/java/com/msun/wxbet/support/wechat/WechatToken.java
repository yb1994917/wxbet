/*
 * Copyright 2015-2020 uuzu.com All right reserved.
 */
package com.msun.wxbet.support.wechat;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.lamfire.json.JSON;

/**
 * @author zxc Sep 19, 2016 2:41:36 PM
 */
@Service
public class WechatToken {

    private String appId;
    private String appSecret;

    private String token;
    private long   invalidTime;

    /**
     * 获取微信Access Token http://mp.weixin.qq.com/wiki/14/9f9c82c1af308e3b14ba9b973f99a8ba.html
     * 
     * @return
     * @throws IOException
     */
    public synchronized String getAccessToken() throws IOException, WechatException {
        long now = System.currentTimeMillis();
        if (now > invalidTime) {
            String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appId
                         + "&secret=" + appSecret;
            String result = HttpUtil.get(url);
            JSON json = JSON.fromJSONString(result);
            if (json.containsKey("access_token")) {
                String token = json.getString("access_token");
                long invalidTime = now + json.getIntValue("expires_in") * 1000 - 60000;
            } else {
                throw new WechatException("获取access_token失败: " + result);
            }
        }
        return token;
    }
}
