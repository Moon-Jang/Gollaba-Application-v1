package kr.mj.gollaba.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestUtils {

    @Getter
    @Setter
    public static class Parent {

        private Long srl;

        private String name;

        private String licence;

        private List<Child> children;

    }

    @Getter
    @Setter
    public static class Child {

        private Long srl;

        private String a;

        private String b;

    }

    public static void main(String[] args) {
        Parent parent1 = generate();
        Parent parent2 = generate();
        parent2.setLicence("5678");
        Child child = new Child();
        child.setSrl(11L);
        child.setA("hello");
        child.setB("world!");
        parent2.getChildren().add(child);
        //parent2.getChildren().get(0).setA("test");
        compare(parent1, parent2);
    }

    public static Parent generate() {
        Parent parent = new Parent();
        parent.setSrl(1L);
        parent.setLicence("1234");
        parent.setName("A");
        Child child = new Child();
        child.setSrl(11L);
        child.setA("hello");
        child.setB("world!");
        List<Child> list = new ArrayList<>();
        list.add(child);
        parent.setChildren(list);

        return parent;
    }

    public static void compare(Object a, Object b) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> aMap = objectMapper.convertValue(a, Map.class);
        Map<String, Object> bMap = objectMapper.convertValue(b, Map.class);
//        MapDifference<String, Object> diff = Maps.difference(aMap, bMap);
//        diff.entriesDiffering()
//                .entrySet()
//                .stream()
//                .collect(Collectors.toMap());
//
//        Map<String, Object> d = new LinkedHashMap<>();
//
//        System.out.println("11");
    }
}
