package com.spring;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Autowired 注解表示该字段应该由 Spring IoC 容器自动注入。
 *
 * 这是一个自定义注解，通常与 Spring IoC 容器一起使用，用于指示该字段应该与容器中的 bean 自动装配。
 * 这个注解可以用来指示 Spring IoC 容器自动将一个 bean 注入到带有该注解的字段中。它是一个自定义注解，通常与 Spring IoC 容器一起使用。
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Autowired {

}
