package com.spring;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Scope 注解用于指定 Bean 的作用域。
 * 这个注解用于指定 Bean 的作用域，被标注的类会被创建为 Spring Bean 并注册到 Spring IoC 容器中，
 * 可以通过名称或类型获取到该 Bean 的实例。该注解中可以指定 Bean 的作用域，默认为空字符串，
 * 表示 Bean 的作用域为单例模式（即默认为 Scope.SINGLETON）。Spring 支持的作用域有单例模式（Singleton）、
 * 原型模式（Prototype）、会话作用域（Session）、请求作用域（Request）等。
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Scope {

    /**
     * 指定 Bean 的作用域。
     *
     * @return Bean 的作用域
     */
    String value() default "";

}
