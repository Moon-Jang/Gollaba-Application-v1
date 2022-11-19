package kr.mj.gollaba.poll.repository;

import kr.mj.gollaba.poll.entity.Poll;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PollRepository extends JpaRepository<Poll, Long> {

}
