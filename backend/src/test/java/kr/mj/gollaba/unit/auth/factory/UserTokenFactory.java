package kr.mj.gollaba.unit.auth.factory;

import kr.mj.gollaba.auth.JwtTokenProvider;
import kr.mj.gollaba.auth.entity.UserToken;

public class UserTokenFactory {

    public static final String TEST_UNIQUE_ID = "testUserId";
    public static final String TEST_NICK_NAME = "홍길동";
    public static final String TEST_ACCESS_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJ1aWQiOiJ0ZXN0VXNlcklkIiwidW4iOiLtmY3quLjrj5kiLCJpYXQiOjE2NTc4MDIwNTksImV4cCI6MTY1NzgwOTI1OX0.NPaVo0jkf6MTEHXhP5uPjRr5XntfuLIQzs3ZNryXsXg";
    public static final String TEST_REFRESH_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2NTc4MDIwNTksImV4cCI6MTY1OTAxMTY1OX0.tWHc_D8eQ8Q-96M6bpKnqYBf4_qGf-v1QC8Np_AcmQo";

    public static UserToken create(JwtTokenProvider jwtTokenProvider) {
        return UserToken.builder()
                .accessToken(jwtTokenProvider.createAccessToken(TEST_UNIQUE_ID, TEST_NICK_NAME))
                .refreshToken(jwtTokenProvider.createRefreshToken())
                .build();
    }

}
