package com.haolong.chapter2.webServer;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

/**
 * @author weihaolong
 * @date 2020-02-04 17:54:36
 * @description 手写一个简单的Web服务器
 */
public class WebServer {

    private Map<String, Object> diMap = new HashMap<>(16);

    private Map<String, Method> handlerMap = new HashMap<>(16);

    /**
     * 初始化资源， 模拟扫描， 首先扫描包下面带 @RequestMapper的注解， 注入到映射map中
     */
    public void init() throws IllegalAccessException, InstantiationException {
        //扫描类路径下的class文件

        //扫描@RequestMapping的方法, 直接扫描 com.haolong.chapter2.webServer下面的包
        Set<Class<?>> classSet = new LinkedHashSet<>();
        File file = new File(Resource.CLASS_PATH + "com/haolong/chapter2/webServer/controller");
        if(!file.exists() || !file.isDirectory()){
            throw new RuntimeException("该文件不存在或者该文件不是文件夹");
        }
        //class列表
        File[] dirfiles = file.listFiles(new FileFilter() {
            // 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
            public boolean accept(File file) {
                return file.getName().endsWith(".class");
            }
        });
        assert dirfiles != null;
        for(File f : dirfiles){
            try {
                classSet.add(Class.forName("com.haolong.chapter2.webServer.controller." + f.getName().substring(0, f.getName().length() - 6)));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        //扫描每个类下面的特殊注解
        for(Class<?> c : classSet){
            for(Method m : c.getMethods()){
                diMap.put(m.getName(), c.newInstance());
                RequestMapping requestMapping = m.getAnnotation(RequestMapping.class);
                if(requestMapping != null){
                    handlerMap.put(requestMapping.value(), m);
                }
            }
        }
    }

    /**
     * 开启服务器端口
     *
     * @param port
     */
    public void start(int port) {
        //初始化资源
        try {
            init();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        try {
            ServerSocket serverSocket = new ServerSocket(port);
                while (true) {
                    Socket socket = serverSocket.accept();
                    //读取tcp里面的数据并且进行校验是否是http请求
                    HttpServletRequest httpServletRequest = new HttpServletRequest(socket.getInputStream());
                    HttpServletResponse httpServletResponse = new HttpServletResponse(socket.getOutputStream());

                    //处理请求
                    service(httpServletRequest, httpServletResponse);
                    socket.close();
                }
            } catch(IOException e){
                e.printStackTrace();
            }
    }

    /**
     * 处理请求
     * @param request
     * @param response
     */
    public void service(HttpServletRequest request, HttpServletResponse response){
        //直接在里面进行处理, 也可以进行扩容进行补充
        Method method = handlerMap.get(request.getRequestUri());

        if(method == null){
            response.page("404");
            return;
        }

        try {
            Object object = method.invoke(diMap.get(method.getName()), null);
            if(object instanceof String){
                System.out.println("请求的页面路径:" + object);
                response.page(object + "");
            }else{
                //直接就是未找到文件处理404 not found;
                response.page("404");
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws ClassNotFoundException {
        WebServer webServer = new WebServer();
        webServer.start(8080);
    }
}
