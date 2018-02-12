package com.hust.hui.alarm.core.test.loader;


import com.hust.hui.alarm.core.entity.AlarmConfig;
import com.hust.hui.alarm.core.loader.api.IConfLoader;
import com.hust.hui.alarm.core.loader.entity.RegisterInfo;

import java.util.Arrays;
import java.util.Collections;

/**
 * Created by yihui on 2018/2/6.
 */
public class SelfAlarmConfLoader implements IConfLoader {
    @Override
    public RegisterInfo getRegisterInfo() {
        RegisterInfo registerInfo = new RegisterInfo();
        registerInfo.setMaxAlarmType(100);
        registerInfo.setDefaultAlarmUsers("yihui");
        registerInfo.setAppName("test");
        return registerInfo;
    }

    @Override
    public boolean alarmEnable() {
        return true;
    }

    @Override
    public AlarmConfig getAlarmConfig(String alarmKey) {
        //db 查询，获取对应的配置信息
        // 下面是模拟，返回一个固定的配置
        AlarmConfig alarmConfig = new AlarmConfig();
        alarmConfig.setAlarmLevel("WEIXIN");
        alarmConfig.setAutoIncEmergency(false);
        alarmConfig.setMinLimit(10);
        alarmConfig.setMaxLimit(14);
        alarmConfig.setUsers(Arrays.asList("yihui"));
        alarmConfig.setAlarmThreshold(Collections.emptyList());
        return alarmConfig;
    }

    @Override
    public int order() {
        return 0;
    }
}
