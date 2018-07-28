package com.hust.hui.alarm.plugin.timewindow.exception;

/**
 * Created by @author yihui in 16:50 18/6/29.
 */
public class GuavaNotFoundException extends RuntimeException{
    public GuavaNotFoundException(String message) {
        super(message);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
