package com.mybatis.learn.param;

import org.apache.ibatis.reflection.TypeParameterResolver;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

public class TypeParameterResolverTest {
    SubClassA<Long> sa = new SubClassA<>();

    public static void main(String[] args) throws NoSuchFieldException {
        Field f = ClassA.class.getDeclaredField("map");
        System.out.println("f.getGenericType() = " + f.getGenericType());
        System.out.println(f.getGenericType() instanceof ParameterizedType);

        // Type type = TypeParameterResolver.resolveFieldType(f,
        //         ParameterizedTypeImpl.make(SubClassA.class, new Type[]{Long.class}, TypeParameterResolverTest.class));
        Type type = TypeParameterResolver.resolveFieldType(f, TypeParameterResolverTest.class.getDeclaredField("sa").getGenericType());
        System.out.println("type.getClass() = " + type.getClass());

        ParameterizedType p = (ParameterizedType) type;
        System.out.println("p.getRawType() = " + p.getRawType());
        System.out.println("p.getOwnerType() = " + p.getOwnerType());

        for (Type t : p.getActualTypeArguments()) {
            System.out.println(t);
        }
    }
}

class ClassA<K, V> {
    protected Map<K, V> map;

    public Map<K, V> getMap() {
        return map;
    }

    public void setMap(Map<K, V> map) {
        this.map = map;
    }
}

class SubClassA<T> extends ClassA<T, T> {

}