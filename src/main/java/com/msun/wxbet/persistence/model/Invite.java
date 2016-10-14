/*
 * Copyright 2015-2020 msun.com All right reserved.
 */
package com.msun.wxbet.persistence.model;

import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * 邀请
 * 
 * @author zxc Oct 14, 2016 12:16:20 PM
 */
@Entity
@Table(name = "invite")
public class Invite extends IdEntity {

    private Date   createTime; // 创建时间
    private Date   updateTime; // 更新时间
    private String content;    // 打赌内容
    private Date   finishTime; // 预计完成时间
    private Float  amount;     // 打赌支付金额,单位元
    private Float  otherAmount; // 对方打赌支付金额,单位元
    private int    count;      // 被邀请者数量,默认1个,只能1个人接受

    private Long   userId;     // 邀请打赌者id
    private User   user;       // 邀请打赌者

    public Invite() {

    }

    public Invite(Long userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Float getOtherAmount() {
        return otherAmount;
    }

    public void setOtherAmount(Float otherAmount) {
        this.otherAmount = otherAmount;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
}
