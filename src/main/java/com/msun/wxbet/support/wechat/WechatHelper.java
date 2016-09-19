/*
 * Copyright 2015-2020 uuzu.com All right reserved.
 */
package com.msun.wxbet.support.wechat;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.lamfire.json.JSON;

/**
 * @author zxc Sep 19, 2016 2:31:20 PM
 */
@Service
public class WechatHelper {

    @Value("${appId}")
    private String            appId;

    @Autowired
    private WechatToken       wechatToken;
    @Autowired
    private WechatJsApiTicket wechatJsApiTicket;

    /**
     * 获取微信菜单
     * 
     * @return
     * @throws IOException
     * @throws WechatException
     */
    public String getMenuJson() throws IOException, WechatException {
        String url = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=" + wechatToken.getAccessToken();
        String result = HttpUtil.get(url);

        JSON json = JSON.fromJSONString(result);
        if (json.containsKey("errcode") && json.getIntValue("errcode") != 0) {
            throw new WechatException("设置微信菜单异常: " + json);
        }
        return result;
    }

    /**
     * 设置微信菜单
     * 
     * @param json
     * @throws IOException
     * @throws WechatException
     */
    public void setMenu(String menu) throws IOException, WechatException {
        String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + wechatToken.getAccessToken();
        String result = HttpUtil.post(url, menu);

        JSON json = JSON.fromJSONString(result);
        if (json.getIntValue("errcode") != 0) {
            throw new WechatException("设置微信菜单异常: " + result);
        }
    }

    /**
     * 发送模板消息
     * http://mp.weixin.qq.com/wiki/5/6dde9eaa909f83354e0094dc3ad99e05.html#.E5.8F.91.E9.80.81.E6.A8.A1.E6.9D.BF.E6
     * .B6.88.E6.81.AF
     * 
     * @param templateId 模板ID
     * @param url 跳转URL
     * @param openId 微信openId
     * @param map 模板替换元素
     */
    public void send(String templateId, String url, String openId, Map<String, String> map) throws IOException,
                                                                                           WechatException {
        JSON jsonObject = new JSON();
        jsonObject.put("touser", openId);
        jsonObject.put("template_id", templateId);
        jsonObject.put("url", url);

        JSON dataJson = new JSON();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            JSON itemJson = new JSON();
            itemJson.put("value", entry.getValue());
            itemJson.put("color", "#173177");
            dataJson.put(entry.getKey(), itemJson);
        }
        jsonObject.put("data", dataJson);
        String json = jsonObject.toString();
        String result = HttpUtil.post("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="
                                      + wechatToken.getAccessToken(), json);

        JSON resultJson = JSON.fromJSONString(result);
        if (resultJson.getIntValue("errcode") != 0 || !"ok".equals(resultJson.getString("errmsg"))) {
            throw new WechatException("Send template message error: " + result);
        }
    }

    public Map<String, String> initJsApiAttribute(String url) throws IOException, WechatException {
        Map<String, String> map = new HashMap<>();

        long timestamp = System.currentTimeMillis() / 1000;
        String noncestr = UUID.randomUUID().toString();
        String jsApiTicket = wechatJsApiTicket.getTicket();

        String s = "jsapi_ticket=" + jsApiTicket + "&noncestr=" + noncestr + "&timestamp=" + timestamp + "&url=" + url;
        String signature = DigestUtils.sha1Hex(s);

        map.put("jsApiAppId", appId);
        map.put("jsApiTimestamp", String.valueOf(timestamp));
        map.put("jsApiNonceStr", noncestr);
        map.put("jsApiSignature", signature);
        return map;
    }
}
