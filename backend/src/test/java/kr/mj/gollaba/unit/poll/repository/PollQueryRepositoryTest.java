package kr.mj.gollaba.unit.poll.repository;

import kr.mj.gollaba.poll.dto.PollQueryFilter;
import kr.mj.gollaba.poll.entity.Poll;
import kr.mj.gollaba.poll.repository.PollQueryRepository;
import kr.mj.gollaba.unit.common.RepositoryTest;
import kr.mj.gollaba.unit.poll.factory.PollFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PollQueryRepositoryTest extends RepositoryTest {

    @Autowired
    private PollQueryRepository pollQueryRepository;

    //@Sql("classpath:sql/poll-create.sql")
//    @BeforeAll
//    public void init() {}

    @DisplayName("투표 객체 전체 조회 by filter")
    @Test
    void findAll() {
        //given
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
        PollQueryFilter filter = PollQueryFilter.builder()
                .title(PollFactory.TEST_TITLE + 1)
                .limit(15)
                .offset(0)
                .build();

        //when
        List<Poll> foundPolls = pollQueryRepository.findAll(filter);

        //then
        assertThat(foundPolls.size()).isEqualTo(15);
    }

    @DisplayName("투표 객체 조회 by Id")
    @Test
    void findById() {
        //given
        Long pollId = PollFactory.TEST_ID;

        //when
        Poll foundPoll = pollQueryRepository.findById(pollId);

        //then
        assertThat(foundPoll.getId()).isEqualTo(pollId);
        assertThat(foundPoll.getTitle()).isEqualTo(PollFactory.TEST_TITLE + 1);
        assertThat(foundPoll.getOptions().size()).isEqualTo(4);
    }

}