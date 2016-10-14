/*
 * Copyright 2015-2020 msun.com All right reserved.
 */
package com.msun.wxbet.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.lamfire.utils.Lists;
import com.msun.wxbet.persistence.dao.InviteDao;
import com.msun.wxbet.persistence.model.Invite;
import com.msun.wxbet.support.utils.MSUNUtils;

/**
 * @author zxc Oct 14, 2016 12:23:57 PM
 */
@Service
public class InviteSerivce {

    @Autowired
    private InviteDao inviteDao;

    public Invite invite(Long id) {
        if (id == null) return null;
        return inviteDao.findOne(id);
    }

    public List<Invite> listInvite() {
        return Lists.newArrayList(inviteDao.findAll());
    }

    public Page<Invite> listBet(int page, int size) {
        return inviteDao.findAll(MSUNUtils.pageRequest(page, size, null));
    }

    public void save(Invite bet) {
        inviteDao.save(bet);
    }

    public void delInvite(Long id) {
        if (id == null) return;
        inviteDao.delete(id);
    }
}
