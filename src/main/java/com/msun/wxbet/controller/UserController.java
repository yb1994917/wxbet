/*
 * Copyright 2015-2020 uuzu.com All right reserved.
 */
package com.msun.wxbet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author zxc Sep 14, 2016 5:11:47 PM
 */
@Controller
@RequestMapping(value = "/user")
public class UserController extends BaseController {

    // 个人中心
    @RequestMapping(value = "/center", method = RequestMethod.GET)
    public ModelAndView list() {
        return new ModelAndView("user/center")//
        .addObject("user", user());
    }

    // 访问鉴权
    @RequestMapping(value = "/auth", method = RequestMethod.GET)
    public ModelAndView detail() {
        return new ModelAndView("user/useridenf");
    }
}
