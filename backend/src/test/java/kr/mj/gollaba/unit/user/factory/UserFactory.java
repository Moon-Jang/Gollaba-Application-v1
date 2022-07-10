package kr.mj.gollaba.unit.user.factory;

import kr.mj.gollaba.user.entity.User;
import kr.mj.gollaba.user.type.UserRoleType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserFactory {

    public static final Long TEST_ID = 1L;
    public static final String TEST_UNIQUE_ID = "testUserId";
    public static final String TEST_NICK_NAME = "홍길동";
    public static final String TEST_PASSWORD = "test1234*";
    public static final String TEST_REG_NO = "860824-1655068";
    public static final UserRoleType TEST_USER_ROLE_TYPE = UserRoleType.ROLE_USER;
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static User create() {
        return User.builder()
                .uniqueId(TEST_UNIQUE_ID)
                .nickName(TEST_NICK_NAME)
                .password(passwordEncoder.encode(TEST_PASSWORD))
                .userRole(TEST_USER_ROLE_TYPE)
                .build();
    }

    public static User create(String uniqueId) {
        return User.builder()
                .uniqueId(uniqueId)
                .nickName(TEST_NICK_NAME)
                .password(passwordEncoder.encode(TEST_PASSWORD))
                .userRole(TEST_USER_ROLE_TYPE)
                .build();
    }

    public static User createWithId() {
        return User.builder()
                .id(TEST_ID)
                .uniqueId(TEST_UNIQUE_ID)
                .nickName(TEST_NICK_NAME)
                .password(passwordEncoder.encode(TEST_PASSWORD))
                .userRole(TEST_USER_ROLE_TYPE)
                .build();
    }

}
