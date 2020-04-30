package com.haolong.chapter2.webServer;

import com.haolong.chapter2.webServer.controller.LoginController;

/**
 * @author weihaolong
 * @date 2020-02-07 15:40
 * @description 资源
 */
public class Resource {
    /**
     * 类路径
     */
    public static final String CLASS_PATH = LoginController.class.getClassLoader().getResource("").getPath();

}
