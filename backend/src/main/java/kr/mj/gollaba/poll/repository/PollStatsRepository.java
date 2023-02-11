package kr.mj.gollaba.poll.repository;

import kr.mj.gollaba.poll.entity.PollStats;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PollStatsRepository extends JpaRepository<PollStats, Long> {

    List<PollStats> findAllByPollIdIn(Iterable<Long> pollIds);

    Optional<PollStats> findByPollId(Long pollId);

    @Query(
        "SELECT ps \n" +
        "FROM PollStats ps \n" +
        "ORDER BY " +
            "ps.totalVoteCount DESC," +
            "ps.totalReadCount DESC," +
            "ps.totalFavoritesCount DESC," +
            "ps.id DESC"
    )
    List<PollStats> findTopPolls(Pageable pageable);

}
