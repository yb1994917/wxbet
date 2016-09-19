/*
 * Copyright 2015-2020 uuzu.com All right reserved.
 */
package com.msun.wxbet.persistence.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.msun.wxbet.persistence.model.Distribution;

/**
 * @author zxc Sep 18, 2016 9:25:54 PM
 */
public interface DistributionDao extends PagingAndSortingRepository<Distribution, Long>, JpaSpecificationExecutor<Distribution> {

    Distribution findByBetId(Long betId);

    List<Distribution> findByUserId(Long userId);

    List<Distribution> findByUserIdAndState(Long userId, int state);
}
