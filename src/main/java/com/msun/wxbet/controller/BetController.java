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
public class BetController extends BaseController {

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list() {
        return new ModelAndView("bet/list");
    }

    @RequestMapping(value = "/apply", method = RequestMethod.GET)
    public ModelAndView apply() {
        return new ModelAndView("bet/apply");
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public ModelAndView detail() {
        return new ModelAndView("bet/applydetail");
    }

    @RequestMapping(value = "/detailOther", method = RequestMethod.GET)
    public ModelAndView detailOther() {
        return new ModelAndView("bet/detailOther");
    }

    @RequestMapping(value = "/applylist", method = RequestMethod.GET)
    public ModelAndView applylist() {
        return new ModelAndView("bet/applylist");
    }

    @RequestMapping(value = "/applyrecord", method = RequestMethod.GET)
    public ModelAndView applyrecord() {
        return new ModelAndView("bet/applyrecord");
    }

    @RequestMapping(value = "/showdetail", method = RequestMethod.GET)
    public ModelAndView showdetail() {
        return new ModelAndView("bet/showdetail");
    }
}
