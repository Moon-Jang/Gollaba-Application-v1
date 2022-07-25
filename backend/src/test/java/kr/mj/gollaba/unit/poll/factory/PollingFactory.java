package kr.mj.gollaba.unit.poll.factory;

import kr.mj.gollaba.poll.entity.Option;
import kr.mj.gollaba.poll.entity.Poll;
import kr.mj.gollaba.poll.type.PollingResponseType;
import kr.mj.gollaba.user.entity.User;

import java.util.List;

public class PollingFactory {

    public static final Long TEST_ID = 1L;
    public static final String TEST_TITLE = "테스트 투표";
    public static final String TEST_CREATOR_NAME = "강남";
    public static final PollingResponseType TEST_RESPONSE_TYPE = PollingResponseType.SINGLE;
    public static final Boolean TEST_IS_BALLOT = false;

    public static Poll create(User user, List<Option> options) {
        Poll poll = Poll.builder()
                .title(TEST_TITLE)
                .creatorName(TEST_CREATOR_NAME)
                .responseType(TEST_RESPONSE_TYPE)
                .isBallot(TEST_IS_BALLOT)
                .build();

        poll.registerCreator(user);
        options.stream()
                .forEach(el -> poll.addoption(el));

        return poll;
    }

    public static Poll createWithId(User user, List<Option> options) {
        Poll poll = Poll.builder()
                .id(TEST_ID)
                .title(TEST_TITLE)
                .creatorName(TEST_CREATOR_NAME)
                .responseType(TEST_RESPONSE_TYPE)
                .isBallot(TEST_IS_BALLOT)
                .build();

        poll.registerCreator(user);
        options.stream()
                .forEach(el -> poll.addoption(el));

        return poll;
    }

}
