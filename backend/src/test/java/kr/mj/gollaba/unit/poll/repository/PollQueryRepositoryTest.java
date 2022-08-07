package kr.mj.gollaba.unit.poll.repository;

import kr.mj.gollaba.exception.GollabaErrorCode;
import kr.mj.gollaba.exception.GollabaException;
import kr.mj.gollaba.poll.dto.PollQueryFilter;
import kr.mj.gollaba.poll.entity.Poll;
import kr.mj.gollaba.poll.repository.PollQueryRepository;
import kr.mj.gollaba.unit.common.RepositoryTest;
import kr.mj.gollaba.unit.poll.factory.PollFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PollQueryRepositoryTest extends RepositoryTest {

    @Autowired
    private PollQueryRepository pollQueryRepository;

    @DisplayName("투표 count 조회")
    @Test
    void findAllCount() {
        //given
        PollQueryFilter filter = PollQueryFilter.builder()
                .title(PollFactory.TEST_TITLE + 1)
                .build();

        //when
        long result = pollQueryRepository.findAllCount(filter);

        //then
        assertThat(result).isEqualTo(62);
    }

    @DisplayName("투표 해당 필터에 맞는 id 조회")
    @Test
    void findIds() {
        //given
        PollQueryFilter filter = PollQueryFilter.builder()
                .title(PollFactory.TEST_TITLE + 1)
                .offset(0)
                .limit(15)
                .build();

        //when
        List<Long> foundIds = pollQueryRepository.findIds(filter);

        //then
        assertThat(foundIds.size()).isEqualTo(15L);
    }

    @DisplayName("투표 객체 전체 조회")
    @Test
    void findAll() {
        //given
        PollQueryFilter filter = PollQueryFilter.builder()
                .limit(15)
                .offset(0)
                .build();

        //when
        List<Long> ids = pollQueryRepository.findIds(filter);
        List<Poll> foundPolls = pollQueryRepository.findAll(ids);

        //then
        assertThat(foundPolls.size()).isEqualTo(15);
    }

    @DisplayName("투표 객체 조회 by 투표 제목")
    @Test
    void findAll_title() {
        //given
        String queryTitle = PollFactory.TEST_TITLE + 1;
        PollQueryFilter filter = PollQueryFilter.builder()
                .title(queryTitle)
                .limit(100)
                .offset(0)
                .build();

        //when
        List<Long> ids = pollQueryRepository.findIds(filter);
        List<Poll> foundPolls = pollQueryRepository.findAll(ids);

        //then
        boolean isAllMatch = foundPolls.stream()
                .allMatch(el -> el.getTitle().contains(queryTitle));
        assertThat(isAllMatch).isTrue();
    }

    @DisplayName("투표 객체 조회 by Id")
    @Test
    void findById() {
        //given
        Long pollId = PollFactory.TEST_ID;

        //when
        Poll foundPoll = pollQueryRepository.findById(pollId)
                .orElseThrow(() -> new GollabaException(GollabaErrorCode.NOT_EXIST_POLL));

        //then
        assertThat(foundPoll.getId()).isEqualTo(pollId);
        assertThat(foundPoll.getTitle()).isEqualTo(PollFactory.TEST_TITLE + 1);
        assertThat(foundPoll.getOptions().size()).isEqualTo(4);
    }

}