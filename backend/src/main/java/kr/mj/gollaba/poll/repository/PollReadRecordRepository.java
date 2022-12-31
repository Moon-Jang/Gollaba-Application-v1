package kr.mj.gollaba.poll.repository;

import kr.mj.gollaba.poll.entity.redis.PollReadCount;
import kr.mj.gollaba.poll.entity.redis.PollReadRecord;
import org.springframework.data.repository.CrudRepository;

public interface PollReadRecordRepository extends CrudRepository<PollReadRecord, Long> {
}
