package com.service;

import com.spring.Autowired; // 导入Autowired注解
import com.spring.BeanNameAware; // 导入BeanNameAware接口
import com.spring.Component; // 导入Component注解
import com.spring.InitializingBean; // 导入InitializingBean接口

/**
 * 用户服务类
 */
@Component // 声明为Spring组件
//@Scope("prototype") //多例
public class UserService implements BeanNameAware, InitializingBean, UserInterface { // 实现BeanNameAware接口、InitializingBean接口、UserInterface接口

    @Autowired // 自动注入OrderService实例
    private OrderService orderService;

    private String beanName; // 保存Bean的名称

    @Override
    public void test() { // 实现UserInterface接口中的test方法
        System.out.println(orderService); // 输出OrderService实例
    }

    @Override
    public void setBeanName(String beanName) { // 实现BeanNameAware接口中的setBeanName方法
        this.beanName = beanName; // 保存Bean的名称
    }

    @Override
    public void afterPropertiesSet() { // 实现InitializingBean接口中的afterPropertiesSet方法
        System.out.println("初始化方法"); // 输出初始化信息
    }
}
