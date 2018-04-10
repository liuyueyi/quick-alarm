package com.hust.hui.alarm.plugin.email.wrapper;

import com.hust.hui.alarm.core.loader.entity.RegisterInfo;
import com.hust.hui.alarm.core.loader.helper.RegisterInfoLoaderHelper;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

/**
 * 选择email报警时，必须指定对应的帐号信息
 *
 * Created by yihui on 2018/4/10.
 */
public class EmailWrapper {

    public static HtmlEmail genEmailClient() throws EmailException {
        RegisterInfo info = RegisterInfoLoaderHelper.load();

        HtmlEmail htmlEmail = new HtmlEmail();
        htmlEmail.setCharset("UTF-8");
        htmlEmail.setHostName(info.getEmailHost());
        htmlEmail.setSmtpPort(info.getEmailPort());
        htmlEmail.setFrom(info.getEmailFrom());
        htmlEmail.setAuthentication(info.getEmailUname(), info.getEmailToken());
        return htmlEmail;
    }


}
