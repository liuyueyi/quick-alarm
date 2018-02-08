package com.hust.hui.alarm.core.execut.spi;

import com.hust.hui.alarm.core.execut.api.IExecute;
import com.hust.hui.alarm.core.execut.gen.ExecuteNameGenerator;

import java.util.List;

/**
 * 空报警执行器, 什么都不干
 * <p>
 * Created by yihui on 2017/5/12.
 */
public class NoneExecute implements IExecute {
    public static final String NAME = ExecuteNameGenerator.genExecuteName(NoneExecute.class);

    @Override
    public void sendMsg(List<String> users, String title, String msg) {

    }
}
