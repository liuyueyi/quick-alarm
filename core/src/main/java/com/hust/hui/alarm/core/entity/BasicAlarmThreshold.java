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

    private int threshold;

    private List<String> users;
}
