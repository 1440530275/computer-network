package com.haolong.util;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class IOUtils {

    private IOUtils() {
    }

    public static byte[] read(InputStream inputStream) {
        byte[] bytes = new byte[1024];
        int len = 0;
        StringBuilder sb = new StringBuilder();
        try {
            while (true) {
                if ((len = inputStream.read(bytes)) == -1) break;
                //注意指定编码格式，发送方和接收方一定要统一，建议使用UTF-8
                sb.append(new String(bytes, 0, len, StandardCharsets.UTF_8));
                if (len < bytes.length) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }

    public static void write(byte[] bytes, OutputStream outputStream){
        try {
            outputStream.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Integer a = -10;
        System.out.println(Integer.toBinaryString(a));
    }
}
