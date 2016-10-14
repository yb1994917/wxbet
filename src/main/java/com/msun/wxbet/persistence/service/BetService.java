/*
 * Copyright 2015-2020 msun.com All right reserved.
 */
package com.msun.wxbet.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.lamfire.utils.Lists;
import com.msun.wxbet.cons.BetState;
import com.msun.wxbet.persistence.dao.BetDao;
import com.msun.wxbet.persistence.dao.ProgressDao;
import com.msun.wxbet.persistence.model.Bet;
import com.msun.wxbet.persistence.model.Progress;
import com.msun.wxbet.support.utils.MSUNUtils;

/**
 * @author zxc Sep 19, 2016 4:15:02 PM
 */
@Service
public class BetService {

    @Autowired
    private BetDao      betDao;
    @Autowired
    private ProgressDao progressDao;

    public Bet bet(Long id) {
        if (id == null) return null;
        return betDao.findOne(id);
    }

    public List<Bet> listBet(Long userId) {
        return betDao.findByUserId(userId);
    }

    public List<Bet> listBet(Long userId, BetState... states) {
        if (states == null || states.length <= 0) return Lists.newArrayList();
        if (states.length == 1) {
            return betDao.findByUserIdAndState(userId, states[0].getValue());
        } else {
            List<Integer> list = Lists.newArrayList();
            for (BetState betState : states) {
                list.add(betState.getValue());
            }
            return betDao.findByUserIdInState(userId, list);
        }
    }

    public long countBet(Long userId, BetState... states) {
        if (states == null || states.length <= 0) return 0;
        if (states.length == 1) {
            return betDao.countByUserIdAndState(userId, states[0].getValue());
        } else {
            List<Integer> list = Lists.newArrayList();
            for (BetState betState : states) {
                list.add(betState.getValue());
            }
            return betDao.countByUserIdInState(userId, list);
        }
    }

    public List<Bet> listBet() {
        return Lists.newArrayList(betDao.findAll());
    }

    public Page<Bet> listBet(int page, int size) {
        return betDao.findAll(MSUNUtils.pageRequest(page, size, null));
    }

    public void save(Bet bet) {
        betDao.save(bet);
    }

    public void delBet(Long id) {
        if (id == null) return;
        betDao.delete(id);
    }

    // ****************************************************************//
    public Progress progress(Long id) {
        if (id == null) return null;
        return progressDao.findOne(id);
    }

    public List<Progress> listProgress() {
        return Lists.newArrayList(progressDao.findAll());
    }

    public List<Progress> listProgress(Long betId) {
        return progressDao.findByBetId(betId);
    }

    public Page<Progress> listProgress(int page, int size) {
        return progressDao.findAll(MSUNUtils.pageRequest(page, size, null));
    }

    public void save(Progress progress) {
        progressDao.save(progress);
    }

    public void delProgress(Long id) {
        if (id == null) return;
        progressDao.delete(id);
    }
}
