package com.mybatis.learn.util;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

public class BeanUtils {

    public static boolean isEmpty(Object o) {
        if (Objects.isNull(o)) {
            return true;
        }
        if (o instanceof Collection) {
            return ((Collection<?>) o).isEmpty();
        } else if (o instanceof String) {
            return StringUtils.isEmpty(((String) o));
        } else if (o.getClass().isArray()) {
            return ArrayUtils.isEmpty(((Object[]) o));
        } else if (o instanceof Map) {
            return MapUtils.isEmpty(((Map<?, ?>) o));
        }
        // ... 其他类型判断
        return false;
    }

    public static boolean isNumber(Object o) {
        if (Objects.isNull(o)) {
            return false;
        }
        if (o instanceof Number) {
            return true;
        }
        if (o instanceof String) {
            try {
                Double.parseDouble(((String) o));
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }
}
