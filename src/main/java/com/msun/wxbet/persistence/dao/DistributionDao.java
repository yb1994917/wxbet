/*
 * Copyright 2015-2020 uuzu.com All right reserved.
 */
package com.msun.wxbet.persistence.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.msun.wxbet.persistence.model.Distribution;

/**
 * @author zxc Sep 18, 2016 9:25:54 PM
 */
public interface DistributionDao extends PagingAndSortingRepository<Distribution, Long>, JpaSpecificationExecutor<Distribution> {

}
