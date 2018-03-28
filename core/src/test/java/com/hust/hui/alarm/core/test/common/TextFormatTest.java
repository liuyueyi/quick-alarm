package com.hust.hui.alarm.core.test.common;

import org.junit.Test;

import java.text.MessageFormat;

/**
 * Created by yihui on 2018/3/27.
 */
public class TextFormatTest {

    @Test
    public void testTempate() {
        System.out.println(MessageFormat.format("hello {0}, just a test {2}", "xiao", "what", 123));
    }

}
