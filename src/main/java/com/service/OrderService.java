package com.service;

import com.spring.Component;

/**
 * @author linjunzhen
 * @version 1.0
 * @date 2022/4/11 15:12
 * 这段代码定义了一个名为 "OrderService" 的类，并在该类上标注了 @Component 注解，表示该类是一个组件，需要被 Spring IoC 容器管理。
 * 这样，在应用启动时，Spring IoC 容器会自动扫描 @ComPonentScan 注解指定的包路径，
 * 并将所有被 @Component 注解标注的类创建为 Bean 注册到容器中。
 * 除了 @Component 注解，还可以使用 @Service、@Repository 或 @Controller 注解来表示不同类型的组件。
 * 同时，注释掉的 @Scope 注解可以用于指定 Bean 的作用域，本例中注释掉的代码表示 OrderService 类的 Bean 的作用域为多例。
 */
@Component
//@Scope("prototype") //多例
public class OrderService {


}
