package kr.mj.gollaba.poll.repository;

import kr.mj.gollaba.poll.entity.Poll;
import kr.mj.gollaba.poll.entity.PollView;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PollViewRepository extends JpaRepository<PollView, Long> {

}
