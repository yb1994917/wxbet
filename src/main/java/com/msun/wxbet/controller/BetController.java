/*
 * Copyright 2015-2020 uuzu.com All right reserved.
 */
package com.msun.wxbet.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
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
    public ModelAndView bet_save(String content, String finishTime, Float amount) throws Exception {
        Bet bet = new Bet(userId());
        bet.setContent(content);
        bet.setAmount(amount);
        bet.setFinishTime(DateUtils.parse(finishTime, ""));
        betService.save(bet);
        return new ModelAndView("bet/apply");
    }

    @RequestMapping(value = "/setting", method = RequestMethod.POST)
    public ModelAndView bet_setting(Long betId, int participate, int visible) {
        Bet bet = betService.bet(betId);
        bet.setParticipate(participate);
        bet.setVisible(visible);
        betService.save(bet);
        return new ModelAndView("bet/apply");
    }

    // 打赌详情
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public ModelAndView bet_detail(Long betId) {
        Bet bet = betService.bet(betId);
        List<Progress> progresseList = betService.listProgress(betId);
        List<Participate> participateList = participateService.listParticipate(betId);
        return new ModelAndView("bet/applydetail")//
        .addObject("bet", bet)//
        .addObject("progresseList", progresseList)//
        .addObject("participateList", participateList);
    }

    // 打赌记录
    @RequestMapping(value = "/progress", method = RequestMethod.GET)
    public ModelAndView progress_create() {
        return new ModelAndView("bet/addRecord");
    }

    @RequestMapping(value = "/progress", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult progress_save(Long betId, String content, String pic) {
        Progress progress = new Progress(userId(), betId);
        progress.setContent(content);
        progress.setPic(pic);
        betService.save(progress);
        return ok("成功");
    }

    // 参与打赌
    @RequestMapping(value = "/join", method = RequestMethod.GET)
    public ModelAndView showdetail(Long betId) {
        Bet bet = betService.bet(betId);
        List<Progress> progresseList = betService.listProgress(betId);
        List<Participate> participateList = participateService.listParticipate(betId);
        return new ModelAndView("bet/showdetail")//
        .addObject("bet", bet)//
        .addObject("progresseList", progresseList)//
        .addObject("participateList", participateList);
    }

    // 打赌结果领取奖金
    @RequestMapping(value = "/result", method = RequestMethod.GET)
    public ModelAndView detailOther() {
        return new ModelAndView("bet/betResult");
    }

    // 打赌列表
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView applylist() {
        List<Bet> betList = betService.listBet(userId(), BetState.SUCCESS);
        return new ModelAndView("bet/applylist")//
        .addObject("betList", betList);
    }

    // 过往打赌列表
    @RequestMapping(value = "/history", method = RequestMethod.GET)
    public ModelAndView applyrecord() {
        List<Bet> betList = betService.listBet(userId());
        return new ModelAndView("bet/applyHistory")//
        .addObject("betList", betList);
    }
}
