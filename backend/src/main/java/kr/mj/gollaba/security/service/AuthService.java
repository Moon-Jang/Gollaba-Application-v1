package kr.mj.gollaba.security.service;

import kr.mj.gollaba.exception.GollabaErrorCode;
import kr.mj.gollaba.exception.GollabaException;
import kr.mj.gollaba.security.JwtTokenProvider;
import kr.mj.gollaba.security.dto.LoginRequest;
import kr.mj.gollaba.user.entity.User;
import kr.mj.gollaba.unit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public String login(LoginRequest request) {
        User user = userRepository.findByUniqueId(request.getEmail())
                .orElseThrow(() -> new GollabaException(GollabaErrorCode.NOT_EXIST_USER_BY_UNIQUE_ID));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new GollabaException(GollabaErrorCode.NOT_MATCHED_PASSWORD);
        }

        return jwtTokenProvider.createToken(request.getEmail());
    }

}
