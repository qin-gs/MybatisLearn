package com.learn.dao;

import com.learn.bean.User;
import com.learn.config.Config;
import org.apache.ibatis.annotations.Select;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * mybatis + spring 的版本依赖
 * <pre>
 * MyBatis-Spring 	MyBatis 	Spring Framework 	Spring Batch 	Java
 * 2.0 	            3.5+ 	    5.0+ 	            4.0+ 	        Java 8+
 * 1.3 	            3.4+ 	    3.2.2+ 	            2.1+ 	        Java 6+
 * </pre>
 */
class UserDaoTest {

    @Test
    void getUsers() {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        // 通过动态代理为mapper创建了一个代理对象
        UserDao dao = context.getBean(UserDao.class);
        List<User> users = dao.getUsers();
        users.forEach(System.out::println);
    }

    @Test
    void mybatisTest() {
        // 生成代理对象
        UserDao dao = (UserDao) Proxy.newProxyInstance(
                UserDaoTest.class.getClassLoader(),
                new Class[]{UserDao.class},
                (proxy, method, args) -> {
                    Select select = method.getAnnotation(Select.class);
                    String[] sqls = select.value();
                    // 执行sql语句
                    System.out.println(Arrays.toString(sqls));
                    return List.of("sql执行结果");
                }
        );
        // 将创建的对象交给spring
        // 1. 用BeanFactoryPostProcessor从BeanFactory中获取修改，但是不能添加
        // 2. 使用ImportBeanDefinitionRegistrar添加


        System.out.println(dao.getUsers());
    }

    void test() {
        // 扫描包，为对象创建BeanDefinition
        GenericBeanDefinition bd = new GenericBeanDefinition();
        bd.setBeanClassName("userDao");
        bd.setBeanClass(UserDao.class);
        bd.setScope(BeanDefinition.SCOPE_SINGLETON);
        bd.setLazyInit(false);
        // 将bd放入BeanDefinitionMap中

    }

}