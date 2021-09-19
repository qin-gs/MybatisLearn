package com.learn.processor;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 注册Mapper对象
 */
public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    /**
     * 扫描需要被注册进去的对象，依次创建并注册进去
     * 这个方法只注册了一个
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder
                .genericBeanDefinition(MyMapperFactoryBean.class)
                .getBeanDefinition();
        // 拿到构造函数的参数，将需要创建代理对象的类从这里传进去
        beanDefinition.getConstructorArgumentValues().addGenericArgumentValue("com.learn.dao.UserDao");
        registry.registerBeanDefinition(
                "userDao",
                beanDefinition
        );
    }
}
