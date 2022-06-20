package com.haolong.chapter2.WebProxyServer;

import org.eclipse.jetty.http.*;

import java.nio.ByteBuffer;

public class ProxyRequestHandler extends HttpTester.Message implements HttpParser.RequestHandler {

    private String method;

    private String uri;

    private HttpVersion version;

    @Override
    public void startRequest(String method, String uri, HttpVersion version) {
        this.method = method;
        this.uri = uri;
        this.version = version;
    }

    @Override
    public MetaData getInfo() {
        return new MetaData.Request(method, HttpURI.from(uri), version, this, getContent() == null ? 0 : getContentBytes().length);
    }
}
