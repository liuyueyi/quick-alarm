package com.hust.hui.alarm.plugin.timewindow.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.hust.hui.alarm.plugin.timewindow.api.IElement;
import com.hust.hui.alarm.plugin.timewindow.conf.TimeWindowConf;
import com.hust.hui.alarm.plugin.timewindow.exception.GuavaNotFoundException;
import com.hust.hui.alarm.plugin.timewindow.util.IndexUtil;
import com.hust.hui.alarm.plugin.timewindow.util.TimeUtil;

import javax.xml.bind.Element;

/**
 * 基于guava内存保存时间窗口内的数据
 *
 * Created by @author yihui in 16:48 18/6/29.
 */
public class GuavaStore<T extends IElement> implements IStore {

    private LoadingCache<Integer, T> elementCache;

    public GuavaStore() {
        this.elementCache = CacheBuilder.newBuilder().build(new CacheLoader<Integer, T>() {
            @Override
            public T load(Integer key) throws Exception {
                throw new GuavaNotFoundException("not found");
            }
        });
    }

    /**
     * 根据时间戳，获取时间窗口中过期的那条数据
     *
     * @param timestamp 执行减的时间戳
     * @param conf       时间窗口的全局配置信息
     * @return
     */
    @Override
    public T getElement(long timestamp, TimeWindowConf conf) {
        try {
            int index = IndexUtil.calculateIndex(timestamp, conf);
            T element = elementCache.get(index);
            if (element != null && TimeUtil.samePeriod(timestamp, element.time(), conf.getPeriod())) {
                // 如果数据存在，且没有过期，则直接返回
                return element;
            }
            return null;
        } catch (Exception e) {
            // 不存在，直接返回null
            return null;
        }
    }

    @Override
    public void addElement(long nowTime, IElement element, TimeWindowConf conf) {
        int index = IndexUtil.calculateIndex(nowTime, conf);
        elementCache.put(index, (T) element);
    }
}
