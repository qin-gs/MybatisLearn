package com.learn.config;

import com.learn.bean.User;
import com.learn.dao.UserDao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

@DisplayName("模拟mybatis 整合 spring")
public class MyConfigTest {

    @Test
    void test() {
        ApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);
        UserDao dao = (UserDao) context.getBean("userDao");
        List<User> users = dao.getUsers();
        users.forEach(System.out::println);
    }
}
