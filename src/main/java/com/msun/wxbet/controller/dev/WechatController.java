/*
 * Copyright 2015-2020 uuzu.com All right reserved.
 */
package com.msun.wxbet.controller.dev;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lamfire.utils.Lists;
import com.msun.wxbet.controller.BaseController;
import com.msun.wxbet.support.wechat.WechatException;
import com.msun.wxbet.support.wechat.WechatHelper;

/**
 * @author zxc Sep 19, 2016 2:29:59 PM
 */
@Controller
public class WechatController extends BaseController {

    @Autowired
    private WechatHelper wechatHelper;

    // 微信菜单设置
    @RequestMapping(value = "/wechatmenuinit", method = RequestMethod.GET)
    public ModelAndView menuinit() {
        ModelAndView mv = new ModelAndView("devtools/wechatMenuInit");
        try {
            mv.addObject("json", wechatHelper.getMenuJson());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/wechatmenuinit/submit", method = RequestMethod.POST, produces = "text/html; charset=UTF-8")
    public String submit(String json) throws IOException, WechatException {
        wechatHelper.setMenu(json);
        return "微信菜单设置成功";
    }

    // 微信模拟登陆
    @RequestMapping(value = "/wechatsimulate", method = RequestMethod.GET)
    public ModelAndView simulate() {
        ModelAndView mv = new ModelAndView("devtools/wechatSimulate");
        mv.addObject("list", Lists.newLinkedList());
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/wechatsimulate/submit", method = RequestMethod.POST, produces = "text/html; charset=UTF-8")
    public String submit(String openId, HttpSession session) {
        session.setAttribute(OPENID_SESSION_KEY, openId);
        return "模拟微信浏览器登入成功";
    }
}
