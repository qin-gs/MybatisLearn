package com.mybatis.learn.meta;

import com.mybatis.learn.util.ObjectUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.reflection.wrapper.BeanWrapper;
import org.apache.ibatis.session.Configuration;
import org.junit.jupiter.api.Test;

import java.util.List;

public class MetaObjectTest {
    public static void main(String[] args) {
        User user = new User();
        user.setId("111");
        user.setName("222");
        user.setAge(0);
        user.setGender("333");
        user.setPhoneNum("444");

        Configuration config = new Configuration();
        MetaObject metaObject = config.newMetaObject(user);
        metaObject.setValue("id", "222");
        // metaObject.setValue("comments", new Comment()); // 设置对象，需要手动new对象放进去
        // metaObject.setValue("book.name", "book"); // 设置嵌套属性
        // metaObject.setValue("comments[0]", "a comment"); // 设置list
        // metaObject.setValue("comments[first]", "a comment"); // 设置map

        System.out.println(metaObject.getValue("gender"));
        System.out.println(user);

    }

    @Test
    public void test() {
        Blog blog = new Blog();
        Comment comment = ObjectUtil.getObject(Comment.class);
        comment.setUser(ObjectUtil.getObject(User.class));
        blog.setComments(List.of(comment));


        Configuration config = new Configuration();
        MetaObject metaObject = config.newMetaObject(blog);
        Object value = metaObject.getValue("comments[0].user.id");
        System.out.println(value);

        BeanWrapper beanWrapper = new BeanWrapper(metaObject, blog);
        beanWrapper.get(new PropertyTokenizer("comment"));
        beanWrapper.get(new PropertyTokenizer("comment[0]"));
    }
}
