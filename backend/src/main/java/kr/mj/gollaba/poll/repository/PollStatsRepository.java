package kr.mj.gollaba.poll.repository;

import kr.mj.gollaba.poll.entity.PollStats;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PollStatsRepository extends JpaRepository<PollStats, Long> {

    List<PollStats> findAllByPollIdIn(Iterable<Long> pollIds);

    Optional<PollStats> findByPollId(Long pollId);

}
