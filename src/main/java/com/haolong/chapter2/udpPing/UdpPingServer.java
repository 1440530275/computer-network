package com.haolong.chapter2.udpPing;

import java.io.IOException;
import java.net.*;
import java.util.Random;

/**
 * @author weihaolong
 * @date 2020-02-10 19:35:28
 * @description udp服务
 */
public class UdpPingServer {

    public static void server(){
        try {
            //udp服务
            DatagramSocket datagramSocket = new DatagramSocket(12000);

            while(true) {
                // 2,准备接收容器
                byte[] container = new byte[64];

                //数据包
                DatagramPacket packet = new DatagramPacket(container, container.length);

                //收到数据
                datagramSocket.receive(packet);

                //进行随机返回数据， 丢包率设置为40%
                int random = new Random().nextInt(10);
                System.out.println("随机数为" + random);

                byte[] data = packet.getData();
                int length = packet.getLength();
                String msg = new String(data, 0, length);
                System.out.println("接收数据:" + msg);

                //40%几率进行不发送数据
                if(random >= 4) {
                    byte[] sendBytes = "回调msg".getBytes();
                    DatagramPacket datagramPacket = new DatagramPacket(sendBytes, sendBytes.length);
                    InetSocketAddress socketAddress = new InetSocketAddress(packet.getAddress(), packet.getPort());
                    datagramPacket.setSocketAddress(socketAddress);
                    //进行发送数据
                    datagramSocket.send(datagramPacket);
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        server();
    }
}
