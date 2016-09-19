/*
 * Copyright 2015-2020 uuzu.com All right reserved.
 */
package com.msun.wxbet.persistence.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.lamfire.utils.Lists;
import com.msun.wxbet.persistence.dao.DistributionDao;
import com.msun.wxbet.persistence.dao.ParticipateDao;
import com.msun.wxbet.persistence.model.Distribution;
import com.msun.wxbet.persistence.model.Participate;

/**
 * @author zxc Sep 19, 2016 4:19:24 PM
 */
@Service
public class ParticipateService {

    @Autowired
    private ParticipateDao  participateDao;
    @Autowired
    private DistributionDao distributionDao;

    public Participate participate(Long id) {
        if (id == null) return null;
        return participateDao.findOne(id);
    }

    public List<Participate> listParticipate(Long betId) {
        return participateDao.findByBetId(betId);
    }

    public List<Participate> listParticipate() {
        return Lists.newArrayList(participateDao.findAll());
    }

    public Page<Participate> listParticipate(int page, int size) {
        return participateDao.findAll(pageRequest(page, size, null));
    }

    public void save(Participate participate) {
        participateDao.save(participate);
    }

    public void delParticipate(Long id) {
        if (id == null) return;
        participateDao.delete(id);
    }

    // ************************************************************************//
    public Distribution distribution(Long id) {
        if (id == null) return null;
        return distributionDao.findOne(id);
    }

    public List<Distribution> listDistribution() {
        return Lists.newArrayList(distributionDao.findAll());
    }

    public Page<Distribution> listDistribution(int page, int size) {
        return distributionDao.findAll(pageRequest(page, size, null));
    }

    public void save(Distribution distribution) {
        distributionDao.save(distribution);
    }

    public void delDistribution(Long id) {
        if (id == null) return;
        distributionDao.delete(id);
    }

    // 创建分页请求
    private PageRequest pageRequest(int pageNumber, int pageSize, String sortType) {
        Sort sort = new Sort(Direction.DESC, "id");
        if (StringUtils.equalsIgnoreCase("auto", sortType)) {
            sort = new Sort(Direction.DESC, "id");
        } else if (StringUtils.equalsIgnoreCase("update_time", sortType)) {
            sort = new Sort(Direction.DESC, "update_time");
        }
        return new PageRequest(pageNumber - 1, pageSize, sort);
    }
}
