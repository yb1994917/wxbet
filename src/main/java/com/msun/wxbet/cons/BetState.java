/*
 * Copyright 2015-2020 msun.com All right reserved.
 */
package com.msun.wxbet.cons;

/**
 * 状态: -1=创建失败,0=创建成功,1=目标完成,2=目标失败,3=终止
 * 
 * @author zxc Sep 19, 2016 4:56:56 PM
 */
public enum BetState {

    FIAL("创建失败", -1), SUCCESS("创建成功", 0), FINISH("目标完成", 1), UN_FINISH("目标失败", 2), CANCEL("取消", 3);

    private String name;
    private int    value;

    private BetState(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public static BetState get(int value) {
        for (BetState _enum : values())
            if (_enum.value == value) return _enum;
        return SUCCESS;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }
}
