/*
 * Copyright 2015-2020 uuzu.com All right reserved.
 */
package com.msun.wxbet.persistence.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 资金分配
 * 
 * @author zxc Sep 18, 2016 9:24:28 PM
 */
@Entity
@Table(name = "distribution")
public class Distribution extends IdEntity {

    private Date   createTime; // 创建时间
    private int    organizer; // 状态: 0=参与者,1=组织者
    private int    state;     // 状态: -1=创建失败,0=创建成功,1=目标完成,2=目标失败,3=终止
    private String name;      // 用户名称,匿名显示匿名
    private Float  capital;   // 打赌活动总资金池,单位元
    private Float  income;    // 打赌收益金额,单位元

    private Long   userId;    // 组织者id
    private Long   betId;     // 打赌id

    public Distribution() {

    }

    public Distribution(Long userId, Long betId) {
        this.userId = userId;
        this.betId = betId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getOrganizer() {
        return organizer;
    }

    public void setOrganizer(int organizer) {
        this.organizer = organizer;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getCapital() {
        return capital;
    }

    public void setCapital(Float capital) {
        this.capital = capital;
    }

    public Float getIncome() {
        return income;
    }

    public void setIncome(Float income) {
        this.income = income;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBetId() {
        return betId;
    }

    public void setBetId(Long betId) {
        this.betId = betId;
    }
}
