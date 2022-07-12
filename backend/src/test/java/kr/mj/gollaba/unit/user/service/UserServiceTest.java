package kr.mj.gollaba.unit.user.service;

import kr.mj.gollaba.exception.GollabaErrorCode;
import kr.mj.gollaba.exception.GollabaException;
import kr.mj.gollaba.unit.common.ServiceTest;
import kr.mj.gollaba.user.repository.UserRepository;
import kr.mj.gollaba.unit.user.factory.UserFactory;
import kr.mj.gollaba.user.dto.SignupRequest;
import kr.mj.gollaba.user.dto.SignupResponse;
import kr.mj.gollaba.user.entity.User;
import kr.mj.gollaba.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


class UserServiceTest extends ServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Spy
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @DisplayName("save 메서드는")
    @Nested
    class save {

        @DisplayName("현재 등록되지 않은 회원일 경우")
        @Nested
        class saveWhenCanRegister {

            @DisplayName("정상 응답객체를 반환한다.")
            @Test
            public void return_response() throws Exception {
                //given
                given(userRepository.existsByUniqueId(anyString()))
                        .willReturn(false);
                given(userRepository.save(any(User.class)))
                        .willReturn(UserFactory.createWithId());

                SignupRequest request = new SignupRequest();
                request.setId(UserFactory.TEST_UNIQUE_ID);
                request.setNickName(UserFactory.TEST_NICK_NAME);
                request.setPassword(UserFactory.TEST_PASSWORD);

                //when
                SignupResponse response =  userService.save(request);

                //then
                assertThat(response.getUserId()).isEqualTo(UserFactory.TEST_ID);
                verify(userRepository, times(1)).existsByUniqueId(anyString());
                verify(userRepository, times(1)).save(any(User.class));
            }
        }

        @DisplayName("이미 등록된 아이디일 경우")
        @Nested
        class saveWhenDuplicate {

            @DisplayName("중복 회원 에러가 발생한다.")
            @Test
            public void error_by_exist_user() throws Exception {
                //given
                given(userRepository.existsByUniqueId(anyString()))
                        .willReturn(true);

                SignupRequest request = new SignupRequest();
                request.setId(UserFactory.TEST_UNIQUE_ID);
                request.setNickName(UserFactory.TEST_NICK_NAME);
                request.setPassword(UserFactory.TEST_PASSWORD);

                //when then
                assertThatThrownBy(() -> userService.save(request))
                        .as(GollabaErrorCode.ALREADY_EXIST_USER.getDescription())
                        .isInstanceOf(GollabaException.class);

                verify(userRepository, times(1)).existsByUniqueId(eq(UserFactory.TEST_UNIQUE_ID));
            }
        }

        @DisplayName("이미 등록된 닉네임일 경우")
        @Nested
        class saveWhenDuplicateNickName {

            @DisplayName("중복 회원 에러가 발생한다.")
            @Test
            public void error_by_exist_user() throws Exception {
                //given
                given(userRepository.existsByUniqueId(anyString()))
                        .willReturn(false);
                given(userRepository.existsByNickName(anyString()))
                        .willReturn(true);

                SignupRequest request = new SignupRequest();
                request.setId(UserFactory.TEST_UNIQUE_ID);
                request.setNickName(UserFactory.TEST_NICK_NAME);
                request.setPassword(UserFactory.TEST_PASSWORD);

                //when then
                assertThatThrownBy(() -> userService.save(request))
                        .as(GollabaErrorCode.ALREADY_EXIST_USER.getDescription())
                        .isInstanceOf(GollabaException.class);

                verify(userRepository, times(1)).existsByUniqueId(eq(UserFactory.TEST_UNIQUE_ID));
                verify(userRepository, times(1)).existsByNickName(eq(UserFactory.TEST_NICK_NAME));
            }
        }
    }

}