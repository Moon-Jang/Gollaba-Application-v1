package kr.mj.gollaba.unit.polling.repository;

import kr.mj.gollaba.poll.entity.Option;
import kr.mj.gollaba.poll.entity.Poll;
import kr.mj.gollaba.poll.repository.PollQueryRepository;
import kr.mj.gollaba.poll.repository.PollRepository;
import kr.mj.gollaba.unit.common.RepositoryTest;
import kr.mj.gollaba.unit.polling.factory.OptionFactory;
import kr.mj.gollaba.unit.polling.factory.PollingFactory;
import kr.mj.gollaba.unit.user.factory.UserFactory;
import kr.mj.gollaba.user.entity.User;
import kr.mj.gollaba.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PollRepositoryTest extends RepositoryTest {

    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private PollQueryRepository pollQueryRepository;

    @Autowired
    private UserRepository userRepository;

    @DisplayName("투표 객체 저장 - 회원")
    @Test
    void save_by_user() {
        //given
        User user = UserFactory.create();
        userRepository.save(user);
        List<Option> options = OptionFactory.createList();
        Poll poll = PollingFactory.create(user, options);

        //when
        Poll savedPoll = pollRepository.save(poll);

        flushAndClear();

        Poll foundPoll = pollQueryRepository.findById(savedPoll.getId());

        //then
        assertThat(foundPoll.getTitle()).isEqualTo(savedPoll.getTitle());
        assertThat(foundPoll.getOptions().size()).isEqualTo(savedPoll.getOptions().size());
        assertThat(foundPoll.getUser().getId()).isEqualTo(savedPoll.getUser().getId());
    }

    @DisplayName("투표 객체 저장 - 비회원")
    @Test
    void save_by_not_user() {
        //given
        List<Option> options = OptionFactory.createList();
        Poll poll = PollingFactory.create(null, options);

        //when
        Poll savedPoll = pollRepository.save(poll);

        flushAndClear();

        Poll foundPoll = pollQueryRepository.findById(savedPoll.getId());

        //then
        assertThat(foundPoll.getTitle()).isEqualTo(savedPoll.getTitle());
        assertThat(foundPoll.getOptions().size()).isEqualTo(savedPoll.getOptions().size());
        assertThat(foundPoll.getUser()).isNull();
        assertThat(foundPoll.getCreatorName()).isEqualTo(savedPoll.getCreatorName());
    }

}