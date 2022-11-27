package kr.mj.gollaba.auth.service;

import kr.mj.gollaba.auth.JwtTokenProvider;
import kr.mj.gollaba.auth.PrincipalDetails;
import kr.mj.gollaba.auth.dto.LoginRequest;
import kr.mj.gollaba.auth.dto.LoginResponse;
import kr.mj.gollaba.auth.repository.UserProviderRepository;
import kr.mj.gollaba.auth.repository.UserTokenRepository;
import kr.mj.gollaba.exception.GollabaErrorCode;
import kr.mj.gollaba.exception.GollabaException;
import kr.mj.gollaba.user.entity.User;
import kr.mj.gollaba.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final UserTokenRepository userTokenRepository;
    private final UserProviderRepository userProviderRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new GollabaException(GollabaErrorCode.FAIL_LOGIN));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new GollabaException(GollabaErrorCode.FAIL_LOGIN);
        }

        String accessToken = jwtTokenProvider.createAccessToken(user);
        String refreshToken = jwtTokenProvider.createRefreshToken();

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public void logout(String refreshToken) {
        if (!userTokenRepository.existsByRefreshToken(refreshToken)) {
            throw new GollabaException(GollabaErrorCode.NOT_EXIST_REFRESH_TOKEN);
        }

        userTokenRepository.deleteByRefreshToken(refreshToken);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new GollabaException(GollabaErrorCode.NOT_EXIST_USER_BY_UNIQUE_ID));

        return new PrincipalDetails(user);
    }

    @Transactional
    public void withdrawKaKao(String providerId) {
        var userProvider = userProviderRepository.findByProviderId(providerId).orElseThrow();
        var user = userProvider.getUser();

        userProviderRepository.delete(userProvider);

        if (userProviderRepository.countByUserId(user.getId()) < 1) {
            userRepository.delete(user);
        }
    }
}
