/*
 * Copyright 2015-2020 msun.com All right reserved.
 */
package com.msun.wxbet.controller;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lamfire.utils.DateUtils;
import com.msun.wxbet.persistence.model.Invite;
import com.msun.wxbet.support.JsonResult;

/**
 * 邀约打赌
 * 
 * @author zxc Oct 14, 2016 12:30:12 PM
 */
@Controller
@RequestMapping(value = "/invite")
public class InviteController extends BaseController {

    // 打赌详情
    @RequestMapping(value = "/detail/{inviteId}", method = RequestMethod.GET)
    public ModelAndView bet_detail(@PathVariable Long inviteId) {
        Invite invite = inviteSerivce.invite(inviteId);

        return new ModelAndView("invite/applydetail")//
        .addObject("invite", invite);
    }

    // 创建邀约打赌
    @RequestMapping(value = "/apply", method = RequestMethod.GET)
    public ModelAndView bet_create() {
        return new ModelAndView("invite/apply");
    }

    // 提交创建邀约打赌
    @RequestMapping(value = "/apply", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult bet_save(String content, String finishTime, Float amount) throws Exception {
        Invite invite = new Invite(userId());
        invite.setContent(content);
        invite.setAmount(amount);
        invite.setFinishTime(DateUtils.parse(finishTime, "yyyy-MM-dd HH:mm"));
        invite.setCreateTime(new Date());
        inviteSerivce.save(invite);
        if (debug) {
            return ok("成功", new Object[][] { { "id", invite.getId() } });
        } else {
            // 正式环境微信支付
            String title = String.format(ORDER_NAME, userId(), amount);
            String prepayId = wechatPayment.createWechatOrder(title, String.valueOf(invite.getId()),
                                                              amount.longValue(), request.getRemoteAddr(), openid());
            return ok("成功", wechatPayment.initPaymentAttribute(prepayId));
        }
    }
}
