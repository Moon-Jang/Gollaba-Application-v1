package kr.mj.gollaba.unit.auth.service;

import kr.mj.gollaba.exception.GollabaErrorCode;
import kr.mj.gollaba.exception.GollabaException;
import kr.mj.gollaba.auth.JwtTokenProvider;
import kr.mj.gollaba.auth.dto.LoginRequest;
import kr.mj.gollaba.auth.dto.LoginResponse;
import kr.mj.gollaba.auth.repository.UserTokenRepository;
import kr.mj.gollaba.auth.service.AuthService;
import kr.mj.gollaba.unit.common.ServiceTest;
import kr.mj.gollaba.unit.user.factory.UserFactory;
import kr.mj.gollaba.user.entity.User;
import kr.mj.gollaba.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


class AuthServiceTest extends ServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserTokenRepository userTokenRepository;

    @Spy
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @DisplayName("login 메서드는")
    @Nested
    class login {

        @DisplayName("존재하지 않는 아이디일 경우")
        @Nested
        class not_exist_id {

            @DisplayName("에러 응답을 반환한다.")
            @Test
            public void return_error_response() throws Exception {
                //given
                given(userRepository.findByUniqueId(anyString()))
                        .willReturn(Optional.empty());

                LoginRequest request = new LoginRequest();
                request.setId(UserFactory.TEST_UNIQUE_ID);
                request.setPassword(UserFactory.TEST_PASSWORD);

                //when then
                assertThatThrownBy(() -> authService.login(request))
                        .as(GollabaErrorCode.NOT_EXIST_USER_BY_UNIQUE_ID.getDescription())
                        .isInstanceOf(GollabaException.class);

                verify(userRepository, times(1)).findByUniqueId(anyString());
            }
        }

        @DisplayName("비밀번호가 틀릴 경우")
        @Nested
        class not_match_password {

            @DisplayName("에러 응답을 반환한다.")
            @Test
            public void return_error_response() throws Exception {
                //given
                given(userRepository.findByUniqueId(anyString()))
                        .willReturn(Optional.empty());

                LoginRequest request = new LoginRequest();
                request.setId(UserFactory.TEST_UNIQUE_ID);
                request.setPassword(UserFactory.TEST_PASSWORD);

                //when then
                assertThatThrownBy(() -> authService.login(request))
                        .as(GollabaErrorCode.NOT_EXIST_USER_BY_UNIQUE_ID.getDescription())
                        .isInstanceOf(GollabaException.class);

                verify(userRepository, times(1)).findByUniqueId(anyString());
            }
        }

        @DisplayName("아이디와 비밀번호가 정상일 경우")
        @Nested
        class match_all {
            @DisplayName("정상 응답을 반환한다.")
            @Test
            public void return_success_response() throws Exception {
                //given
                User user = UserFactory.createWithId();
                given(userRepository.findByUniqueId(anyString()))
                        .willReturn(Optional.of(user));
                given(jwtTokenProvider.createAccessToken(anyString(), anyString()))
                        .willReturn("Bearer sdfsdf1234");
                given(jwtTokenProvider.createRefreshToken())
                        .willReturn("Bearer sdfsdf");

                LoginRequest request = new LoginRequest();
                request.setId(UserFactory.TEST_UNIQUE_ID);
                request.setPassword(UserFactory.TEST_PASSWORD);

                //when
                LoginResponse response = authService.login(request);

                //then
                assertThat(response.getAccessToken()).isNotBlank();
                assertThat(response.getRefreshToken()).isNotBlank();

                verify(userRepository, times(1)).findByUniqueId(eq(UserFactory.TEST_UNIQUE_ID));
                verify(passwordEncoder, times(1)).matches(eq(UserFactory.TEST_PASSWORD), eq(user.getPassword()));
                verify(jwtTokenProvider, times(1)).createAccessToken(eq(UserFactory.TEST_UNIQUE_ID), eq(UserFactory.TEST_NICK_NAME));
                verify(jwtTokenProvider, times(1)).createRefreshToken();
            }
        }

    }

    @DisplayName("아이디와 비밀번호가 정상일 경우")
    @Nested
    class logout {

        @DisplayName("존재하지 않는 리프레시 토큰일 경우")
        @Nested
        class not_exist_refresh_token {

//            @Test
//            public void return_error_response() throws Exception {
//                //given
//                given(userTokenRepository.findByRefreshToken(anyString()))
//                        .willReturn(Optional.empty());
//
//                //when
//
//                //then
//            }
        }
    }

    @Test
    void loadUserByUsername() {
    }
}