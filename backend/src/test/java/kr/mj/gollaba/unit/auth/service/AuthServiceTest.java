package kr.mj.gollaba.unit.auth.service;

import kr.mj.gollaba.auth.PrincipalDetails;
import kr.mj.gollaba.exception.GollabaErrorCode;
import kr.mj.gollaba.exception.GollabaException;
import kr.mj.gollaba.auth.JwtTokenProvider;
import kr.mj.gollaba.auth.dto.LoginRequest;
import kr.mj.gollaba.auth.dto.LoginResponse;
import kr.mj.gollaba.auth.repository.UserTokenRepository;
import kr.mj.gollaba.auth.service.AuthService;
import kr.mj.gollaba.unit.auth.factory.UserTokenFactory;
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
import org.springframework.security.core.userdetails.UserDetails;
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

            @DisplayName("예외가 발생한다.")
            @Test
            public void return_error_response() throws Exception {
                //given
                given(userRepository.findByEmail(anyString()))
                        .willReturn(Optional.empty());

                LoginRequest request = new LoginRequest();
                request.setEmail(UserFactory.TEST_EMAIL);
                request.setPassword(UserFactory.TEST_PASSWORD);

                //when then
                assertThatThrownBy(() -> authService.login(request))
                        .as(GollabaErrorCode.NOT_EXIST_USER_BY_UNIQUE_ID.getDescription())
                        .isInstanceOf(GollabaException.class);

                verify(userRepository, times(1)).findByEmail(anyString());
            }
        }

        @DisplayName("비밀번호가 틀릴 경우")
        @Nested
        class not_match_password {

            @DisplayName("예외가 발생한다.")
            @Test
            public void occur_exception() throws Exception {
                //given
                given(userRepository.findByEmail(anyString()))
                        .willReturn(Optional.empty());

                LoginRequest request = new LoginRequest();
                request.setEmail(UserFactory.TEST_EMAIL);
                request.setPassword(UserFactory.TEST_PASSWORD);

                //when then
                assertThatThrownBy(() -> authService.login(request))
                        .as(GollabaErrorCode.NOT_EXIST_USER_BY_UNIQUE_ID.getDescription())
                        .isInstanceOf(GollabaException.class);

                verify(userRepository, times(1)).findByEmail(anyString());
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
                given(userRepository.findByEmail(anyString()))
                        .willReturn(Optional.of(user));
                given(jwtTokenProvider.createAccessToken(any(User.class)))
                        .willReturn(UserTokenFactory.TEST_ACCESS_TOKEN);
                given(jwtTokenProvider.createRefreshToken())
                        .willReturn(UserTokenFactory.TEST_REFRESH_TOKEN);

                LoginRequest request = new LoginRequest();
                request.setEmail(UserFactory.TEST_EMAIL);
                request.setPassword(UserFactory.TEST_PASSWORD);

                //when
                LoginResponse response = authService.login(request);

                //then
                assertThat(response.getAccessToken()).isNotBlank();
                assertThat(response.getRefreshToken()).isNotBlank();

                verify(userRepository, times(1)).findByEmail(eq(UserFactory.TEST_EMAIL));
                verify(passwordEncoder, times(1)).matches(eq(UserFactory.TEST_PASSWORD), eq(user.getPassword()));
                verify(jwtTokenProvider, times(1)).createAccessToken(eq(user));
                verify(jwtTokenProvider, times(1)).createRefreshToken();
            }
        }

    }

    @DisplayName("logout 메서드는")
    @Nested
    class logout {

        @DisplayName("존재하지 않는 리프레시 토큰일 경우")
        @Nested
        class not_exist_refresh_token {

            @DisplayName("예외가 발생한다.")
            @Test
            public void occur_exception() throws Exception {
                //given
                given(userTokenRepository.existsByRefreshToken(anyString()))
                        .willReturn(false);

                String refreshToken = UserTokenFactory.TEST_REFRESH_TOKEN;

                //when then
                assertThatThrownBy(() -> authService.logout(refreshToken))
                        .as(GollabaErrorCode.NOT_EXIST_REFRESH_TOKEN.getDescription())
                        .isInstanceOf(GollabaException.class);

                verify(userTokenRepository, times(1)).existsByRefreshToken(eq(refreshToken));
            }
        }

        @DisplayName("존재하는 리프레시 토큰일 경우")
        @Nested
        class exist_refresh_token {

            @DisplayName("토큰 정보가 정상적으로 삭제된다.")
            @Test
            public void remove_user_toekn() throws Exception {
                //given
                given(userTokenRepository.existsByRefreshToken(anyString()))
                        .willReturn(true);

                String refreshToken = UserTokenFactory.TEST_REFRESH_TOKEN;

                //when then
                authService.logout(refreshToken);

                verify(userTokenRepository, times(1)).existsByRefreshToken(eq(refreshToken));
                verify(userTokenRepository, times(1)).deleteByRefreshToken(eq(refreshToken));
            }
        }
    }

    @DisplayName("loadUserByUsername은")
    @Nested
    class loadUserByUsername {

        @DisplayName("존재하지 않는 아이디일 경우")
        @Nested
        class not_exists_uniqueId {

            @DisplayName("익셉션이 발생한다.")
            @Test
            public void occur_exception() throws Exception {
                //given
                given(userRepository.findByEmail(anyString()))
                        .willReturn(Optional.empty());

                String uniqueId = UserFactory.TEST_EMAIL;

                //when then
                assertThatThrownBy(() -> authService.loadUserByUsername(uniqueId))
                        .as(GollabaErrorCode.NOT_EXIST_USER_BY_UNIQUE_ID.getDescription())
                        .isInstanceOf(GollabaException.class);

                verify(userRepository, times(1)).findByEmail(eq(uniqueId));
            }
        }

        @DisplayName("존재하는 아이디일 경우")
        @Nested
        class exists_uniqueId {

            @DisplayName("UserDetails 객체를 반환한다.")
            @Test
            void exist() {
                //given
                User user = UserFactory.create();
                given(userRepository.findByEmail(anyString()))
                        .willReturn(Optional.of(user));

                String uniqueId = UserFactory.TEST_EMAIL;

                //when
                UserDetails result = authService.loadUserByUsername(uniqueId);

                //then
                assertThat(result).isInstanceOf(PrincipalDetails.class);
                verify(userRepository, times(1)).findByEmail(eq(uniqueId));
            }
        }

    }
}