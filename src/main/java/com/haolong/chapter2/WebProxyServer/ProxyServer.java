package com.haolong.chapter2.WebProxyServer;

import lombok.extern.log4j.Log4j2;
import org.eclipse.jetty.http.*;
import org.eclipse.jetty.util.BufferUtil;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * @author weihaolong
 * @date 2020-02-22 14:33
 * @desc 代理服务器, 思路。 初步实现http代理， 后续实现https代理策略。在这里不实现http frc的解析
 * <p>
 * 测试，将代理模式打开，访问http://www.baidu.com
 * @see com.sun.net
 */
@Log4j2
public class ProxyServer {

    private final static Integer port = 8000;



    /**
     * 启动服务
     */
    public void start() throws IOException {
        try {
            ServerSocket serverSocket = new ServerSocket(port); //启动服务
            while (true) {
                Socket socket = serverSocket.accept();
                //读取tcp里面的数据并且进行校验是否是http请求
                //对请求进行处理
                ProxyRequestHandler proxyRequestHandler = new ProxyRequestHandler();
                HttpParser parser = new HttpParser(proxyRequestHandler);
                parseRequest(parser, socket.getInputStream()); //对请求进行解析
                if(proxyRequestHandler.getVersion() == null){ //读取数据，请求是否是http数据， http流是什么
//                    notHttpScheme(socket);
                    socket.close();
                    continue;
                }
                log.debug("请求报文:");
                for (HttpField field : proxyRequestHandler) {
                    log.debug("{}:{}", field.getName(), field.getValues());
                }

                ProxyResponseHandler proxyResponseHandler = proxyRequest(proxyRequestHandler);

                //对返回的数据进行处理
                ByteBuffer byteBuffer = proxyResponseHandler.generate();
//                log.debug("返回报文:{}", proxyResponseHandler.generate());
//                parser.parseNext(BufferUtil.toBuffer(responseBody));
                //将byteBuffer转换成流中
                socket.getOutputStream().write(byteBuffer.array());
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void parseRequest(HttpParser parser, InputStream stream) throws IOException {
//        ProxyRequestHandler proxyRequestHandler = new ProxyRequestHandler();
//        HttpParser parser = new HttpParser(proxyRequestHandler);

        byte[] array = new byte[1];
        ByteBuffer buffer = ByteBuffer.wrap(array);
        buffer.limit(1);

        while (true) {
            buffer.position(1);
            int l = stream.read(array);
            if (l < 0)
                parser.atEOF();
            else
                buffer.position(0);

            if (parser.parseNext(buffer) || l < 0)
                return;
//            else if (l < 0)
//                return;
        }
    }

//    public static ProxyResponseHandler parseResponse(InputStream stream) throws IOException{
//        ProxyResponseHandler proxyResponseHandler = new ProxyResponseHandler();
//        HttpParser parser = new HttpParser(proxyResponseHandler);
//
//        byte[] array = new byte[1];
//        ByteBuffer buffer = ByteBuffer.wrap(array);
//        buffer.limit(1);
//
//        while (true) {
//            buffer.position(1);
//            int l = stream.read(array);
//            if (l < 0)
//                parser.atEOF();
//            else
//                buffer.position(0);
//
//            if (parser.parseNext(buffer))
//                return proxyResponseHandler;
//            else if (l < 0)
//                return null;
//        }
//    }

    private ProxyResponseHandler proxyRequest(ProxyRequestHandler requestHandler) throws IOException {
        MetaData.Request requestInfo = (MetaData.Request)requestHandler.getInfo();
        String host = requestInfo.getURI().getHost();
        int port = HttpScheme.getDefaultPort(requestInfo.getURI().getScheme());
        try(Socket socket = new Socket(host, port))
        {
            //https
            SSLSocketFactory sslSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            SSLSocket sslSocket = (SSLSocket) sslSocketFactory.createSocket(socket, host, port, true);
            sslSocket.startHandshake();
            ByteBuffer output = requestHandler.generate();
            sslSocket.getOutputStream().write(output.array(),output.arrayOffset()+output.position(),output.remaining());

//            ByteBuffer output = requestHandler.generate();
//            socket.getOutputStream().write(output.array(),output.arrayOffset()+output.position(),output.remaining());
            ProxyResponseHandler proxyResponseHandler = new ProxyResponseHandler();
            HttpParser parser = new HttpParser(proxyResponseHandler);
            parseRequest(parser, socket.getInputStream());
            return proxyResponseHandler;
        }
    }

    public static void main(String[] args) throws IOException {
        ProxyServer proxyServer = new ProxyServer();
        proxyServer.start();
//        String request = "POST /mcp/pc/pcsearch HTTP/1.1";
//        ProxyRequestHandler requestHandler = new ProxyRequestHandler();
//        HttpParser parser = new HttpParser(requestHandler);
//        parser.parseNext(BufferUtil.toBuffer(request));
//        String responseBody = "HTTP/1.0 405 Method Not Allowed\r\n" +
//                "Allow: GET, HEAD\r\n" +
//                "Date: Mon, 25 Apr 2022 09:42:29 GMT\r\n" +
//                "Content-Type: text/html; charset=UTF-8\r\n" +
//                "Server: gws\r\n" +
//                "Content-Length: 31\r\n" +
//                "X-XSS-Protection: 0\r\n" +
//                "X-Frame-Options: SAMEORIGIN\r\n" +
//                "\r\n" +
//                "<!DOCTYPE html>\r\n" +
//                "<html lang=en>";
//                "Access-Control-Allow-Credentials: true\r\n" +
//                "Access-Control-Allow-Headers: Content-Type\r\n" +
//                "Access-Control-Allow-Methods: POST, GET\r\n" +
//                "Access-Control-Allow-Origin: https://www.baidu.com\r\n" +
//                "Content-Length: 130\r\n" +
//                "Content-Type: application/json\r\n" +
//                "Date: Mon, 25 Apr 2022 08:20:17 GMT\r\n" +
//                "Tracecode: 61678748173017109742454892554042504\r\n";
//                + "{\"errno\":0,\"errmsg\":\"ok\",\"IsPrint\":false,\"data\":{\"log_id\":\"6167874817301710974\",\"action_rule\":{\"pos_1\":[],\"pos_2\":[],\"pos_3\":[]}}}";
//        try(Socket socket = new Socket("www.google.com",80)) {
////            HttpTester.Request request = HttpTester.newRequest();
//            request.setMethod("POST");
//            request.setURI("/search");
//            request.setVersion(HttpVersion.HTTP_1_0);
//            request.put(HttpHeader.HOST, "www.google.com");
//            request.put("Content-Type", "application/x-www-form-urlencoded");
//            request.setContent("q=jetty%20server");
//            ByteBuffer output = request.generate();
//            socket.getOutputStream().write(output.array(), output.arrayOffset() + output.position(), output.remaining());

//        ProxyResponseHandler responseHandler = new ProxyResponseHandler();
//        //将input数据取出来， 转换成byteBuffer类型
//        HttpParser responseParser = new HttpParser(responseHandler);
//        ByteBuffer buffer = BufferUtil.toBuffer(responseBody);
//        responseParser.parseNext(buffer);
//        ByteBuffer byteBuffer = responseHandler.generate();
////            ProxyResponseHandler responseHandler = new ProxyResponseHandler();
////            HttpParser parser = new HttpParser(responseHandler);
////            parser.parseNext(BufferUtil.toBuffer(b));
//        //将数据解析成buffer数据
//        log.info("解析数据完毕:{}", new String(byteBuffer.array(), StandardCharsets.ISO_8859_1));
    }
}
