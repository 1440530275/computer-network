package com.haolong.chapter2.mailClient;

import java.util.Calendar;
import java.util.Set;

/**
 * @author weihaolong
 * @date 2020-02-12 09:56:27
 * @description 邮件bean
 */
public class MailBean {

    /**
     * 发件人
     */
    private String mailFrom;

    /**
     * 收件人
     */
    private Set<String> receiver;

    /**
     * 发送的主题
     */
    private String subject;

    /**
     * 消息内容
     */
    private String content;

    public MailBean(String mailFrom, Set<String> receiver, String subject, String content){
        this.mailFrom = mailFrom;
        this.receiver = receiver;
        this.subject = subject;
        this.content = content;
    }

    public String getMailFrom(){
        return this.mailFrom;
    }

    public Set<String> getReceiver(){
        return this.receiver;
    }

    public String getSubject(){
        return this.subject;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("From:<" + mailFrom + ">\r\n");
        for (String s : receiver) {
            stringBuilder.append("To:<" + s + ">\r\n");
        }
        stringBuilder.append("Subject:" + subject + "\r\n");
        stringBuilder.append("Date:" + Calendar.getInstance().getTime().toString() + "\r\n");
        stringBuilder.append("Content-Type:text/plain;charset=\"GB2312\"" + "\r\n");
        stringBuilder.append("\r\n");
        stringBuilder.append(content);
        stringBuilder.append("\r\n.\r\n");
        return stringBuilder.toString();
    }
}
