/*
 * Copyright 2015-2020 msun.com All right reserved.
 */
package com.msun.wxbet.persistence.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 用户
 * 
 * @author zxc Sep 18, 2016 9:10:28 PM
 */
@Entity
@Table(name = "user")
public class User extends IdEntity {

    private String  openid;
    private Date    createTime = new Date();
    private Date    updateTime = new Date();
    private String  nickname;
    private String  avatar;
    private Integer state;
    private Float   income;                 // 已赚取,累计收入
    private Float   disburse;               // 已支付,累计支出
    private int     credit;                 // 用户信用额度
    private int     betTotal;               // 累计打赌数量
    private int     betSuccess;             // 累计打赌成功数量

    public User() {

    }

    public User(String openid) {
        this.openid = openid;
    }

    public Float getIncome() {
        return income;
    }

    public void setIncome(Float income) {
        this.income = income;
    }

    public Float getDisburse() {
        return disburse;
    }

    public void setDisburse(Float disburse) {
        this.disburse = disburse;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public int getBetTotal() {
        return betTotal;
    }

    public void setBetTotal(int betTotal) {
        this.betTotal = betTotal;
    }

    public int getBetSuccess() {
        return betSuccess;
    }

    public void setBetSuccess(int betSuccess) {
        this.betSuccess = betSuccess;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
