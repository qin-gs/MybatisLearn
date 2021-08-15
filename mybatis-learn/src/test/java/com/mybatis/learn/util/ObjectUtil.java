package com.mybatis.learn.util;

import com.mybatis.learn.bean.User;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.RandomStringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.*;

/**
 * 用随机值初始化简单bean对象中的属性
 */
public class ObjectUtil {

    /**
     * 默认生成的字符串长度
     */
    private static final int STRING_LEN = 10;

    /**
     * 创建一个初始化完成的对象
     */
    public static <T> T getObject(Class<T> clazz) {
        try {
            T t = clazz.newInstance();
            Field[] fields = clazz.getDeclaredFields();
            Arrays.stream(fields).forEach(x -> {
                try {
                    BeanUtils.setProperty(t, x.getName(), getRandomValue(x));
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            });
            return t;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 还差byte, short
     * TODO List, Map 需要根据泛型创建对象，目前还没处理
     */
    public static <T> T getRandomValue(Field field) {
        Class<?> clazz = field.getType();
        Random random = new Random();
        if (Objects.equals(clazz, String.class)) {
            return (T) RandomStringUtils.randomAlphabetic(STRING_LEN);
        } else if (Objects.equals(clazz, Date.class)) {
            return (T) new Date();
        } else if (Objects.equals(clazz, Boolean.class) || Objects.equals(clazz, boolean.class)) {
            return (T) (((Boolean) (random.nextFloat() > 0.5)));
        } else if (Objects.equals(clazz, Integer.class) || Objects.equals(clazz, int.class)) {
            return (T) ((Integer) random.nextInt());
        } else if (Objects.equals(clazz, Double.class) || Objects.equals(clazz, double.class)) {
            return (T) ((Double) random.nextDouble());
        } else if (Objects.equals(clazz, Long.class) || Objects.equals(clazz, long.class)) {
            return (T) ((Long) random.nextLong());
        } else if (List.class.isAssignableFrom(clazz)) {
            ParameterizedType type = (ParameterizedType) field.getGenericType();
            return (T) new ArrayList<>();
        } else if (Map.class.isAssignableFrom(clazz)) {
            return (T) new HashMap<>();
        } else {
            System.err.println("类型不匹配 " + field.getName() + " " + field.getType());
        }
        return null;
    }

}
