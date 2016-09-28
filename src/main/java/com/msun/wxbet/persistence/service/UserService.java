/*
 * Copyright 2015-2020 uuzu.com All right reserved.
 */
package com.msun.wxbet.persistence.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msun.wxbet.persistence.dao.UserDao;
import com.msun.wxbet.persistence.model.User;

/**
 * @author zxc Sep 19, 2016 3:05:26 PM
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public void save(User user) {
        userDao.save(user);
    }

    public User getUserByOpenId(String openId) {
        User user = getUserByOpenIdIfExist(openId);
        if (user == null) {
            throw new RuntimeException("Open id not found: " + openId);
        }
        return user;
    }

    public User getUserByOpenIdIfExist(String openId) {
        return userDao.findByOpenid(openId);
    }
}
