package com.hust.hui.alarm.plugin.email.test;

import com.hust.hui.alarm.plugin.email.wrapper.EmailWrapper;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.HtmlEmail;
import org.junit.Test;

import java.net.URL;

/**
 * Created by yihui on 2018/4/10.
 */
public class EmailWrapperTest {

    private String template = "<html><meta charset=utf-8>\n" +
            "\n" +
            "<style>\n" +
            "div.card {\n" +
            "  background-color:white; \n" +
            " box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);\n" +
            "  text-align: center;\n" +
            "}\n" +
            "\n" +
            "div.header {\n" +
            "    background-color: #4CAF50;\n" +
            "    box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);\n" +
            "    color: white;\n" +
            "    padding: 10px;\n" +
            "    font-size: 40px;\n" +
            "}\n" +
            "\n" +
            "div.container {\n" +
            "    padding: 10px;\n" +
            "}\n" +
            "</style>\n" +
            "\n" +
            "<div class=\"card\">\n" +
            "  <div class=\"header\">\n" +
            "    <h1>星期一</h1>\n" +
            "  </div>\n" +
            "\n" +
            "  <div class=\"container\">\n" +
            "    <p>2016.04.10</p>\n" +
            "  </div>\n" +
            "</div>\n" +
            "</html>";


    /**
     * fixme 在实际测试时，请修改 alarm.properties 文件中的配置信息
     */
    @Test
    public void testHtmlEmailSend() {
        try {
            // Create the attachment
            EmailAttachment attachment = new EmailAttachment();
            attachment.setURL(new URL("http://s11.mogucdn.com/mlcdn/c45406/180410_256l2egkgj3lfdkjkbf41b1i09l3f_1280x1280.jpg"));
            attachment.setDisposition(EmailAttachment.ATTACHMENT);
            attachment.setDescription("公众号");
            attachment.setName("logo.jpg");


            HtmlEmail email = EmailWrapper.genEmailClient();

            // 添加附件
            email.attach(attachment);

            email.setSubject("alarm测试!");

            // set the html message
            email.setHtmlMsg(template);

            // set the alternative message
            email.setTextMsg("Your email client does not support HTML messages");

            email.addTo("bangzewu@126.com");
            String ans = email.send();
            System.out.println(ans);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
