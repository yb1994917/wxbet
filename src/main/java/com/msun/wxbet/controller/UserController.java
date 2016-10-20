/*
 * Copyright 2015-2020 msun.com All right reserved.
 */
package com.msun.wxbet.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.msun.wxbet.cons.BetState;
import com.msun.wxbet.persistence.model.Bet;
import com.msun.wxbet.persistence.model.Participate;

/**
 * @author zxc Sep 14, 2016 5:11:47 PM
 */
@Controller
@RequestMapping(value = "/user")
public class UserController extends BaseController {

    // 个人中心
    @RequestMapping(value = "/center", method = RequestMethod.GET)
    public ModelAndView list() {
        long betCount = betService.countBet(userId(), BetState.SUCCESS);
        long betHistoryCount = betService.countBet(userId(), BetState.FINISH, BetState.UN_FINISH, BetState.CANCEL);
        return new ModelAndView("user/center")//
        .addObject("user", user())//
        .addObject("betCount", betCount)//
        .addObject("betHistoryCount", betHistoryCount);
    }

    // 打赌列表
    @RequestMapping(value = "/bet", method = RequestMethod.GET)
    public ModelAndView betlist() {
        // 我的打赌
        List<Bet> betList = betService.listBet(userId(), BetState.SUCCESS);
        List<Bet> betHistoryList = betService.listBet(userId(), BetState.FINISH, BetState.UN_FINISH, BetState.CANCEL);
        long betCount = betService.countBet(userId(), BetState.SUCCESS);
        // 我的鼓励
        long partiCount = participateService.countParticipateByPublisher(userId());
        return new ModelAndView("user/betlist")//
        .addObject("betCount", betCount)//
        .addObject("betList", betList)//
        .addObject("betHistoryList", betHistoryList)//
        .addObject("partiCount", partiCount);
    }

    // 鼓励列表
    @RequestMapping(value = "/invite", method = RequestMethod.GET)
    public ModelAndView invitelist() {
        // 我的打赌
        long betCount = betService.countBet(userId(), BetState.SUCCESS);
        // 我的鼓励
        List<Participate> participateList = participateService.listParticipateByPublisher(userId());
        long partiCount = participateService.countParticipateByPublisher(userId());
        return new ModelAndView("user/betlist")//
        .addObject("betCount", betCount)//
        .addObject("partiCount", partiCount)//
        .addObject("participateList", participateList);
    }

    // 访问鉴权
    @RequestMapping(value = "/auth", method = RequestMethod.GET)
    public ModelAndView detail() {
        return new ModelAndView("user/useridenf");
    }
}
