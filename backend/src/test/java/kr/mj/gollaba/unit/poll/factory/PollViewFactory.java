package kr.mj.gollaba.unit.poll.factory;

import kr.mj.gollaba.poll.entity.Option;
import kr.mj.gollaba.poll.entity.Poll;
import kr.mj.gollaba.poll.entity.PollView;
import kr.mj.gollaba.poll.entity.Voter;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PollViewFactory {

    public static final Long TEST_ID = 1L;
    public static final String TEST_IP_ADDRESS = "0.0.0.0";

    public static PollView create(Poll poll) {
        var pollView = PollView.builder()
            .poll(poll)
            .ipAddress(TEST_IP_ADDRESS)
            .build();

        return pollView;
    }

    public static PollView createWithId(Poll poll) {
        return createWithId(poll, TEST_ID);
    }

    public static PollView createWithId(Poll poll, long id) {
        var pollView = PollView.builder()
            .poll(poll)
            .ipAddress(TEST_IP_ADDRESS)
            .build();

        ReflectionTestUtils.setField(pollView, "id", id);

        return pollView;
    }
}
