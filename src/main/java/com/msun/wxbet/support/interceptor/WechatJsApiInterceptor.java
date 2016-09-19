/*
 * Copyright 2015-2020 uuzu.com All right reserved.
 */
package com.msun.wxbet.support.interceptor;

import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.msun.wxbet.cons.Definition;
import com.msun.wxbet.persistence.model.User;
import com.msun.wxbet.persistence.service.UserService;
import com.msun.wxbet.support.wechat.WechatHelper;

/**
 * @author zxc Sep 19, 2016 3:11:24 PM
 */
public class WechatJsApiInterceptor extends HandlerInterceptorAdapter implements Definition {

    private String       baseUrl;
    private boolean      debug;

    @Autowired
    private WechatHelper wechatHelper;
    @Autowired
    private UserService  userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取当前页面地址
        String url = baseUrl + request.getRequestURI();
        if (StringUtils.hasLength(request.getQueryString())) {
            url += "?" + request.getQueryString();
        }
        // 初始化微信JSAPI所需要的参数
        Map<String, String> attributes = wechatHelper.initJsApiAttribute(url);
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            request.setAttribute(entry.getKey(), entry.getValue());
        }

        // 是否是debug
        request.setAttribute("jsApiDebug", debug);
        // 当前用户
        User user = null;
        String openId = (String) request.getSession(true).getAttribute(OPENID_SESSION_KEY);
        if (openId != null) {
            user = userService.getUserByOpenIdIfExist(openId);
        }

        // 设置分享链接
        String shareUrl = baseUrl + "/share?redirectUrl=" + URLEncoder.encode("/wechatpage/mall/productlist", "UTF-8");
        if (user != null) {
            shareUrl = shareUrl + "&shareCode=" + user.getId();
        }
        request.setAttribute("jsShareText", "分享内容");
        request.setAttribute("jsShareUrl", shareUrl);
        return true;
    }
}
