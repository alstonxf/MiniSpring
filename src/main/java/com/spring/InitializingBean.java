package com.spring;

/**
 * InitializingBean 接口用于在 Bean 实例化后，设置 Bean 属性前执行一些初始化操作。
 * 这个接口用于在 Bean 实例化后，设置 Bean 属性前执行一些初始化操作。
 * 实现该接口的类可以在 afterPropertiesSet() 方法中实现初始化逻辑，
 * 例如建立数据库连接、读取配置文件等操作。在 Spring IoC 容器创建 Bean 的过程中，
 * 如果发现某个 Bean 实现了 InitializingBean 接口，则在该 Bean 实例化完成后，会自动调用其 afterPropertiesSet() 方法进行初始化。
 */
public interface InitializingBean {

    /**
     * 在 Bean 实例化后，设置 Bean 属性前执行一些初始化操作。
     */
    public void afterPropertiesSet();

}
