package com.hust.hui.alarm.plugin.timewindow.api;

/**
 * Created by @author yihui in 16:41 18/6/29.
 */
public interface IElement {
    /**
     * 时间窗口中的元素，最新写入的时间戳
     *
     * @return
     */
    long time();

    void add(IElement element);
}
