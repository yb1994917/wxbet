/*
 * Copyright 2015-2020 msun.com All right reserved.
 */
package com.msun.wxbet.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lamfire.utils.DateUtils;
import com.msun.wxbet.cons.BetState;
import com.msun.wxbet.persistence.model.Bet;
import com.msun.wxbet.persistence.model.Participate;
import com.msun.wxbet.persistence.model.Progress;
import com.msun.wxbet.support.JsonResult;
import com.msun.wxbet.support.wechat.WechatPayment;

/**
 * @author zxc Sep 14, 2016 5:11:21 PM
 */
@Controller
@RequestMapping(value = "/bet")
public class BetController extends BaseController {

    @Autowired
    private WechatPayment wechatPayment;

    // 打赌详情
    @RequestMapping(value = "/detail/{betId}", method = RequestMethod.GET)
    public ModelAndView bet_detail(@PathVariable Long betId) {
        Bet bet = betService.bet(betId);
        bet.setPv(bet.getPv() + 1);
        betService.save(bet);

        List<Progress> progresseList = betService.listProgress(betId);
        List<Participate> participateList = participateService.listParticipate(betId);
        return new ModelAndView("bet/apply/applydetail")//
        .addObject("bet", bet)//
        .addObject("progresseList", progresseList)//
        .addObject("participateList", participateList);
    }

    // 创建打赌
    @RequestMapping(value = "/apply", method = RequestMethod.GET)
    public ModelAndView bet_create() {
        return new ModelAndView("bet/apply");
    }

    @RequestMapping(value = "/apply", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult bet_save(String content, String finishTime, Float amount) throws Exception {
        Bet bet = new Bet(userId());
        bet.setContent(content);
        bet.setAmount(amount);
        bet.setState(BetState.FIAL.getValue());
        bet.setFinishTime(DateUtils.parse(finishTime, "yyyy-MM-dd HH:mm"));
        bet.setCreateTime(new Date());
        betService.save(bet);
        if (debug) {
            return ok("成功", new Object[][] { { "id", bet.getId() } });
        } else {
            // 正式环境微信支付
            String title = String.format(ORDER_NAME, userId(), amount);
            String prepayId = wechatPayment.createWechatOrder(title, String.valueOf(bet.getId()), amount.longValue(),
                                                              request.getRemoteAddr(), openid());
            return ok("成功", wechatPayment.initPaymentAttribute(prepayId));
        }
    }

    @RequestMapping(value = "/setting", method = RequestMethod.POST)
    public ModelAndView bet_setting(Long betId, int participate, int visible) {
        Bet bet = betService.bet(betId);
        bet.setParticipate(participate);
        bet.setVisible(visible);
        bet.setUpdateTime(new Date());
        betService.save(bet);
        return new ModelAndView("bet/apply/apply");
    }

    // 打赌记录
    @RequestMapping(value = "/progress/{betId}", method = RequestMethod.GET)
    public ModelAndView progress_create(@PathVariable Long betId) {
        Bet bet = betService.bet(betId);
        return new ModelAndView("bet/addRecord")//
        .addObject("bet", bet);
    }

    @RequestMapping(value = "/progress", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult progress_save(Long betId, String content, String pic) {
        Progress progress = new Progress(userId(), betId);
        progress.setCreateTime(new Date());
        progress.setContent(content);
        progress.setPic(pic);
        betService.save(progress);
        return ok("成功");
    }

    // 参与打赌
    @RequestMapping(value = "/join", method = RequestMethod.GET)
    public ModelAndView join(Long betId) {
        Bet bet = betService.bet(betId);
        List<Progress> progresseList = betService.listProgress(betId);
        List<Participate> participateList = participateService.listParticipate(betId);
        return new ModelAndView("bet/apply/showdetail")//
        .addObject("bet", bet)//
        .addObject("progresseList", progresseList)//
        .addObject("participateList", participateList);
    }

    @RequestMapping(value = "/join", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult dojoin(Long betId, Float amount, String name) throws Exception {
        Bet bet = betService.bet(betId);
        if (bet == null) return fail("打赌不存在");

        Participate participate = new Participate(bet.getUserId(), userId(), betId);
        participate.setAmount(amount);
        participate.setCreateTime(new Date());
        participate.setName(name);
        participateService.save(participate);
        if (debug) {
            return ok("成功", new Object[][] { { "id", participate.getId() } });
        } else {
            // 正式环境微信支付
            String title = String.format(ORDER_NAME, userId(), amount);
            String prepayId = wechatPayment.createWechatOrder(title, String.valueOf(participate.getId()),
                                                              amount.longValue(), request.getRemoteAddr(), openid());
            return ok("成功", wechatPayment.initPaymentAttribute(prepayId));
        }
    }

    // 仲裁,判定打赌结果
    @RequestMapping(value = "/arbitration", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult arbitration(Long betId, int state) throws Exception {
        Bet bet = betService.bet(betId);
        if (bet == null) return fail("打赌不存在");

        bet.setState(state);
        bet.setRealTime(new Date());
        betService.save(bet);
        return ok("操作成功");
    }

    // 监督人列表
    @RequestMapping(value = "/supervisor/{betId}", method = RequestMethod.GET)
    public ModelAndView supervisor(@PathVariable Long betId) {
        Bet bet = betService.bet(betId);
        List<Participate> participateList = participateService.listParticipate(betId);
        return new ModelAndView("bet/apply/supervisor")//
        .addObject("bet", bet)//
        .addObject("participateList", participateList);
    }

    // 打赌结果领取奖金
    @RequestMapping(value = "/result/{betId}", method = RequestMethod.GET)
    public ModelAndView detailOther(@PathVariable Long betId) {
        Bet bet = betService.bet(betId);
        List<Progress> progresseList = betService.listProgress(betId);
        List<Participate> participateList = participateService.listParticipate(betId);
        return new ModelAndView("bet/apply/betResult")//
        .addObject("bet", bet)//
        .addObject("progresseList", progresseList)//
        .addObject("participateList", participateList);
    }
}
