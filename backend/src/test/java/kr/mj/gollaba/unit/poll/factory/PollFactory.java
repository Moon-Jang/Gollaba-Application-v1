package kr.mj.gollaba.unit.poll.factory;

import kr.mj.gollaba.poll.entity.Option;
import kr.mj.gollaba.poll.entity.Poll;
import kr.mj.gollaba.poll.type.PollingResponseType;
import kr.mj.gollaba.user.entity.User;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PollFactory {

    public static final Long TEST_ID = 1L;
    public static final String TEST_TITLE = "테스트 투표";
    public static final String TEST_CREATOR_NAME = "강남";
    public static final PollingResponseType TEST_RESPONSE_TYPE = PollingResponseType.SINGLE;
    public static final Boolean TEST_IS_BALLOT = false;
    public static final LocalDateTime TEST_ENDED_AT = LocalDateTime.now().plusDays(7L);
    public static final String TEST_IMAGE_URL = "TEST_IMAGE_URL";
    public static final Integer TEST_READ_COUNT = 0;

    public static List<Poll> createList() {
        List<Poll> polls = new ArrayList<>();

        for (int i = 0; i < 25; i++) {
            Poll poll = Poll.builder()
                    .title(TEST_TITLE + i)
                    .creatorName(TEST_CREATOR_NAME)
                    .responseType(TEST_RESPONSE_TYPE)
                    .isBallot(TEST_IS_BALLOT)
                    .endedAt(TEST_ENDED_AT)
                    .pollImageUrl(TEST_IMAGE_URL)
                    .readCount(TEST_READ_COUNT)
                    .build();

            OptionFactory.createList()
                    .stream()
                    .forEach(el -> poll.addOption(el));

            polls.add(poll);
        }

        return polls;
    }

    public static Poll create(User user, List<Option> options) {
        Poll poll = Poll.builder()
                .title(TEST_TITLE)
                .creatorName(TEST_CREATOR_NAME)
                .responseType(TEST_RESPONSE_TYPE)
                .isBallot(TEST_IS_BALLOT)
                .endedAt(TEST_ENDED_AT)
                .pollImageUrl(TEST_IMAGE_URL)
                .readCount(TEST_READ_COUNT)
                .build();

        poll.registerCreator(user);
        options.stream()
                .forEach(el -> poll.addOption(el));

        return poll;
    }

    public static Poll createWithId(User user, List<Option> options) {
        Poll poll = Poll.builder()
                .id(TEST_ID)
                .title(TEST_TITLE)
                .creatorName(TEST_CREATOR_NAME)
                .responseType(TEST_RESPONSE_TYPE)
                .isBallot(TEST_IS_BALLOT)
                .endedAt(TEST_ENDED_AT)
                .pollImageUrl(TEST_IMAGE_URL)
                .readCount(TEST_READ_COUNT)
                .build();

        poll.registerCreator(user);
        options.stream()
                .forEach(el -> poll.addOption(el));

        return poll;
    }

    public static Poll createWithIdAndBallot(User user, List<Option> options) {
        Poll poll = Poll.builder()
                .id(TEST_ID)
                .title(TEST_TITLE)
                .creatorName(TEST_CREATOR_NAME)
                .responseType(TEST_RESPONSE_TYPE)
                .isBallot(true)
                .endedAt(TEST_ENDED_AT)
                .pollImageUrl(TEST_IMAGE_URL)
                .readCount(TEST_READ_COUNT)
                .build();

        poll.registerCreator(user);
        options.stream()
                .forEach(el -> poll.addOption(el));

        return poll;
    }

}
