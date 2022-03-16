package com.haolong.demo.http;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public class MyHttpService {

    public static void main(String[] args) throws IOException {
        InetSocketAddress inetSocketAddress = new InetSocketAddress(InetAddress.getLocalHost(), 8080);
        HttpServer server = HttpServer.create(inetSocketAddress, 0);
        MyHttpHandler myHttpHandler = new MyHttpHandler();
        server.createContext("/", myHttpHandler);
        server.start();
    }
}
