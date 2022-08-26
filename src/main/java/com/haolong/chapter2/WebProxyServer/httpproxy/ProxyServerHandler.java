package com.haolong.chapter2.WebProxyServer.httpproxy;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

public class ProxyServerHandler implements HttpHandler {

    private final Boolean probe;

    static Set<String> responseHeaderExclusive;

    static Set<String> requestHeaderExclusive;

    private static final String CONTENT_ENCODING = "Content-Encoding";
    private static final String ACCEPT_ENCODING = "Accept-Encoding";
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String TRANSFER_ENCODING = "Transfer-Encoding";
    private static final String CHUNKED = "chunked";

    static Set<String> contentEncodingSet;

    public ProxyServerHandler(Boolean probe) {
        this.probe = probe;
    }

    static {
        String[] responseHeaders = new String[]{
                "Connection", "Keep-Alive", "Proxy-Authenticate", "Proxy-Authorization",
                "TE", "Trailers", CONTENT_LENGTH, "Transfer-Encoding"}; //, "Transfer-Encoding"
        responseHeaderExclusive = new HashSet<>();
        responseHeaderExclusive.addAll(Arrays.asList(responseHeaders));


        String[] headers = new String[]{
                "Connection", "Keep-Alive", "Proxy-Authenticate", "Proxy-Authorization",
                "TE", "Trailers"};
        requestHeaderExclusive = new HashSet<>();
        requestHeaderExclusive.addAll(Arrays.asList(headers));

        String[] compress = new String[]{"gzip", "deflate"};
        contentEncodingSet = new HashSet<>();
        contentEncodingSet.addAll(Arrays.asList(compress));
    }

    @Override
    public void handle(HttpExchange exchange) throws MalformedURLException {
        URL url = exchange.getRequestURI().toURL();
        HttpURLConnection urlConnection;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(3000);
            urlConnection.setConnectTimeout(3000);
            urlConnection.setRequestMethod(exchange.getRequestMethod()); //方法
            urlConnection.setDoOutput(true);
            Headers headers = exchange.getRequestHeaders();

            //header无法进行设置，只能通过其它方式处理。所以使用原生的并不合适
            copyRequestHeaders(urlConnection, exchange); //请求header
            if (headers.getFirst(CONTENT_LENGTH) != null || headers.getFirst(TRANSFER_ENCODING) != null) {
                //有请求的主题内容， 需要进行赋值
                exchange.getRequestBody().transferTo(urlConnection.getOutputStream());
            }
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
                System.out.println(key + ":" + responseHeaders.get(key).toString());
            }
//            List<String> ce = exchange.getResponseHeaders().get(CONTENT_ENCODING);
//            String contentEncoding = ce.get(0);
//            List<String> ac = exchange.getRequestHeaders().get(ACCEPT_ENCODING);
//            String acceptEncoding = ac.get(0);
//            if(probe && contentEncodingSet.contains(contentEncoding)){
//                exchange.getResponseHeaders().put(TRANSFER_ENCODING, Collections.singletonList(CHUNKED));
//                exchange.sendResponseHeaders(urlConnection.getResponseCode(), 0);
//                byte[] b = StreamUtils.readStream(urlConnection.getInputStream(), contentEncoding); //读数据,没意义
//                System.out.println(b.length);
//                byte[] b = response.getBytes(StandardCharsets.UTF_8);
//                readContent(response);
//                if(b.length > 0) {
//                    StreamUtils.writeOutputStream(exchange.getResponseBody(), b);
//                }
//                urlConnection.getInputStream().transferTo(exchange.getResponseBody());
//            }else{
            //直接进行流输出
            exchange.sendResponseHeaders(urlConnection.getResponseCode(), 0);
            urlConnection.getInputStream().transferTo(exchange.getResponseBody());
//            }
        } catch (IOException e) {
            System.out.println("请求发生异常:" + e.getMessage());
        } finally {
            exchange.close();
        }
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

    //第一，需要判断服务器返回的是 chunked还是content-length
    //第二，由于是流，读取一次之后解析出来，需要进行再次传输，所以，需要再次编码，将编码数据为编码为常见的格式，并且进行输出
    private void readContent(String content) {

    }
}
