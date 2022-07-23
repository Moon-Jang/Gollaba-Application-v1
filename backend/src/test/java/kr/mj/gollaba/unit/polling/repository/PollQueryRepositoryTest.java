package kr.mj.gollaba.unit.polling.repository;

import kr.mj.gollaba.poll.entity.Option;
import kr.mj.gollaba.poll.entity.Poll;
import kr.mj.gollaba.poll.repository.PollQueryRepository;
import kr.mj.gollaba.poll.repository.PollRepository;
import kr.mj.gollaba.unit.common.RepositoryTest;
import kr.mj.gollaba.unit.polling.factory.PollingFactory;
import kr.mj.gollaba.unit.polling.factory.optionFactory;
import kr.mj.gollaba.unit.user.factory.UserFactory;
import kr.mj.gollaba.user.entity.User;
import kr.mj.gollaba.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PollQueryRepositoryTest extends RepositoryTest {

    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private PollQueryRepository pollQueryRepository;

    @Autowired
    private UserRepository userRepository;

    @DisplayName("투표 객체 조회")
    @Test
    void findById() {
        //given
        User user = UserFactory.create();
        userRepository.save(user);
        List<Option> options = optionFactory.createList();
        Poll poll = PollingFactory.create(user, options);

        //when
        Poll savedPoll = pollRepository.save(poll);

        //flushAndClear();

        Poll foundPoll = pollQueryRepository.findById(savedPoll.getId());

        //then
        assertThat(savedPoll.getTitle()).isEqualTo(foundPoll.getTitle());
    }
}