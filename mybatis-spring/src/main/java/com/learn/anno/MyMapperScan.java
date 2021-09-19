package com.learn.anno;

import com.learn.processor.MyImportBeanDefinitionRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Import(MyImportBeanDefinitionRegistrar.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyMapperScan {
}
