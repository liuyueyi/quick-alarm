package com.hust.hui.alarm.core.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Created by yihui on 2017/4/28.
 */
@Getter
@Setter
@ToString
public class AlarmThreshold implements Comparable<AlarmThreshold> {

    /**
     * 报警类型
     */
    private String alarmLevel;


    /**
     * 晋升此报警的阀值
     */
    private int threshold;


    /**
     * 对应的报警用户
     */
    private List<String> users;


    @Override
    public int compareTo(AlarmThreshold o) {
        if (o == null) {
            return -1;
        }

        return threshold - o.getThreshold();
    }
}
