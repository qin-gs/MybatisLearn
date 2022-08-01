package com.mybatis.learn.type;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * java.reflect.Type
 */
public class TypeTest {

    public static void main(String[] args) {
        classTest();
        parameterizedTypeTest();
    }

    public static void classTest() {
        String[] str1 = new String[0];
        String[] str2 = new String[2];

        // 元素类型相同 且 维数相同的数组共享一个Class对象
        System.out.println(str1.getClass());
        System.out.println(str2.getClass());
        System.out.println(str1.getClass().equals(str2.getClass()));

        List<String>[] list1 = new ArrayList[2];
        List<Integer>[] list2 = new ArrayList[4];
        System.out.println(list1.getClass().equals(list2.getClass()));
    }

    public static void parameterizedTypeTest() {
        List<String> list = new ArrayList<>();
        // System.out.println(((ParameterizedType) list.getClass()).getRawType());
        System.out.println(list instanceof ParameterizedType);
    }
}
