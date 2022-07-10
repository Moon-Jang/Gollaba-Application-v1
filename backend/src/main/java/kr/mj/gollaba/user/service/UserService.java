package kr.mj.gollaba.user.service;

import kr.mj.gollaba.exception.GollabaErrorCode;
import kr.mj.gollaba.exception.GollabaException;
import kr.mj.gollaba.user.dto.SignupRequest;
import kr.mj.gollaba.user.dto.SignupResponse;
import kr.mj.gollaba.user.entity.User;
import kr.mj.gollaba.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SignupResponse save(SignupRequest request) {
        if (userRepository.existsByUniqueId(request.getUniqueId())) {
            throw new GollabaException(GollabaErrorCode.ALREADY_EXIST_USER);
        }

        if (userRepository.existsByNickName(request.getNickName())) {
            throw new GollabaException(GollabaErrorCode.ALREADY_EXIST_USER);
        }

        User user = request.toEntity(passwordEncoder);
        User saveResult = userRepository.save(user);

        return SignupResponse.builder()
                .userId(saveResult.getId())
                .build();
    }

}
