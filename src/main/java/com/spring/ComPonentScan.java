package com.spring;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ComPonentScan 注解用于指定要扫描的组件所在的包路径。
 * 这个注解用于指定要扫描的组件所在的包路径，被标注的类会被扫描并注册到 Spring IoC 容器中，可以通过名称或类型获取到该组件的实例。
 * 该注解中可以指定要扫描的组件所在的包路径，默认为空字符串，即扫描当前包及其子包下的所有组件。
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ComPonentScan {

    /**
     * 要扫描的组件所在的包路径。
     *
     * @return 组件所在的包路径
     */
    String value() default "";

}
