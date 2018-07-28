package com.hust.hui.alarm.plugin.timewindow.util;

import com.hust.hui.alarm.plugin.timewindow.conf.TimeWindowConf;

/**
 * Created by @author yihui in 16:34 18/6/29.
 */
public class IndexUtil {
    public static int calculateIndex(long time, TimeWindowConf conf) {
        long relativeTime = time - conf.getStart();
        relativeTime = relativeTime % conf.getLen();
        return (int) (relativeTime / conf.getPeriod());
    }
}