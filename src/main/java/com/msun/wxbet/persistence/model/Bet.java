/*
 * Copyright 2015-2020 msun.com All right reserved.
 */
package com.msun.wxbet.persistence.model;

import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.msun.wxbet.support.utils.MSUNUtils;

/**
 * 打赌
 * 
 * @author zxc Sep 18, 2016 9:16:48 PM
 */
@Entity
@Table(name = "bet")
public class Bet extends IdEntity {

    private Date   createTime;     // 创建时间
    private Date   updateTime;     // 更新时间
    private String content;        // 打赌内容
    private Date   finishTime;     // 预计完成时间
    private Date   realTime;       // 实际完成时间
    private Float  amount  = 0.00f; // 打赌支付金额,单位元
    private int    type;           // 类型:0=自发打赌,1=接受邀约打赌
    private int    state;          // 状态:-1=创建失败,0=创建成功,1=目标完成,2=目标失败,3=终止
    private int    participate;    // 状态:0=容许参与,1=停止参与
    private int    visible;        // 状态:0=公开,1=私密
    private Float  capital = 0.00f; // 打赌活动总资金池,单位元
    private long   pv;             // 浏览数

    private Long   userId;         // 组织者id
    private User   user;           // 组织者
    private Long   inviteeId;      // 邀请者id

    public Bet() {

    }

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    public User getUser() {
        return user;
    }

    public Long getInviteeId() {
        return inviteeId;
    }

    public void setInviteeId(Long inviteeId) {
        this.inviteeId = inviteeId;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Bet(Long userId) {
        this.userId = userId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    @Transient
    public int getFinishTimeDesc() {
        if (finishTime == null) return 0;
        return MSUNUtils.subtractDays(finishTime.getTime(), System.currentTimeMillis());
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public long getPv() {
        return pv;
    }

    public void setPv(long pv) {
        this.pv = pv;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Date getRealTime() {
        return realTime;
    }

    public void setRealTime(Date realTime) {
        this.realTime = realTime;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getParticipate() {
        return participate;
    }

    public void setParticipate(int participate) {
        this.participate = participate;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    public Float getCapital() {
        return capital;
    }

    public void setCapital(Float capital) {
        this.capital = capital;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
