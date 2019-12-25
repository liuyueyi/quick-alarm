package com.hust.hui.alarm.core.execut.spi;

import com.hust.hui.alarm.core.execut.api.IExecute;
import com.hust.hui.alarm.core.execut.gen.ExecuteNameGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 空报警执行器, 什么都不干
 * <p>
 * Created by yihui on 2017/5/12.
 */
public class NoneExecute implements IExecute {
    public static final String NAME = ExecuteNameGenerator.genExecuteName(NoneExecute.class);

    private static final Logger logger = LoggerFactory.getLogger("alarm");

    @Override
    public void sendMsg(List<String> users, String title, String msg) {
        if (logger.isDebugEnabled()) {
            logger.debug("{} mock! users: {}, title: {}, msg: {}", this.getName(), users, title, msg);
        }
    }
}
