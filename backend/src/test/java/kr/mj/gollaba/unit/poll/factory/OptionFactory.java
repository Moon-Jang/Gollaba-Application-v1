package kr.mj.gollaba.unit.poll.factory;

import kr.mj.gollaba.poll.entity.Option;
import kr.mj.gollaba.poll.entity.Voter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class OptionFactory {

    public static final Long TEST_ID = 1L;
    public static final String TEST_DESCRIPTION = "테스트_투표항목";

    public static Option create(List<Voter> voters) {
        Option option = Option.builder()
                .description(TEST_DESCRIPTION)
                .build();

        if (voters != null) {
            voters.stream()
                    .forEach(el -> el.vote(option));
        }

        return option;
    }

    public static Option createWithId(int sequence, List<Voter> voters) {
        Option option =  Option.builder()
                .id(sequence + 10L)
                .description(TEST_DESCRIPTION)
                .build();

        if (voters != null) {
            voters.stream()
                    .forEach(el -> el.vote(option));
        }

        return option;
    }

    public static List<Option> createList() {
        return IntStream.range(0, 4)
                .mapToObj(i -> OptionFactory.create(null))
                .collect(Collectors.toList());
    }

    public static List<Option> createListWithId() {
        return IntStream.range(0, 4)
                .mapToObj(i -> createWithId(i, null))
                .collect(Collectors.toList());
    }

}
