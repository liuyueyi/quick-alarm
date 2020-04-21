package com.hust.hui.alarm.plugin.feishu.util;

import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by @author yihui in 12:01 20/4/21.
 */
public class FeishuPublisher {
    private static final String TEMPLATE = "title:\t%s\n\ncontent:\t%s";
    private static final Logger logger = LoggerFactory.getLogger(FeishuPublisher.class);
    private static final String FEISHU_URL = "https://open.feishu.cn/open-apis/bot/hook/";
    private static final MediaType JSON;
    private static OkHttpClient okHttpClient;

    static {
        okHttpClient = new OkHttpClient();
        JSON = MediaType.get("application/json; charset=utf-8");
    }

    public static void sendMessage(String title, String content, String token) {
        try {
            doPost(title, content, token);
        } catch (Exception e) {
            logger.error("failed to publish msg: {} to DingDing! {}", content, e);
        }
    }

    public static String doPost(String title, String content, String token) throws IOException {
        RequestBody body = RequestBody.create(buildTextMsgBody(title, content), JSON);

        try (Response response = okHttpClient.newCall(new Request.Builder().url(FEISHU_URL + token).post(body).build())
                .execute()) {
            return response.body().string();
        }
    }

    private static String buildTextMsgBody(String title, String content) {
        JSONObject msg = new JSONObject(4);
        msg.put("title", title);
        msg.put("text", content);
        return msg.toJSONString();
    }
}
