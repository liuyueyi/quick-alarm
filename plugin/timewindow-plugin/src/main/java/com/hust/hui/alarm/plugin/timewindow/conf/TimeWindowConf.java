package com.hust.hui.alarm.plugin.timewindow.conf;

import lombok.Data;

/**
 * 一个原则，根据当前时间戳与com.hust.hui.alarm.plugin.timewindow.TimeWindowConf#start, 来定位当前时间戳对应的时间窗口中的位置
 *
 * Created by @author yihui in 16:27 18/6/29.
 */
@Data
public class TimeWindowConf {
    /**
     * 起始值
     */
    private Long start = 0L;
    /**
     * 时间间隔
     */
    private Integer period;

    /**
     * 时间窗口的大小，如 period = 60s, size=10, 则表示时间窗口的总长度为10min, 每分钟刷新一次，减去过期的数据
     */
    private Integer size;

    /**
     * 时间窗口的长度
     */
    private Integer len;

    public TimeWindowConf(Integer period, Integer size) {
        this.period = period;
        this.size = size;
        len = size * period;
    }
}
