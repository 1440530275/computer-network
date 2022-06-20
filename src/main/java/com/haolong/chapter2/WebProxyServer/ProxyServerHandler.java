package com.haolong.chapter2.WebProxyServer;

import com.haolong.util.IOUtils;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.eclipse.jetty.http.HttpField;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpTester;
import org.eclipse.jetty.http.HttpVersion;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.*;

//@Slf4j
public class ProxyServerHandler implements HttpHandler {

    static Set<String> responseHeaderExclusive;

    static Set<String> requestHeaderExclusive;

    static {
        String[] responseHeaders = new String[] {
                "Connection", "Keep-Alive", "Proxy-Authenticate", "Proxy-Authorization",
                "TE", "Trailers", "Transfer-Encoding", "Content-Length"};
        responseHeaderExclusive = new HashSet<>();
        responseHeaderExclusive.addAll(Arrays.asList(responseHeaders));


        String[] headers = new String[]{
                "Connection", "Keep-Alive", "Proxy-Authenticate", "Proxy-Authorization",
                "TE", "Trailers"};
        requestHeaderExclusive = new HashSet<>();
        requestHeaderExclusive.addAll(Arrays.asList(headers));
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        URL url = exchange.getRequestURI().toURL();
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        Headers headers = exchange.getRequestHeaders();
        //header无法进行设置，只能通过其它方式处理。所以使用原生的并不合适
        copyRequestHeaders(urlConnection, exchange); //请求header
        if (headers.getFirst("Content-Length") != null || headers.getFirst("Transfer-Encoding") != null) {
            //有请求的主题内容， 需要进行赋值
            byte[] requestBodyBytes = IOUtils.read(exchange.getRequestBody());
            IOUtils.write(requestBodyBytes, urlConnection.getOutputStream()); //长度没有设置
//            String contentLengthHeader = headers.getFirst("Content-Length");
//            if (contentLengthHeader != null) {
//                urlConnection.setRequestProperty("Content-Length", contentLengthHeader);
//            }
        }
        urlConnection.setRequestMethod(exchange.getRequestMethod()); //方法
        //进行接连
//        urlConnection.connect(); //握手请求
        //返回数据处理
        Map<String, List<String>> responseHeaders = urlConnection.getHeaderFields(); //获取请求之后的返回报文头
        System.out.println("返回消息头数据:");
        for (String key : responseHeaders.keySet()) {
            if (key == null || key.length() == 0) {
                continue;
            }
            if (responseHeaderExclusive.contains(key)) {
                continue;
            }
            System.out.println(key + ":" + responseHeaders.get(key));
            exchange.getResponseHeaders().put(key, responseHeaders.get(key));
        }
        byte[] responseByte = IOUtils.read(urlConnection.getInputStream()); //读数据
        System.out.println("消息体:" + new String(responseByte, StandardCharsets.UTF_8));
        exchange.sendResponseHeaders(urlConnection.getResponseCode(), responseByte.length > 0 ? -1 : responseByte.length);
        if(responseByte.length > 0){
            exchange.getResponseBody().write(responseByte);
        }
        exchange.close();
    }

    protected void copyRequestHeaders(URLConnection connection, HttpExchange exchange) {
        Headers headers = exchange.getRequestHeaders();
        for (String key : headers.keySet()) {
            copyRequestHeader(connection, headers, key);
        }
    }

    protected void copyRequestHeader(URLConnection connection, Headers headers, String headerName) {
        if (requestHeaderExclusive.contains(headerName))
            return;
        List<String> enumeration = headers.get(headerName);
        for (String e : enumeration) {//sometimes more than one value
            connection.setRequestProperty(headerName, e);
        }
    }

//    private void setXForwardedForHeader(Headers headers,
//                                        HttpRequest proxyRequest) {
//        String forHeaderName = "X-Forwarded-For";
//        String forHeader = servletRequest.getRemoteAddr(); //地址
//        String existingForHeader = headers.getFirst(forHeaderName);
//        if (existingForHeader != null) {
//            forHeader = existingForHeader + ", " + forHeader;
//        }
//        proxyRequest.setHeader(forHeaderName, forHeader);
//
//        String protoHeaderName = "X-Forwarded-Proto";
//        String protoHeader = servletRequest.getScheme();
//        proxyRequest.setHeader(protoHeaderName, protoHeader);
//    }


    public static void main(String[] args) {
        try(Socket socket = new Socket("www.google.com",80))
        {
            HttpTester.Request request = HttpTester.newRequest();
            request.setMethod("POST");
            request.setURI("/search");
            request.setVersion(HttpVersion.HTTP_1_0);
            request.put(HttpHeader.HOST,"www.google.com");
            request.put("Content-Type","application/x-www-form-urlencoded");
            request.setContent("q=jetty%20server");
            ByteBuffer output = request.generate();

            socket.getOutputStream().write(output.array(),output.arrayOffset()+output.position(),output.remaining());
            HttpTester.Input input = HttpTester.from(socket.getInputStream());
            HttpTester.Response response = HttpTester.parseResponse(input);
            System.err.printf("%s %s %s%n",response.getVersion(),response.getStatus(),response.getReason());
            for (HttpField field:response)
                System.err.printf("%s: %s%n",field.getName(),field.getValue());
            System.err.printf("%n%s%n",response.getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
