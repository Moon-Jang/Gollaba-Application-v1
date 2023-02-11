package kr.mj.gollaba.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class QueryStringGenerator {

    public static String generate(Object o) {
        if (o == null) return "";

        StringBuilder sb = new StringBuilder();
        sb.append("?");

        try {
            for (Field f : o.getClass().getDeclaredFields()) {
                Object value = getFieldValue(o, f);

                if (value == null) continue;

                if (value instanceof List) {
                    sb.append(f.getName() + "=");
                    List<Object> list = (List) value;

                    for (int i = 0; i < list.size(); i++) {
                        if (i == list.size() - 1) {
                            sb.append(String.valueOf(list.get(i)) + "&");
                            continue;
                        }

                        sb.append(String.valueOf(list.get(i)) + ",");
                    }

                    continue;
                }

                sb.append(f.getName() + "=" + String.valueOf(value) + "&");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String result = sb.toString();
        return result.substring(0, result.length() - 1);
    }

    private static Object getFieldValue(Object o, Field f) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String fieldName = f.getName();
        Class clazz = f.getDeclaringClass();
        String getterMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        Method getterMethod = clazz.getDeclaredMethod(getterMethodName);
        return getterMethod.invoke(o);
    }

}
