package com.hust.hui.alarm.plugin.timewindow.util;

/**
 * Created by @author yihui in 16:56 18/6/29.
 */
public class TimeUtil {
    public static int MS_IN_MIN = 60 * 1000;
    public static int MIN_IN_DAY = 24 * 60;

    /**
     * 判断两个时间戳是否在同一时间段内，一个简单的实例是，给出两个时间戳，判断是否为同一分钟, period为 60 * 1000
     *
     * @param left   目标时间戳
     * @param right  比较时间戳
     * @param period 时间片段
     * @return
     */
    public static boolean samePeriod(long left, long right, long period) {
        return left / period == right / period;
    }
}
