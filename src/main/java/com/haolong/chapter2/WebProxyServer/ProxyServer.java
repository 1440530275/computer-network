package com.haolong.chapter2.WebProxyServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author weihaolong
 * @date 2020-02-22 14:33
 * @description 代理服务器
 */
public class ProxyServer {

    /**
     * 启动服务
     */
    public void start(){
        try {
            ServerSocket serverSocket = new ServerSocket(50023);
            while(true){
                Socket socket = serverSocket.accept();

                //把这个socket交给线程进行处理
                ProxyThread proxyThread = new ProxyThread(socket);

                //进行处理
                proxyThread.run();

//                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ProxyServer proxyServer = new ProxyServer();
        proxyServer.start();
    }
}
