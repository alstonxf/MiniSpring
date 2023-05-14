package com.spring;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Component 注解用于标注一个类为组件类，被 Spring IoC 容器扫描并管理。
 * 这个注解用于标注一个类为组件类，被 Spring IoC 容器扫描并管理。在注解中可以指定组件名称，默认为空字符串，即使用类名首字母小写形式作为组件名称。
 * 被标注的类会被扫描并注册到 Spring IoC 容器中，可以通过名称或类型获取到该组件的实例。
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Component {

    /**
     * 组件名称，默认为空字符串，即使用类名首字母小写形式作为组件名称。
     *
     * @return 组件名称
     */
    String value() default "";

}
