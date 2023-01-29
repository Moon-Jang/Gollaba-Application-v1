package kr.mj.gollaba.common.util;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class IterableUtils {

    public static <T> Stream<T> stream(Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false);
    }


    public static int size(Iterable<?> iterable) {
        int counter = 0;
        for (Object o : iterable) {
            counter++;
        }
        return counter;
    }

    public static boolean isEmpty(Iterable<?> iterable) {
        return size(iterable) == 0 ? true : false;
    }
}
