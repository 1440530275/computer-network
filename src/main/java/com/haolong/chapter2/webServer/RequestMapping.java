package com.haolong.chapter2.webServer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author weihaolong
 * @date 2020-02-07 14:43
 * @description
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {

    /**
     * 路径uri
     * @return
     */
    String value() default "";
}
