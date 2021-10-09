package com.mybatis.learn.util;

import java.util.Objects;

/**
 * ognl表达式的工具类示例
 */
public class OgnlUtil {

    public static boolean isEmpty(Object o) {
        return BeanUtils.isEmpty(o);
    }

    public static boolean isNotEmpty(Object o) {
        return !BeanUtils.isEmpty(o);
    }
}
