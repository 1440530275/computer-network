package com.haolong.chapter2.udpPing;

import java.io.IOException;
import java.net.*;

/**
 * @author weihaolong
 * @date 2020-02-06 21:43:47
 * @desc 写一个udp程序进行ping
 */
public class UdpPingClient {

    public static void client() {
        try {
            DatagramSocket datagramSocket = new DatagramSocket(12001);
            datagramSocket.setSoTimeout(1000);
            for (int i = 0; i < 10; i++) {
                byte[] sendBytes;
                String msg = "测试ping";
                sendBytes = msg.getBytes();
                //发送的数据包
                DatagramPacket sendPacket = new DatagramPacket(sendBytes, sendBytes.length);
                InetSocketAddress socketAddress = new InetSocketAddress("127.0.0.1", 12000);
                sendPacket.setSocketAddress(socketAddress);

                long startTime = System.currentTimeMillis();
                datagramSocket.send(sendPacket);

                //进行接收消息
                byte[] receiveBytes = new byte[64];
                DatagramPacket receivePacket = new DatagramPacket(receiveBytes, receiveBytes.length);

                //进行阻塞收到包为止
                try {
                    datagramSocket.receive(receivePacket);
                    //超时或者接收到数据
                    System.out.println("64 bytes from " + socketAddress.getAddress() + " time= " + (System.currentTimeMillis() - startTime) + "ms");
                } catch (SocketTimeoutException e) {
                    System.out.println("接收数据超时！");
                }
            }

            datagramSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        client();
    }

}
