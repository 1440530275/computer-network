package com.haolong.chapter2.WebProxyServer.httpproxy;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpsServer;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 * @author weihaolong
 * @date 2020-02-22 14:33
 * @desc 代理服务器, 思路。 初步实现http代理， 后续实现https代理策略。在这里不实现http frc的解析
 * <p>
 * 测试，将代理模式打开，访问http://www.baidu.com
 * @see com.sun.net
 */
@Log4j2
public class ProxyServer {

    private final static Integer HTTP_PORT = 8000;

    private final static Integer HTTPS_PORT = 8001;

    private final static String ROOT_PATH = "/";

    /**
     * 启动服务
     */
    public void http_start() throws IOException {
        InetSocketAddress inetSocketAddress = new InetSocketAddress(InetAddress.getLocalHost(), HTTP_PORT);
        HttpServer httpServer = HttpServer.create(inetSocketAddress, 0);
        //默认开启探针模式, 可以进行探测相关的报文数据
        httpServer.createContext(ROOT_PATH, new ProxyServerHandler(true));
        httpServer.start();
    }

    public void https_start() throws IOException {
        InetSocketAddress inetSocketAddress = new InetSocketAddress(InetAddress.getLocalHost(), HTTPS_PORT);
        HttpsServer httpsServer = HttpsServer.create(inetSocketAddress, 0);
//        HttpsConfigurator
    }

    public static void main(String[] args) throws IOException {
        ProxyServer proxyServer = new ProxyServer();
        proxyServer.http_start();
    }
}
