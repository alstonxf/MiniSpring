package com.service;

import com.spring.ComPonentScan;

/**
 * AppConfig 类是整个应用的配置类，用于配置 Spring IoC 容器中的 Bean。
 * 通过 @ComPonentScan 注解指定了需要扫描的包路径。
 * 该类是整个应用的配置类，用于配置 Spring IoC 容器中的 Bean。
 * 在该类中通过 @ComPonentScan 注解指定了需要扫描的包路径，Spring IoC 容器会自动扫描该路径下所有被 @Component、@Service、@Repository 或 @Controller 注解标注的类，并将其创建为 Bean 注册到容器中。
 * 除了 @ComPonentScan 注解，还可以使用 XML 配置文件、Java 配置类等方式来配置 Spring IoC 容器。
 */
@ComPonentScan("com.service")
public class MyConfig {

}
