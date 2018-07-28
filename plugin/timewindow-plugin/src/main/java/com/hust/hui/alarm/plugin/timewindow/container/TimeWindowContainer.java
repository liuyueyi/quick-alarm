package com.hust.hui.alarm.plugin.timewindow.container;

import com.hust.hui.alarm.plugin.timewindow.api.IElement;
import com.hust.hui.alarm.plugin.timewindow.cache.GuavaStore;
import com.hust.hui.alarm.plugin.timewindow.cache.IStore;
import com.hust.hui.alarm.plugin.timewindow.conf.TimeWindowConf;
import com.hust.hui.alarm.plugin.timewindow.util.TimeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by @author yihui in 16:34 18/6/29.
 */
public class TimeWindowContainer<T extends IElement> {
    private TimeWindowConf conf;
    private IStore<T> store;

    public TimeWindowContainer(long start, int period, int size, IStore<T> store) {
        this.conf = new TimeWindowConf(period, size);
        this.conf.setStart(start);

        this.store = store;
    }

    public TimeWindowContainer(int period, int size, IStore<T> store) {
        this(0, period, size, store);
    }

    public TimeWindowContainer(int period, int size) {
        this(period, size, new GuavaStore<T>());
    }

    /**
     * 默认维持一天的时间窗口，每分钟一个梯度，供1440个时间片段，数据存于Guava缓存
     */
    public TimeWindowContainer() {
        this(TimeUtil.MS_IN_MIN, TimeUtil.MIN_IN_DAY);
    }

    public List<IElement> getRemoveList(long now, long lastRemove, int windowLen) {
        List<IElement> removeList = new ArrayList<>();
        long nowRemove = now - windowLen;
        lastRemove += conf.getPeriod();
        while (lastRemove <= nowRemove) {
            IElement element = store.getElement(lastRemove, conf);
            if (element != null) {
                removeList.add(element);
            }
            lastRemove += conf.getPeriod();
        }
        return removeList;
    }

    public void addElement(long time, T element) {
        IElement old = store.getElement(time, conf);
        if (old != null) {
            old.add(element);
        }
        store.addElement(time, element, conf);
    }
}