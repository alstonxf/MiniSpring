package com.spring;

/**
 * BeanNameAware 接口用于设置 Bean 的名称。
 * 这个接口用于设置 Bean 的名称，通常在 Bean 被创建时由 Spring IoC 容器调用。
 * 实现该接口的 Bean 可以在 setBeanName() 方法中获取 Bean 的名称，并进行相应的处理。
 */
public interface BeanNameAware {

    /**
     * 设置 Bean 的名称。
     *
     * @param beanName Bean 的名称
     */
    public void setBeanName(String beanName);
}
