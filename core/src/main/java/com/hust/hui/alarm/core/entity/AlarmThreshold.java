package com.hust.hui.alarm.core.entity;

import com.hust.hui.alarm.core.execut.api.IExecute;
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
    private IExecute executor;


    /**
     * 晋升此报警的阀值
     *
     * 报警计数count >= min && count < max, 则选择
     */
    private int min;


    private int max;


    /**
     * 对应的报警用户
     */
    private List<String> users;


    @Override
    public int compareTo(AlarmThreshold o) {
        if (o == null) {
            return -1;
        }

        return min - o.getMin();
    }
}
