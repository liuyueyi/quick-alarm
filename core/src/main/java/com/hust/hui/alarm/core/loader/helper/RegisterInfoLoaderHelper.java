package com.hust.hui.alarm.core.loader.helper;

import com.hust.hui.alarm.core.exception.RegisterInfoIllegalException;
import com.hust.hui.alarm.core.loader.entity.RegisterInfo;
import com.hust.hui.alarm.core.loader.util.PropertiesUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * Created by yihui on 2018/2/7.
 */
public class RegisterInfoLoaderHelper {

    private static final Logger log = LoggerFactory.getLogger(RegisterInfoLoaderHelper.class);

    private static final String DEFAULT_CONF_NAME = "alarm.properties";

    public static RegisterInfo load() {
        try {
            Properties properties = PropertiesUtil.read(DEFAULT_CONF_NAME);

            RegisterInfo info = new RegisterInfo();
            PropertiesUtil.copy(properties, info);
            checkRegisterInfo(info);

            return info;
        } catch (RegisterInfoIllegalException ex) {
            log.error("illegal register info: {}", ex);
            return null;
        } catch (Exception e) {
            log.error("load register info error: {}", e);
            return null;
        }
    }


    private static void checkRegisterInfo(RegisterInfo registerInfo) {
        if (StringUtils.isBlank(registerInfo.getAlarmConfPath())) {
            throw new RegisterInfoIllegalException("alarmConfPath should not be null or empty!");
        }


        if (StringUtils.isBlank(registerInfo.getAppName())) {
            throw new RegisterInfoIllegalException("appName should not be null or empty!");
        }


        if (StringUtils.isBlank(registerInfo.getDefaultAlarmUsers())) {
            throw new RegisterInfoIllegalException("defaultAlarmUser should not be null or empty!");
        }

        if (registerInfo.getMaxAlarmType() == null) {
            registerInfo.setMaxAlarmType(1000);
        }
    }
}
