/*
 * Copyright 2015-2020 msun.com All right reserved.
 */
package com.msun.wxbet.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.lamfire.utils.Lists;
import com.msun.wxbet.persistence.dao.PaymentOrderDao;
import com.msun.wxbet.persistence.model.PaymentOrder;
import com.msun.wxbet.support.utils.MSUNUtils;

/**
 * @author zxc Oct 21, 2016 3:54:19 PM
 */
@Service
public class OrderService {

    @Autowired
    private PaymentOrderDao paymentOrderDao;

    public PaymentOrder order(Long id) {
        if (id == null) return null;
        return paymentOrderDao.findOne(id);
    }

    public List<PaymentOrder> listOrder() {
        return Lists.newArrayList(paymentOrderDao.findAll());
    }

    public Page<PaymentOrder> listOrder(int page, int size) {
        return paymentOrderDao.findAll(MSUNUtils.pageRequest(page, size, null));
    }

    public void save(PaymentOrder order) {
        paymentOrderDao.save(order);
    }

    public void delOrder(Long id) {
        if (id == null) return;
        paymentOrderDao.delete(id);
    }
}
