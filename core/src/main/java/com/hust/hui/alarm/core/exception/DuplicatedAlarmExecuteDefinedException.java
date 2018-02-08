package com.hust.hui.alarm.core.exception;

/**
 * Created by yihui on 2018/2/7.
 */
public class DuplicatedAlarmExecuteDefinedException extends RuntimeException {

    public DuplicatedAlarmExecuteDefinedException(String message) {
        super(message);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
