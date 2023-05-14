package com.service;

import com.spring.BeanPostProcessor; // 导入BeanPostProcessor接口
import com.spring.Component; // 导入Component注解

import java.lang.reflect.InvocationHandler; // 导入InvocationHandler接口
import java.lang.reflect.Method; // 导入Method类
import java.lang.reflect.Proxy; // 导入Proxy类

/**
 * My的Bean后置处理器
 * 以上代码是一个简单的实现Bean后置处理器的示例。在Spring容器中，当Bean实例化完成之后，可以通过Bean后置处理器来对Bean进行一些处理，从而实现一些AOP的功能。
 *
 * 具体来说，该代码实现了一个名为MyBeanPostProcessor的Bean后置处理器，其作用是对名为userService的Bean进行增强，实现AOP切面的功能。
 * 具体实现方式是，对于userService这个Bean，在初始化后，创建一个代理对象，代理对象的方法逻辑中会先执行一些切面逻辑，然后再执行原对象的方法。
 * 通过这样的方式，就可以在不修改原对象代码的情况下，实现一些额外的功能。
 */
@Component // 声明为Spring组件
public class MyBeanPostProcessor implements BeanPostProcessor { // 实现BeanPostProcessor接口

    @Override
    public Object postProcessBeforeInitialization(String beanName, Object bean) { // 在初始化前进行处理
        if ("userService".equals(beanName)) { // 如果是userService
            System.out.println("1111postProcessBeforeInitialization"); // 输出信息
        }
        return bean; // 返回bean对象
    }

    @Override
    public Object postProcessAfterInitialization(String beanName, Object bean) { // 在初始化后进行处理
        if ("userService".equals(beanName)) { // 如果是userService
            Object proxyInstance = Proxy.newProxyInstance( // 创建代理对象
                    MyBeanPostProcessor.class.getClassLoader(), // 类加载器
                    bean.getClass().getInterfaces(), // 要实现的接口
                    new InvocationHandler() { // 代理对象的方法逻辑
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            System.out.println("切面逻辑"); // 切面逻辑
                            return method.invoke(bean, args); // 执行原对象方法
                        }
                    });
            return proxyInstance; // 返回代理对象
        }
        return bean; // 返回bean对象
    }
}
