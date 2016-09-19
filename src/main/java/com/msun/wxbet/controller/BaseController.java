/*
 * Copyright 2015-2020 uuzu.com All right reserved.
 */
package com.msun.wxbet.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.msun.wxbet.cons.Definition;
import com.msun.wxbet.persistence.service.BetService;
import com.msun.wxbet.persistence.service.ParticipateService;
import com.msun.wxbet.persistence.service.UserService;
import com.msun.wxbet.support.FileHelper;
import com.msun.wxbet.support.JsonResult;

/**
 * @author zxc Aug 8, 2016 5:00:13 PM
 */
public class BaseController implements Definition {

    @Autowired
    protected FileHelper         fileHelper;
    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected UserService        userService;
    @Autowired
    protected BetService         betService;
    @Autowired
    protected ParticipateService participateService;

    public JsonResult ok(String msg) {
        return JsonResult.successMsg(msg);
    }

    public JsonResult ok(String msg, Object data) {
        return JsonResult.success(data, msg);
    }

    public JsonResult fail(String msg) {
        return JsonResult.failMsg(msg);
    }
}
