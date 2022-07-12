package kr.mj.gollaba.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import kr.mj.gollaba.exception.GollabaErrorCode;
import kr.mj.gollaba.exception.GollabaException;
import kr.mj.gollaba.security.JwtTokenProvider;
import kr.mj.gollaba.security.PrincipalDetails;
import kr.mj.gollaba.security.dto.LoginRequest;
import kr.mj.gollaba.security.dto.LoginResponse;
import kr.mj.gollaba.security.repository.UserTokenRepository;
import kr.mj.gollaba.user.entity.User;
import kr.mj.gollaba.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

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
        userTokenRepository.deleteByRefreshToken(refreshToken);
    }

    @Override
    public UserDetails loadUserByUsername(String uniqueId) throws UsernameNotFoundException {
        User user = userRepository.findByUniqueId(uniqueId)
                .orElseThrow(() -> new GollabaException(GollabaErrorCode.NOT_EXIST_USER_BY_UNIQUE_ID));

        return new PrincipalDetails(user);
    }

}
