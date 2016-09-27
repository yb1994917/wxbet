/*
 * Copyright 2015-2020 uuzu.com All right reserved.
 */
package com.msun.wxbet.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.lamfire.json.JSON;
import com.msun.wxbet.cons.Definition;
import com.msun.wxbet.persistence.model.User;
import com.msun.wxbet.persistence.service.BetService;
import com.msun.wxbet.persistence.service.ParticipateService;
import com.msun.wxbet.persistence.service.UserService;
import com.msun.wxbet.support.FileHelper;
import com.msun.wxbet.support.JsonResult;
import com.msun.wxbet.support.wechat.WechatPayment;

/**
 * @author zxc Aug 8, 2016 5:00:13 PM
 */
public class BaseController implements Definition {

    @Autowired
    protected FileHelper         fileHelper;
    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected HttpSession        session;

    @Autowired
    protected UserService        userService;
    @Autowired
    protected BetService         betService;
    @Autowired
    protected ParticipateService participateService;

    @Autowired
    protected WechatPayment      wechatPayment;

    public String openid() {
        return (String) session.getAttribute(OPENID_SESSION_KEY);
    }

    public User user() {
        return userService.getUserByOpenId(openid());
    }

    public Long userId() {
        User user = user();
        return user == null ? null : user.getId();
    }

    public JsonResult ok(String msg) {
        return JsonResult.successMsg(msg);
    }

    public JsonResult ok(String msg, Object[][] keyValues) {
        JSON data = new JSON();
        for (int i = 0; i < keyValues.length; i++) {
            data.put((String) keyValues[i][0], keyValues[i][1]);
        }
        return JsonResult.success(data, msg);
    }

    public JsonResult ok(String msg, Object data) {
        return JsonResult.success(data, msg);
    }

    public JsonResult fail(String msg) {
        return JsonResult.failMsg(msg);
    }
}
