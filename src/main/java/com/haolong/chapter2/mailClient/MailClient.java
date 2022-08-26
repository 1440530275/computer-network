package com.haolong.chapter2.mailClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Base64;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author weihaolong
 * @date 2020-02-11 11:35:44
 */
public class MailClient {

    public Socket socket;

    /**
     * 当前连接状态
     */
    private Boolean isConnection;

    public MailClient(String host, int port) {
        if (host == null || port == 0) {
            throw new RuntimeException("参数不全");
        }
        try {
            socket = new Socket(host, port);
            isConnection = true;
            if(!isOK(socket.getInputStream())){
                throw new RuntimeException("连接服务器出错!!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void login(String user, String pass) {
        if(user == null || pass == null){
            throw new RuntimeException("用户名和密码不允许为空!");
        }
        sendCommand("HELO HI\r\n");

        sendCommand("Auth login\r\n");

        //用户名和密码
        sendCommand(new String(Base64.getEncoder().encode(user.getBytes())) + "\r\n");
        sendCommand(new String(Base64.getEncoder().encode(pass.getBytes())) + "\r\n");
    }

    /**
     * 发送命令
     *
     * @param msg 命令消息
     */
    public void sendCommand(String msg) {
        System.out.println(msg);
        if(!isConnection){
            throw new RuntimeException("当前连接已经关闭");
        }
        byte[] sendBytes = msg.getBytes();
        try {
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(sendBytes);
            outputStream.flush();
            if(!isOK(socket.getInputStream())){
                //失败了直接退出
                quit();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送邮件
     */
    public void sendMail(MailBean mailBean){
        if(!isConnection){
            throw new RuntimeException("连接失败， 检查失败原因");
        }

        //收件人
        sendCommand("MAIL FROM:<" + mailBean.getMailFrom() + ">" + "\r\n");

        //发件人
        for (String s : mailBean.getReceiver()) {
            sendCommand("RCPT TO:<" + s + ">" + "\r\n");
        }

        //数据
        sendCommand("DATA\r\n");

        //发送整体报文
        sendCommand(mailBean.toString());

        //直接销毁对象
        mailBean = null;
    }

    /**
     * 发送完毕之后进行退出操作
     */
    public void quit() {
        sendCommand("quit\r\n");

        //tcp直接断开。
        isConnection = false;
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isOK(InputStream inputStream) {
        byte[] bytes = new byte[1024];
        int len;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            while ((len = inputStream.read(bytes)) != -1) {
                stringBuilder.append(new String(bytes, 0, len));
                if (len < bytes.length) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(stringBuilder);
        String response = stringBuilder.toString();
        if (response.startsWith("2") || response.startsWith("3")) {
            // 211 系统状态或系统帮助响应
            // 214 帮助信息
            // 220 <domain> 服务就绪
            // 221 <domain> 服务关闭传输信道
            // 250 要求的邮件操作完成
            // 251 用户非本地，将转发向<forward-path>
            // 354 开始邮件输入，以<CRLF>.<CRLF>结束
            return true;
        }

        // 421 <domain> 服务未就绪，关闭传输信道（当必须关闭时，此应答可以作为对任何命令的响应）
        // 450 要求的邮件操作未完成，邮箱不可用（例如，邮箱忙）
        // 451 放弃要求的操作；处理过程中出错
        // 452 系统存储不足，要求的操作未执行
        // 500 格式错误，命令不可识别（此错误也包括命令行过长）
        // 501 参数格式错误
        // 502 命令不可实现
        // 503 错误的命令序列
        // 504 命令参数不可实现
        // 550 要求的邮件操作未完成，邮箱不可用（例如，邮箱未找到，或不可访问）Connection frequency limited
        // 551 用户非本地，请尝试<forward-path>
        // 552 过量的存储分配，要求的操作未执行
        // 553 邮箱名不可用，要求的操作未执行（例如邮箱格式错误）
        // 554 操作失败
        return false;
    }

    public static void main(String[] args) {
        MailClient mailClient = new MailClient("smtp.mxhichina.com", 25);

        //进行登录操作
        mailClient.login("", "");

        //发送邮件
        String mailFrom = "";
        Set<String> receiver = Stream.of("").collect(Collectors.toSet());
        String subject = "手写邮箱客户端进行发送数据";
        String content = "发送真的会成功!";
        MailBean mailBean = new MailBean(mailFrom, receiver, subject, content);
        mailClient.sendMail(mailBean);

        //发送成功之后进行退出操作
        mailClient.quit();
    }


}
