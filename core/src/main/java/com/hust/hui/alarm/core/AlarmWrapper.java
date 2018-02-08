package com.hust.hui.alarm.core;


import com.hust.hui.alarm.common.concurrent.DefaultThreadFactory;
import com.hust.hui.alarm.core.entity.AlarmConfig;
import com.hust.hui.alarm.core.execut.AlarmExecuteSelector;
import com.hust.hui.alarm.core.helper.ExecuteHelper;
import com.hust.hui.alarm.core.loader.ConfLoaderFactory;
import com.hust.hui.alarm.core.loader.api.IConfLoader;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by yihui on 2017/4/19.
 */
public class AlarmWrapper {
    private static final Logger logger = LoggerFactory.getLogger(AlarmWrapper.class);


    private ExecutorService alarmExecutorService;


    private ConcurrentHashMap<String, AtomicInteger> alarmCountMap;


    private IConfLoader confLoader;


    public static AlarmWrapper getInstance() {
        return InnerInstance.instance;
    }


    private static class InnerInstance {
        static AlarmWrapper instance = new AlarmWrapper();
    }


    private AlarmWrapper() {
        // 记录每种异常的报警数
        alarmCountMap = new ConcurrentHashMap<>();


        // 加载报警配置信息
        confLoader = ConfLoaderFactory.loader();

        // 初始化线程池
        initExecutorService();
    }


    public void initExecutorService() {
        // 报警线程池
        alarmExecutorService = new ThreadPoolExecutor(3, 5, 60,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(10), new DefaultThreadFactory("sms-sender"),
                new ThreadPoolExecutor.CallerRunsPolicy());


        // 每分钟清零一把报警计数
        ScheduledExecutorService scheduleExecutorService = Executors.newScheduledThreadPool(1);
        scheduleExecutorService.scheduleAtFixedRate(() -> {
            for (Map.Entry<String, AtomicInteger> entry : alarmCountMap.entrySet()) {
                entry.getValue().set(0);
            }
        }, 0, 1, TimeUnit.MINUTES);
    }


    public void sendMsg(String key, String content) {
        sendMsg(new AlarmContent(key, null, content));
    }


    public void sendMsg(String key, String title, String content) {
        sendMsg(new AlarmContent(key, title, content));
    }


    /**
     * 1. 获取报警的配置项
     * 2. 获取当前报警的次数
     * 3. 选择适当的报警类型
     * 4. 执行报警
     * 5. 报警次数+1
     *
     * @param alarmContent
     */
    private void sendMsg(AlarmContent alarmContent) {
        try {
            // get alarm config
            AlarmConfig alarmConfig = confLoader.getAlarmConfig(alarmContent.key);

            // get alarm count
            int count = getAlarmCount(alarmContent.key);
            alarmContent.setCount(count);


            ExecuteHelper executeHelper;
            if (confLoader.alarmEnable()) { // get alarm execute
                executeHelper = AlarmExecuteSelector.getExecute(alarmConfig, count);
            } else {  // 报警关闭, 则走空报警流程, 将报警信息写入日志文件
                executeHelper = AlarmExecuteSelector.getDefaultExecute();
            }


            // do send msg
            doSend(executeHelper, alarmContent);
        } catch (Exception e) {
            logger.error("AlarmWrapper.sendMsg error! content:{}, e:{}", alarmContent, e);
        }
    }


    private void doSend(final ExecuteHelper executeHelper, final AlarmContent alarmContent) {
        alarmExecutorService.execute(() -> executeHelper.getIExecute().sendMsg(executeHelper.getUsers(), alarmContent.getTitle(), alarmContent.getContent()));
    }


    /**
     * 线程安全的获取报警总数 并自动加1
     *
     * @param key
     * @return
     */
    private int getAlarmCount(String key) {
        if (!alarmCountMap.containsKey(key)) {
            synchronized (this) {
                if (!alarmCountMap.containsKey(key)) {
                    alarmCountMap.put(key, new AtomicInteger(0));
                }
            }
        }

        return alarmCountMap.get(key).addAndGet(1);
    }


    /**
     * 报警的实体类
     */
    @ToString
    private static class AlarmContent {

        private static String LOCAL_IP;

        private static String PREFIX;

        static {
            try {
                LOCAL_IP = InetAddress.getLocalHost().getHostAddress();
            } catch (Exception e) {
                LOCAL_IP = "127.0.0.1";
            }

            try {
                PREFIX = "[" + ConfLoaderFactory.loader().getRegisterInfo().getAppName() + "]";
            } catch (Exception e) {
                PREFIX = "[default]";
            }
        }


        private String key;
        private String title;
        private String content;
        private int count;


        public AlarmContent(String key, String title, String content) {
            this.key = key;
            this.title = title;
            this.content = content;
        }

        public String getTitle() {
            if (title == null) {
                return PREFIX;
            } else {
                return title;
            }
        }


        public void setCount(int count) {
            this.count = count;
        }


        public String getContent() {
            return " ip:" + LOCAL_IP + " >>> key:" + key + " >>> 异常数: " + count + " >>> " + content;
        }
    }
}
