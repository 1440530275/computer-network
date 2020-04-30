package com.haolong.chapter2.WebProxyServer;


import java.io.*;
import java.net.Socket;

/**
 * @author weihaolong
 * @date 2020-02-22 15:41
 * @description 可以进行优化， 写成线程池的形式
 */
public class ProxyThread extends Thread {

    Socket socket;

    public ProxyThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream proxyInputStream = null;
        OutputStream proxyOutputStream = null;
        BufferedReader bufferedReader = null;
        OutputStream outputStream = null;
        try {
            InputStream inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);

            String line;
            String firstLine = null;
            String method;
            String absoluteURI;
            String version;
            String host = null;
            int port = 0;
            StringBuffer stringBuffer = new StringBuffer();
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line + "\r\n");
                if (line.equals("")) {
                    break;
                }

                if (firstLine == null) {
                    firstLine = line;
                    String[] s = firstLine.split(" ");
                    method = s[0];
                    absoluteURI = s[1];
                    version = s[2];
                }
                if (line.contains("Host")) {
                    host = line.substring(6);
                    String[] s = host.split(":");
                    if (s.length > 1) {
                        host = host.substring(0, host.length() - s[1].length());
                        port = Integer.parseInt(s[1]);
                    } else {
                        port = 80;
                    }
                }
            }
            //暂时先不考虑https的事情
            System.out.println("进行转发给:" + host + ":" + port);
            //走到这一步说明已经握手了
            Socket proxySocket = new Socket(host, port);

            proxyInputStream = proxySocket.getInputStream();
            proxyOutputStream = proxySocket.getOutputStream();

            //首先先进行将实现读取出来的数据写入进去
            proxyOutputStream.write(stringBuffer.toString().getBytes());

            new ProxyHandleThread(inputStream, proxyOutputStream).start();

            //转发给客户
            while (true) {
                outputStream.write(proxyInputStream.read());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }

                if (outputStream != null) {
                    outputStream.close();

                }

                if (proxyInputStream != null) {
                    proxyInputStream.close();
                }

                if (proxyOutputStream != null) {
                    proxyOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
