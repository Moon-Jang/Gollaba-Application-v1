package kr.mj.polling.security.service;

import kr.mj.polling.exception.PollingAppErrorCode;
import kr.mj.polling.exception.PollingAppException;
import kr.mj.polling.security.JwtTokenProvider;
import kr.mj.polling.security.dto.LoginRequest;
import kr.mj.polling.user.entity.User;
import kr.mj.polling.unit.repository.UserRepository;
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
                .orElseThrow(() -> new PollingAppException(PollingAppErrorCode.NOT_EXIST_USER_BY_UNIQUE_ID));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new PollingAppException(PollingAppErrorCode.NOT_MATCHED_PASSWORD);
        }

        return jwtTokenProvider.createToken(request.getEmail());
    }

}
