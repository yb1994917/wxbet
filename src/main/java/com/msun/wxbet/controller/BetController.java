/*
 * Copyright 2015-2020 uuzu.com All right reserved.
 */
package com.msun.wxbet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author zxc Sep 14, 2016 5:11:21 PM
 */
@Controller
@RequestMapping(value = "/bet")
public class BetController extends BaseController {

    // 创建打赌
    @RequestMapping(value = "/apply", method = RequestMethod.GET)
    public ModelAndView bet_create() {
        return new ModelAndView("bet/apply");
    }

    @RequestMapping(value = "/apply", method = RequestMethod.POST)
    public ModelAndView bet_save() {
        return new ModelAndView("bet/apply");
    }

    // 打赌详情
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public ModelAndView bet_detail() {
        return new ModelAndView("bet/applydetail");
    }

    // 打赌记录
    @RequestMapping(value = "/progress", method = RequestMethod.GET)
    public ModelAndView progress() {
        return new ModelAndView("bet/addRecord");
    }

    // 参与打赌
    @RequestMapping(value = "/join", method = RequestMethod.GET)
    public ModelAndView showdetail() {
        return new ModelAndView("bet/showdetail");
    }

    // 打赌结果领取奖金
    @RequestMapping(value = "/result", method = RequestMethod.GET)
    public ModelAndView detailOther() {
        return new ModelAndView("bet/betResult");
    }

    // 打赌列表
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView applylist() {
        return new ModelAndView("bet/applylist");
    }

    // 过往打赌列表
    @RequestMapping(value = "/history", method = RequestMethod.GET)
    public ModelAndView applyrecord() {
        return new ModelAndView("bet/applyHistory");
    }
}
