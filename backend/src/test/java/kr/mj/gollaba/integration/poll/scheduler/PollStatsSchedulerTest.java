package kr.mj.gollaba.integration.poll.scheduler;

import kr.mj.gollaba.poll.dto.FindAllPollRequest;
import kr.mj.gollaba.poll.service.PollService;
import kr.mj.gollaba.poll.service.PollStatsService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

@Disabled
@SpringBootTest
@ActiveProfiles("dev")
public class PollStatsSchedulerTest {

    @Autowired
    private PollStatsService pollStatsService;

    @Autowired
    private PollService pollService;

    @DisplayName("test")
    @Test
    void test() {
        //given
        var request = new FindAllPollRequest();
        request.setTitle("test");

        //when
        pollService.findAll(request);

        //then
    }

}
