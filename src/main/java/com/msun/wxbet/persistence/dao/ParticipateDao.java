/*
 * Copyright 2015-2020 uuzu.com All right reserved.
 */
package com.msun.wxbet.persistence.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.msun.wxbet.persistence.model.Participate;

/**
 * @author zxc Sep 18, 2016 9:26:22 PM
 */
public interface ParticipateDao extends PagingAndSortingRepository<Participate, Long>, JpaSpecificationExecutor<Participate> {

}
