/*
 * Copyright 2015-2020 msun.com All right reserved.
 */
package com.msun.wxbet.persistence.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.msun.wxbet.persistence.model.Participate;

/**
 * @author zxc Sep 18, 2016 9:26:22 PM
 */
public interface ParticipateDao extends PagingAndSortingRepository<Participate, Long>, JpaSpecificationExecutor<Participate> {

    List<Participate> findByBetId(Long betId);

    // 组织者
    List<Participate> findByUserId(Long userId);

    long countByUserId(Long userId);

    // 参与者
    List<Participate> findByPartnerId(Long userId);

    long countByPartnerId(Long userId);

    List<Participate> findByUserIdAndBetId(Long userId, Long betId);

    List<Participate> findByPartnerIdAndBetId(Long userId, Long betId);

    long countByUserIdAndBetId(Long userId, Long betId);

    long countByPartnerIdAndBetId(Long userId, Long betId);
}
