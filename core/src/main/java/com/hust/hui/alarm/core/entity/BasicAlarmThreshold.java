package com.hust.hui.alarm.core.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Created by yihui on 2017/5/12.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class BasicAlarmThreshold {

    private String level;

    /**
     * 启用定义的报警方式的阀值下限，
     *
     * 当报警计数 count >= min
     *   - max 非null, count < max 则选择本报警方式; count >= max 则不选择本报警方式
     *   - max 为null（即表示为定义时），则max赋值为  恰好大于 min 的 {@link BasicAlarmThreshold#threshold}值
     *
     */
    private int threshold;


    /**
     * 报警
     */
    private Integer max;

    private List<String> users;
}
