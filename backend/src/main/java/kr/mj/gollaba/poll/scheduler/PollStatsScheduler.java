package kr.mj.gollaba.poll.scheduler;

import kr.mj.gollaba.common.Scheduler;
import kr.mj.gollaba.poll.service.PollReadCountService;
import kr.mj.gollaba.poll.service.PollStatsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Scheduler
@Slf4j
@RequiredArgsConstructor
public class PollStatsScheduler {
    private final PollStatsService pollStatsService;

    @Scheduled(cron = "30 1 3 * * *")
    public void produceStats() {
        log.info("produceStats start");
        pollStatsService.produceStats(LocalDate.now());
        log.info("produceStats end");
    }
}
