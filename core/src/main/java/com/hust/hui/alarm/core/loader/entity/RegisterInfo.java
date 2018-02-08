package com.hust.hui.alarm.core.loader.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 注册实体类
 * <p>
 * Created by yihui on 2018/2/7.
 */
@Data
public class RegisterInfo implements Serializable {

    private String alarmConfPath;

    private Integer maxAlarmType;

    private String defaultAlarmUsers;

    private String appName;
}
