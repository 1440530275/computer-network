package com.haolong.chapter2.webServer.controller;

import com.haolong.chapter2.webServer.RequestMapping;

import java.io.*;

/**
 * @author weihaolong
 */
public class LoginController {


    @RequestMapping("/HelloWorld")
    public String index(){
        return "/HelloWorld";
    }

    public String login(){
        return null;
    }

    public static void main(String[] args) {
        String classPath = LoginController.class.getClassLoader().getResource("").getPath();
        File file = new File(classPath + "page/HelloWorld.html");
        if(file.exists()){
            System.out.println("文件存在");
            try {
                byte[] buffer = new byte[1024];
                int len;
                StringBuilder response = new StringBuilder();
                InputStream inputStream = new FileInputStream(file);
                while((len = inputStream.read(buffer)) != -1){
                    response.append(new String(buffer, 0, len));
                    if(len < len){
                        //读取完毕
                        break;
                    }
                }
                System.out.println(response);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("文件不存在");
        }
    }
}
