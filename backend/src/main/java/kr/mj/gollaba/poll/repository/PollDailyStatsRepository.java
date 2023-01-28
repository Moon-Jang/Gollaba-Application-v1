package kr.mj.gollaba.poll.repository;

import kr.mj.gollaba.poll.entity.PollDailyStats;
import kr.mj.gollaba.poll.entity.PollStats;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PollDailyStatsRepository extends JpaRepository<PollDailyStats, Long> {

}
