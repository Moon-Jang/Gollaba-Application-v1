package kr.mj.gollaba.poll.repository;

import kr.mj.gollaba.poll.entity.redis.PollReadCount;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PollReadCountRepository extends CrudRepository<PollReadCount, Long> {
}
