package kr.mj.gollaba.poll.repository;

import kr.mj.gollaba.poll.entity.PollDailyStats;
import kr.mj.gollaba.poll.entity.PollStats;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PollDailyStatsRepository extends JpaRepository<PollDailyStats, Long> {

    @Query(
        "SELECT pds \n" +
            "FROM PollDailyStats pds \n" +
            "WHERE pds.date = :date \n" +
            "ORDER BY " +
            "pds.dailyVoteCount DESC," +
            "pds.dailyReadCount DESC," +
            "pds.dailyFavoritesCount DESC," +
            "pds.poll.id DESC"
    )
    List<PollDailyStats> findTrendingPolls(LocalDate date, Pageable pageable);

}
