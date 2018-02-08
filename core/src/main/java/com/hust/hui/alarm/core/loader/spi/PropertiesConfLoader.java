package com.hust.hui.alarm.core.loader.spi;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.hust.hui.alarm.core.entity.AlarmConfig;
import com.hust.hui.alarm.core.loader.api.IConfLoader;
import com.hust.hui.alarm.core.loader.entity.RegisterInfo;
import com.hust.hui.alarm.core.loader.helper.PropertiesConfListenerHelper;
import com.hust.hui.alarm.core.loader.helper.RegisterInfoLoaderHelper;
import com.hust.hui.alarm.core.loader.parse.AlarmConfParse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * Created by yihui on 2018/2/7.
 */
@Slf4j
public class PropertiesConfLoader implements IConfLoader {

    private RegisterInfo registerInfo;

    private Map<String, AlarmConfig> cacheMap;

    public boolean load() {
        // 获取注册信息
        registerInfo = RegisterInfoLoaderHelper.load();
        if (registerInfo == null) {
            return false;
        }


        // 获取报警的配置类
        File file;
        String path = registerInfo.getAlarmConfPath();
        if (path.startsWith("/")) {
            file = new File(path);
        } else {
            URL url = this.getClass().getClassLoader().getResource(path);
            file = new File(url.getFile());
        }


        // 加载成功，才替换 cacheMap的内容； 主要是为了防止修改配置出现问题
        Map<String, AlarmConfig> tmp = init(file);
        boolean ans = tmp != null;
        // 注册配置文件的变动
        ans = ans && PropertiesConfListenerHelper.registerConfChangeListener(file, this::init);

        if (ans) {
            cacheMap = tmp;
        }
        return ans;
    }


    private Map<String, AlarmConfig> init(File file) {
        try {
            // 正常来讲，是一个完整的json串
            List<String> list = IOUtils.readLines(new FileInputStream(file), "utf-8");
            String config = Joiner.on("").join(list);
            return AlarmConfParse.parseConfig(config, Splitter.on(",").splitToList(registerInfo.getDefaultAlarmUsers()));
        } catch (IOException e) {
            log.error("load config into cacheMap error! e: {}", e);
            return null;
        }
    }


    @Override
    public RegisterInfo getRegisterInfo() {
        return registerInfo;
    }

    @Override
    public boolean alarmEnable() {
        return true;
    }

    @Override
    public AlarmConfig getAlarmConfig(String alarmKey) {
        AlarmConfig config = cacheMap.get(alarmKey);
        if (config == null) {
            return cacheMap.get(AlarmConfParse.DEFAULT_ALARM_KEY);
        } else {
            return config;
        }
    }
}
