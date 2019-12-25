package com.hust.hui.alarm.dingding.test;

import com.hust.hui.alarm.plugin.dingding.util.DingdingPublisher;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by @author yihui in 17:40 19/12/25.
 */
public class TestDingPublisher {

    @Test
    public void testPublish() throws IOException {
        String ans =
                DingdingPublisher.doPost("测试报警", "xxx");
        System.out.println(ans);
    }

}
