package com.hust.hui.alarm.core.test.conf;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.hust.hui.alarm.core.entity.AlarmConfig;
import com.hust.hui.alarm.core.loader.parse.AlarmConfParse;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by yihui on 2018/3/5.
 */
public class AlarmConfParseTest {


    @Test
    public void testParse() {
        try {
            // 正常来讲，是一个完整的json串
            List<String> list = IOUtils.readLines(this.getClass().getClassLoader().getResourceAsStream("alarmConfig"));
            String config = Joiner.on("").join(list);
            Map<String, AlarmConfig> map = AlarmConfParse.parseConfig(config, Splitter.on(",").splitToList("test1,test2"));
            System.out.println(map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
