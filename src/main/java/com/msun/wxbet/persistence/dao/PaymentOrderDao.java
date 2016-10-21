/*
 * Copyright 2015-2020 uuzu.com All right reserved.
 */
package com.msun.wxbet.persistence.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.msun.wxbet.persistence.model.PaymentOrder;

/**
 * @author zxc Oct 21, 2016 3:53:39 PM
 */
public interface PaymentOrderDao extends PagingAndSortingRepository<PaymentOrder, Long>, JpaSpecificationExecutor<PaymentOrder> {

}

