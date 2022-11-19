package kr.mj.gollaba.unit.user.factory;

import kr.mj.gollaba.auth.entity.UserProvider;
import kr.mj.gollaba.auth.types.ProviderType;
import kr.mj.gollaba.user.entity.User;
import org.springframework.test.util.ReflectionTestUtils;

public class UserProviderFactory {

    public static final Long TEST_ID = 1L;
    public static final ProviderType TEST_PROVIDER_TYPE = ProviderType.KAKAO;
    public static final String TEST_PROVIDER_ID = "TEST_12345";

    public static UserProvider create(User user) {
        return UserProvider.builder()
                .providerId(TEST_PROVIDER_ID)
                .providerType(TEST_PROVIDER_TYPE)
                .user(user)
                .build();
    }

    public static UserProvider createWithId(User user) {
        var userProvider = UserProvider.builder()
                .providerId(TEST_PROVIDER_ID)
                .providerType(TEST_PROVIDER_TYPE)
                .user(user)
                .build();

        ReflectionTestUtils.setField(userProvider, "id", TEST_ID);
        return userProvider;
    }

    public static UserProvider createWithId(User user, long id) {
        var userProvider = UserProvider.builder()
                .providerId(TEST_PROVIDER_ID)
                .providerType(TEST_PROVIDER_TYPE)
                .user(user)
                .build();

        ReflectionTestUtils.setField(userProvider, "id", id);
        return userProvider;
    }

}
