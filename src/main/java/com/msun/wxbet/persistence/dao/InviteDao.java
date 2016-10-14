/*
 * Copyright 2015-2020 msun.com All right reserved.
 */
package com.msun.wxbet.persistence.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.msun.wxbet.persistence.model.Invite;

/**
 * @author zxc Oct 14, 2016 12:22:50 PM
 */
public interface InviteDao extends PagingAndSortingRepository<Invite, Long>, JpaSpecificationExecutor<Invite> {

}
