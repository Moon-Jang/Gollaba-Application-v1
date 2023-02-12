package kr.mj.gollaba.poll.scheduler;

import kr.mj.gollaba.common.Scheduler;
import kr.mj.gollaba.poll.service.PollReadCountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

@Scheduler
@Slf4j
@RequiredArgsConstructor
public class PollReadCountScheduler {

    private final PollReadCountService readCountService;

    @Scheduled(fixedDelay = 1000 * 60 * 5)
    public void saveReadCount() {
        log.info("saveReadCount start");
        readCountService.saveReadCount();
        log.info("saveReadCount end");
    }
}
