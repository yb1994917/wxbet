/*
 * Copyright 2015-2020 uuzu.com All right reserved.
 */
package com.msun.wxbet.persistence.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 所有的付款订单
 * 
 * @author zxc Oct 21, 2016 3:39:38 PM
 */
@Entity
@Table(name = "payment_order")
public class PaymentOrder extends IdEntity {

    private Date  createTime; // 创建时间
    private Date  updateTime; // 更新时间
    private Float money;
    private Long  userId;
    private int   status;

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

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
