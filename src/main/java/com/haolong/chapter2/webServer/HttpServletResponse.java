package com.haolong.chapter2.webServer;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author weihaolong
 * @date 2020-02-05 17:59
 * @description response响应
 */
class HttpServletResponse {
    // 响应头
    private Map<String, String> headers = new HashMap<>(16);

    private HttpStatus httpStatus;

    private OutputStream outputStream;

    public HttpServletResponse() {

    }

    public HttpServletResponse(OutputStream outputStream) {
        this.outputStream = outputStream;
        headers.put("Content-Type", "text/html;charset=utf-8");
        headers.put("Connection", "keep-alive");
    }

    public void page(String uri) {
        File file = new File(Resource.CLASS_PATH + "page" + uri + ".html");
        String firstLine = "HTTP/1.1 " + HttpStatus.OK.status() + " " + HttpStatus.OK.getReasonPhrase() + "\r\n";

        if (!file.exists() || uri.equals("404")) {
            file = new File(Resource.CLASS_PATH + "page" + "/404.html");
            firstLine = "HTTP/1.1 " + HttpStatus.NOT_FOUND.status() + " " + HttpStatus.NOT_FOUND.getReasonPhrase() + "\r\n";
        }

        try {
            //写入header部分
            String head = firstLine + builderHeader() + "\r\n";
            outputStream.write(head.getBytes());

            //这是写入body部分
            byte[] buffer = new byte[1024];
            int len;
            int contentLength = 0;
            InputStream inputStream = new FileInputStream(file);
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
                contentLength += len;
                if (len < buffer.length) {
                    //读取完毕
                    headers.put("Content-length", contentLength + "");
                    break;
                }
            }
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将头部组装起来
     *
     * @return
     */
    private String builderHeader() {
        StringBuilder stringBuilder = new StringBuilder();
        headers.forEach((k, v) -> {
            stringBuilder.append(k + ": " + v + "\r\n");
        });
        return stringBuilder.toString();
    }
}
