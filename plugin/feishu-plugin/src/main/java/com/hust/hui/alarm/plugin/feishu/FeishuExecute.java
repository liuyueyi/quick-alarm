package com.hust.hui.alarm.plugin.feishu;

import com.hust.hui.alarm.core.execut.api.IExecute;
import com.hust.hui.alarm.plugin.feishu.util.FeishuPublisher;

import java.util.List;

/**
 * Created by @author yihui in 12:01 20/4/21.
 */
public class FeishuExecute implements IExecute {

    @Override
    public void sendMsg(List<String> users, String title, String msg) {
        users.forEach(user -> FeishuPublisher.sendMessage(title, msg, user));
    }

}
