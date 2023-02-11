package kr.mj.gollaba.unit.poll.repository;

import kr.mj.gollaba.poll.entity.redis.PollReadCount;
import kr.mj.gollaba.poll.entity.redis.PollReadRecord;
import kr.mj.gollaba.poll.repository.PollReadCountRepository;
import kr.mj.gollaba.poll.repository.PollReadRecordRepository;
import kr.mj.gollaba.unit.poll.factory.PollFactory;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;

import static kr.mj.gollaba.unit.poll.factory.PollFactory.TEST_ID;
import static kr.mj.gollaba.unit.poll.factory.PollFactory.TEST_READ_COUNT;
import static kr.mj.gollaba.unit.poll.factory.VoterFactory.TEST_IP_ADDRESS;
import static org.assertj.core.api.Assertions.assertThat;

@Disabled
@SpringBootTest
@ActiveProfiles("test")
public class PollReadCountRepositoryTest {

    @Autowired
    private PollReadCountRepository pollReadCountRepository;

    @Autowired
    private PollReadRecordRepository pollReadRecordRepository;

    @Test
    void test() {
        //given
        var savedData = pollReadCountRepository.save(
            PollReadCount.of(
                PollFactory.TEST_ID,
                TEST_READ_COUNT
            )
        );


        //when
        var result = pollReadCountRepository.findById(TEST_ID);

        //then
        System.out.println(result);
        assertThat(result).isNotNull();
    }

    @Test
    void test2() {
        //given
        var entity = pollReadRecordRepository.findById(TEST_ID)
            .orElseGet(() ->
                pollReadRecordRepository.save(
                    PollReadRecord.of(TEST_ID)
                )
            );

        entity.add("123.1.1.3");
        pollReadRecordRepository.save(entity);

        //when
        var result = pollReadRecordRepository.findById(TEST_ID)
            .orElseThrow();

        //then
        System.out.println(result);
        assertThat(result).isNotNull();
    }
}
