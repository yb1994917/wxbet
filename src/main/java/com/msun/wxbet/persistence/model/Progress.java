/*
 * Copyright 2015-2020 uuzu.com All right reserved.
 */
package com.msun.wxbet.persistence.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

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
    private Long   betId;     // 打赌id
    private Long   userId;    // 组织者id

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
