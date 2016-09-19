/*
 * Copyright 2015-2020 uuzu.com All right reserved.
 */
package com.msun.wxbet.persistence.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.msun.wxbet.persistence.model.Bet;

/**
 * @author zxc Sep 18, 2016 9:25:30 PM
 */
public interface BetDao extends PagingAndSortingRepository<Bet, Long>, JpaSpecificationExecutor<Bet> {

    List<Bet> findByUserId(Long userId);

    List<Bet> findByUserIdAndState(Long userId, int state);
}
