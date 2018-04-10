package com.hust.hui.alarm.plugin.email;

import com.hust.hui.alarm.core.execut.api.IExecute;
import com.hust.hui.alarm.plugin.email.wrapper.EmailWrapper;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by yihui on 2018/4/10.
 */
public class EmailExecute implements IExecute {

    private static final Logger logger = LoggerFactory.getLogger("alarm");


    @Override
    public void sendMsg(List<String> users, String title, String msg) {
        try {
            HtmlEmail email = EmailWrapper.genEmailClient();
            email.setSubject(title);
            email.setHtmlMsg(msg);
            for (String u : users) {
                email.addTo(u);
            }

            email.send();
        } catch (EmailException e) {
            logger.error("email send error! users: {}, title: {}, msg: {} e:{}", users, title, msg, e);
        }
    }


}
