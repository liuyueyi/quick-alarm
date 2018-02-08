package com.hust.hui.alarm.core.entity;

import com.hust.hui.alarm.core.execut.spi.NoneExecute;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collections;
import java.util.List;

/**
 * Created by yihui on 2017/4/26.
 */
@Getter
@Setter
@ToString
public class BasicAlarmConfig {

    /**
     * 报警用户
     */
    private List<String> users;


    /**
     * 报警的阀值, 当autoIncEmergency设置为false时, 此处可以不配置
     */
    private List<BasicAlarmThreshold> threshold;


    /**
     * 最小的报警数
     */
    private Integer min = AlarmConfig.DEFAULT_MIN_NUM;


    /**
     * 最大的报警数
     */
    private Integer max = AlarmConfig.DEFAULT_MAX_NUM;


    /**
     * 报警类型
     */
    private String level = NoneExecute.NAME;


    /**
     * true 表示当报警超过当前的阀值之后, 将提升报警的程度
     */
    private boolean autoIncEmergency = false;


    public List<BasicAlarmThreshold> getThreshold() {
        if(threshold == null) {
            return Collections.emptyList();
        }

        return threshold;
    }
}
