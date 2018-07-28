package com.hust.hui.alarm.plugin.timewindow.cache;

import com.hust.hui.alarm.plugin.timewindow.api.IElement;
import com.hust.hui.alarm.plugin.timewindow.conf.TimeWindowConf;

/**
 * Created by @author yihui in 16:48 18/6/29.
 */
public interface IStore<T extends IElement> {
    /**
     * 根据时间戳，获取时间窗口中过期的那条数据
     *
     * @param timestamp 查询的时间戳
     * @param conf       时间窗口的全局配置信息
     * @return
     */
    T getElement(long timestamp, TimeWindowConf conf);

    /**
     * 新增一条数据
     *
     * @param nowTime
     * @param element
     * @param conf
     */
    void addElement(long nowTime, T element, TimeWindowConf conf);
}
