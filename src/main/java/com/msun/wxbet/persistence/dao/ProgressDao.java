/*
 * Copyright 2015-2020 uuzu.com All right reserved.
 */
package com.msun.wxbet.persistence.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.msun.wxbet.persistence.model.Progress;

/**
 * @author zxc Sep 18, 2016 9:26:36 PM
 */
public interface ProgressDao extends PagingAndSortingRepository<Progress, Long>, JpaSpecificationExecutor<Progress> {

    List<Progress> findByBetId(Long betId);

    List<Progress> findByUserId(Long userId);
}
