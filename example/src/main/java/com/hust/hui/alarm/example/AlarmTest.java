package com.hust.hui.alarm.example;

import com.hust.hui.alarm.core.AlarmWrapper;

/**
 * Created by yihui on 2017/4/28.
 */
public class AlarmTest {


    public void sendMsg() throws InterruptedException {
        String key = "NPE";
        String title = "NPE异常";
        String msg = "出现NPE异常了!!!";


        AlarmWrapper.getInstance().sendMsg(key, title, msg);  // 微信报警


        // 不存在异常配置类型, 采用默认报警, 次数较小, 则直接部署出
        AlarmWrapper.getInstance().sendMsg("zzz", "不存在xxx异常配置", "报警嗒嗒嗒嗒");


        Thread.sleep(1000);
    }


    public void testMultiConfLoader() {
        AlarmWrapper.getInstance().sendMsg("XXX", "指定默认ConfLoader报警", "报警内容!", "输出：{3}");


        AlarmWrapper.getInstance().sendMsg("TTT", "SelfAlarmConfLoader报警", "报警内容!", "输出：{3}");
    }


    public static void main(String[] args) throws InterruptedException {
        // 测试异常升级的case
        // 计数 [1 - 2] 默认报警（即无日志） （其中 < 3 的是因为未达到下限, 采用的默认报警）
        // 计数 [3 - 4] 默认邮件报警（其中 < 5 采用的默认报警, 与下面的区别是报警用户）
        // 计数 [5 - 9] 邮件报警 （大于5小于10根据上升规则,还是选择邮件报警）
        // 计数 [10 - 12) 钉钉报警
        // 计数 [10 - 19] 微信报警
        // 计数 [20 - 30] 短信报警
        // 计数 [31 -] 默认报警 （超过上限, 不报警）
        for (int i = 0; i < 40; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    AlarmWrapper.getInstance().sendMsg("YYY", "异常报警升级测试");
                }
            }).start();
        }


        Thread.sleep(1000 * 600);
    }

}
