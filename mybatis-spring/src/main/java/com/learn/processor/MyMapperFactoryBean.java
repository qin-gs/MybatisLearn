package com.learn.processor;

import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Collections;

/**
 * 使用FactoryBean自己创建代理对象，不通过spring创建，需要将被创建的对象传进来
 */
public class MyMapperFactoryBean<T> implements FactoryBean<T> {
    private Class<T> clazz;

    public MyMapperFactoryBean(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T getObject() throws Exception {
        // 生成代理对象
        T t = (T) Proxy.newProxyInstance(
                MyMapperFactoryBean.class.getClassLoader(),
                new Class[]{clazz},
                (proxy, method, args) -> {
                    Select select = method.getAnnotation(Select.class);
                    String[] sqls = select.value();
                    // 执行sql语句
                    System.out.println(Arrays.toString(sqls));
                    return Collections.emptyList();
                }
        );
        return t;
    }

    @Override
    public Class<T> getObjectType() {
        return clazz;
    }
}
