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

    // 报警规则的路径
    private String alarmConfPath;

    // 最多可配置的报警规则类型
    private Integer maxAlarmType;


    // 默认的报警用户
    private String defaultAlarmUsers;


    // 应用名
    private String appName;


    // 报警规则配置信息
    private String emailHost; // 如 smtp.163.com
    private Integer emailPort; // 25
    private String emailUname; // 发送邮件的用户名
    private String emailToken; // 发送邮件的token
    private String emailFrom; // 发送邮件的帐号
    private Boolean emailSsl; // true 表示启用ssl发送

}
