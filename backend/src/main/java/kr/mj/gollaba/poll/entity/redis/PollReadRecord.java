package kr.mj.gollaba.poll.entity.redis;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RedisHash(value = "pollRecord", timeToLive = 60 * 5)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PollReadRecord {

    @Id
    private Long pollId;

    private Set<String> ipAddressList = new HashSet<>();

    public PollReadRecord(Long pollId) {
        this.pollId = pollId;
    }

    public static PollReadRecord of(Long pollId) {
        return new PollReadRecord(pollId);
    }

    public void add(String ipAddress) {
        ipAddressList.add(ipAddress);
    }

    public boolean isAlreadyRead(String ipAddress) {
        return ipAddressList.contains(ipAddress);
    }

}
