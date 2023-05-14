package com.spring;

/**
 * BeanDefinition 类表示一个 Bean 的定义，包含 Bean 的类型和作用域。
 * 这个类表示一个 Bean 的定义，包含 Bean 的类型和作用域。
 * 其中，类型用 Class 类型表示，作用域用字符串表示。类中包含了两个 getter 和 setter 方法，用于获取和设置 Bean 的类型和作用域。
 */
public class BeanDefinition {

    // Bean 的类型
    private Class type;

    // Bean 的作用域
    private String scope;

    /**
     * 获取 Bean 的类型。
     *
     * @return Bean 的类型
     */
    public Class getType() {
        return type;
    }

    /**
     * 设置 Bean 的类型。
     *
     * @param type Bean 的类型
     */
    public void setType(Class type) {
        this.type = type;
    }

    /**
     * 获取 Bean 的作用域。
     *
     * @return Bean 的作用域
     */
    public String getScope() {
        return scope;
    }

    /**
     * 设置 Bean 的作用域。
     *
     * @param scope Bean 的作用域
     */
    public void setScope(String scope) {
        this.scope = scope;
    }
}
