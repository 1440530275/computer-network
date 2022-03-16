package net;

import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

/**
 * @author weihaolong
 * @date 2021-12-01
 * 因特网地址测试
 */
public class InetAddressTest {

    @Test
    public void getHostAddressTest() throws UnknownHostException {
        String host = "www.baidu.com";
        String ipAddress = "112.80.248.0";
        InetAddress inetAddress = InetAddress.getByAddress(host, new byte[]{112, 80, (byte) 248, 0});
        System.out.println(inetAddress.toString());
    }
}
