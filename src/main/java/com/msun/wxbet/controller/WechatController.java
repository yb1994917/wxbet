/*
 * Copyright 2015-2020 msun.com All right reserved.
 */
package com.msun.wxbet.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.xml.sax.SAXException;

import com.lamfire.json.JSONArray;
import com.msun.wxbet.support.wechat.WechatException;
import com.msun.wxbet.support.wechat.WechatHelper;

/**
 * @author zxc Sep 19, 2016 2:29:59 PM
 */
@Controller
public class WechatController extends BaseController {

    @Value("${apiToken}")
    private String       apiToken;
    @Value("${userSet}")
    private String       userSet;
    @Autowired
    private WechatHelper wechatHelper;

    // 微信菜单设置
    @RequestMapping(value = "/wechatmenuinit", method = RequestMethod.GET)
    public ModelAndView menuinit() {
        ModelAndView mv = new ModelAndView("devtools/wechatMenuInit");
        try {
            mv.addObject("json", wechatHelper.getMenuJson());
        } catch (Exception e) {
            _.error("wechatmenuinit getMenuJson error!", e);
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
        return new ModelAndView("devtools/wechatSimulate")//
        .addObject("list", JSONArray.fromJSONString(userSet).asList());
    }

    @ResponseBody
    @RequestMapping(value = "/wechatsimulate/submit", method = RequestMethod.POST, produces = "text/html; charset=UTF-8")
    public String submit(String openId, HttpSession session) {
        session.setAttribute(OPENID_SESSION_KEY, openId);
        return "模拟微信浏览器登入成功";
    }

    /**
     * 该接口用于微信配置消息接口时校验
     * 
     * @param echostr
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/wechat/api", method = RequestMethod.GET)
    public String api(String echostr) {
        return echostr;
    }

    /**
     * 接收来自微信服务器的消息
     * 
     * @param signature
     * @param timestamp
     * @param nonce
     * @param requestBody
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/wechat/api", method = RequestMethod.POST, produces = "text/xml; charset=UTF-8")
    public String index(String signature, String timestamp, String nonce, @RequestBody String requestBody)
                                                                                                          throws IOException,
                                                                                                          SAXException,
                                                                                                          ParserConfigurationException {
        _.debug("wechat api request body:" + requestBody);
        String result = "";

        String token = apiToken;
        List<String> list = new ArrayList<>();
        list.add(token);
        list.add(timestamp);
        list.add(nonce);
        Collections.sort(list);
        StringBuilder sb = new StringBuilder();
        for (String s : list)
            sb.append(s);
        String sha1 = DigestUtils.sha1Hex(sb.toString());
        if (sha1.equals(signature)) {
            result = wechatHelper.process(requestBody);
        } else {
            _.error("error signature: " + signature);
        }
        return result;
    }
}
