/*
 * Copyright 2015-2020 uuzu.com All right reserved.
 */
package com.msun.wxbet.persistence.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 参与
 * 
 * @author zxc Sep 18, 2016 9:20:59 PM
 */
@Entity
@Table(name = "participate")
public class Participate extends IdEntity {

    private Date   createTime; // 创建时间
    private String name;      // 用户名称,匿名显示匿名
    private String comment;   // 打赌内容
    private Float  amount;    // 打赌参与金额,单位元

    private Long   userId;    // 组织者id
    private Long   betId;     // 打赌id

    public Participate() {

    }

    public Participate(Long userId, Long betId) {
        this.userId = userId;
        this.betId = betId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
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
