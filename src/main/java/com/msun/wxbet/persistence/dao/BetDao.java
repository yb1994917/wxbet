/*
 * Copyright 2015-2020 msun.com All right reserved.
 */
package com.msun.wxbet.persistence.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.msun.wxbet.persistence.model.Bet;

/**
 * @author zxc Sep 18, 2016 9:25:30 PM
 */
public interface BetDao extends PagingAndSortingRepository<Bet, Long>, JpaSpecificationExecutor<Bet> {

    List<Bet> findByUserId(Long userId);

    List<Bet> findByUserIdAndState(Long userId, int state);

    @Query("select e from Bet e where e.state IN :states and e.userId= :userId")
    List<Bet> findByUserIdInState(@Param("userId") Long userId, @Param(value = "states") Collection<Integer> states);

    long countByUserIdAndState(Long userId, int state);

    @Query("select count(e) from Bet e where e.state IN :states and e.userId= :userId")
    long countByUserIdInState(@Param("userId") Long userId, @Param(value = "states") Collection<Integer> states);
}
