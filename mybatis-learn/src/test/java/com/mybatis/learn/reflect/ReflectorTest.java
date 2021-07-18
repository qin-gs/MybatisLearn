package com.mybatis.learn.reflect;

import com.mybatis.learn.bean.Blog;
import org.apache.ibatis.reflection.Reflector;

import java.util.Arrays;

public class ReflectorTest {
    public static void main(String[] args) {
        Reflector reflector = new Reflector(Blog.class);
        System.out.println(reflector.getDefaultConstructor());
        System.out.println(Arrays.toString(reflector.getGetablePropertyNames()));
        System.out.println(Arrays.toString(reflector.getSetablePropertyNames()));
    }
}
