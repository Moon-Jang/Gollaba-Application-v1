package kr.mj.gollaba.config;

import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HashIdConfig {

    private final String salt;
    private final int MIN_HASH_LENGTH = 16;

    public HashIdConfig(@Value("${security.hash-ids.salt}") String salt) {
        this.salt = salt;
    }

    @Bean
    public Hashids hashids() {
        return new Hashids(salt, MIN_HASH_LENGTH);
    }
}
