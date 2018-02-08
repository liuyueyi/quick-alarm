package com.hust.hui.alarm.core.loader.parse;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.hust.hui.alarm.core.entity.AlarmConfig;
import com.hust.hui.alarm.core.entity.AlarmThreshold;
import com.hust.hui.alarm.core.entity.BasicAlarmConfig;
import com.hust.hui.alarm.core.entity.BasicAlarmThreshold;
import com.hust.hui.alarm.core.execut.spi.LogExecute;
import com.hust.hui.alarm.core.execut.spi.NoneExecute;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by yihui on 2017/4/28.
 */
public class AlarmConfParse {
    public static final String DEFAULT_ALARM_KEY = "default";

    private static final Logger logger = LoggerFactory.getLogger(AlarmConfParse.class);

    private static final BasicAlarmConfig DEFAULT_ALARM_CONFIG = getDefaultAlarmConfig();

    private static final TypeReference<Map<String, BasicAlarmConfig>> typeReference = new TypeReference<Map<String, BasicAlarmConfig>>() {
    };


    private static List<String> currentUsers;

    /**
     * 初始化配置信息
     *
     * @param configs
     */
    public static Map<String, AlarmConfig> parseConfig(String configs, List<String> defaultUsers) {
        currentUsers = defaultUsers;
        Map<String, BasicAlarmConfig> map = parseStrConfig2Map(configs);
        if (map == null) {
            return null;
        }


        // 尽量避免多线程获取配置参数时,出现并发修改的异常, 这里采用全覆盖方式
        ConcurrentHashMap<String, AlarmConfig> backConfigMap = new ConcurrentHashMap<>();
        AlarmConfig temp;
        for (Map.Entry<String, BasicAlarmConfig> entry : map.entrySet()) {
            temp = parse2BizConfig(entry.getValue());
            if (temp == null) {
                continue;
            }


            // 支持多个报警类型（以英文逗号进行分割） 对应同一个报警配置
            for (String key : StringUtils.split(entry.getKey(), ",")) {
                backConfigMap.put(key, temp);
            }
        }


        return backConfigMap;
    }


    /**
     * 将json串格式的报警规则配置，映射为对应实体类
     * <p>
     * 如果传如的是null, 则采用默认的兜底配置
     * 如果传入的是非法的配置，直接返回null， 这样做的目的如下
     * <p>
     * - 启动时，直接获知配置有问题，需要修改
     * - 启动中，修改配置，此时新配置有问题，依然使用旧的配置
     *
     * @param configs
     * @return
     */
    private static Map<String, BasicAlarmConfig> parseStrConfig2Map(String configs) {
        Map<String, BasicAlarmConfig> map = null;

        if (configs != null) {
            try {
                map = JSON.parseObject(configs, typeReference);
            } catch (Exception e) {
                logger.error("ConfigWrapper.parseStrConfig2Map() init config error! configs: {}, e:{}", configs, e);
                return null;
            }
        }

        if (map == null) {
            map = new HashMap<>(1);
        }


        if (!map.containsKey(DEFAULT_ALARM_KEY)) {
            map.put(DEFAULT_ALARM_KEY, DEFAULT_ALARM_CONFIG);
        }
        return map;
    }


    /**
     * 将配置项转换为业务DO对象, 会做一些兼容, 保证 level. min, max, users, thresholds 都不会为null
     *
     * @param basicAlarmConfig
     * @return
     */
    private static AlarmConfig parse2BizConfig(BasicAlarmConfig basicAlarmConfig) {
        if (basicAlarmConfig.getUsers() == null || basicAlarmConfig.getUsers().isEmpty()) { // 如果没有填写用户, 则直接抛弃
            return null;
        }

        AlarmConfig alarmConfig = new AlarmConfig();

        // 如果配置的报警类型是异常的, 则下面会兼容一把，设置为 NONE, 避免因为配置的原因导致系统异常
        alarmConfig.setAlarmLevel(basicAlarmConfig.getLevel());
        alarmConfig.setAutoIncEmergency(basicAlarmConfig.isAutoIncEmergency());
        // 报警用户, 要求用户必须存在
        alarmConfig.setUsers(basicAlarmConfig.getUsers());
        // 报警上限, 如果用户没有填写，采用默认的（因为短信报警按条数要钱, 没必要一直无上限的报）
        alarmConfig.setMaxLimit(basicAlarmConfig.getMax() == null ? AlarmConfig.DEFAULT_MAX_NUM : basicAlarmConfig.getMax());
        // 报警下限, 如果用户没有填写, 采用默认的最小值0
        alarmConfig.setMinLimit(basicAlarmConfig.getMin() == null ? AlarmConfig.DEFAULT_MIN_NUM : basicAlarmConfig.getMin());


        List<AlarmThreshold> alarmThresholdList = new ArrayList<>(basicAlarmConfig.getThreshold().size());
        for (BasicAlarmThreshold basicAlarmThreshold : basicAlarmConfig.getThreshold()) {
            AlarmThreshold temp = new AlarmThreshold();
            temp.setAlarmLevel(basicAlarmThreshold.getLevel());
            temp.setThreshold(basicAlarmThreshold.getThreshold());
            temp.setUsers(basicAlarmThreshold.getUsers());

            alarmThresholdList.add(temp);
        }


        Collections.sort(alarmThresholdList);
        alarmConfig.setAlarmThreshold(alarmThresholdList);
        return alarmConfig;
    }


    /**
     * 一个保底的报警方案
     *
     * @return
     */
    private static BasicAlarmConfig getDefaultAlarmConfig() {
        BasicAlarmConfig defaultConfig = new BasicAlarmConfig();
        defaultConfig.setMin(5);
        defaultConfig.setMax(30);
        defaultConfig.setUsers(currentUsers);
        defaultConfig.setLevel(NoneExecute.NAME);
        defaultConfig.setAutoIncEmergency(true);

        BasicAlarmThreshold logThreshold = new BasicAlarmThreshold();
        logThreshold.setThreshold(10);
        logThreshold.setLevel(LogExecute.NAME);
        logThreshold.setUsers(currentUsers);

        defaultConfig.setThreshold(Collections.singletonList(logThreshold));

        return defaultConfig;
    }


}
