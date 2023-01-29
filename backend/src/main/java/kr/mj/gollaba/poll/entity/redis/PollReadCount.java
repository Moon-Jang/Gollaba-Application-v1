package kr.mj.gollaba.poll.entity.redis;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;


@RedisHash(value = "pollReadCount", timeToLive = 60 * 10)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PollReadCount {

    @Id
    private Long pollId;

    private Integer readCount = 0;

    public PollReadCount(Long pollId, Integer readCount) {
        this.pollId = pollId;
        this.readCount = readCount;
    }

    public static PollReadCount of(Long pollId, Integer readCount) {
        return new PollReadCount(pollId, readCount);
    }

    public void addCount() {
        readCount++;
    }

}
