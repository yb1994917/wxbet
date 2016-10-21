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
    private String  nickname;               // 用户名称
    private String  avatar;                 // 用户图像,默认/images/noimages.png
    private Integer subscribe;              // 关注公众号: 0=未关注,1=已关注
    private String  country;                // 国家或地区
    private String  province;               // 所在省份
    private String  city;                   // 所在城市
    private Integer sex;                    // 性别:0=未知,1=男性,2=女性

    private Integer state;                  // 状态: 0=未审核,1=正常,2=停止
    private float   income     = 0.00f;     // 已赚取,累计收入
    private float   disburse   = 0.00f;     // 已支付,累计支出
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

    public Integer getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(Integer subscribe) {
        this.subscribe = subscribe;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
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
