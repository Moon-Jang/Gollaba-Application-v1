package kr.mj.gollaba.auth.service;

import kr.mj.gollaba.auth.JwtTokenProvider;
import kr.mj.gollaba.auth.PrincipalDetails;
import kr.mj.gollaba.auth.dto.LoginRequest;
import kr.mj.gollaba.auth.dto.LoginResponse;
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

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final UserTokenRepository userTokenRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUniqueId(request.getId())
                .orElseThrow(() -> new GollabaException(GollabaErrorCode.FAIL_LOGIN));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new GollabaException(GollabaErrorCode.FAIL_LOGIN);
        }

        String accessToken = jwtTokenProvider.createAccessToken(user.getUniqueId(), user.getNickName());
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
    public UserDetails loadUserByUsername(String uniqueId) throws UsernameNotFoundException {
        User user = userRepository.findByUniqueId(uniqueId)
                .orElseThrow(() -> new GollabaException(GollabaErrorCode.NOT_EXIST_USER_BY_UNIQUE_ID));

        return new PrincipalDetails(user);
    }


}
