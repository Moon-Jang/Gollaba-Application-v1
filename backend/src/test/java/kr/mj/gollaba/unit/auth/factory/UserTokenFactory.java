package kr.mj.gollaba.unit.auth.factory;

import kr.mj.gollaba.auth.JwtTokenProvider;
import kr.mj.gollaba.auth.entity.UserToken;

public class UserTokenFactory {

    public static final String TEST_UNIQUE_ID = "testUserId";
    public static final String TEST_NICK_NAME = "홍길동";

    public static UserToken create(JwtTokenProvider jwtTokenProvider) {
        return UserToken.builder()
                .accessToken(jwtTokenProvider.createAccessToken(TEST_UNIQUE_ID, TEST_NICK_NAME))
                .refreshToken(jwtTokenProvider.createRefreshToken())
                .build();
    }

}
