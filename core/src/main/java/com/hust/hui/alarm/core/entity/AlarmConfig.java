package com.hust.hui.alarm.core.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Created by yihui on 2017/4/19.
 */
@Getter
@Setter
@ToString
public class AlarmConfig {

    public static final int DEFAULT_MIN_NUM = 0;
    public static final int DEFAULT_MAX_NUM = 30;


    /**
     * 报警用户
     */
    private List<String> users;


    /**
     * 报警的阀值
     */
    private List<AlarmThreshold> alarmThreshold;


    /**
     * 最小的报警数
     */
    private int minLimit;


    /**
     * 最大的报警数
     */
    private int maxLimit;


    /**
     * 报警类型
     */
    private String alarmLevel;


    /**
     * true 表示当报警超过当前的阀值之后, 将提升报警的程度
     */
    private boolean autoIncEmergency;

}
