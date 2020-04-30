package com.haolong.chapter2.WebProxyServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author weihaolong
 * @date 2020-02-22 18:20
 * @description
 */
public class ProxyHandleThread extends Thread{

    private InputStream clientInput;
    private OutputStream proxyOutput;

    public ProxyHandleThread(InputStream clientInput, OutputStream proxyOutput) {
        this.clientInput = clientInput;
        this.proxyOutput = proxyOutput;
    }

    @Override
    public void run() {
        try {
            while (true) {
                proxyOutput.write(clientInput.read());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
