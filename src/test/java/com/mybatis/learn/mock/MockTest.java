package com.mybatis.learn.mock;

import com.mybatis.learn.bean.Blog;
import org.junit.Test;
import org.mockito.Mockito;

public class MockTest {

    @Test
    public void test() {
        Blog blog = Mockito.mock(Blog.class);
        blog.setId("123");

        // 验证某些行为 mock创建对象后，会记录所有的交互
        Mockito.verify(blog).setId("123");
    }

    @Test
    public void stubTest() {
        Blog blog = Mockito.mock(Blog.class);
        Mockito.when(blog.getId()).thenReturn("12");
        Mockito.when(blog.getTitle()).thenThrow(new NoSuchMethodError());

        System.out.println(blog.getId());
        System.out.println(blog.getTitle());
    }
}
