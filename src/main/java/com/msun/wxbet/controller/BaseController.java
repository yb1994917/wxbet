/*
 * Copyright 2015-2020 uuzu.com All right reserved.
 */
package com.msun.wxbet.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.lamfire.logger.Logger;
import com.lamfire.logger.LoggerFactory;
import com.msun.wxbet.support.JsonResult;

/**
 * @author zxc Aug 8, 2016 5:00:13 PM
 */
public class BaseController {

    static final Logger          _               = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    protected HttpServletRequest request;

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
