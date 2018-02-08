package com.hust.hui.alarm.core.test.conf;

import com.hust.hui.alarm.core.entity.AlarmConfig;
import com.hust.hui.alarm.core.loader.helper.PropertiesConfListenerHelper;

import java.io.File;
import java.util.Map;

/**
 * Created by yihui on 2018/2/7.
 */
public class ConfChangeListenerTest {

    private Map<String, AlarmConfig> load(File file) {
        System.out.println("----->now: {}" + System.currentTimeMillis());
        return null;
    }


    public static void main(String[] args) throws InterruptedException {
        File file = new File("/tmp/alarmConfig");

        ConfChangeListenerTest test = new ConfChangeListenerTest();

        try {
            PropertiesConfListenerHelper.registerConfChangeListener(file, test::load);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Thread.sleep(1000 * 10);
    }
}
