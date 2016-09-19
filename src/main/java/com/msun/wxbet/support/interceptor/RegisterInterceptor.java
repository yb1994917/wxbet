/*
 * Copyright 2015-2020 uuzu.com All right reserved.
 */
package com.msun.wxbet.support.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.msun.wxbet.cons.Definition;
import com.msun.wxbet.persistence.service.UserService;

/**
 * @author zxc Sep 19, 2016 3:03:57 PM
 */
@Component
public class RegisterInterceptor extends HandlerInterceptorAdapter implements Definition {

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String openId = (String) request.getSession(true).getAttribute(OPENID_SESSION_KEY);
        if (userService.getUserByOpenIdIfExist(openId) != null) {
            return true;
        } else {
            response.sendRedirect(request.getContextPath() + "/404");
            return false;
        }
    }
}
