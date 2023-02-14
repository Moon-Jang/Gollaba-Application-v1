package kr.mj.gollaba.unit.user.factory;

import kr.mj.gollaba.user.entity.User;
import kr.mj.gollaba.user.type.UserRoleType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

public class UserFactory {

    public static final Long TEST_ID = 1L;
    public static final String TEST_EXIST_EMAIL = "testid123299@test.com";
    public static final String TEST_EMAIL = "test@test.com";
    public static final String TEST_EXIST_UNIQUE_ID = "testid123299";
    public static final String TEST_UNIQUE_ID = "testid12328502";
    public static final String TEST_NICK_NAME = "홍길동99";
    public static final String TEST_PASSWORD = "test1234*";
    public static final String TEST_PROFILE_IMAGE_URL = "TEST_PROFILE_IMAGE_URL";
    public static final String TEST_BACKGROUND_IMAGE_URL = "TEST_BACKGROUND_IMAGE_URL";
    public static final UserRoleType TEST_USER_ROLE_TYPE = UserRoleType.ROLE_USER;
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static User create() {
        return User.builder()
                .email(TEST_EMAIL)
                .nickName(TEST_NICK_NAME)
                .password(passwordEncoder.encode(TEST_PASSWORD))
                .profileImageUrl(TEST_PROFILE_IMAGE_URL)
                .backgroundImageUrl(TEST_BACKGROUND_IMAGE_URL)
                .userRole(TEST_USER_ROLE_TYPE)
                .build();
    }

    public static User create(String email) {
        return User.builder()
                .email(email)
                .nickName(TEST_NICK_NAME)
                .password(passwordEncoder.encode(TEST_PASSWORD))
                .profileImageUrl(TEST_PROFILE_IMAGE_URL)
                .backgroundImageUrl(TEST_BACKGROUND_IMAGE_URL)
                .userRole(TEST_USER_ROLE_TYPE)
                .build();
    }

    public static User createWithId() {
        var user = User.builder()
                .email(TEST_EMAIL)
                .nickName(TEST_NICK_NAME)
                .password(passwordEncoder.encode(TEST_PASSWORD))
                .profileImageUrl(TEST_PROFILE_IMAGE_URL)
                .backgroundImageUrl(TEST_BACKGROUND_IMAGE_URL)
                .userRole(TEST_USER_ROLE_TYPE)
                .build();

        ReflectionTestUtils.setField(user, "id", TEST_ID);
        return user;
    }

    public static User createWithId(Long id) {
        var user =  User.builder()
                .email(TEST_EMAIL)
                .nickName(TEST_NICK_NAME)
                .password(passwordEncoder.encode(TEST_PASSWORD))
                .profileImageUrl(TEST_PROFILE_IMAGE_URL)
                .backgroundImageUrl(TEST_BACKGROUND_IMAGE_URL)
                .userRole(TEST_USER_ROLE_TYPE)
                .build();

        ReflectionTestUtils.setField(user, "id", id);
        return user;
    }

}
