package com.service;

import com.spring.MyApplicationContext;

/**
 * 程序入口类，用于启动 Spring 容器和获取 Bean 对象
 *
 * TestMain 类的作用是作为 Spring 应用的入口程序，其主要功能有：
 *
 * 创建一个 ApplicationContext 对象，作为 Spring IoC 容器的实现类；
 * 通过 ApplicationContext 对象获取名为 "userService" 的 Bean 实例；
 * 调用 userService 的 test() 方法。
 * 在具体实现过程中，TestMain 类通过创建一个 MyApplicationContext 对象，将 AppConfig.class 传入其中，来启动 Spring 容器。然后，它通过调用 ApplicationContext 对象的 getBean() 方法获取名为 "userService" 的 Bean 实例，并将其强制转换为 UserInterface 接口类型。最后，它调用 userService 的 test() 方法，输出相关信息。
 *
 * 总的来说，TestMain 类是整个 Spring 应用的入口程序，通过它可以启动 Spring 容器，获取 Bean 实例，并进行相应的操作。
 */
public class TestMain {

    public static void main(String[] args) {

        // 创建 ApplicationContext 实例，并传入 AppConfig 类
        MyApplicationContext applicationContext = new MyApplicationContext(MyConfig.class);

        // 通过 ApplicationContext 实例获取名为 "userService" 的 Bean，其类型是 UserInterface 接口
        UserInterface userService = (UserInterface) applicationContext.getBean("userService");

        // 调用获取的 Bean 的 test() 方法
        userService.test();

        System.out.println(userService.getEmail()); //调用的userService对象的email
        System.out.println(userService.email);//调用的是接口的email属性
    }
}
