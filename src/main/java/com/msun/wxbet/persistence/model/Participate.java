/*
 * Copyright 2015-2020 msun.com All right reserved.
 */
package com.msun.wxbet.persistence.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.msun.wxbet.support.utils.MSUNUtils;

/**
 * 参与,鼓励
 * 
 * @author zxc Sep 18, 2016 9:20:59 PM
 */
@Entity
@Table(name = "participate")
public class Participate extends IdEntity {

    private Date   createTime;    // 创建时间
    private String name;          // 用户名称,匿名显示匿名
    private String comment;       // 打赌内容
    private Float  amount = 0.00f; // 打赌参与金额,单位元

    private Long   partnerId;     // 参与者id
    private User   partner;       // 参与者
    private Long   userId;        // 组织者id
    private User   user;          // 组织者
    private Long   betId;         // 打赌id
    private Bet    bet;           // 打赌

    public Participate() {

    }

    public Participate(Long userId, Long partnerId, Long betId) {
        this.userId = userId;
        this.partnerId = partnerId;
        this.betId = betId;
    }

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "betId", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    public Bet getBet() {
        return bet;
    }

    public void setBet(Bet bet) {
        this.bet = bet;
    }

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "partnerId", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    public User getPartner() {
        return partner;
    }

    public void setPartner(User partner) {
        this.partner = partner;
    }

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }

    @Transient
    public String getCreateTimeDesc() {
        return MSUNUtils.isToDay(createTime) ? ("今天" + new SimpleDateFormat("HH:mm").format(createTime)) : new SimpleDateFormat(
                                                                                                                                "MM-dd HH:mm").format(createTime);
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
