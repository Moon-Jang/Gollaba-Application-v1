package kr.mj.gollaba.unit.security.factory;

import kr.mj.gollaba.security.JwtTokenProvider;
import kr.mj.gollaba.security.entity.UserToken;
import kr.mj.gollaba.user.entity.User;
import kr.mj.gollaba.user.type.UserRoleType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
