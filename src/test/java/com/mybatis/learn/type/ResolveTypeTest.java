package com.mybatis.learn.type;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResolveTypeTest {

    public static void main(String[] args) throws NoSuchFieldException {
        List<String> list = new ArrayList<>();
        System.out.println(list instanceof ParameterizedType);
        System.out.println(list instanceof TypeVariable);

        Foo<String, String> foo = new Foo<>();
        System.out.println(foo instanceof ParameterizedType);
        System.out.println(foo instanceof TypeVariable);
    }
}

class Foo<K, V> {
    Map<K, V> map;
}

