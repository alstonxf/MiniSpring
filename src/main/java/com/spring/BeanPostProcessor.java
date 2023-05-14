package com.spring;

/**
 * BeanPostProcessor 接口定义了两个方法，用于在 Bean 的初始化前后进行处理。
 * 这个接口定义了两个方法，用于在 Bean 的初始化前后进行处理。在 Bean 初始化前可以进行一些预处理操作，如属性注入、属性转换等；
 * 在 Bean 初始化后可以进行一些后处理操作，如代理、AOP 等。实现该接口的类可以在这两个方法中进行相应的处理。
 *
 */
public interface BeanPostProcessor {

    /**
     * 在 Bean 的初始化前进行处理。
     *
     * @param beanName Bean 的名称
     * @param bean Bean 实例
     * @return 处理后的 Bean 实例
     */
    public Object postProcessBeforeInitialization(String beanName, Object bean);

    /**
     * 在 Bean 的初始化后进行处理。
     *
     * @param beanName Bean 的名称
     * @param bean Bean 实例
     * @return 处理后的 Bean 实例
     */
    public Object postProcessAfterInitialization(String beanName, Object bean);

}
