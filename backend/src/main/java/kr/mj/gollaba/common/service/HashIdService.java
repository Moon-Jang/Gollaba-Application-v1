package kr.mj.gollaba.common.service;

import kr.mj.gollaba.exception.GollabaErrorCode;
import kr.mj.gollaba.exception.GollabaException;
import lombok.RequiredArgsConstructor;
import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class HashIdService {

    private final Hashids hashids;

    public HashIdService(@Value("${security.hash-ids.salt}") String salt,
                         @Value("${security.hash-ids.min-length}") int minLength) {
        this.hashids = new Hashids(salt, minLength);
    }

    public String encode(long id) {
        return hashids.encode(id);
    }

    public long decode(String hash) {
        long[] result = hashids.decode(hash);

        if (result.length < 1) {
            throw new GollabaException(GollabaErrorCode.FAIL_TO_DECODE_HASH_ID);
        }

        return result[0];
    }
}
