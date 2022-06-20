package com.haolong.chapter2.WebProxyServer;

import org.eclipse.jetty.http.*;

import java.nio.ByteBuffer;

public class ProxyResponseHandler extends HttpTester.Message implements  HttpParser.ResponseHandler {

    private int status;

    private String reason;

    @Override
    public void startResponse(HttpVersion version, int status, String reason) {
        this.status = status;
        this.reason = reason;
    }

    @Override
    public MetaData getInfo() {
        return new MetaData.Response(getVersion(), status, reason, this, getContentBytes() == null ? -1 : getContentBytes().length);
    }
}
