package com.hust.hui.alarm.core.loader;

import com.hust.hui.alarm.core.loader.api.IConfLoader;
import com.hust.hui.alarm.core.exception.NoAlarmLoaderSpecifyException;

import java.util.*;

/**
 * Created by yihui on 2018/2/7.
 */
public class ConfLoaderFactory {

    private static IConfLoader currentAlarmConfLoader;

    public static IConfLoader loader() {
        if (currentAlarmConfLoader == null) {
            synchronized (ConfLoaderFactory.class) {
                if (currentAlarmConfLoader == null) {
                    initConfLoader();
                }
            }
        }

        return currentAlarmConfLoader;
    }


    private static void initConfLoader() {
        Iterator<IConfLoader> iterator = ServiceLoader.load(IConfLoader.class).iterator();

        List<IConfLoader> list = new ArrayList<>();
        // 根据优先级进行排序，选择第一个加载成功的Loader
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
        list.sort(Comparator.comparingInt(IConfLoader::order));

        for (IConfLoader iConfLoader : list) {
            if (iConfLoader.load()) {
                currentAlarmConfLoader = iConfLoader;
                break;
            }
        }


        if (currentAlarmConfLoader == null) {
            throw new NoAlarmLoaderSpecifyException("no special alarmConfLoader selected!");
        }
    }


}
