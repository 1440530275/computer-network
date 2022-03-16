package com.haolong.chapter2.WebProxyServer;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 * @author weihaolong
 * @date 2020-02-22 14:33
 * @desc 代理服务器, 思路。 初步实现http代理， 后续实现https代理策略。在这里不实现http frc的解析。直接使用java里面内置的解析工具使用。
 * @see com.sun.net
 */
public class ProxyServer {

    private final static Integer port = 8000;

    private final static String ROOT_PATH = "/";

    /**
     * 启动服务
     */
    public void start() throws IOException {
        InetSocketAddress inetSocketAddress = new InetSocketAddress(InetAddress.getLocalHost(), port);
        HttpServer httpServer = HttpServer.create(inetSocketAddress, 0);
        httpServer.createContext(ROOT_PATH, new ProxyServerHandler());
        httpServer.start();
    }

    public static void main(String[] args) throws IOException {
        ProxyServer proxyServer = new ProxyServer();
        proxyServer.start();
    }
}
