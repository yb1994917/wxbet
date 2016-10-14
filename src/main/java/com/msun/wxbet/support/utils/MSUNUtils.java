/*
 * Copyright 2015-2020 msun.com All right reserved.
 */
package com.msun.wxbet.support.utils;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.lamfire.utils.DateUtils;

/**
 * @author zxc Sep 27, 2016 11:39:07 AM
 */
public class MSUNUtils {

    public static int subtractDays(Long bigger, Long smaller) {
        if (bigger == null) return 0;
        return (int) ((bigger - smaller) / DateUtils.MILLIS_PER_DAY);
    }

    public static boolean isToDay(Date date) {
        return date == null ? false : isSameDay(date, new Date());
    }

    public static boolean isSameDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) return false;
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return isSameDay(cal1, cal2);
    }

    public static boolean isSameDay(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null) return false;
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
               && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)
               && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }

    // 创建分页请求
    public static PageRequest pageRequest(int pageNumber, int pageSize, String sortType) {
        Sort sort = new Sort(Direction.DESC, "id");
        if (StringUtils.equalsIgnoreCase("auto", sortType)) {
            sort = new Sort(Direction.DESC, "id");
        } else if (StringUtils.equalsIgnoreCase("update_time", sortType)) {
            sort = new Sort(Direction.DESC, "update_time");
        }
        return new PageRequest(pageNumber - 1, pageSize, sort);
    }
}
