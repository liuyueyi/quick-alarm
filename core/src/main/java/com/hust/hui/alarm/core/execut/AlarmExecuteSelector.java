package com.hust.hui.alarm.core.execut;


import com.hust.hui.alarm.core.execut.spi.NoneExecute;
import com.hust.hui.alarm.core.entity.AlarmConfig;
import com.hust.hui.alarm.core.entity.AlarmThreshold;
import com.hust.hui.alarm.core.helper.ExecuteHelper;

import java.util.List;
import java.util.Objects;

/**
 * 报警选择器, 选择具体的 IExecut 执行报警
 * <p/>
 * Created by yihui on 2017/4/19.
 */
public class AlarmExecuteSelector {


    public static ExecuteHelper getDefaultExecute() {
        return ExecuteHelper.DEFAULT_EXECUTE;
    }


    /**
     * 获取具体的报警执行器
     * <p>
     * 1. 未开启严重等级上升时, 直接返回
     * 2. 开启之后, 判断当前的计数 范围
     *
     * @param alarmConfig 报警配置项, 内部所有的参数都不可能为null
     */
    public static ExecuteHelper getExecute(final AlarmConfig alarmConfig, int count) {
        // 未达到报警的下限 or 超过报警的上限时
        if (count < alarmConfig.getMinLimit() || count > alarmConfig.getMaxLimit()) {
            return new ExecuteHelper(SimpleExecuteFactory.getExecute(NoneExecute.NAME), alarmConfig.getUsers());
        }


        // 未开启报警升级, 直接返回
        if (!alarmConfig.isAutoIncEmergency()) {
            return new ExecuteHelper(SimpleExecuteFactory.getExecute(alarmConfig.getAlarmLevel()), alarmConfig.getUsers());
        }


        // 报警等级开启上升之趋势
        // 1. 获取设置的默认等级
        // 2. 判断当前的报警次数, 选择对应的报警类型
        // 3. 选择具体的报警类型

        String defaultLevel = alarmConfig.getAlarmLevel();
        String selectLevel = null;
        List<String> selectUser = alarmConfig.getUsers();

        List<AlarmThreshold> list = alarmConfig.getAlarmThreshold();
        boolean useNew = false;
        boolean containDefaultLevel = false;
        for (AlarmThreshold alarmThreshold : list) {
            if (Objects.equals(alarmThreshold.getAlarmLevel(), defaultLevel)) {
                containDefaultLevel = true;
            }
        }


        for (AlarmThreshold alarmThreshold : list) {
            // 表示当前的报警等级已经赶上默认的报警等级了, 所以要选择新的报警类型
            if (Objects.equals(alarmThreshold.getAlarmLevel(), defaultLevel)) {
                useNew = true;
            }

            if (count < alarmThreshold.getThreshold()) {
                break;
            }

            selectLevel = alarmThreshold.getAlarmLevel();
            selectUser = alarmThreshold.getUsers(); // 选择新的报警类型时, 需要更新报警用户
        }


        // 阀值列表中不包含默认报警类型，则根据新的来
        if (!containDefaultLevel && selectLevel != null) {
            return new ExecuteHelper(SimpleExecuteFactory.getExecute(selectLevel), selectUser);
        }


        // 如果阀值列表中包含了默认报警类型, 且已经超过默认阀值
        if (useNew && selectLevel != null) {
            return new ExecuteHelper(SimpleExecuteFactory.getExecute(selectLevel), selectUser);
        } else {
            return new ExecuteHelper(SimpleExecuteFactory.getExecute(defaultLevel), alarmConfig.getUsers());
        }
    }
}
