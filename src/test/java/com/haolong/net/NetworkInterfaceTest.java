package com.haolong.net;

import org.junit.jupiter.api.Test;

import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;

/**
 * @author weihaolong
 * @date 2021-12-01
 */
public class NetworkInterfaceTest {

    @Test
    public void getInterAddressTest() throws SocketException {
        Enumeration<NetworkInterface> enumeration =  NetworkInterface.getNetworkInterfaces();
        while(enumeration.hasMoreElements()){
            List<InterfaceAddress> interfaceAddressList = enumeration.nextElement().getInterfaceAddresses();
            for(InterfaceAddress interfaceAddress : interfaceAddressList){
                System.out.println("英特网地址:" + interfaceAddress.getAddress());
                System.out.println("广播地址:" + interfaceAddress.getBroadcast());
                System.out.println("网络前缀:" + interfaceAddress.getNetworkPrefixLength());
                System.out.println();
            }
        }
    }
}
