/*
 * Copyright 2015-2020 uuzu.com All right reserved.
 */
package com.msun.wxbet.support.interceptor;

import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.lamfire.json.JSON;
import com.msun.wxbet.cons.Definition;

/**
 * @author zxc Sep 19, 2016 3:08:55 PM
 */
@Component
public class WechatAuthorizationInterceptor extends HandlerInterceptorAdapter implements Definition {

    @Value("${appId}")
    private String appId;
    @Value("${appSecret}")
    private String appSecret;
    @Value("${baseUrl}")
    private String baseUrl;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String code = request.getParameter("code");
        String openId = (String) request.getSession(true).getAttribute(OPENID_SESSION_KEY);

        if (openId == null && code != null) {
            // ------------------------
            // 如果参数中包含code且Session中openId为空，那么说明是微信回调的URL，通过code获取openId，存入Session
            // ------------------------
            String checkCodeUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=";
            checkCodeUrl += appId;
            checkCodeUrl += "&secret=";
            checkCodeUrl += appSecret;
            checkCodeUrl += "&code=";
            checkCodeUrl += code;
            checkCodeUrl += "&grant_type=authorization_code";
            String json = IOUtils.toString(new URL(checkCodeUrl), "UTF-8");
            JSON jsonObject = JSON.fromJSONString(json);

            _.info("[wechat oauth2] " + jsonObject);
            if (jsonObject.containsKey("openid")) {
                openId = jsonObject.getString("openid");
                request.getSession(true).setAttribute(OPENID_SESSION_KEY, openId);
                return true;
            } else {
                throw new RuntimeException("获取不到用户OpenId: " + json);
            }
        } else if (openId == null) {
            // ------------------------
            // 如果Session中不包含openId，则需要跳转到微信授权页面
            // ------------------------
            String backUrl = baseUrl + request.getRequestURI();
            if (request.getQueryString() != null) {
                backUrl += "?" + request.getQueryString();
            }
            String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=";
            url += appId;
            url += "&redirect_uri=";
            url += URLEncoder.encode(backUrl, "UTF-8");
            url += "&response_type=code&scope=snsapi_base#wechat_redirect";
            response.sendRedirect(url);
            return false;
        } else {
            // ------------------------
            // 如果Session中包含openId，说明已授权，不需要做任何处理
            // ------------------------
            return true;
        }
    }
}
