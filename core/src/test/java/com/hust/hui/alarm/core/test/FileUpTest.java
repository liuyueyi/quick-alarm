package com.hust.hui.alarm.core.test;

import com.hust.hui.alarm.core.entity.AlarmConfig;
import com.hust.hui.alarm.core.loader.helper.PropertiesConfListenerHelper;
import org.junit.Test;

import java.io.File;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 测试文件变更
 * <p>
 * Created by yihui on 2017/4/28.
 */
public class FileUpTest {

    private long lastTime;

    private void ttt() {
        throw new NullPointerException();
    }

    @Test
    public void testFileUpdate() {
        File file = new File("/tmp/alarmConfig");

        lastTime = file.lastModified();

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (file.lastModified() > lastTime) {
                    System.out.println("file update! time : " + file.lastModified());
                    lastTime = file.lastModified();
                    ttt();
                }
            }
        }, 0, 1, TimeUnit.SECONDS);


        try {
            Thread.sleep(1000 * 60 * 10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private Map<String, AlarmConfig> parse(File f) {
        throw new NullPointerException();
    }


    @Test
    public void testRegisteFileListener() {
        PropertiesConfListenerHelper.registerConfChangeListener(
                new File("/tmp/alarmConfig"),
                this::parse);


        try {
            Thread.sleep(1000 * 60 * 10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
