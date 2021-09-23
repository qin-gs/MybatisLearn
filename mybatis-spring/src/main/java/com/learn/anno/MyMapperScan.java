package com.learn.anno;

import com.learn.processor.MyImportBeanDefinitionRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 模拟mybatis扫描所有的@Mapper注册进去交给spring管理
 */
@Import(MyImportBeanDefinitionRegistrar.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyMapperScan {
}
