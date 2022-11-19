package kr.mj.gollaba.integration.common;


import kr.mj.gollaba.common.service.HashIdService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HashIdServiceTest {

    private HashIdService hashIdService;
    private static final int MIN_LENGTH = 10;


    @BeforeEach
    void setUp() {
        hashIdService = new HashIdService("salt", MIN_LENGTH);
    }

    @Test
    void encode() {
        //given
        final long id = 1;

        //when
        String result = hashIdService.encode(id);

        //then
        assertThat(result.length()).isEqualTo(MIN_LENGTH);
    }

    @Test
    void decode() {
        //given
        final long id = 1;

        //when
        String hash = hashIdService.encode(id);
        final long result = hashIdService.decode(hash);

        //then
        assertThat(result).isEqualTo(id);
    }

}
