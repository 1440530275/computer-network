package com.haolong.chapter2.WebProxyServer;

import com.haolong.util.IOUtils;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.util.*;

public class ProxyServerHandler implements HttpHandler {

    static Set<String> responseHeaderExclusive;

    static Set<String> requestHeaderExclusive;

    static {
        responseHeaderExclusive = new HashSet<>();
        responseHeaderExclusive.add("Content-Length");
        responseHeaderExclusive.add("Date");


        String[] headers = new String[]{
                "Connection", "Keep-Alive", "Proxy-Authenticate", "Proxy-Authorization",
                "TE", "Trailers", "Transfer-Encoding", "Upgrade"};
        requestHeaderExclusive = new HashSet<>();
        requestHeaderExclusive.addAll(Arrays.asList(headers));
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        //进行处理的部分， 维持长链接
        URL url = exchange.getRequestURI().toURL();
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        Headers headers = exchange.getRequestHeaders();
        if (headers.getFirst("Content-Length") != null || headers.getFirst("Transfer-Encoding") != null) {
            //有请求的主题内容， 需要进行赋值
            byte[] requestBodyBytes = IOUtils.read(exchange.getRequestBody());
            IOUtils.write(requestBodyBytes, urlConnection.getOutputStream());
        }
        //header无法进行设置，只能通过其它方式处理。所以使用原生的并不合适
//        copyRequestHeaders(urlConnection, exchange); //请求header
        urlConnection.setRequestMethod(exchange.getRequestMethod()); //方法
        //进行接连
        urlConnection.connect();
        //返回数据处理
        Map<String, List<String>> responseHeaders = urlConnection.getHeaderFields(); //获取请求之后的返回报文头
        for (String key : responseHeaders.keySet()) {
            if (key == null || key.length() == 0) {
                continue;
            }
            if (responseHeaderExclusive.contains(key)) {
                continue;
            }
            exchange.getResponseHeaders().put(key, responseHeaders.get(key));
        }
        byte[] responseByte = IOUtils.read(urlConnection.getInputStream()); //读数据
//        long l = urlConnection.getInputStream().transferTo(exchange.getResponseBody());
//        int length = streamReversal(exchange.getResponseBody(), urlConnection.getInputStream());
        exchange.sendResponseHeaders(urlConnection.getResponseCode(), responseByte.length);

        IOUtils.write(responseByte, exchange.getResponseBody());
        exchange.close();
    }

    protected void copyRequestHeaders(URLConnection connection, HttpExchange exchange) {
        Headers headers = exchange.getRequestHeaders();
        for (String key : headers.keySet()) {
            copyRequestHeader(connection, headers, key);
        }
    }

    protected void copyRequestHeader(URLConnection connection, Headers headers, String headerName) {
        //Instead the content-length is effectively set via InputStreamEntity
        if (headerName.equalsIgnoreCase("Content-Length"))
            return;
        if (requestHeaderExclusive.contains(headerName))
            return;
        if (headerName.equalsIgnoreCase("Accept-encoding"))
            return;
        List<String> enumeration = headers.get(headerName);
        connection.getHeaderFields().put(headerName, enumeration);
//        while (enumeration.hasMoreElements()) {//sometimes more than one value
//            String headerValue = enumeration.nextElement();
//            // In case the proxy host is running multiple virtual servers,
//            // rewrite the Host header to ensure that we get content from
//            // the correct virtual server
//            if (!doPreserveHost && headerName.equalsIgnoreCase(HttpHeaders.HOST)) {
//                HttpHost host = getTargetHost(servletRequest);
//                headerValue = host.getHostName();
//                if (host.getPort() != -1)
//                    headerValue += ":"+host.getPort();
//            } else if (!doPreserveCookies && headerName.equalsIgnoreCase(org.apache.http.cookie.SM.COOKIE)) {
//                headerValue = getRealCookie(headerValue);
//            }
//            proxyRequest.addHeader(headerName, headerValue);
//        }
    }
}
