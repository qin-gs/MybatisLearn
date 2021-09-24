package com.mybatis.learn.bean;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.reflection.wrapper.BeanWrapper;
import org.apache.ibatis.session.Configuration;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class MetaObjectTest {

    @Test
    public void test() {
        // 使用MetaObject操作对象属性
        // 操作子属性
        // 自动创建属性对象
        // 自动插座属性名，支持下划线转驼峰
        // 基于索引访问数组
        Object user = new Blog();
        Configuration configuration = new Configuration();
        MetaObject metaObject = configuration.newMetaObject(user);
        metaObject.setValue("title", "qqq");
        metaObject.setValue("author.name", "Name");
        System.out.println(metaObject.getValue("title"));
        System.out.println(metaObject.getValue("author.name"));

        System.out.println(metaObject.findProperty("author.phone_num", true));

    }

    @Test
    public void testMetaObject() {
        Blog blog = new Blog();
        Comment comment1 = new Comment();
        User user1 = new User();
        user1.setName("用户名1");
        comment1.setUser(user1);

        Comment comment2 = new Comment();
        User user2 = new User();
        user2.setName("用户名2");
        comment2.setUser(user2);

        List<Comment> list = new ArrayList<>();
        list.add(comment1);
        list.add(comment2);

        blog.setComments(list);

        Configuration configuration = new Configuration();
        MetaObject metaObject = configuration.newMetaObject(blog);
        System.out.println(metaObject.getValue("comments[0].user.name"));

        BeanWrapper wrapper = new BeanWrapper(metaObject, blog);
        System.out.println(wrapper.get(new PropertyTokenizer("comments[0]")));
        System.out.println(wrapper.get(new PropertyTokenizer("comments")));
    }
}
