/*
 * Copyright 2015-2020 uuzu.com All right reserved.
 */
package com.msun.wxbet.persistence.model;

import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * 进度
 * 
 * @author zxc Sep 18, 2016 9:22:06 PM
 */
@Entity
@Table(name = "progress")
public class Progress extends IdEntity {

    private Date   createTime; // 创建时间
    private String content;   // 打赌过程记录
    private String pic;       // 过程记录图片

    private Long   userId;    // 组织者id
    private User   user;      // 组织者
    private Long   betId;     // 打赌id
    private Bet    bet;       // 打赌

    public Progress() {

    }

    public Progress(Long userId, Long betId) {
        this.userId = userId;
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
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Long getBetId() {
        return betId;
    }

    public void setBetId(Long betId) {
        this.betId = betId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
