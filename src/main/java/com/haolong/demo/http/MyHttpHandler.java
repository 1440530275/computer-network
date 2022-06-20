package com.haolong.demo.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@Log4j2
public class MyHttpHandler implements HttpHandler {
//    static Logger logger = LogManager.getLogger(MyHttpHandler.class);

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        //进行处理

    }

    public static void main(String[] args) {
        log.info("你好");
    }

}
