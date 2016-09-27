/*
 * Copyright 2015-2020 uuzu.com All right reserved.
 */
package com.msun.wxbet.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.xml.sax.SAXException;

import com.lamfire.json.JSON;
import com.lamfire.utils.DateFormatUtils;
import com.lamfire.utils.StringUtils;
import com.msun.wxbet.support.JsonResult;
import com.msun.wxbet.support.wechat.WechatException;

/**
 * @author zxc Aug 4, 2016 9:09:40 PM
 */
@Controller
public class IndexController extends BaseController {

    @RequestMapping(value = { "/", "/index" })
    public ModelAndView index() {
        // return new ModelAndView("index");
        return new ModelAndView(new RedirectView("/user/center", true, false));
    }

    /**
     * 支付成功通知(微信回调)
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/wechatpaymentnotify", method = RequestMethod.POST)
    public String notify(@RequestBody String xml) throws ParserConfigurationException, SAXException, WechatException,
                                                 IOException {
        String orderId = wechatPayment.paymentNotify(xml);
        _.info("微信回调:orderId" + orderId);
        // 支付成功处理逻辑
        return "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
    }

    // 图片
    @RequestMapping(value = "/img", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> img(String img) {
        try {
            return ResponseEntity.ok()//
            .contentType(MediaType.IMAGE_JPEG)//
            .body(fileHelper.file(img));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 上传文件
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult img(@RequestParam("imgfile") MultipartFile imgfile) {
        String img = fileHelper.upload2save(imgfile);
        if (StringUtils.isEmpty(img)) return fail("上传失败");
        return ok("上传成功", img);
    }

    // 健康检查
    @Bean
    public ServletRegistrationBean healthAction() {
        return new ServletRegistrationBean(new HttpServlet() {

            private static final long serialVersionUID = -33776623249934740L;

            public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
                                                                                       IOException {
                JSON json = new JSON();
                json.put("status", 200);
                json.put("time", DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss:SSS"));
                response.setContentType("application/json;charset=utf-8");
                PrintWriter out = response.getWriter();
                out.println(json.toJSONString());
                out.flush();
                out.close();
            }

            public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
                                                                                        IOException {
                doGet(request, response);
            }
        }, "/health");
    }
}
