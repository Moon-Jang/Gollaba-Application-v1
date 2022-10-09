package kr.mj.gollaba.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonStringifyUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String stringify(Object o) {
        String result;

        try {
            result = objectMapper.writeValueAsString(o);
        }catch (JsonProcessingException e) {
            result = null;
        }

        return result;
    }
}
