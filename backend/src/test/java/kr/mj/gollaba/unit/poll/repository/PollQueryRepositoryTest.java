package kr.mj.gollaba.unit.poll.repository;

import kr.mj.gollaba.poll.dto.PollQueryFilter;
import kr.mj.gollaba.poll.entity.Option;
import kr.mj.gollaba.poll.entity.Poll;
import kr.mj.gollaba.poll.repository.PollQueryRepository;
import kr.mj.gollaba.poll.repository.PollRepository;
import kr.mj.gollaba.unit.common.RepositoryTest;
import kr.mj.gollaba.unit.poll.factory.OptionFactory;
import kr.mj.gollaba.unit.poll.factory.PollFactory;
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

    @DisplayName("투표 객체 전체 조회 by filter")
    @Test
    void findAll() {
        //given
        pollRepository.saveAll(PollFactory.createList());

        flushAndClear();

        PollQueryFilter filter = PollQueryFilter.builder()
                .limit(15)
                .offset(0)
                .build();

        //when
        List<Poll> foundPolls = pollQueryRepository.findAll(filter);

        //then
        assertThat(foundPolls.size()).isEqualTo(15);
    }

    @DisplayName("투표 객체 제목 조회 by filter")
    @Test
    void findAll_title() {
        //given
        pollRepository.saveAll(PollFactory.createList());

        flushAndClear();

        PollQueryFilter filter = PollQueryFilter.builder()
                .title(PollFactory.TEST_TITLE + 1)
                .limit(15)
                .offset(0)
                .build();

        //when
        List<Poll> foundPolls = pollQueryRepository.findAll(filter);

        //then
        assertThat(foundPolls.size()).isEqualTo(1);
    }

    @DisplayName("투표 객체 조회 by Id")
    @Test
    void findById() {
        //given
        User user = UserFactory.create();
        userRepository.save(user);
        List<Option> options = OptionFactory.createList();
        Poll poll = PollFactory.create(user, options);

        //when
        Poll savedPoll = pollRepository.save(poll);

        flushAndClear();

        Poll foundPoll = pollQueryRepository.findById(savedPoll.getId());

        //then
        assertThat(savedPoll.getTitle()).isEqualTo(foundPoll.getTitle());
    }

}