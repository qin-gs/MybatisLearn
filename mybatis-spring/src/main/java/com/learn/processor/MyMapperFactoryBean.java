package com.learn.processor;

import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Collections;

/**
 * 使用FactoryBean自己创建代理对象，不通过spring创建，需要将被创建的对象传进来
 * <p>
 * <a href="https://mybatis.org/spring/zh/factorybean.html">文档</a>
 */
@SuppressWarnings("unchecked")
public class MyMapperFactoryBean<T> implements FactoryBean<T> {
    private Class<T> mapperInterface;

    public MyMapperFactoryBean(Class<T> clazz) {
        this.mapperInterface = clazz;
    }

    @Override
    public T getObject() throws Exception {
        // 生成代理对象
        return (T) Proxy.newProxyInstance(
                MyMapperFactoryBean.class.getClassLoader(),
                new Class[]{mapperInterface},
                (proxy, method, args) -> {
                    Select select = method.getAnnotation(Select.class);
                    String[] sqls = select.value();
                    // 执行sql语句
                    System.out.println(Arrays.toString(sqls));
                    return Collections.emptyList();
                }
        );
    }

    @Override
    public Class<T> getObjectType() {
        return mapperInterface;
    }
}
