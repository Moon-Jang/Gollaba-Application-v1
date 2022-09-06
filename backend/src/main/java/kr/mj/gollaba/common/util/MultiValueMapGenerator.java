package kr.mj.gollaba.common.util;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class MultiValueMapGenerator {

    public static MultiValueMap<String, String> generate(Object o) {
        if (o == null) return null;

        MultiValueMap<String, String> result = new LinkedMultiValueMap<>();

        try {
            for (Field f : o.getClass().getDeclaredFields()) {
                Object value = getFieldValue(o, f);
                String key = f.getName();

                if (value == null) continue;

                if (value instanceof List) {
                    ((List) value).forEach(el -> result.add(key, String.valueOf(el)));
                    continue;
                }

                result.add(key, String.valueOf(value));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private static Object getFieldValue(Object o, Field f) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String fieldName = f.getName();
        Class clazz = f.getDeclaringClass();
        String getterMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        Method getterMethod = clazz.getDeclaredMethod(getterMethodName);
        return getterMethod.invoke(o);
    }

}
