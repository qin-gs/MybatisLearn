package com.mybatis.learn.param;

import com.mybatis.learn.mapper.BlogMapper;
import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ParamTest {
    @Test
    public void singleTest() {

    }

    public static void main(String[] args) {
        BlogMapper mapper;
        Method[] methods = BlogMapper.class.getMethods();
        for (Method method : methods) {
            final Class<?>[] paramTypes = method.getParameterTypes();
            final Annotation[][] paramAnnotations = method.getParameterAnnotations();
            System.out.println("method.getName() = " + method.getName());
            System.out.println("paramTypes = " + Arrays.toString(paramTypes));
            System.out.println("paramAnnotations = " + Arrays.deepToString(paramAnnotations));
        }

    }
}
